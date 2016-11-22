package jd.controllers;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import jd.Util.AppMappings;
import jd.persistence.dto.ApplyGiftcardDTO;
import jd.persistence.dto.CreateGiftcardDTO;
import jd.persistence.model.*;
import jd.persistence.repository.GiftcardRepository;
import jd.persistence.repository.PaymethodRepository;
import jd.persistence.repository.PrincipalRepository;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jd.Util.AppUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by eduardom on 10/3/16.
 */
@RestController
@RequestMapping(value = AppMappings.GIFTCARD)
@Api(
        name="Giftcard",
        description = "Establece procedimientos para la creacion y aplicacion de las giftcards",
        group = "Metodos de Pago",
        visibility = ApiVisibility.PUBLIC,
        stage = ApiStage.RC
)
public class GiftcardController {

    GiftcardRepository giftcardRepository;

    @Autowired
    PaymethodRepository paymethodRepository;

    @Autowired
    PrincipalRepository principalRepository;

    @Autowired
    private JavaMailSender mailSender;

    Date fechaActual = new Date();
    AppUtils utils = new AppUtils();

    @Autowired
    public GiftcardController(GiftcardRepository giftcardRepository) {
        this.giftcardRepository = giftcardRepository;
    }

    // create
    @RequestMapping(method = RequestMethod.POST)
    @ApiMethod(description = "Permite al usuario hacer la compra de una giftcard añadiendo saldo a traves de alguno " +
                             "de sus métodos de pagos registrados en la base de datos")
    public Object create(@RequestBody CreateGiftcardDTO createGiftcardDTO,
                         HttpServletRequest request,
                         Authentication auth) {
        Hashtable<String, String> message= new Hashtable<String, String>();
        boolean success=false;

        try {
            Principal principal = principalRepository.findByEmail(auth.getName());
            Paymethod paymethod = paymethodRepository.findOne(createGiftcardDTO.getPaymethod());

            //Verifico si la tarjeta le pertenece al principal
            if (!principal.getPayments().contains(paymethod)){
                message.put("message","algo pasa, esta tarjeta no es de este usuario");
                return message;
            }

            switch (paymethod.getPaytype()) {

                case "CARD":
                    //Stripe's payments
                    Stripe.apiKey = utils.getStripeApiKey();

                    int amount = (int) (createGiftcardDTO.getAmount() * 100);
                    Map<String, Object> chargeParams = new HashMap<String, Object>();
                    chargeParams.put("amount", amount); // Amount in cents
                    chargeParams.put("currency", "usd");
                    chargeParams.put("source", utils.getStripeToken(paymethod, createGiftcardDTO.getPaycardsec()));
                    chargeParams.put("description", "GIFTCARD PURCHASED FOR " + createGiftcardDTO.getRecipient_email().toUpperCase());

                    Charge charge = Charge.create(chargeParams);

                    if ((charge.getId() != null) && (!charge.getId().equals(""))) { //if return Id_token
                        if (charge.getStatus().toUpperCase().equals("SUCCEEDED")) {

                            Tranpay etpm = new Tranpay();
                            etpm.setTrantype("STRIPE");
                            etpm.setTranamount((Double.parseDouble(String.valueOf(charge.getAmount())) / 100));
                            etpm.setTrandate(fechaActual);
                            etpm.setTranupdate(fechaActual);
                            etpm.setTrantoken(charge.getId());
                            etpm.setTranstatus(charge.getStatus().toUpperCase());

                            paymethod.getTransactionspayments().add(etpm);
                            paymethodRepository.save(paymethod);

                            success=true;
                            message.put("message","Transaccion Exitosa");
                        }

                    }else{
                        message.put("message","Tarjeta rechazada");
                    }
                break;

                case "JDCARD":
                    /* valido el saldo de la jdcard antes de pagar*/
                    if (createGiftcardDTO.getAmount() <= paymethod.getPaybalance()) {

                        Tranpay jd_etpm = new Tranpay();
                        jd_etpm.setTrantype("JDEBIT");
                        jd_etpm.setTranamount(-(Double.parseDouble(String.valueOf(createGiftcardDTO.getAmount()))));
                        jd_etpm.setTrandate(fechaActual);
                        jd_etpm.setTranupdate(fechaActual);
                        jd_etpm.setTrantoken("jd_" + utils.getCadenaAlfaNumAleatoria(9));
                        jd_etpm.setTranstatus("SUCCEEDED");

                        paymethod.getTransactionspayments().add(jd_etpm);
                        paymethodRepository.save(paymethod);

                        success=true;
                        message.put("message","Transaccion Exitosa");

                    }else{
                        message.put("message","Saldo insuficiente");
                    }

                break;
            }

            if(success){
                Giftcard giftcard = new Giftcard();
                giftcard.setAmount(createGiftcardDTO.getAmount());
                giftcard.setClaimcode(utils.getCadenaNumAleatoria(14));
                giftcard.setDcreate(fechaActual);
                giftcard.setDupdate(fechaActual);
                giftcard.setRecipientmessage(createGiftcardDTO.getRecipientmessage());
                giftcard.setRecipient_name(createGiftcardDTO.getRecipient_name());
                giftcard.setRecipient_email(createGiftcardDTO.getRecipient_email());

                giftcardRepository.save(giftcard);


                final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort();

                /*NOTIFICACION PARA QUIEN COMPRA*/
                final SimpleMailMessage email=new SimpleMailMessage();
                email.setTo(principal.getEmail());
                email.setSubject("Has comprado una GIFTCARD");
                email.setText("Hola "+principal.getName()+", " +
                        "has realizado la compra de una GIFTCARD satisfactoriamente, " +
                        "a continuación los datos de la compra: \n" +
                        "Importe: " + giftcard.getAmount() + "" +
                        "Para: " + giftcard.getRecipient_name() + "\n" +
                        "Email: " + giftcard.getRecipient_email() + "\n");
                mailSender.send(email);

                /*NOTIFICACION PARA QUIEN RECIBE*/
                final SimpleMailMessage recipient=new SimpleMailMessage();
                recipient.setTo(giftcard.getRecipient_email());
                recipient.setSubject("Felicidades has recibido una GIFTCARD");
                recipient.setText("Hola "+giftcard.getRecipient_name()+", " +
                        ", has recibido una GIFTCARD de " +
                        principal.getName() + "\n " +
                        "ClaimCode: " + giftcard.getClaimcode().toUpperCase() + "\n" +
                        "Amount: " + giftcard.getAmount() + "\n" +
                        " podras aplicarla cuando gustes desde la seccion de metodos de pago");
                mailSender.send(recipient);
            }

            return message;

        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        } catch (CardException e) {
            e.printStackTrace();
        }

        return null;
    }

