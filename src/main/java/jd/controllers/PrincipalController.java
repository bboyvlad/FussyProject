package jd.controllers;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;

import jd.persistence.dto.*;
import jd.Util.AppUtils;
import jd.persistence.model.*;
import jd.persistence.repository.*;

import jd.security.PrincipalAuthenticationProvider;
import jd.security.PrincipalUserDetailsService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.*;

import java.io.IOException;
import java.util.List;

/*import com.twocheckout.*;
import com.twocheckout.model.*;*/

import com.itextpdf.text.DocumentException;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/**
 * Created by eduardom on 9/3/16.
 */

@Controller
@RequestMapping(value="/users")
public class PrincipalController {

    private PrincipalRepository principalRepository;

    @Autowired
    PrincipalUserDetailsService userDetailsService;

    @Autowired
    Principal_CoordinatesRepository principalCoordinatesRepository;

    @Autowired
    PaymethodRepository paymethodRepository;

    @Autowired
    ShopcartRepository shopcartRepository;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    MyaircraftRepository myaircraftRepository;

    @Autowired
    AircraftypeRepository aircraftypeRepository;

    @Autowired
    ServicerequestRepository servicerequestRepository;

    @Autowired
    PriceRepository price;

    @Autowired
    PricepoundRepository pricepound;

    @Autowired
    PricedateRepository pricedate;

    @Autowired
    ProductRepository product;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    PrincipalAuthenticationProvider principalAuthenticationProvider;

    @Autowired
    public PrincipalController(PrincipalRepository principalRepository){
        this.principalRepository = principalRepository;
    }

    private Date fechaActual = new Date();

    private AppUtils utils = new AppUtils();

    //check user
    @RequestMapping(value = "/loggedUser",  method = RequestMethod.GET)
    public @ResponseBody Object login( Authentication usr){

        return principalRepository.findByEmail(usr.getName());
    }

