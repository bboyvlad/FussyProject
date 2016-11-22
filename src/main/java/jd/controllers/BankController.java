package jd.controllers;

import jd.Util.AppMappings;
import jd.persistence.dto.checkPayDto;
import jd.persistence.model.*;
import jd.persistence.repository.BankRepository;
import jd.persistence.repository.PaymethodRepository;
import jd.persistence.repository.PrincipalRepository;
import jd.persistence.repository.ReceivedpayRepository;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by eduardom on 11/3/16.
 */

@Controller
@RequestMapping(value = AppMappings.BANK)
@Api(
        name="Bank",
        description = "Establece procedimientos para la gestion de Cuentas bancarias de la organización",
        group = "Gestiones de Pago",
        visibility = ApiVisibility.PUBLIC,
        stage = ApiStage.RC
)
public class BankController {

    @Autowired
    BankRepository bank;

    @Autowired
    ReceivedpayRepository received;

    @Autowired
    PaymethodRepository paymethodRepository;

    @Autowired
    PrincipalRepository principal;

    @RequestMapping(value = "/manage", method = RequestMethod.POST)
    @ApiMethod(description = "Permite al ROLEADMIN añadir a la base de datos una cuenta bancaria de la organizacion")
    public @ResponseBody Bank createBank(@RequestBody Bank newbank){
        try{
            Bank b = new Bank();
            b.setBankname(newbank.getBankname());
            b.setBankacctnum(newbank.getBankacctnum());
            b.setBankbalance(0.00);
            b.setDcreate(new Date());
            b.setNotes(newbank.getNotes());
            b.setEnabled(true);
            b.setDeleted(false);

            return bank.save(b);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    @RequestMapping(value = "/manage/{id}", method = RequestMethod.GET)
    @ApiMethod(description = "Permite al ROLEADMIN recuperar una cuenta bancaria y sus transacciones de la base de datos")
    public @ResponseBody Bank getBank(@ApiPathParam(description = "ID de la cuenta bancaria") @PathVariable long id){

        return bank.findOne(id);

    }

    @RequestMapping(value = "/manage/all", method = RequestMethod.GET)
    @ApiMethod(description = "Permite al ROLEADMIN recuperar un listado de todas(incluye soft deleted) las cuentas bancarias y sus transacciones previamente registradas en la base de datos")
    public @ResponseBody List<Bank> showall(){
        try{
            return bank.findAll();
        }catch (Exception e){
            System.out.println("Sin resultados");
            return null;
        }
    }

    @RequestMapping(value = "/manage/{id}", method = RequestMethod.PATCH)
    @ApiMethod(description = "Permite al ROLEADMIN modificar los datos de una cuenta bancaria en especifico, la misma debe existir en la base de datos")
    public @ResponseBody Bank updateBank(@RequestBody Bank b, @ApiPathParam(description = "ID de la cuenta bancaria") @PathVariable long id){
        try{
            Bank bankOne = bank.findOne(id);

            bankOne.setBankname(b.getBankname());
            bankOne.setBankacctnum(b.getBankacctnum());
            bankOne.setNotes(b.getNotes());
            bankOne.setEnabled(b.isEnabled());

            return bank.save(bankOne);
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

    @RequestMapping(value = "/manage/{id}", method = RequestMethod.DELETE)
    @ApiMethod(description = "Permite al ROLEADMIN aplicar un softdelete de una cuenta bancaria previamente registrada en la base de datos")
    public @ResponseBody String[] softDeleteBank(@ApiPathParam(description = "ID de la cuenta bancaria") @PathVariable long id){
        try{
            Bank b=bank.findOne(id);
            b.setDeleted(true);
            bank.save(b);
            return new String[]{"message", "success"};
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new String[]{"message", "failure"};
        }
    }

    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    @ApiMethod(description = "Permite al ROLEADMIN recuperar un listado de cuentas bancarias activas(no deleted) y sus transacciones previamente registradas en la base de datos")
    public @ResponseBody List<Bank> showAllActive(){
        return bank.findByDeletedNot(true);
    }

    @RequestMapping(value = "/sendpayment", method = RequestMethod.POST)
    @ApiMethod(description = "Permite al ROLEUSER reportar un deposito/transferencia hacia una cuenta bancaria de la organización")
    public @ResponseBody String[] sendPayment(@RequestBody Receivedpay pay, Authentication auth){

        try{

            Principal Pp = principal.findByEmail(auth.getName());

            final Paymethod[] inspaymethod = new Paymethod[1];

            Pp.getPayments().forEach((pm)->{
                if(pm.getPayid()==pay.getPaymethod()){
                    inspaymethod[0] =pm;
                }
            });

            Receivedpay Rp = new Receivedpay();

            Rp.setBank(pay.getBank()); //Banco donde se depositó
            Rp.setRelatedref(pay.getRelatedref()); //numero de referencia del deposito
            Rp.setInsname(pay.getInsname());

            Rp.setInspaymethod(pay.getInspaymethod()); //metodo de pago que usó el usuario
            Rp.setInsbank(inspaymethod[0].getPaycardname()); //nombre del banco o nombre que uso el usuario al momento de definir sus metodos de pago
            Rp.setInsacctnum(inspaymethod[0].getPayacctnum()); //numero de cuenta del metodo de pago del usuario

            Rp.setPaymethod(pay.getPaymethod()); //el paymethod(jdcard)donde será aplicado el pago
            Rp.setAmount(pay.getAmount()); //monto depositado
            Rp.setDcreate(pay.getDcreate());
            Rp.setDupdate(new Date()); // el dia que se registro en "dcreate" es cuando el tipo depositó
            Rp.setApproved(false);

            received.save(Rp);

            return new String[]{"message","success"};

        }catch(Exception e){
            System.out.println(e.getLocalizedMessage());
            return new String[]{"message","failure"};
        }

    }

    @RequestMapping(value = "/checkpayment", method = RequestMethod.POST)
    @ApiMethod(description = "Permite al ROLEADMIN validar un deposito/transferencia reportada por un ROLEUSER hacia una cuenta bancaria de la organización")
    public @ResponseBody String[] checkPayment(@RequestBody checkPayDto dto){
        try{

            Receivedpay pay = received.findOne(dto.getPayment()); //Me traigo el pago reportado

            pay.setApproved(dto.isApproved());

            if(dto.isApproved()){

                //El metodo de pago del usuario donde será aplicado
                Paymethod paymethod=paymethodRepository.findOne(pay.getPaymethod());

                //preparo la transacción de abono al metodo de pago que apuntó el usuario
                Tranpay tranpay=new Tranpay();
                tranpay.setTrantype("BANK");
                tranpay.setTranamount(dto.getFamount());
                tranpay.setTrandate(pay.getDcreate());
                tranpay.setTranupdate(new Date());
                tranpay.setTrantoken(pay.getRelatedref());
                tranpay.setTranstatus("SUCCEEDED");

                //añado la transaccion al metodo de pago
                paymethod.getTransactionspayments().add(tranpay);

                //Preciso el banco para colocarle la transaccion del pago aprobado
                Bank bnk=bank.findOne(pay.getBank());

                //preparo la transaccion al banco donde ha depositado el usuario
                TransactionBank transactionBank = new TransactionBank();

                transactionBank.setPaymethod(pay.getPaymethod());
                transactionBank.setRelatedref(pay.getRelatedref());
                transactionBank.setInsbank(pay.getInsbank());
                transactionBank.setInsacctnum(pay.getInsacctnum());
                transactionBank.setAmount(pay.getAmount());
                transactionBank.setDcreate(pay.getDcreate());
                transactionBank.setDupdate(new Date());
                transactionBank.setChecked(true);

                //añado la transaccion al banco
                bnk.getTransactions().add(transactionBank);


                //guardo la transaccion en el banco
                bank.save(bnk);

                //Guarda la transaccion en el paymethod
                paymethodRepository.save(paymethod);

                //Actualizo el registro de los pagos
                pay.setDupdate(new Date());
                pay.setFamount(dto.getFamount());
                received.save(pay);

            }

            received.save(pay);
            return new String[]{"message","success"};

        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            return new String[] {"message","failure"};
        }

    }

    @RequestMapping(value = "/showreceived", method = RequestMethod.GET)
    public @ResponseBody List<Receivedpay> showPaymentsReceived(){
        return received.findByApprovedFalse();
    }

}
