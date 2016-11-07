package jd.controllers;

import jd.persistence.dto.rptPrincipalDTO;
import jd.persistence.model.Principal;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(method = RequestMethod.GET)
    public void prueba(HttpServletResponse httpServletResponse,
                       HttpServletRequest request) throws JRException, IOException {

        System.out.println("");

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

    }

}
