package jd.controllers;

import jd.Util.AppUtils;
import jd.persistence.dto.*;
import jd.persistence.model.*;
import jd.persistence.repository.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.omg.IOP.ExceptionDetailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by eduardom on 10/8/16.
 */

@Controller
@RequestMapping(value="/servicerequest")
public class ServicerequestController {

    AppUtils utils = new AppUtils();

    ServicerequestRepository servicerequestRepository;

    @Autowired
    private Environment env;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    LocationRepository loc;

    @Autowired
    PrincipalRepository principalRepository;

    @Autowired
    PaymethodRepository paymethodRepository;

    @Autowired
    ShopcartRepository shopcartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PriceRepository price;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    PricepoundRepository pricepound;

    @Autowired
    PricedateRepository pricedate;

    @Autowired
    DeferedpayRepository deferedpay;

    @Autowired
    ServiceticketRepository ticketrepo;


    Date fechaActual = new Date();

    @Autowired
    public ServicerequestController(ServicerequestRepository servicerequestRepository) {
        this.servicerequestRepository = servicerequestRepository;
    }

    //Create a new servicerequet
    @RequestMapping(value = "/{paymethod_id}/{shopcart_id}",method = RequestMethod.POST)
    public @ResponseBody Object generateServiceRequest(Authentication auth,
                                         @PathVariable long paymethod_id,
                                         @PathVariable long shopcart_id,
                                          HttpServletResponse httpServletResponse,
                                                       HttpServletRequest request) throws IOException, JRException, MessagingException {
        String CACMail="efernandez.ve@gmail.com";
        String OPERMail="efernandez.ve@gmail.com";

        try{
                Hashtable<String, String> message= new Hashtable<String, String>();
                final boolean[] owmpay = {false};
                final boolean[] owncart = {false};
                final Paymethod[] paymethod = new Paymethod[1];
                final Shopcart[] shopcart = new Shopcart[1];
                final Myaircraft[] aircraftGo = new Myaircraft[1];

                Principal Pp= principalRepository.findByEmail(auth.getName()); //.findOne(principal_id);

                //Verifico si el paymethod es del usuario
                Pp.getPayments().forEach((pay)->{
                    if(pay.getPayid()==paymethod_id && pay.getPaytype().equals("JDCARD")){
                        owmpay[0] =true;
                        paymethod[0] =pay; //Metodo de pago que ha seleccionado el usuario
                    }
                });
                if (!owmpay[0]){
                    message.put("message","algo pasa, esta tarjeta no le pertenece a este usuario");
                    return message;
                }

                //Verifico si el carro de compras le pertenece al principal
                Pp.getCart().forEach((cart)->{
                    if(cart.getId()==shopcart_id){
                        owncart[0] =true;
                        shopcart[0] =cart;
                    }
                });
                if (!owncart[0]){
                    message.put("message","algo pasa, este carro de compras no le pertenece a este usuario");
                    return message;
                }

                Pp.getMyaircrafts().forEach((myaircraft)->{
                    if(shopcart[0].getMyaircraft()==myaircraft.getId()){
                        aircraftGo[0]=myaircraft;
                    }
                });

                System.out.println("aircraft del shopcart es "+shopcart[0].getMyaircraft());

                if (aircraftGo[0]== null){
                    message.put("message","algo pasa, esta aeronave al parecer no le pertenece a este usuario");
                    return message;
                }

                Set<Itemcart> cartitems = new HashSet<Itemcart>(0); //Items del carro de compra (servicios)
                Set<Itemrequest> itemsrequest = new HashSet<Itemrequest>(0); //Items del service request, su fuente es el caritems
                Set <rptServiceItemRequestDTO> irequestDTOs = new HashSet<rptServiceItemRequestDTO>(0);

                Servicerequest servicerequest = new Servicerequest();

                final double[] serviceamount={0};
                final double serviceGuarantee;

                cartitems= shopcart[0].getItems();

                cartitems.forEach((citem)->{

                    Itemrequest ir=new Itemrequest();

                    rptServiceItemRequestDTO irDTO=new rptServiceItemRequestDTO();

                    switch (citem.getPricetype()){
                        case "U":
                            Price Pc= price.findOne(citem.getProduct());
                            ir.setProduct(Pc.getId());//sourceID
                            ir.setQuantity(citem.getQuantity());
                            ir.setUnitprice(Pc.getPrice1());
                            ir.setTotalprice(citem.getQuantity()*Pc.getPrice1());
                            ir.setChecked(true);
                            ir.setPricename(Pc.getName());
                            ir.setPricedesc(citem.getPricedesc());
                            ir.setPricetype("U");

                            irDTO.setPricedesc(citem.getPricedesc().toUpperCase());
                            irDTO.setAmount(ir.getTotalprice());
                            irDTO.setDescription(Pc.getName().toUpperCase());
                            irDTO.setQuantity(citem.getQuantity());
                            irDTO.setMeasure(Pc.getMeasure().toUpperCase());

                            break;

                        case "D":
                            Pricedate Pd= pricedate.findOne(citem.getProduct());
                            ir.setProduct(Pd.getId());//sourceID
                            ir.setQuantity(citem.getQuantity());
                            ir.setUnitprice(Pd.getPrice1());
                            ir.setTotalprice(citem.getQuantity()*Pd.getPrice1());
                            ir.setChecked(true);
                            ir.setPricename(Pd.getName());
                            ir.setPricedesc(citem.getPricedesc());
                            ir.setPricetype("D");

                            irDTO.setPricedesc(citem.getPricedesc().toUpperCase());
                            irDTO.setAmount(ir.getTotalprice());
                            irDTO.setDescription(Pd.getName().toUpperCase());
                            irDTO.setQuantity(citem.getQuantity());
                            irDTO.setMeasure(Pd.getMeasure().toUpperCase());

                            break;

                        case "P":
                            Pricepound Po= pricepound.findOne(citem.getProduct());
                            ir.setProduct(Po.getId());//sourceID
                            ir.setQuantity(citem.getQuantity());
                            ir.setUnitprice(Po.getPrice1());
                            ir.setTotalprice(citem.getQuantity()*Po.getPrice1());
                            ir.setChecked(true);
                            ir.setPricename(Po.getName());
                            ir.setPricedesc(citem.getPricedesc());
                            ir.setPricetype("P");

                            irDTO.setPricedesc(citem.getPricedesc().toUpperCase());
                            irDTO.setAmount(ir.getTotalprice());
                            irDTO.setDescription(Po.getName().toUpperCase());
                            irDTO.setQuantity(citem.getQuantity());
                            irDTO.setMeasure(Po.getMeasure().toUpperCase());

                            break;
                    }

                    irequestDTOs.add(irDTO);
                    itemsrequest.add(ir);
                    serviceamount[0]=serviceamount[0]+ir.getTotalprice();

                });

            serviceGuarantee= serviceamount[0]*0.15;  //Porcentaje de garantia

            servicerequest.setPrincipal(Pp.getId());
            servicerequest.setPaymethod(paymethod[0].getPayid());
            servicerequest.setGuarantee(serviceGuarantee);
            servicerequest.setAmount(serviceamount[0]);
            servicerequest.setLocation(shopcart[0].getLocation());
            servicerequest.setAviationtype(shopcart[0].getAviationtype());
            servicerequest.setMyaircraft(aircraftGo[0].getId());
            servicerequest.setRdate(shopcart[0].getRdate());

            servicerequest.setDcreate(fechaActual);
            servicerequest.setDupdate(fechaActual);

            /*Calcula los dias de expiracion de un service request*/
            Calendar c = Calendar.getInstance();
            c.setTime(fechaActual);
            c.add(Calendar.DATE, 15);

            String serialcode="svc_"+utils.getCadenaAlfaNumAleatoria(15);

            servicerequest.setDexpired(c.getTime());
            servicerequest.setDlanding(shopcart[0].getDlanding());
            servicerequest.setReleased(false);
            servicerequest.setSerialcode(serialcode);

            if(serviceamount[0]<= paymethod[0].getPayavailable()){ //verifico si tiene saldo disponible

                //String[] classpathEntries = classpath.split(File.pathSeparator);

                System.out.println("classpath: "+System.getProperty("java.class.path"));

                System.out.println("ruta: "+java.lang.System.getProperty("user.dir"));
                System.out.println("Tiene saldo");
                System.out.println("balance "+paymethod[0].getPaybalance());
                System.out.println("Monto operacion "+serviceamount[0]);
                System.out.println(serialcode);

                Location airport=loc.findOne(shopcart[0].getLocation());
                servicerequest.setItems(itemsrequest);

                //servicerequestRepository.save(servicerequest);
                servicerequestRepository.saveAndFlush(servicerequest);

                /*genera el pdf*/

                Map<String,Object> params = new HashMap<String,Object>();

                params.put("fcreate",fechaActual);

                params.put("client",Pp.getName().toUpperCase());
                params.put("email",Pp.getEmail());
                params.put("guarantee",serviceGuarantee);
                params.put("locationiata",airport.getIATA());

                params.put("id",servicerequest.getId());

                params.put("landing",shopcart[0].getDlanding());
                params.put("returndate",shopcart[0].getRdate()); //return date

                params.put("locationicao",airport.getICAO());
                params.put("tail",aircraftGo[0].getTailnumber());

                params.put("airport",airport.getName().toUpperCase());
                params.put("city",airport.getCity().toUpperCase());
                params.put("typeaviation",getAviationname(aircraftGo[0].getAviationtype()));

                params.put("craftype",aircraftGo[0].getCraftype());

                params.put("mpound",aircraftGo[0].getMtow());
                params.put("serialcode",serialcode);

                JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(irequestDTOs);

                JasperPrint jasperPrint= JasperFillManager.fillReport(java.lang.System.getProperty("user.home")+"/fussyfiles/reports/rptservicerequest.jasper",
                        params,beanCollectionDataSource);

                /*se crea el pdf en el directorio*/
                System.out.println("Tratando de escrbir el pdf en el direcorio");

                JasperExportManager.exportReportToPdfFile(jasperPrint,
                        java.lang.System.getProperty("user.home")+"/fussyfiles/principal/"+serialcode+".pdf");

                System.out.println("se guardo en "+java.lang.System.getProperty("user.home")+"/fussyfiles/principal/"+serialcode+".pdf");

                /*Se envia el correo al cliente*/
                MimeMessage msg = mailSender.createMimeMessage();

                // use the true flag to indicate you need a multipart message
                MimeMessageHelper helper = new MimeMessageHelper(msg, true);
                helper.setTo(CACMail);
                helper.addBcc(OPERMail);
                helper.setText("El Cliente "+Pp.getName()+" "+Pp.getLastname()+", ha generado satisfactoriamente un service request " +
                        "se debe hacer un service release para la validación");
                helper.setSubject("Han generado un Service Request");

                // let's attach the infamous windows Sample file (this time copied to c:/)
                FileSystemResource file = new FileSystemResource(java.lang.System.getProperty("user.home")+"/fussyfiles/principal/"+serialcode+".pdf");

                helper.addAttachment(serialcode+".pdf", file);

                /*Service request para los proveedores*/

                //itemsrequest

                /*endmail*/

                /*Genero el Bloqueo del pago*/
                Deferedpay defered=new Deferedpay();
                defered.setDescription("Service Request # "+serialcode);
                defered.setPaymethod(paymethod[0].getPayid());
                defered.setServicerequest(servicerequest.getId());
                defered.setDefertype("JDEBIT");
                defered.setAmount(serviceamount[0]+serviceGuarantee);
                defered.setDcreate(new Date());
                defered.setPending(true);

                deferedpay.save(defered);

                message.put("message","Service request generado");

                mailSender.send(msg);

            }else{
                message.put("message","Saldo insuficiente");
            }
            return message;

        }catch(Exception e){
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/manage/prepareticket/{servicerequest}",method = RequestMethod.GET)
    public @ResponseBody Object prepareTicket(@PathVariable long servicerequest){
        try{

            Servicerequest sr=servicerequestRepository.findOne(servicerequest);
            Principal Pp=principalRepository.findOne(sr.getPrincipal());

            final Myaircraft[] PpAir = {new Myaircraft()};

            Pp.getMyaircrafts().forEach((aircft)->{
                if(aircft.getId()==sr.getMyaircraft()){
                    PpAir[0] =aircft;
                }
            });

            Location loc= locationRepository.findOne(sr.getLocation());

            generateTicketDto prepare=new generateTicketDto();

            prepare.setServicerequest(sr.getId());
            prepare.setPrincipal(sr.getPrincipal());
            prepare.setPrincipalname(Pp.getName().toUpperCase()+" "+Pp.getLastname().toUpperCase());
            prepare.setLocation(sr.getLocation());
            prepare.setLocationname(loc.getIATA()+" "+loc.getName().toUpperCase()+" - "+loc.getCity().toUpperCase());
            prepare.setDlanding(sr.getDlanding());
            prepare.setDcreate(sr.getDcreate());
            prepare.setDupdate(sr.getDupdate());
            prepare.setSerialcode(sr.getSerialcode());


            /*Feeds and Taxes from fly*/
            Set<Itemrequest> feeds= new HashSet<>();
             /*por unidad*/
            List<Price> prices=price.findByLocationAndAviationAndValidtoGreaterThanEqual(sr.getLocation(), sr.getAviationtype(),sr.getDlanding());
            prices.forEach((svcprice)->{
                if(svcprice.isFeesenable()){

                    Itemrequest feed= new Itemrequest();

                    feed.setProduct(svcprice.getId());
                    feed.setPricetype("U");
                    feed.setPricename(svcprice.getName().toUpperCase());
                    feed.setPricedesc(svcprice.getUnitdesc().toUpperCase());
                    feed.setUnitprice(svcprice.getPrice1());
                    feed.setQuantity(0);
                    feed.setCatalog(5);

                    feeds.add(feed);
                }
            });

            /*por rango de fecha*/
            List<Pricedate> pricesdate=pricedate.findByLocationAndAviationAndFromdateLessThanEqualAndTodateGreaterThanEqual(sr.getLocation(),sr.getAviationtype(),sr.getDlanding(),sr.getDlanding());

            pricesdate.forEach((pricedate)->{
                if(pricedate.isFeesenable()) {
                    Itemrequest feed= new Itemrequest();

                    feed.setProduct(pricedate.getId());
                    feed.setPricetype("D");
                    feed.setPricename(pricedate.getName().toUpperCase());
                    feed.setPricedesc(pricedate.getUnitdesc().toUpperCase());
                    feed.setUnitprice(pricedate.getPrice1());
                    feed.setQuantity(0);
                    feed.setCatalog(5);

                    feeds.add(feed);
                }
            });

            /*rango de pounds*/

            List<Pricepound> pricespounds=pricepound.findByLocationAndAviationAndFrompoundLessThanEqualAndTopoundGreaterThanEqual(sr.getLocation(),sr.getAviationtype(),PpAir[0].getMtow(),PpAir[0].getMtow());

            pricespounds.forEach((pricepound)->{

                if(pricepound.isFeesenable()) {
                    Itemrequest feed= new Itemrequest();

                    feed.setProduct(pricepound.getId());
                    feed.setPricetype("P");
                    feed.setPricename(pricepound.getName().toUpperCase());
                    feed.setPricedesc(pricepound.getUnitdesc().toUpperCase());
                    feed.setUnitprice(pricepound.getPrice1());
                    feed.setQuantity(0);
                    feed.setCatalog(5);

                    feeds.add(feed);
                }
            });

            Set<Itemrequest> prepareItems=new HashSet<>();
            prepareItems.addAll(sr.getItems());
            prepareItems.addAll(feeds);

            prepare.setItems(prepareItems);

            return prepare;

        }catch(Exception e){
            return e.getLocalizedMessage();
        }
    }

    @RequestMapping(value = "/manage/generateticket",method = RequestMethod.POST)
    public  @ResponseBody String[] geneateTicket(@RequestBody newTicketDto rdto,
                                                 HttpServletResponse httpServletResponse,
                                                 HttpServletRequest request) throws JRException, IOException {

        Servicerequest sr=servicerequestRepository.findOne(rdto.getServicerequest());
        Principal Pp = principalRepository.findOne(sr.getPrincipal());

        /*Recuerda verificar que ya haya sido cerrado el sr.*/

        //Verifico si el paymethod es del usuario
        final boolean[] owmpay = {false};
        final Paymethod[] paymethod = new Paymethod[1];

        Pp.getPayments().forEach((pay)->{
            if(pay.getPayid()==sr.getPaymethod() && pay.getPaytype().equals("JDCARD")){
                owmpay[0] =true;
                paymethod[0] =pay; //Metodo de pago que ha seleccionado el usuario
            }
        });
        if (!owmpay[0]){
            return new String[]{"message","algo pasa, esta tarjeta no esta relacionada con este usuario"};
        }

        /*El aircraft que viajó*/
        final Myaircraft[] aircraftGo = new Myaircraft[1];
        Pp.getMyaircrafts().forEach((myaircraft)->{
            if(sr.getMyaircraft()==myaircraft.getId()){
                aircraftGo[0]=myaircraft;
            }
        });
        if (aircraftGo[0]== null){
            return new String[]{"message","algo pasa, esta aeronave al parecer no le pertenece a este usuario o ya no esta disponible"};
        }

        Serviceticket st=new Serviceticket();
        Set<Itemticket> ticketitems = new HashSet<Itemticket>(0);

        /*Coleccion para el reporte de los items del ticket*/
        //Set <rptServiceItemTicketDto> iticketDTOs = new HashSet<rptServiceItemTicketDto>(0);
        List<rptServiceItemTicketDto>iticketDTOs=new ArrayList<rptServiceItemTicketDto>();

        final double[] serviceamount={0};

        rdto.getItems().forEach((titem)->{
            Itemticket it=new Itemticket();
            rptServiceItemTicketDto irDTO=new rptServiceItemTicketDto(); //para el reporte

            switch (titem.getPricetype()){
                case "U":
                    Price Pc= price.findOne(titem.getProduct());

                    it.setProduct(Pc.getId()); //sourceID
                    it.setCatalog(titem.getCatalog());
                    it.setPricename(Pc.getName());
                    it.setPricedesc(Pc.getUnitdesc());
                    it.setPricetype("U");
                    it.setProvider(Pc.getProvider());
                    it.setQuantity(titem.getQuantity());
                    it.setUnitprice(Pc.getPrice1());
                    it.setTotalprice(it.getQuantity()*it.getUnitprice());

                    irDTO.setFeenabled(Pc.isFeesenable());
                    irDTO.setPricedesc(titem.getPricedesc());
                    irDTO.setAmount(it.getTotalprice());
                    irDTO.setDescription(Pc.getName());

                    break;

                case "D":
                    Pricedate Pd= pricedate.findOne(titem.getProduct());

                    it.setProduct(Pd.getId()); //sourceID
                    it.setCatalog(titem.getCatalog());
                    it.setPricename(Pd.getName());
                    it.setPricedesc(Pd.getUnitdesc());
                    it.setPricetype("D");
                    it.setProvider(Pd.getProvider());
                    it.setQuantity(titem.getQuantity());
                    it.setUnitprice(Pd.getPrice1());
                    it.setTotalprice(it.getQuantity()*it.getUnitprice());

                    irDTO.setFeenabled(Pd.isFeesenable());
                    irDTO.setPricedesc(titem.getPricedesc());
                    irDTO.setAmount(it.getTotalprice());
                    irDTO.setDescription(Pd.getName());

                    break;

                case "P":
                    Pricepound Po= pricepound.findOne(titem.getProduct());

                    it.setProduct(Po.getId()); //sourceID
                    it.setCatalog(titem.getCatalog());
                    it.setPricename(Po.getName());
                    it.setPricedesc(Po.getUnitdesc());
                    it.setPricetype("P");
                    it.setProvider(Po.getProvider());
                    it.setQuantity(titem.getQuantity());
                    it.setUnitprice(Po.getPrice1());
                    it.setTotalprice(it.getQuantity()*it.getUnitprice());

                    irDTO.setFeenabled(Po.isFeesenable());
                    irDTO.setPricedesc(titem.getPricedesc());
                    irDTO.setAmount(it.getTotalprice());
                    irDTO.setDescription(Po.getName());

                    break;
            }
            ticketitems.add(it);
            iticketDTOs.add(irDTO);
            serviceamount[0]=serviceamount[0]+it.getTotalprice();
        });

        /*Preparo y guardo el service ticket*/
        Serviceticket ticket=new Serviceticket();

        ticket.setTicket(rdto.getTicket());
        ticket.setAmount(serviceamount[0]);
        ticket.setPaymethod(sr.getPaymethod());
        ticket.setAviationtype(sr.getAviationtype());
        ticket.setLocation(sr.getLocation());
        ticket.setDcreate(sr.getDcreate());
        ticket.setDupdate(new Date());
        ticket.setDlanding(sr.getDlanding());
        ticket.setPrincipal(sr.getPrincipal());
        ticket.setSerialcode(sr.getSerialcode());
        ticket.setClosed(true);

        ticket.setItems(ticketitems);
        ticketrepo.saveAndFlush(ticket);

        /*Desbloqueo el pago del service request*/
        Deferedpay defered=deferedpay.findByServicerequestIs(sr.getId());
        deferedpay.delete(defered);

        /*Realizo el Cobro del Monto Correspondiente a la JDcard*/
         Tranpay jd_etpm = new Tranpay();
         jd_etpm.setTrantype("JDEBIT");
         jd_etpm.setTranamount(-(serviceamount[0]));
         jd_etpm.setTrandate(fechaActual);
         jd_etpm.setTranupdate(fechaActual);
         jd_etpm.setTrantoken("JD_" + utils.getCadenaAlfaNumAleatoria(9));
         jd_etpm.setTranstatus("SUCCEEDED");
         paymethod[0].getTransactionspayments().add(jd_etpm);  //Guardo la transacción justo aqui.
        //paymethodRepository.save(paymethod[0]); /*Creo que esto no es necesario*/

        /*Actualizo el Service request*/
        sr.setClosed(true);
        sr.setDupdate(new Date());
        sr.setTicket(true);
        servicerequestRepository.save(sr);

        /*Preciso la localidad*/
        Location airport=loc.findOne(ticket.getLocation());

        /*Genero el PDF del ServiceTicket*/
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("fcreate",fechaActual);
        params.put("client",Pp.getName().toUpperCase());
        params.put("email",Pp.getEmail());
        params.put("locationiata",airport.getIATA());
        params.put("airport",airport.getName().toUpperCase());
        params.put("city",airport.getCity().toUpperCase());
        params.put("typeaviation",getAviationname((int)ticket.getAviationtype()));
        params.put("craftype",aircraftGo[0].getCraftype());
        params.put("mpound",aircraftGo[0].getMtow());
        params.put("serialcode",ticket.getSerialcode());

        //Le doy un orden a los items, para que el report pueda agrupar los resultados correctamente
        Collections.sort(iticketDTOs,(dto1,dto2)->
                Boolean.compare(dto1.isFeenabled(),dto2.isFeenabled())
        );

        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(iticketDTOs);

        JasperPrint jasperPrint= JasperFillManager.fillReport(java.lang.System.getProperty("user.home")+"/fussyfiles/reports/rptserviceticket.jasper",
                params,beanCollectionDataSource);

        System.out.println("Tratando de escrbir el pdf en el direcorio");

        httpServletResponse.getOutputStream();
        httpServletResponse.addHeader("Content-disposition","attachment; filename=serviceticket.pdf");
        ServletOutputStream stream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
        stream.flush();
        stream.close();

        System.out.println("Listo!");

        return new String[]{"message","success"};
        /*
        JasperExportManager.exportReportToPdfFile(jasperPrint,
                java.lang.System.getProperty("user.home")+"/fussyfiles/principal/"+ticket.getSerialcode()+".pdf");
        JasperExportManager.exportReportToPdfFile(jasperPrint, "tigre.pdf");
        */

    }

    @RequestMapping(value = "/manage/isreleased/{id}",method = RequestMethod.POST)
    public @ResponseBody String[] generateRelesease(@PathVariable long id) throws MessagingException {

        System.out.println("id "+id);
        Servicerequest sr = servicerequestRepository.findOne(id);
        Principal Pp = principalRepository.findOne(sr.getPrincipal());

        if(sr.isClosed()){
            System.out.println("lo siento, no puedo hacer release a un servicerequest cerrado");
            return new String[]{"message","failure"};
        }

        sr.setReleased(true);
        if(sr.getDlanding().before(new Date()) || sr.getDlanding().equals(new Date()) ){ // si el sr es antes de hoy

             /*Se envia el correo al cliente*/
            MimeMessage msg = mailSender.createMimeMessage();

            // use the true flag to indicate you need a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(Pp.getEmail());
            helper.setText("Estimado "+Pp.getName()+" "+Pp.getLastname()+", hemos validado satisfactoriamente su service request ");

            helper.setSubject("Tu service request se ha validado");

            // let's attach the infamous windows Sample file (this time copied to c:/)
            FileSystemResource file = new FileSystemResource(java.lang.System.getProperty("user.home")+"/fussyfiles/principal/"+sr.getSerialcode()+".pdf");

            helper.addAttachment(sr.getSerialcode()+".pdf", file);

            mailSender.send(msg);

            servicerequestRepository.save(sr);

            return new String[]{"message","success"};

        }else{
            System.out.println("Fecha fuera del periodo");
            return new String[]{"message","failure"};
        }
    }

    @RequestMapping(value = "/manage/all",method = RequestMethod.GET)
    public @ResponseBody List<Servicerequest> showServicesRequests(){
        try{
            return servicerequestRepository.findAll();
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

    @RequestMapping(value = "/manage/pending",method = RequestMethod.GET)
    public @ResponseBody Object showServicesRequestsPending(){
        List<Object[]> results= servicerequestRepository.findPending();

        Set<showServicesRequestDto> shw = new HashSet<showServicesRequestDto>(0);

        results.stream().forEach((record) -> {
            showServicesRequestDto dto = new showServicesRequestDto();

            try {

                dto.setServicerequest(((BigInteger) record[0]).longValue());
                dto.setPrincipalname(((String) record[1]).toUpperCase());
                dto.setLocationname(((String) record[2]).toUpperCase());

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

                Date createDate = df.parse((String) record[3]);
                Date landingDate = new Date(((Timestamp)record[4]).getTime());


                dto.setDcreate(createDate);
                dto.setDlanding(landingDate);
                dto.setSerialcode((String) record[5]);

                shw.add(dto);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        });

        return shw;
    }

    @RequestMapping(value = "/manage/open",method = RequestMethod.GET)
    public @ResponseBody List<Servicerequest> showServicesRequestsOpen(){
        try{

            Set<Servicerequest>srs=new HashSet<>();
            srs.addAll(servicerequestRepository.findByClosedFalse());


            return servicerequestRepository.findByClosedFalse();
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

    @RequestMapping(value = "/manage/close",method = RequestMethod.GET)
    public @ResponseBody List<Servicerequest> showServicesRequestsClose(){
        try{
            return servicerequestRepository.findByClosedTrue();
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

    @RequestMapping(value = "/pending",method = RequestMethod.GET)
    public @ResponseBody List<showServicesRequestDto> showServicesRequestePendingUser(Authentication auth){

        Principal Pp = principalRepository.findByEmail(auth.getName());
        List<Object[]> results= servicerequestRepository.findForUser(Pp.getId(),false);
        List<showServicesRequestDto> shw = new ArrayList<>(0);
        results.stream().forEach((record) -> {
            showServicesRequestDto dto = new showServicesRequestDto();
            try {
                dto.setServicerequest(((BigInteger) record[0]).longValue());
                dto.setPrincipal(((BigInteger) record[1]).longValue());
                dto.setPrincipalname(((String) record[2]).toUpperCase());
                dto.setLocationname(((String) record[3]).toUpperCase());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date createDate = df.parse((String) record[4]);
                Date landingDate =new Date(((Timestamp)record[5]).getTime());
                Date returnDate= new Date(((Timestamp)record[6]).getTime());
                dto.setDcreate(createDate);
                dto.setDlanding(landingDate);
                dto.setRdate(returnDate);
                dto.setSerialcode((String) record[7]);
                shw.add(dto);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        return shw;
    }

    @RequestMapping(value = "/finalized",method = RequestMethod.GET)
    public @ResponseBody List<showServicesRequestDto> showServicesRequesteFinalizedUser(Authentication auth){

        Principal Pp = principalRepository.findByEmail(auth.getName());
        List<Object[]> results= servicerequestRepository.findForUser(Pp.getId(),true);

        List<showServicesRequestDto> shw = new ArrayList<>(0);

        results.stream().forEach((record) -> {
            showServicesRequestDto dto = new showServicesRequestDto();
            try {
                dto.setServicerequest(((BigInteger) record[0]).longValue());
                dto.setPrincipal(((BigInteger) record[1]).longValue());
                dto.setPrincipalname(((String) record[2]).toUpperCase());
                dto.setLocationname(((String) record[3]).toUpperCase());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date createDate = df.parse((String) record[4]);
                Date landingDate =new Date(((Timestamp)record[5]).getTime());
                Date returnDate= new Date(((Timestamp)record[6]).getTime());
                dto.setDcreate(createDate);
                dto.setDlanding(landingDate);
                dto.setRdate(returnDate);
                dto.setSerialcode((String) record[7]);
                shw.add(dto);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        return shw;
    }

    @RequestMapping(value = "/reverse/{servicerequest}",method = RequestMethod.PATCH)
    public @ResponseBody String[] reverseServicesRequest(Authentication auth,
                                                         @PathVariable long servicerequest){

        try{
            Principal Pp = principalRepository.findByEmail(auth.getName());
            Servicerequest svr= servicerequestRepository.findOne(servicerequest);

            if (svr.getPrincipal()==Pp.getId()){

                /*Desbloqueo el pago del service request*/
                Deferedpay defered=deferedpay.findByServicerequestIs(svr.getId());
                deferedpay.delete(defered);

                /*Se cierra el svr y se le coloca la fecha de expiración*/
                svr.setClosed(true);
                svr.setDupdate(new Date());
                svr.setDexpired(new Date());

                /*Actualizo el service request*/
                servicerequestRepository.save(svr);

                return new String[]{"message","success"};
            }
            return new String[]{"message","failure"};

        }catch (Exception e) {
            System.out.println(e.getMessage());
           return new String[]{"message","failure"};
        }
    }

    String getAviationname(int aviationtype){

        if(aviationtype==1){
            return "Commercial aviation";
        }

        if(aviationtype==2){
            return "General aviation";
        }
        return null;
    }

}
