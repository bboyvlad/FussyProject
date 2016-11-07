package jd.controllers;

import jd.Util.AppUtils;
import jd.persistence.dto.rptServiceItemRequestDTO;
import jd.persistence.model.*;
import jd.persistence.repository.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
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
    PricepoundRepository pricepound;

    @Autowired
    PricedateRepository pricedate;

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
    //try{
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
                    paymethod[0] =pay;
                }
            });
            if (!owmpay[0]){
                message.put("messaje","algo pasa, esta tarjeta no le pertenece a este usuario");
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
                message.put("messaje","algo pasa, esta carro de compras no le pertenece a este usuario");
                return message;
            }

            Pp.getMyaircrafts().forEach((myaircraft)->{
                if(shopcart[0].getMyaircraft()==myaircraft.getId()){
                    aircraftGo[0]=myaircraft;
                }
            });

            Set<Itemcart> cartitems = new HashSet<Itemcart>(0);

            Set<Itemrequest> itemsrequest = new HashSet<Itemrequest>(0);

            Set <rptServiceItemRequestDTO> irequestDTOs = new HashSet<rptServiceItemRequestDTO>(0);

            Servicerequest servicerequest = new Servicerequest();

            final double[] serviceamount={0};
            cartitems= shopcart[0].getItems();

            cartitems.forEach((citem)->{

                Itemrequest ir=new Itemrequest();

                rptServiceItemRequestDTO irDTO=new rptServiceItemRequestDTO();

                switch (citem.getPricetype()){
                    case "U":
                        Price Pc= price.findOne(citem.getProduct());
                        ir.setProduct(Pc.getId());//sourceID
                        ir.setQuantity(citem.getQuantity());
                        ir.setUnitprice(Pc.getCost1());
                        ir.setTotalprice(citem.getQuantity()*Pc.getCost1());
                        ir.setChecked(true);
                        ir.setPricename(Pc.getName());
                        ir.setPricedesc(citem.getPricedesc());
                        ir.setPricetype("U");

                        irDTO.setPricedesc(citem.getPricedesc());
                        irDTO.setAmount(ir.getTotalprice());
                        irDTO.setDescription(Pc.getName());

                        break;
                    case "D":
                        Pricedate Pd= pricedate.findOne(citem.getProduct());
                        ir.setProduct(Pd.getId());//sourceID
                        ir.setQuantity(citem.getQuantity());
                        ir.setUnitprice(Pd.getCost1());
                        ir.setTotalprice(citem.getQuantity()*Pd.getCost1());
                        ir.setChecked(true);
                        ir.setPricename(Pd.getName());
                        ir.setPricedesc(citem.getPricedesc());
                        ir.setPricetype("D");

                        irDTO.setPricedesc(citem.getPricedesc());
                        irDTO.setAmount(ir.getTotalprice());
                        irDTO.setDescription(Pd.getName());

                        break;
                    case "P":
                        Pricepound Po= pricepound.findOne(citem.getProduct());
                        ir.setProduct(Po.getId());//sourceID
                        ir.setQuantity(citem.getQuantity());
                        ir.setUnitprice(Po.getCost1());
                        ir.setTotalprice(citem.getQuantity()*Po.getCost1());
                        ir.setChecked(true);
                        ir.setPricename(Po.getName());
                        ir.setPricedesc(citem.getPricedesc());
                        ir.setPricetype("P");

                        irDTO.setPricedesc(citem.getPricedesc());
                        irDTO.setAmount(ir.getTotalprice());
                        irDTO.setDescription(Po.getName());

                        break;
                }

                irequestDTOs.add(irDTO);
                itemsrequest.add(ir);
                serviceamount[0]=serviceamount[0]+ir.getTotalprice();
            });

        servicerequest.setPrincipal(Pp.getId());
        servicerequest.setPaymethod(paymethod[0].getPayid());
        servicerequest.setGuarantee((serviceamount[0]*30)/100);
        servicerequest.setAmount(serviceamount[0]);
        servicerequest.setLocation(shopcart[0].getLocation());
        servicerequest.setAviationtype(shopcart[0].getAviationtype());

        servicerequest.setDcreate(fechaActual);
        servicerequest.setDupdate(fechaActual);

        Calendar c = Calendar.getInstance();
        c.setTime(fechaActual);
        c.add(Calendar.DATE, 15);

        String serialcode="svc_"+utils.getCadenaAlfaNumAleatoria(15);

        servicerequest.setDexpired(c.getTime());
        servicerequest.setDlanding(shopcart[0].getDlanding());
        servicerequest.setReleased(false);
        servicerequest.setSerialcode(serialcode);



        if(serviceamount[0]<= paymethod[0].getPaybalance()){ //verifico si tiene saldo disponible

            //String[] classpathEntries = classpath.split(File.pathSeparator);

            System.out.println("classpath: "+System.getProperty("java.class.path"));

            System.out.println("ruta: "+java.lang.System.getProperty("user.dir"));
            System.out.println("Tiene saldo");
            System.out.println("balance "+paymethod[0].getPaybalance());
            System.out.println("Monto operacion "+serviceamount[0]);
            System.out.println(serialcode);

            Location airport=loc.findOne(shopcart[0].getLocation());
            servicerequest.setItems(itemsrequest);
            servicerequestRepository.save(servicerequest);

            /*genera el pdf*/

            Map<String,Object> params = new HashMap<String,Object>();
            params.put("fcreate",fechaActual);
            params.put("client",Pp.getName().toUpperCase());
            params.put("email",Pp.getEmail());
            params.put("locationiata",airport.getIATA());
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

            /*Se envia el correo al cliente*/
            MimeMessage msg = mailSender.createMimeMessage();

            // use the true flag to indicate you need a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(Pp.getEmail());
            helper.addBcc("efernandez.ve@gmail.com");
            helper.setText("Hola "+Pp.getName()+", has generado satisfactoriamente un service request " +
                    "ahora despreocupate que nosotros nos encargamos, te avisaremos tan pronto este listo");
            helper.setSubject("Has generado un Service Request");

            // let's attach the infamous windows Sample file (this time copied to c:/)
            FileSystemResource file = new FileSystemResource(java.lang.System.getProperty("user.home")+"/fussyfiles/principal/"+serialcode+".pdf");

            helper.addAttachment(serialcode+".pdf", file);

            mailSender.send(msg);

            /*endmail*/

            /*Cobro de la transaccion a la JDcard*/

            /* valido el saldo de la jdcard antes de pagar*/

                Tranpay jd_etpm = new Tranpay();
                jd_etpm.setTrantype("JDEBIT");
                jd_etpm.setTranamount(-(serviceamount[0]));
                jd_etpm.setTrandate(fechaActual);
                jd_etpm.setTranupdate(fechaActual);
                jd_etpm.setTrantoken("jd_" + utils.getCadenaAlfaNumAleatoria(9));
                jd_etpm.setTranstatus("SUCCEEDED");
                paymethod[0].getTransactionspayments().add(jd_etpm);

                paymethodRepository.save(paymethod[0]);

            message.put("message","Service request generado");

        }else{
            message.put("message","Saldo insuficiente");
        }
        return message;

    //}catch(Exception e){
      //  return e.getMessage();
    //}
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