    // apply
    @RequestMapping(value = "/apply" ,method = RequestMethod.POST)
    @ApiMethod(description = "Permite al usuario aplicar una gifcard(saldo) a una JDCARD previamente registrada en la base de datos ")
    public Object apply(@RequestBody ApplyGiftcardDTO applygiftcard){
        Hashtable<String, String> message= new Hashtable<String, String>();
        try {
            Giftcard giftcard= giftcardRepository.findByclaimcode(applygiftcard.getClaimcode());
            Paymethod paymethod=paymethodRepository.findOne(applygiftcard.getJdcard());

            if(!giftcard.isRegistered()){
                Tranpay newtransaction=new Tranpay();
                newtransaction.setTranstatus("SUCCEEDED");
                newtransaction.setTranamount(giftcard.getAmount());
                newtransaction.setTrandate(fechaActual);
                newtransaction.setTrantoken("gift_"+giftcard.getClaimcode());
                newtransaction.setTrantype("GIFCARD");
                newtransaction.setTranupdate(fechaActual);

                paymethod.getTransactionspayments().add(newtransaction);

                paymethodRepository.save(paymethod);

                giftcard.setRegistered(true);
                giftcard.setDupdate(fechaActual);

                giftcardRepository.save(giftcard);

                message.put("message","Operacion exitosa");
                return message;

            }else{
                message.put("message","Esta giftcard ya ha sido aplicada");
                return message;
            }

        }catch (Exception e){
            return e.getMessage();
        }
    }


}
