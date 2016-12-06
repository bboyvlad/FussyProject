package jd.controllers;


import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import jd.Util.AppUtils;
import jd.persistence.model.Paymethod;
import jd.persistence.dto.RegisterPayTransactionDTO;
import jd.persistence.model.Tranpay;
import jd.persistence.repository.PaymethodRepository;
import jd.persistence.repository.TranpayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eduardom on 9/6/16.
 */
@Controller
@RequestMapping(value="/payments")
public class PaymethodController {

    PaymethodRepository paymethodRepository;

    Date fechaActual = new Date();
    AppUtils utils = new AppUtils();

    @Autowired
    TranpayRepository tranpayRepository;

    @Autowired
    public PaymethodController(PaymethodRepository paymethodRepository){
        this.paymethodRepository = paymethodRepository;
    }

    @RequestMapping( method = RequestMethod.POST)
    public @ResponseBody Object addpayment(@RequestBody Paymethod payadd){

        try{
            Paymethod pay= new Paymethod();
            pay.setPaytype(payadd.getPaytype());
            pay.setPaystatus(payadd.getPaystatus());
            pay.setPayacctnum(payadd.getPayacctnum());
            pay.setPaycardname(payadd.getPaycardname());
            pay.setPaycardseccode(payadd.getPaycardseccode());
            pay.setPaycreate(payadd.getPaycreate());
            pay.setPayvalid(payadd.getPayvalid());
            pay.setPaylocked(0.00);
            pay.setPaycardrandomcode(payadd.getPaycardrandomcode());

            return paymethodRepository.save(pay);
        }catch(Exception e){
            return e.getCause();
        }
    }

    @RequestMapping(value="/{card_id}/refill",  method = RequestMethod.POST)
    public @ResponseBody Object refillCard(@RequestBody RegisterPayTransactionDTO refill,
                                           @PathVariable Long card_id){

        //Tarjeta a Debitar
        Paymethod paySelected = paymethodRepository.findOne(refill.getIdpaymethod());

        //JdCard para Acreditar
        Paymethod cardToRefill = paymethodRepository.findOne(card_id);


        switch (paySelected.getPaytype()){

            case "CARD":

                Stripe.apiKey = utils.getStripeApiKey();

                int amount = (int) (refill.getAmount() * 100);
                Map<String, Object> chargeParams = new HashMap<String, Object>();
                chargeParams.put("amount", amount); // Amount in cents
                chargeParams.put("currency", "usd");
                chargeParams.put("source", utils.getStripeToken(paySelected,refill.getPay_ccsec()));
                chargeParams.put("description","REFILL JDCARD #"+cardToRefill.getPayacctnum().substring(4));

                try {
                    Charge charge = Charge.create(chargeParams);

                    if ((charge.getId() != null) && (!charge.getId().equals(""))) {
                        Tranpay etpm = new Tranpay();
                        etpm.setTrantype("STRIPE");
                        etpm.setTranamount((Double.parseDouble(String.valueOf(charge.getAmount()))/100));
                        etpm.setTrandate(fechaActual);
                        etpm.setTrandate(fechaActual);
                        etpm.setTrantoken(charge.getId());
                        etpm.setTranstatus(charge.getStatus().toUpperCase());

                        cardToRefill.getTransactionspayments().add(etpm);
                        return paymethodRepository.save(cardToRefill);
                    }

                } catch (AuthenticationException e) {
                    e.printStackTrace();
                } catch (InvalidRequestException e) {
                    e.printStackTrace();
                } catch (APIConnectionException e) {
                    e.printStackTrace();
                } catch (CardException e) {
                    String[] messaje={"message", e.getMessage()};
                    return messaje;
                } catch (APIException e) {
                    e.printStackTrace();
                }

        }
        return null;
    }

    @RequestMapping(value = "/{pay_id}/transaction",  method = RequestMethod.POST)
    public @ResponseBody Object addtransaction(@RequestBody RegisterPayTransactionDTO regpay, @PathVariable Long payid){

        switch (regpay.getType()) {
            /*
            case "DOWNPAYMENT":
                Tranpay tpm = new Tranpay();
                tpm.setTrans_type(regpay.getType());
                tpm.setTrans_operationdate(regpay.getOperationDate());
                tpm.setTrans_date(fechaActual);
                tpm.setStatus("NOTIFIED");
                return transactionPayRepository.save(tpm);
            */

            case "S": //Stripe Method

                Stripe.apiKey = utils.getStripeApiKey();

                int amount = (int) (regpay.getAmount() * 100);
                Map<String, Object> chargeParams = new HashMap<String, Object>();
                chargeParams.put("amount", amount); // Amount in cents
                chargeParams.put("currency", regpay.getCurrency());
                chargeParams.put("source", regpay.getSourcetoken());
                chargeParams.put("description", regpay.getDescription());

                try {
                    Charge charge = Charge.create(chargeParams);

                    if ((charge.getId() != null) && (!charge.getId().equals(""))) {
                        Tranpay etpm = new Tranpay();
                        etpm.setTrantype("STRIPE");
                        etpm.setTranamount((Double.parseDouble(String.valueOf(charge.getAmount()))/100));
                        etpm.setTrandate(fechaActual);
                        etpm.setTranupdate(fechaActual);
                        etpm.setTrantoken(charge.getId());
                        etpm.setTranstatus(charge.getStatus().toUpperCase());

                        Paymethod paymentMethodActive = new Paymethod();
                        paymentMethodActive= paymethodRepository.findOne(payid);
                        paymentMethodActive.getTransactionspayments().add(etpm);

                        return paymethodRepository.save(paymentMethodActive);
                    }

                } catch (AuthenticationException e) {
                    e.printStackTrace();
                } catch (InvalidRequestException e) {
                    e.printStackTrace();
                } catch (APIConnectionException e) {
                    e.printStackTrace();
                } catch (CardException e) {
                    e.printStackTrace();
                } catch (APIException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Paymethod> showall(){
        return paymethodRepository.findAll();
    }

    @RequestMapping(value = "/{pay_id}", method = RequestMethod.GET)
    public @ResponseBody Object showById(@PathVariable Long pay_id){
        try {
            return paymethodRepository.findOne(pay_id);
        }catch (Exception e){
            return e.getLocalizedMessage();
        }
    }

    @RequestMapping(value = "/tcocredentials", method = RequestMethod.GET)
    public @ResponseBody Object getTcoCredentials(){
        try {
            HashMap<String,String> keys= new HashMap<String,String>();
            keys.put("sellerid",utils.getTcoSellerId());
            keys.put("publishablekey",utils.getTcoPublishableKey());
            return keys;
        }catch (Exception e){
            return e.getLocalizedMessage();
        }
    }

    @RequestMapping(value = "/{payid}", method = RequestMethod.PUT)
    public @ResponseBody Object showById(@PathVariable Long payid,@RequestBody Paymethod payU){
        try {
            Paymethod pay = new Paymethod();
            pay.setPayid(payid);
            pay.setPayacctnum(payU.getPayacctnum());
            pay.setPaycardname(payU.getPaycardname());
            pay.setPaytype(payU.getPaytype());
            pay.setPaycreate(payU.getPaycreate());
            pay.setPayvalid(payU.getPayvalid());  //Debo validar si la fecha esta correcta
            pay.setPaycardname(payU.getPaycardname());

            return paymethodRepository.save(pay);

        }catch (Exception e){
            return e.getLocalizedMessage();
        }
    }


}
