package com.wms.view;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/7/3
 */
public class ApplicationIReportView extends JasperReportsMultiFormatView {
    private JasperReport jasperReport;

    public ApplicationIReportView(){
        super();
    }

    protected JasperPrint fillReport(Map<String,Object> model) throws Exception{
       if(model.containsKey("url")){
           System.out.println("-----------"+String.valueOf(model.get("url")));
           setUrl(String.valueOf(model.get("url")));
           this.jasperReport = loadReport();
       }
       return super.fillReport(model);
    }

    protected JasperReport getReport(){
        return this.jasperReport;
    }
}