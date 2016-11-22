package jd.controllers;

import com.itextpdf.text.DocumentException;
import jd.persistence.dto.rptPrincipalDTO;
import jd.persistence.model.Principal;
import jd.persistence.repository.PrincipalRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eduardom on 10/22/16.
 */

@Controller
@RequestMapping(value = "/test")
public class testController {

    @Autowired
    PrincipalRepository principalRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String prueba(HttpServletResponse httpServletResponse,
                       HttpServletRequest request) throws JRException, IOException {

        /*System.out.println("");

        rptPrincipalDTO rpt = new rptPrincipalDTO();
        rpt.setName("andrea");
        rpt.setId(1L);
        rpt.setLastname("canas");

        rptPrincipalDTO rp = new rptPrincipalDTO();
        rp.setName("Eduardo");
        rp.setId(2L);
        rp.setLastname("Fernandez");

        List<rptPrincipalDTO> listOfUser = new ArrayList<rptPrincipalDTO>();

        listOfUser.add(rpt);
        listOfUser.add(rp);


        Map<String,Object> params = new HashMap<String,Object>();
        params.put("usuario","Eduardo Miguel");

        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(listOfUser);

        URL in = this.getClass().getResource("tigre.jasper");
        System.out.println(in);

        String route=java.lang.System.getProperty("user.home");

        System.out.println(route);



        JasperPrint jasperPrint=JasperFillManager.fillReport(java.lang.System.getProperty("user.home")+"/fussyfiles/reports/tigre.jasper", params,beanCollectionDataSource);

        JasperExportManager.exportReportToPdfFile(jasperPrint, "tigre.pdf");

        httpServletResponse.getOutputStream();
        httpServletResponse.addHeader("Content-disposition","attachment; filename=tigre.pdf");

        ServletOutputStream stream = httpServletResponse.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();
        */
        return java.lang.System.getProperty("user.home");

    }

    //**** test jasper
    @RequestMapping(value = "/jasper", method = RequestMethod.GET)
    public @ResponseBody
    void getRpt1(HttpServletResponse httpServletResponse) throws JRException, IOException {

        List<Principal> principals= principalRepository.findAll();
        List<rptPrincipalDTO> usuarios = new ArrayList<rptPrincipalDTO>();

        principals.forEach((p)->{
            rptPrincipalDTO rpt = new rptPrincipalDTO();
            rpt.setId(p.getId());
            rpt.setName(p.getName());
            rpt.setLastname(p.getLastname());

            System.out.println(p.getName());

            usuarios.add(rpt);
        });

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("usuario","Eduardo Miguel");

        //File jasper=new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("userlist.jasper"));

        System.out.println("Generating PDF...");
        JasperReport jasperReport =
                JasperCompileManager.compileReport("src/main/resources/reports/tigre.jrxml");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, //ruta definida anteriomente
                params, //parametros que van fijo ( no repeat)
                new JRBeanCollectionDataSource(usuarios) //colecciones o iterables
        );

        //Vacio
        /*
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, //ruta definida anteriomente
                new HashMap(),
                new JREmptyDataSource()
        );
        */

        //JasperExportManager.exportReportToPdfFile(jasperPrint, "tigre.pdf"); //para guardarlo en un directorio

        //Con valores
        /*
         JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, //ruta definida anteriomente
                params, //parametros que van fijo ( no repeat)
                new JRBeanCollectionDataSource(usuarios) //colecciones o iterables
        );
        */

        httpServletResponse.getOutputStream();
        httpServletResponse.addHeader("Content-disposition","attachment; filename=tigre.pdf");

        ServletOutputStream stream = httpServletResponse.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();

//        FacesContext.getCurrentInstance().responseComplete();
    }

    @RequestMapping(value = "/verpdf", method = RequestMethod.GET)
    public @ResponseBody void verpdf(HttpServletResponse httpServletResponse) throws JRException, IOException, MessagingException {

        System.out.println("");
        List<Principal> principals= principalRepository.findAll();
        List<rptPrincipalDTO> listOfUser = new ArrayList<rptPrincipalDTO>();

        principals.forEach((p)->{
            rptPrincipalDTO rpt = new rptPrincipalDTO();
            rpt.setId(p.getId());
            rpt.setName(p.getName());
            rpt.setLastname(p.getLastname());

            System.out.println(p.getName());

            listOfUser.add(rpt);
        });

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("usuario","Eduardo Miguel");

        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(listOfUser);
        JasperPrint jasperPrint=JasperFillManager.fillReport("src/main/resources/reports/tigre.jasper", params,beanCollectionDataSource);

        JasperExportManager.exportReportToPdfFile(jasperPrint, "tigre.pdf");

        httpServletResponse.getOutputStream();
        httpServletResponse.addHeader("Content-disposition","attachment; filename=tigre.pdf");

        ServletOutputStream stream = httpServletResponse.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();

    }

    @RequestMapping(value ="/test", method = RequestMethod.GET)
    public @ResponseBody void testdouble(Authentication auth)throws DocumentException, IOException{

        try{
            System.out.println("Generating PDF...");
            JasperReport jasperReport =
                    JasperCompileManager.compileReport("src/main/resources/reports/hellojasper.jrxml");

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, new HashMap(), new JREmptyDataSource());
            //JasperExportManager.exportReportToPdfFile(jasperPrint, "HelloJasper.pdf");

            System.out.println("HelloJasper.pdf has been generated!");
        }
        catch (JRException e){
            e.printStackTrace();
        }

    }

    @RequestMapping(value ="/tco", method = RequestMethod.POST)
    public @ResponseBody Object testto(@RequestBody Object obj){
        System.out.println(obj);
        return obj;
    }



}