    //create a user
    @RequestMapping(value = "/create",  method = RequestMethod.POST)
    public @ResponseBody Object create(@RequestBody Principal user,
                                       final HttpServletRequest request){
        Hashtable<String, String> message = new Hashtable<String, String>();
        try {

            System.out.println("Revisando el email");

            if(!principalExist(user.getEmail())){

                System.out.println("No Existe!");

                user.setEnabled(false);
                //user.setTokenExpired(false);
                Principal u = new Principal();
                u.setName(user.getName());
                u.setLastname(user.getLastname());
                u.setEmail(user.getEmail());
                u.setPassword(principalAuthenticationProvider.encodePassword(user.getPassword()));
                u.setEnabled(false);
                u.setCardcode(null);
                u.setCredentialsexpired(false);
                u.setExpired(false);
                u.setLocked(false);

                Role userRole = roleRepository.findByCode("ROLE_GUEST");
                u.setRoles(Arrays.asList(userRole));

                Principal usr = principalRepository.save(u);

                final long iat = System.currentTimeMillis() / 1000l; // issued at claim
                final long exp = iat + 86400L; // expires claim. In this case the token expires in 60 seconds (86400 24h)

                //Genera el tookken para ser enviado por correo y activar la cuenta
                final JWTSigner signer = new JWTSigner(utils.getSecret());
                final HashMap<String, Object> claims = new HashMap<String, Object>();
                claims.put("exp", exp);
                claims.put("iat", iat);
                claims.put("user_id",usr.getId());
                claims.put("enabled",true);
                claims.put("op","activateuser");
                final String jwt = signer.sign(claims);

                final String appUrl = "http://" + request.getServerName() + ":"
                        + request.getServerPort();

                final SimpleMailMessage email=new SimpleMailMessage();
                email.setSubject(usr.getName()+" J&D International S.A., te da la bienvenida !");
                email.setTo(usr.getEmail());
                email.setText("Hola "+usr.getName()+", para activar tu cuenta has click en el " +
                        "siguiente enlace: "+appUrl+"/users/activate/"+jwt+".jd");

                mailSender.send(email);
                System.out.println("Register mail send");
                return principalRepository.findOne(usr.getId());

            }else{
                System.out.println("Si Existe!");
                message.put("message","Este email ya se encuentra registrado");
                return message;
            }



        }catch (Exception e){
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/checkmail/{email}", method = RequestMethod.POST)
    public @ResponseBody Object checkEmail(@PathVariable String email){
        Hashtable<String, String> message = new Hashtable<String, String>();
        if(!principalExist(email)){
            message.put("message","Email OK");
        }else{
            message.put("message","Email Exist");
        }
        return message;
    }

    //parsea el token
    @RequestMapping(value ="/activate/{token}", method=RequestMethod.GET)
    public Object activateUser(@PathVariable String token,
                               Authentication auth) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, IOException {

        final JWTVerifier jwtVerifier = new JWTVerifier(utils.getSecret());
        try {
            final Map<String,Object> claims = jwtVerifier.verify(token);

                    String user_id=claims.get("user_id").toString();
                    boolean enabled = Boolean.parseBoolean(claims.get("enabled").toString());
                    Principal usr = principalRepository.findOne(Long.parseLong(user_id));
                    usr.setEnabled(enabled);
                    principalRepository.save(usr);

                    return "redirect:/#!/users/log-in";

        } catch (JWTVerifyException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    * PAYMETHOD
    */

    //buy a jdcard with stripe's payment method
    @RequestMapping(value = "/jdcard",  method = RequestMethod.POST) //cardController JD CardModel
    public @ResponseBody Object addJdcardStripe(@RequestBody RegisterPayTransactionDTO regpay,
                                        Authentication auth) {

        Hashtable<String, String> messageJdcard = new Hashtable<String, String>();
        final boolean[] checked = {false};
        Principal Pp = principalRepository.findByEmail(auth.getName()); //.findOne(principal_id);

        final Paymethod[] Ps = new Paymethod[1];

        Pp.getPayments().forEach((paymethod)->{
            if(paymethod.getPayid().equals(regpay.getIdpaymethod())){
                checked[0] =true;
                Ps[0] =paymethod;
            }
        });

        /*Verifico si el metodo de pago le pertenece a este usuario*/
        if (!checked[0]){
            System.out.println("el metodo de pago no le pertenece a este usuario");
            return null;
        }

        if((Ps[0].getPaystatus().equals("ACTIVE")) && (Ps[0].getPaytype().equals("CARD"))){

            try {

                //Stripe's payments
                Stripe.apiKey = utils.getStripeApiKey();
                int amount = (int) (regpay.getAmount() * 100);
                Map<String, Object> chargeParams = new HashMap<String, Object>();
                chargeParams.put("amount", amount); // Amount in cents
                chargeParams.put("currency", "usd");
                chargeParams.put("source", utils.getStripeToken(Ps[0], regpay.getPay_ccsec())); //Ps[0] CARD
                chargeParams.put("description", "BUYING A JDCARD");
                Charge charge = Charge.create(chargeParams);


                if ((charge.getId() != null) && (!charge.getId().equals(""))) { //if return Id_token

                    Paymethod pay= new Paymethod();
                    pay.setPaytype("JDCARD");
                    pay.setPaystatus("INACTIVE");
                    pay.setPayacctnum(utils.getCadenaNumAleatoria(16));//cardcode
                    pay.setPaycardname(regpay.getCardname());
                    pay.setPaylocked(0.00);
                    pay.setPaycardseccode(utils.getCadenaNumAleatoria(3));
                    pay.setPaycreate(fechaActual);
                    pay.setPayvalid(utils.sumarMesesAFecha(fechaActual,24));

                    Principal userActive = new Principal();
                    userActive= principalRepository.findByEmail(auth.getName());  //.findOne(principal_id);
                    userActive.getPayments().add(pay);
                    principalRepository.save(userActive);

                    Paymethod paymentMethod = paymethodRepository.findBypayacctnum(pay.getPayacctnum());


                    Tranpay etpm = new Tranpay();
                    etpm.setTrantype("STRIPE");
                    etpm.setTranamount((Double.parseDouble(String.valueOf(charge.getAmount()))/100));
                    etpm.setTrandate(fechaActual);
                    etpm.setTranupdate(fechaActual);
                    etpm.setTrantoken(charge.getId());
                    etpm.setTranstatus(charge.getStatus().toUpperCase());

                    Paymethod paymentMethodActive = paymethodRepository.findOne(paymentMethod.getPayid());
                    paymentMethodActive.getTransactionspayments().add(etpm);

                    paymethodRepository.save(paymentMethodActive);

                    SimpleMailMessage email=new SimpleMailMessage();
                    email.setTo(Pp.getEmail());
                    email.setSubject("Felicidades has adquirido una JDCARD exitosamente");
                    email.setText("Hola "+Pp.getName()+", has comprado una JDCARD exitosamente, y estará " +
                                  "disponible en sus metodos de pago");
                    mailSender.send(email);


                    /* generando PDF si funciona
                    System.out.println("Generando PDF");

                    Map<String,Object> params = new HashMap<String,Object>();
                    params.put("customer",Pp.getName());
                    params.put("number",pay.getPayacctnum());
                    params.put("expired",pay.getPayvalid().toString());

                    JasperPrint jasperPrint=JasperFillManager.fillReport(java.lang.System.getProperty("user.home")+"/fussyfiles/reports/jdcard.jasper", params,new JREmptyDataSource());

                    */

                    return paymentMethodActive;

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
        messageJdcard.put("message","Tarjeta Invalida");
        return messageJdcard;
    }

/*    //buy a jdcard with 2checkout's payment method
    @RequestMapping(value = "/jdcardto",  method = RequestMethod.POST)
    public @ResponseBody String[] addJdcard2checkout(@RequestBody RegisterPayTransactionDTO regpay,
                                                 Authentication auth) {

        final boolean[] checked = {false};
        final Paymethod[] Ps = new Paymethod[1];
        String[] message;


        Principal Pp =principalRepository.findByEmail(auth.getName());
        Pp.getPayments().forEach((paymethod)->{
            if(paymethod.getPayid().equals(regpay.getIdpaymethod())){
                checked[0] =true;
                Ps[0] =paymethod;
            }
        });

        *//*Verifico si el metodo de pago le pertenece a este usuario*//*

        if (!checked[0]){
            System.out.println("el metodo de pago no le pertenece a este usuario");
            return null;
        }

        if((Ps[0].getPaystatus().equals("ACTIVE")) && (Ps[0].getPaytype().equals("CARD"))) {

            Twocheckout.privatekey = utils.getTcoPrivateKey();
            Twocheckout.mode = "sandbox";

            try {

                HashMap<String, String> billing = new HashMap<String, String>();
                billing.put("name", Ps[0].getPaycardname());
                billing.put("addrLine1", Ps[0].getPayaddrlineone());
                billing.put("city", Ps[0].getPaycity());
                billing.put("state", Ps[0].getPaystate());
                billing.put("country", Ps[0].getPaycountry());
                billing.put("zipCode", Ps[0].getPaycountry());
                billing.put("email", Pp.getEmail());

                HashMap<String, Object> request = new HashMap<String, Object>();

                request.put("sellerId", utils.getTcoSellerId());
                request.put("merchantOrderId", "JDCARD PURCHASE");
                request.put("token", regpay.getSourcetoken());
                request.put("currency", "USD");
                request.put("total", regpay.getAmount());
                request.put("billingAddr", billing);

                *//*Genero el cobro de la transacción*//*
                System.out.println("Generando el cargo en 2checkout");
                Authorization response = TwocheckoutCharge.authorize(request);
                System.out.println(response.getResponseMsg() + " "+response.getResponseCode());

                if(response.getResponseCode().equals("APPROVED")){

                    Paymethod pay= new Paymethod();

                    pay.setPaytype("JDCARD");
                    pay.setPaystatus("ACTIVE");
                    pay.setPayacctnum(utils.getCadenaNumAleatoria(16));//cardcode
                    pay.setPaycardname(regpay.getCardname());
                    pay.setPaylocked(0.00);
                    pay.setPaycardseccode(utils.getCadenaNumAleatoria(3));
                    pay.setPaycreate(fechaActual);
                    pay.setPayvalid(utils.sumarMesesAFecha(fechaActual,24));

                    System.out.println("Añadiendo el metodo de pago nuevo");
                    Pp.getPayments().add(pay);

                    principalRepository.save(Pp);

                    System.out.println("Buscando el metodo de pago por numero de tarjeta");
                    Paymethod paymentMethod = paymethodRepository.findBypayacctnum(pay.getPayacctnum());

                    Tranpay etpm = new Tranpay();
                    etpm.setTrantype("JD CHARGE");
                    etpm.setTranamount(response.getTotal().doubleValue());
                    etpm.setTrandate(fechaActual);
                    etpm.setTranupdate(fechaActual);
                    etpm.setTrantoken(response.getTransactionId());
                    etpm.setTranstatus("SUCCEEDED");
                    etpm.setTrandescription("JDCARD PURCHASE");

                    Paymethod paymentMethodActive = paymethodRepository.findOne(paymentMethod.getPayid());
                    paymentMethodActive.getTransactionspayments().add(etpm);

                    paymethodRepository.save(paymentMethodActive);


                    SimpleMailMessage email=new SimpleMailMessage();
                    email.setTo(Pp.getEmail());
                    email.setSubject("Felicidades has adquirido una JDCARD exitosamente");
                    email.setText("Hola "+Pp.getName()+", has comprado una JDCARD exitosamente, y estará " +
                            "disponible en sus metodos de pago");
                    mailSender.send(email);

                    return new String[]{"message","success"};
                }else{
                    return new String[]{"message","failure"};
                }

            } catch (Exception e) {
                System.out.println("Mensaje de error: " + e);
                return null;
            }
        }else{
            return new String[]{"message","Card inactive"};
        }

    }*/
    //add payment method to user
    @RequestMapping(value = "/paymethod",  method = RequestMethod.POST)
    public @ResponseBody Object addpaymethod(@RequestBody Paymethod payadd,Authentication auth){

        try{
            System.out.println("user: "+auth.getName());
            Principal Pp=principalRepository.findByEmail(auth.getName());

            Paymethod pay= new Paymethod();
            System.out.println("paytype "+ pay.getPaytype());
            switch (payadd.getPaytype()){
                case "CARD":
                    pay.setPayacctnum(payadd.getPayacctnum());
                    pay.setPaycardname(payadd.getPaycardname());
                    pay.setPayvalid(payadd.getPayvalid());//Debo validar si la fecha esta correcta
                    pay.setPaycardtype(payadd.getPaycardtype());
                    pay.setPayaddrlineone(payadd.getPayaddrlineone());
                    pay.setPaycity(payadd.getPaycity());
                    pay.setPaystate(payadd.getPaystate());
                    pay.setPaycountry(payadd.getPaycountry());
                    pay.setPayzip(payadd.getPayzip());
                case "BANK":
                    pay.setPaycardname(payadd.getPaycardname().toUpperCase());
                    pay.setPayacctnum(payadd.getPayacctnum());
                    pay.setPaycardrandomcode(payadd.getPaycardrandomcode());
                    pay.setPayenabled(true);
            }
            pay.setPayalias(payadd.getPayalias());
            pay.setPaycardrandomcode("rdn_"+utils.getCadenaNumAleatoria(6));
            pay.setPaytype(payadd.getPaytype());
            pay.setPaystatus(payadd.getPaystatus().toUpperCase());
            pay.setPaycreate(fechaActual);

            Pp.getPayments().add(pay);

            Role userRole = roleRepository.findByCode("ROLE_USER");

            Pp.getRoles().removeAll(Pp.getRoles());
            Pp.getRoles().addAll(Arrays.asList(userRole));

            principalRepository.save(Pp);

            SimpleMailMessage email=new SimpleMailMessage();
            email.setTo(Pp.getEmail());
            email.setSubject("Felicidades has registrado un método de pago");
            email.setText("Hola "+Pp.getName()+", has registrado con exito un metodo de pago " +
                          "en nuestra plataforma podrás verificarlo en tus métodos de pago.");

            mailSender.send(email);

            return Pp.getPayments();

        } catch(NullPointerException e){
            return e.getMessage();
        } catch(Exception e){
            return e.getCause();
        }
    }

    //return payments from user
    @RequestMapping(value="/paymentmethod", method = RequestMethod.GET)
    public @ResponseBody Set<Paymethod> getPayMethodUser(Authentication auth){
        try{
            return principalRepository.findByEmail(auth.getName()).getPayments();  //.findOne(user_id).getPayments();
        }catch (Exception e){
            return null;
        }
    }

    //return payments from user date
    @RequestMapping(value="/datetransactionpay", method = RequestMethod.POST)
    public @ResponseBody Set<Tranpay> getTransactionByPaymethod(@RequestBody dateTransactionPayDto dto,
                                                                Authentication auth){
        try{
            Principal Pp=principalRepository.findByEmail(auth.getName());
            final Paymethod[] pm = new Paymethod[1];
            Pp.getPayments().forEach((pay)->{
                if(pay.getPayid()==dto.getPaymethod()){
                    pm[0] =pay;
                }
            });

            Set<Tranpay> transactions= new HashSet<>();
            pm[0].getTransactionspayments().forEach((trans)->{
                if(trans.getTrandate().after(dto.getFromdate()) && trans.getTrandate().before(dto.getTodate())){
                    transactions.add(trans);
                }
            });

            return transactions;

        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

    @RequestMapping(value="/paymethod", method = RequestMethod.PATCH)
    public @ResponseBody String[] updatePaymethod(@RequestBody Paymethod upay,
                                                  Authentication auth){

        Principal Pp=principalRepository.findByEmail(auth.getName());

        final Paymethod[] pay = new Paymethod[1];

        Pp.getPayments().forEach((selpay)->{
            if(selpay.getPayid()==upay.getPayid()){
                pay[0] =selpay;
            }
        });

        try{

            if(pay[0].getPaytype().equals("CARD")){
                pay[0].setPayalias(upay.getPayalias());
                pay[0].setPaycardname(upay.getPaycardname());
                pay[0].setPaycardtype(upay.getPaycardtype());
                pay[0].setPayacctnum(upay.getPayacctnum());
                pay[0].setPayvalid(upay.getPayvalid());
                pay[0].setNotes(upay.getNotes());
                /*ToCheckout info*/
                pay[0].setPayaddrlineone(upay.getPayaddrlineone());
                pay[0].setPaycity(upay.getPaycity());
                pay[0].setPaystate(upay.getPaystate());
                pay[0].setPaycountry(upay.getPaycountry());
                pay[0].setPayzip(upay.getPayzip());
            }

            if(pay[0].getPaytype().equals("JDCARD")){
                pay[0].setPaycardname(pay[0].getPaycardname());
            }

            pay[0].setPaystatus(upay.getPaystatus());

            paymethodRepository.save(pay[0]);
            return new String[]{"message","success"};

        }catch(Exception e){

            System.out.println(e.getLocalizedMessage());
            return new String[]{"message","failure"};

        }


    }

    @RequestMapping(value="/paymethod/{paymethod}", method = RequestMethod.DELETE)
    public @ResponseBody String[] softDeletePay(@PathVariable long paymethod, Authentication auth){

        Principal Pp=principalRepository.findByEmail(auth.getName());
        try{
            final Paymethod[] pay = new Paymethod[1];

            Pp.getPayments().forEach((selpay)->{
                if(selpay.getPayid()==paymethod){
                    pay[0] =selpay;
                }
            });
            pay[0].setDeleted(true);

            paymethodRepository.save(pay[0]);
            return new String[]{"message","success"};

        }catch(Exception e){
            System.out.println(e.getLocalizedMessage());
            return new String[]{"message","failure"};
        }

    }

    /*
    * PRINCIPAL ADMINISTRATION ROLE_ADMIN ONLY
    * */

    //show user by id (ADMIN ONLY)
    @RequestMapping(value = "/manage/{user_id}", method = RequestMethod.GET)
    public @ResponseBody Object getUser(@PathVariable long user_id){
        try {
            return principalRepository.findOne(user_id);
        }catch (Exception e){
            return e.getLocalizedMessage();
        }
    }

    //remove user by id
    @RequestMapping(value = "/manage/{user_id}", method = RequestMethod.DELETE)
    public @ResponseBody Boolean delete(@PathVariable Long user_id){
        try {
            principalRepository.delete(user_id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //show all user
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public @ResponseBody List<Principal> getAll(){
        try{
            return principalRepository.findAll();
        }catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    //update a user
    @RequestMapping(value = "/manage/{principalid}", method = RequestMethod.PUT)
    public @ResponseBody Principal updateUser(@PathVariable Long principalid, @RequestBody PrincipalDto users){


            String roleselected;
            Principal p= principalRepository.findOne(principalid);


            System.out.println("principal: "+p.getName());

            p.setName(users.getName());
            p.setLastname(users.getLastname());
            p.setEnabled(users.isEnabled());
            p.setCardcode(users.getCardcode());
            p.setEmail(p.getEmail());
            p.setEnabled(p.isEnabled());
            p.setCredentialsexpired(p.isCredentialsexpired());

            switch (users.getRole()){

                case "U":
                    roleselected="ROLE_USER";
                    break;

                case "A":
                    roleselected="ROLE_ADMIN";
                    break;

                case "P":
                    roleselected="ROLE_PROVIDER";
                    break;

                case "S":
                    roleselected="ROLE_SYSADMIN";
                    break;

                case "G":
                    roleselected="ROLE_GUEST";
                    break;

                case "M":
                    roleselected="ROLE_MANAGER";
                    break;

                case "O":
                    roleselected="ROLE_OPER";
                    break;

                case "PA":
                    roleselected="ROLE_PARTNER";
                    break;

                case "SU":
                    roleselected="ROLE_SUPER";
                    break;

                default:
                    roleselected="ROLE_GUEST";
            }

            Role userRole = roleRepository.findByCode(roleselected);

            p.getRoles().removeAll(p.getRoles());

            if(users.getPassword()!=null){
                p.setPassword(principalAuthenticationProvider.encodePassword(users.getPassword()));
            }else{
                p.setPassword(p.getPassword());
            }
            p.getRoles().addAll(Arrays.asList(userRole));

            return principalRepository.save(p);
    }

    /*
    * SECURITY POLICY
    * */

    //Genera una tarjeta de coordenadas al usuario user_id
    @RequestMapping(value ="/setcoordinates", method=RequestMethod.GET)
    public @ResponseBody Object setTarjetaCoordenadas(Authentication auth,
                                                      HttpServletResponse httpServletResponse,
                                                      HttpServletRequest request) throws JRException, IOException {

        //probando vainas
        System.out.println("antes: "+System.getProperty("user.home"));

        Hashtable<String, String> message = new Hashtable<String, String>();
        try{

            //Estos valores seran resueltos con los datos de la sesion activa.
            //Principal usrActive = principalRepository.findOne(user_id);

            Principal usrActive=principalRepository.findByEmail(auth.getName());

            List<rptCoordinatesDTO> coordinates = new ArrayList<rptCoordinatesDTO>();

            final HashMap<String, Object> crdObject = new HashMap<String, Object>();
            for(int fila=0; fila<5; fila++){
                rptCoordinatesDTO crddto=new rptCoordinatesDTO();
                for(int columna=0; columna<5; columna++){

                    String coordenada=utils.getCadenaAlfaNumAleatoria(3);
                    crdObject.put(getLetra(columna)+(fila+1),coordenada);

                    crddto.setFila(fila+1);

                    switch (columna){
                        case 0:
                            crddto.setA(coordenada);
                            break;
                        case 1:
                            crddto.setB(coordenada);
                            break;
                        case 2:
                            crddto.setC(coordenada);
                            break;
                        case 3:
                            crddto.setD(coordenada);
                            break;
                        case 4:
                            crddto.setE(coordenada);
                            break;
                    }
                }
                coordinates.add(crddto);
            }

            String jdserial=utils.getCadenaAlfaNumAleatoria(5);

            Principal_Coordinate coordinate = new Principal_Coordinate();
            coordinate.setId(usrActive.getId());
            coordinate.setCoordinate(utils.Encriptar(crdObject.toString()));
            coordinate.setSerial(jdserial);
            coordinate.setDcreate(fechaActual);
            coordinate.setDupdate(fechaActual);

            usrActive.setCoordinates(coordinate);

            principalRepository.save(usrActive);

            final JWTSigner actUser = new JWTSigner(utils.getSecret());
            final HashMap<String, Object> actCrd = new HashMap<String, Object>();
            actCrd.put("user_id",usrActive.getId());
            actCrd.put("active",true);

            final String jwtusrCrd = actUser.sign(actCrd)+".jd";


            /*GENERAMOS EL PDF DE LAS COORDENADAS*/

            Map<String,Object> params = new HashMap<String,Object>();
            params.put("serial",jdserial);


            JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(coordinates);
            JasperPrint jasperPrint=JasperFillManager.fillReport(java.lang.System.getProperty("user.home")+"/fussyfiles/reports/jdcoordinates.jasper", params,beanCollectionDataSource);

            //JasperExportManager.exportReportToPdfFile(jasperPrint, "jdcoordinate.pdf");

            httpServletResponse.getOutputStream();
            httpServletResponse.addHeader("Content-disposition","attachment; filename=jdcoordinate.pdf");

            ServletOutputStream stream = httpServletResponse.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            stream.flush();
            stream.close();


            final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort();

            final SimpleMailMessage email=new SimpleMailMessage();
            email.setSubject("Seguridad J&D International S.A.");
            email.setTo(usrActive.getEmail());
            email.setText("Hola "+usrActive.getName()+", has creado una tarjeta de coordenadas correctamente, " +
                    "para activarla has clic en el " +
                    "siguiente enlace: "+appUrl+"/users/coordinateactivate/"+jwtusrCrd);

            message.put("message", "Coordenadas generadas con exito");

            mailSender.send(email);
            return crdObject;

        }catch (Exception e){
            return e.getCause();
        }
    }

    //activa la tarjeta de coordenadas que ha sido generada por el usuario
    @RequestMapping(value ="/coordinateactivate/{token}", method=RequestMethod.GET)
    public Object activateCoordinates(@PathVariable String token){

        Hashtable<String, String> message = new Hashtable<String, String>();
        final JWTVerifier jwtVerifier = new JWTVerifier(utils.getSecret());
        try {
            final Map<String,Object> claims = jwtVerifier.verify(token);

            String usr_id=claims.get("user_id").toString();
            boolean active = Boolean.parseBoolean(claims.get("active").toString());

            Principal usract = principalRepository.findOne(Long.parseLong(usr_id));
            usract.getCoordinates().setActive(active);
            usract.getCoordinates().setDupdate(fechaActual);

            principalRepository.save(usract);
            //message.put("message","tarjeta de coordenadas activada con exito");
            //return message;
            return "redirect:/dashboard";

        }catch(Exception e){
            return e.getCause();
        }

    }

    //parsea y chequea las coordenadas de la TC de un usuario user_id
    @RequestMapping(value ="/checkcoordinate", method=RequestMethod.POST)
    public @ResponseBody Object validateCoordenada(Authentication auth,
                                                   @RequestBody CoordinatesDTO coordinatesDTO){
        Hashtable<String, String> message = new Hashtable<String, String>();
        try {

            Principal usrAct = principalRepository.findByEmail(auth.getName());  //.findOne(user_id);

            String textencrypted = usrAct.getCoordinates().getCoordinate();

            if(usrAct.getCoordinates().isActive()){

                String str = utils.Desencriptar(textencrypted);

                Properties props = new Properties();
                props.load(new StringReader(str.substring(1, str.length() - 1).replace(", ", "\n")));

                Map<String, String> crdStr = new HashMap<String, String>();

                for (Map.Entry<Object, Object> e : props.entrySet()) {
                    crdStr.put((String) e.getKey(), (String) e.getValue());
                }

                //Valor de la coordenada uno
                String valCrd1=crdStr.get(coordinatesDTO.getCrdOne());
                //Valor de la coordenada dos
                String valCrd2=crdStr.get(coordinatesDTO.getCrdTwo());

                boolean val1=false;
                if(valCrd1.equals(coordinatesDTO.getValOne())){
                    val1=true;
                }

                boolean val2=false;
                if(valCrd2.equals(coordinatesDTO.getValTwo())){
                    val2=true;
                }

                if (val1 && val2){
                    message.put("message","Autorized");
                }else{
                    message.put("message","Unautorized");
                }
                return message;
            }

            message.put("message","Coordinate card inactive");

            return message;

        }catch (Exception e){
            return e.getLocalizedMessage();
        }
    }

    /*
    * SHOPCART
    * */

    @RequestMapping(value = "/prepareflight", method=RequestMethod.POST)
    public @ResponseBody Object prepareFlight(@RequestBody PrepareflightDTO flight,
                                              Authentication auth){

        Principal p=principalRepository.findByEmail(auth.getName()); //.findOne(principal);

        final Myaircraft[] aircraft = {new Myaircraft()};
        p.getMyaircrafts().forEach((g_aircraft)->{
            if(g_aircraft.getId()==flight.getMyaircraft()){
                aircraft[0] =g_aircraft;
            }
        });

        final Mycaptain[] captain = {new Mycaptain()};
        p.getMycaptains().forEach((g_captain)->{
            if(g_captain.getId()==flight.getCaptain()){
                captain[0] =g_captain;
            }
        });

        /*Busqueda de precios segun los criterios seleccionados*/
        if(aircraft[0]!=null && captain[0]!=null){

            Set<PrepareflightpriceDTO> svcprices=new HashSet<PrepareflightpriceDTO>();

            /*Servicios precios por unidad*/
            List<Price> prices=price.findByLocationAndAviationAndValidtoGreaterThanEqual(flight.getLocation(), aircraft[0].getAviationtype(),flight.getLanding());
            prices.forEach((svcprice)->{
                if(!svcprice.isFeesenable()){
                    PrepareflightpriceDTO price=new PrepareflightpriceDTO();
                    price.setId(svcprice.getId());
                    price.setPricetype("U");
                    price.setPricename(svcprice.getName().toUpperCase());
                    price.setPricedesc(svcprice.getUnitdesc().toUpperCase());
                    price.setPrice(svcprice.getPrice1());
                    price.setCurrency(svcprice.getCurrency().toUpperCase());

                    svcprices.add(price);
                }
            });

            /*Servicios precios por rango de fecha*/
            List<Pricedate> pricesdate=pricedate.findByLocationAndAviationAndFromdateLessThanEqualAndTodateGreaterThanEqual(flight.getLocation(),aircraft[0].getAviationtype(),flight.getLanding(),flight.getLanding());

            pricesdate.forEach((pricedate)->{
                if(!pricedate.isFeesenable()) {
                    PrepareflightpriceDTO price = new PrepareflightpriceDTO();
                    price.setId(pricedate.getId());
                    price.setPricetype("D");
                    price.setPricename(pricedate.getName().toUpperCase());
                    price.setPricedesc(pricedate.getUnitdesc().toUpperCase());
                    price.setPrice(pricedate.getPrice1());
                    price.setCurrency(pricedate.getCurrency().toUpperCase());

                    svcprices.add(price);
                }
            });

            /*Servicios por rango de pounds*/

            List<Pricepound> pricespounds=pricepound.findByLocationAndAviationAndFrompoundLessThanEqualAndTopoundGreaterThanEqual(flight.getLocation(),aircraft[0].getAviationtype(),aircraft[0].getMtow(),aircraft[0].getMtow());

            pricespounds.forEach((pricepound)->{

                if(!pricepound.isFeesenable()) {
                    PrepareflightpriceDTO price = new PrepareflightpriceDTO();
                    price.setId(pricepound.getId());
                    price.setPricetype("P");
                    price.setPricename(pricepound.getName().toUpperCase());
                    price.setPricedesc(pricepound.getUnitdesc().toUpperCase());
                    price.setPrice(pricepound.getPrice1());
                    price.setCurrency(pricepound.getCurrency().toUpperCase());

                    svcprices.add(price);
                }
            });

            return svcprices;

        }

        return "faltan parametros para planificarte un vuelo";

    }
    //ShoppingCart - add shopcart
    @RequestMapping(value = "/shopcart", method=RequestMethod.POST)
    public @ResponseBody Object addItemCart(@RequestBody ShopcartDTO flight,
                                            Authentication auth){
        try{

            Principal p=principalRepository.findByEmail(auth.getName()); //.findOne(principal);

            final Myaircraft[] aircraft = {new Myaircraft()};
            p.getMyaircrafts().forEach((g_aircraft)->{
                if(g_aircraft.getId()==flight.getMyaircraft()){
                    aircraft[0] =g_aircraft;
                }
            });

            final Mycaptain[] captain = {new Mycaptain()};
            p.getMycaptains().forEach((g_captain)->{
                if(g_captain.getId()==flight.getCaptain()){
                    captain[0] =g_captain;
                }
            });

            Shopcart cart=new Shopcart();

            cart.setCaptain(flight.getCaptain());

            cart.setIncomingloc(flight.getIncomingloc()); //localidad de donde viene

            cart.setRdate(flight.getReturndate()); //Fecha de regreso

            cart.setName(flight.getDescription());
            cart.setLocation(flight.getLocation());
            cart.setMyaircraft(aircraft[0].getId());
            cart.setAviationtype(aircraft[0].getAviationtype());
            cart.setDcreate(fechaActual);
            cart.setDupdate(fechaActual);
            cart.setDlanding(flight.getLanding());

            Set<Itemcart> items = new HashSet<Itemcart>(0);

            flight.getItems().forEach((i)->{

                Itemcart item=new Itemcart();
                item.setProduct(i.getId()); //soruceid
                item.setPricetype(i.getPricetype());
                item.setPricename(i.getPricename());
                item.setPricedesc(i.getPricedesc());
                item.setQuantity(i.getQuantity());
                item.setUnitprice(i.getPrice());
                item.setTotalprice((i.getQuantity())*(i.getPrice()));

                items.add(item);
            });

            cart.setItems(items);

            p.getCart().add(cart);

            return principalRepository.save(p).getCart();

        }catch(Exception e){
            System.out.println(e.getCause());
            return null;
        }
    }
    //show all shopping cart from user
    @RequestMapping(value="/shopcart/show", method = RequestMethod.GET)
    public @ResponseBody Object getCartUser(Authentication auth){
        try{
            return principalRepository.findByEmail(auth.getName()).getCart();
        }catch(Exception e){
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/shopcart/{shopcartid}",method = RequestMethod.DELETE)
    public @ResponseBody Object hardDeleteMyshopcart(@PathVariable long shopcartid,
                                                     Authentication auth ) {
        Hashtable<String, String> message = new Hashtable<String, String>();
        try{
            Principal p=principalRepository.findByEmail(auth.getName());
            boolean found = false;
            for(Iterator<Shopcart> shop = p.getCart().iterator(); shop.hasNext();){
                Shopcart s = shop.next();
                long splitted = s.getId();
                if(splitted==shopcartid){
                    shop.remove();
                    found = true;
                }
            }
            if(found){
                principalRepository.save(p).getCart();
                message.put("message", "Operacion Exitosa");
            return message;
                //return new ResponseEntity<String>("Operacion Exitosa", HttpStatus.OK);
            }else{
                message.put("message", "Registro no encontrado");
            return message;
                //return new ResponseEntity<String>("Registro no encontrado", HttpStatus.BAD_REQUEST);
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Error en la operacion", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/shopcart/{shopcartid}/{itemcartid}",method = RequestMethod.DELETE)
    public @ResponseBody Object deleteCartItem(@PathVariable long shopcartid,
                                               @PathVariable long itemcartid,
                                               Authentication auth){

        Principal Pp=principalRepository.findByEmail(auth.getName());
        Set<Shopcart> myshops = Pp.getCart();

        final Shopcart[] selcart = {new Shopcart()};

        Set<Itemcart> items = new HashSet<Itemcart>(0);

        myshops.forEach((cart)->{
            if(cart.getId()==shopcartid){
                selcart[0] =cart;
            }
        });

        boolean found = false;
        for(Iterator<Itemcart> itm = selcart[0].getItems().iterator(); itm.hasNext();){
            Itemcart s = itm.next();
            long splitted = s.getId();
            if(splitted==itemcartid){
                itm.remove();
                found = true;
            }
        }

        shopcartRepository.save(selcart[0]);
        return selcart[0];

    }
    /*
    *MY AIRCRAFT
    * */
    @RequestMapping(value = "/myaircraft",method = RequestMethod.POST)
    @ApiMethod(description = "Permite al usuario añadir una aeronave a su lista de aeronaves")
    public @ResponseBody Object createMyaircraft(@RequestBody MyaircraftDTO dto,
                                                 Authentication auth) {
        Hashtable<String, String> message= new Hashtable<String, String>();

        try{

            Principal Pp=principalRepository.findByEmail(auth.getName()); //.findOne(principal_id);

            Aircraftype at=aircraftypeRepository.findOne(dto.getAircrafttype());

            Myaircraft my=new Myaircraft();

            my.setName(dto.getName());
            my.setModel(at.getManufacture());
            my.setAircraftype(dto.getAircrafttype());
            my.setCraftype(at.getCraftype());
            my.setSeries(at.getSeries());
            my.setEngines(at.getEngines());
            my.setUnits(at.getUnits());
            my.setMrmp(at.getMrmp());
            my.setMtow(at.getMtow());
            my.setOwe(at.getOwe());
            my.setMzfw(at.getMzfw());
            my.setMlgw(at.getMlgw());
            my.setMtank(at.getMtank());
            my.setCraftlength(at.getCraftlength());
            my.setCraftwidth(at.getCraftwidth());
            my.setAppspeed(at.getAppspeed());
            my.setWingspan(at.getWingspan());
            my.setTailheight(at.getTailheight());
            my.setArc(at.getArc());
            my.setActive(true);
            my.setAviationtype(dto.getAviationtype());
            my.setTailnumber(dto.getTailnumber().toUpperCase());

            Pp.getMyaircrafts().add(my);

            principalRepository.save(Pp);

            return Pp.getMyaircrafts();

        }catch(Exception e){

        }


        return null;
    }

    @RequestMapping(value = "/myaircraft/{aircraftid}",method = RequestMethod.GET)
    public @ResponseBody Object retrieveMyaircraft(@PathVariable long aircraftid,
                                                   Authentication auth){
        try{
            Set<Myaircraft> myaircrafts = principalRepository.findByEmail(auth.getName()).getMyaircrafts(); //.findOne(principal).getMyaircrafts();

            final Myaircraft[] airselected = new Myaircraft[1];
            myaircrafts.forEach((aircraft)->{
                if((aircraft.getId() == aircraftid)){
                    airselected[0] =aircraft;
                }
            });/**/

            if(airselected[0].isDeleted()){
                return new ResponseEntity<String>("El recurso no esta disponible", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }else{
                return airselected[0];
            }

        }catch (Exception e){
            return e.getMessage();
        }
    }

    @RequestMapping(value="/myaircraft/show", method = RequestMethod.GET)
    public @ResponseBody Object getAircraftsUser(Authentication auth){
        try{
            Set<Myaircraft> aircrafts = principalRepository.findByEmail(auth.getName()).getMyaircrafts();
            return aircrafts ; //.findOne(user_id).getCart();
        }catch(Exception e){
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/myaircraft",method = RequestMethod.PATCH)
    public @ResponseBody String[] updateMyaircraft(Authentication auth,
                                                 @RequestBody MyaircraftDTO myaircraft)/**/{

        try{
            Principal p=principalRepository.findByEmail(auth.getName()); //.findOne(principal);
            Set<Myaircraft> my = p.getMyaircrafts();

            Aircraftype at=aircraftypeRepository.findOne(myaircraft.getAircrafttype());

            my.forEach((air)->{
                if(air.getId()==myaircraft.getId()){

                    air.setName(myaircraft.getName());
                    air.setAviationtype(myaircraft.getAviationtype());
                    air.setTailnumber(myaircraft.getTailnumber().toUpperCase());
                    air.setAircraftype(myaircraft.getAircrafttype());
                    air.setModel(at.getManufacture());
                    air.setCraftype(at.getCraftype());
                    air.setSeries(at.getSeries());
                    air.setEngines(at.getEngines());
                    air.setUnits(at.getUnits());
                    air.setMrmp(at.getMrmp());
                    air.setMtow(at.getMtow());
                    air.setOwe(at.getOwe());
                    air.setMzfw(at.getMzfw());
                    air.setMlgw(at.getMlgw());
                    air.setMtank(at.getMtank());
                    air.setCraftlength(at.getCraftlength());
                    air.setCraftwidth(at.getCraftwidth());
                    air.setAppspeed(at.getAppspeed());
                    air.setWingspan(at.getWingspan());
                    air.setTailheight(at.getTailheight());
                    air.setArc(at.getArc());
                    air.setActive(myaircraft.isActive());
                }
            });

            principalRepository.save(p);

            return new String[]{"message", "success"};
            //return new ResponseEntity<String>("Operacion Exitosa", HttpStatus.OK);

        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            return new String[]{"message", "failure"};
            //return new ResponseEntity<String>("Hubo un peo alli", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/myaircraft/{aircraftid}",method = RequestMethod.DELETE)
    public @ResponseBody Object softdeleteMyaircraft(@PathVariable long aircraftid,
                                                     Authentication auth ) {
        Hashtable<String, String> message = new Hashtable<String, String>();
        try{
            Principal p=principalRepository.findByEmail(auth.getName()); //.findOne(principal);
            Set<Myaircraft> pmy = p.getMyaircrafts();
            pmy.forEach((aircraft)->{
                if(aircraft.getId()==aircraftid){
                    aircraft.setDeleted(true);
                }
            });

            principalRepository.save(p);
            message.put("message", "Operacion Exitosa");
            return message;

        }catch(Exception e){
            System.out.println(e.getMessage());
            message.put("message", "Error en la operacion");
            return message;
            //return new ResponseEntity<String>("Error en la operacion", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /*My Captain */
    @RequestMapping(value = "/mycaptain",method = RequestMethod.POST)
    @ApiMethod(description = "Permite al usuario añadir un capitán(piloto) a su lista de capitanes")
    public @ResponseBody Object createMycaptain(@RequestBody Mycaptain captain,
                                                Authentication auth ) {

        Hashtable<String, String> message = new Hashtable<String, String>();
        try{

            Principal Pp=principalRepository.findByEmail(auth.getName()); //.findOne(principal_id);
            Set<Mycaptain> capitains=Pp.getMycaptains();
            capitains.add(captain);
            principalRepository.save(Pp);


            message.put("message", "Operacion Exitosa");
            return message;
            //return new ResponseEntity<String>("Operación exitosa", HttpStatus.OK);

        }catch(Exception e){

            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "/mycaptain/{captainId}",method = RequestMethod.GET)
    public @ResponseBody Object retrieveMycaptain(@PathVariable long captainId,
                                                  Authentication auth){
        Hashtable<String, String> message = new Hashtable<String, String>();
        try{
            Set<Mycaptain> captains = principalRepository.findByEmail(auth.getName()).getMycaptains(); //.findOne(principal).getMycaptains();
            final Mycaptain[] mycaptain = new Mycaptain[1];
            captains.forEach((captain)->{
                if((captain.getId() == captainId)){
                    mycaptain[0] =captain;
                }
            });
            if(mycaptain[0].isDeleted()){
                message.put("message", "El recurso no esta disponible");
            return message;
                //return new ResponseEntity<String>("El recurso no esta disponible", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }else{
                return mycaptain[0];
            }
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @RequestMapping(value="/mycaptain/show", method = RequestMethod.GET)
    public @ResponseBody Object getCaptainsUser(Authentication auth){

        try{
            Set<Mycaptain> captains = principalRepository.findByEmail(auth.getName()).getMycaptains();
            return captains ; //.findOne(user_id).getCart();
        }catch(Exception e){
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/mycaptain",method = RequestMethod.PATCH)
    public @ResponseBody Object updateMycaptain( Authentication auth,
                                                 @RequestBody Mycaptain captain){
        Hashtable<String, String> message = new Hashtable<String, String>();
        try{
            Principal p=principalRepository.findByEmail(auth.getName()); //.findOne(principal);
            Set<Mycaptain> mycapitains = p.getMycaptains();

            mycapitains.forEach((cap)->{
                if(cap.getId()==captain.getId()){
                    cap.setName(captain.getName());
                    cap.setLicense(captain.getLicense());
                    cap.setDateofbirth(captain.getDateofbirth());
                    cap.setAddress(captain.getAddress());
                    cap.setCity(captain.getCity());
                    cap.setCountry(captain.getCountry());
                    cap.setPhone(captain.getPhone());
                    cap.setEmail(captain.getEmail());
                    cap.setActive(captain.isActive());
                }
            });

            principalRepository.save(p);
            message.put("message", "Operacion Exitosa");
            return message;
            //return new ResponseEntity<String>("Operacion Exitosa", HttpStatus.OK);

        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            message.put("message", "Hubo un peo alli");
            return message;
            //return new ResponseEntity<String>("Hubo un peo alli", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/mycaptain/{captainId}",method = RequestMethod.DELETE)
    public @ResponseBody Object softdeleteMycaptain(@PathVariable long captainId,
                                                    Authentication auth ) {
        Hashtable<String, String> message = new Hashtable<String, String>();
        try{
            Principal p=principalRepository.findByEmail(auth.getName()); //.findOne(principal);
            Set<Mycaptain> mycaptains = p.getMycaptains();
            mycaptains.forEach((captain)->{
                if(captain.getId()==captainId){
                    captain.setDeleted(true);
                }
            });

            principalRepository.save(p);
            message.put("message", "Operacion Exitosa");
            return message;
            //return new ResponseEntity<String>("Operacion Exitosa", HttpStatus.OK);

        }catch(Exception e){
            System.out.println(e.getMessage());
            message.put("message", "Error en la operacion");
            return message;
            //return new ResponseEntity<String>("Error en la operacion", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /*
    *SERVICES REQUESTS
    * */

    @RequestMapping(value = "/servicerequest", method = RequestMethod.GET)
    public @ResponseBody List<Servicerequest> shoyMyServicesRequests(Authentication auth ){
        try{
            Principal Pp = principalRepository.findByEmail(auth.getName());
            List<Servicerequest> svc=servicerequestRepository.findByPrincipal(Pp.getId());

            return svc;
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }


    //Tarjeta de Coordenadas
    String getLetra(int numero){
        switch (numero){
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            case 4:
                return "E";
        }
        return null;
    }

    //Tarjeta de Coordenadas
    int getNumero(String letra){
        switch (letra){
            case "A":
                return 0;
            case "B":
                return 1;
            case "C":
                return 2;
            case "D":
                return 3;
            case "E":
                return 4;
        }
        return 9;
    }

    //Verificacion de email si existe
    boolean principalExist(String email){
        Principal Pp =principalRepository.findByEmail(email);
        if (Pp==null) {
            return false;
        }
            return false;
    }

}
