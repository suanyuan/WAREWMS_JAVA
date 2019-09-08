package com.wms.controller;

import com.wms.entity.*;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.DocMtDetailsMybatisDao;
import com.wms.mybatis.dao.DocQcDetailsMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.DocMtDetailsQuery;
import com.wms.query.DocQcDetailsQuery;
import com.wms.utils.BeanConvertUtil;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/7/3
 */
@Controller
@RequestMapping("testReport")
public class TestReport {

    @Autowired
    BasSkuMybatisDao basSkuMybatisDao;
    @Autowired
    DocMtDetailsMybatisDao docMtDetailsMybatisDao;
    @Autowired
    DocQcDetailsMybatisDao docQcDetailsMybatisDao;

    @RequestMapping(params = "testReport")
    public String toMain(Model model) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("PANO", "有点僵");
        map.put("PALINENO", System.currentTimeMillis());
        list.add(map);
        JRDataSource jrDataSource = new JRMapArrayDataSource(list.toArray());
        model.addAttribute("url", "WEB-INF/jasper/report1.jasper");
        model.addAttribute("format", "pdf");
        model.addAttribute("jrMainDataSource", jrDataSource);
        return "iReportView";
    }

    @RequestMapping(params = "testSf")
    public String toTestSf(Model model) throws Exception {

   /*     List<DocMtHeader> dataw = new ArrayList<DocMtHeader>();
        //DocMtHeader docMtHeader = new DocMtHeader(2,2,2,2,2);
        docMtHeader.setDetls(new ArrayList<DocMtDetails>());
        DocMtDetails docMtDetails = new DocMtDetails();
        DocMtDetailsQuery detailsQuery = new DocMtDetailsQuery();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(detailsQuery));
        List<DocMtDetails> docMtDetailsList = docMtDetailsMybatisDao.queryByListLotatt(mybatisCriteria);
        if(docMtDetailsList.size() > 0){
            for ( DocMtDetails docMtDetails1:
                    docMtDetailsList) {
                docMtHeader.getDetls().add(docMtDetails1);
            }
            dataw.add(docMtHeader);

        }
*/
       //验收记录
        List<DocQcHeader> docQcHeaderList = new ArrayList<DocQcHeader>();
        DocQcHeader docQcHeader = new DocQcHeader();
        docQcHeader.setDetls(new ArrayList<DocQcDetails>());
        DocQcDetailsQuery docQcDetailsQuery = new DocQcDetailsQuery();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        docQcDetailsQuery.setQcno("QC00000497");
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(docQcDetailsQuery));
        List<DocQcDetails> docQcDetailsList = docQcDetailsMybatisDao.queryByListPano(mybatisCriteria);
        for (DocQcDetails docQcDetails1: docQcDetailsList) {
            docQcHeader.getDetls().add(docQcDetails1);
    }
        docQcHeaderList.add(docQcHeader);
       /* try {

            HashMap<String, Object> paramsMap = new HashMap<String, Object>();
            // 第二种配置数据源的方式 InputStream
            paramsMap.put("dtls", docQcHeaderList);
            // 注意更改路径
            File exe_rpt = new File(("F:/exploit/iReportTest/report1Query.jasper"));

            JRDataSource dataSource = new JRBeanCollectionDataSource(docQcHeaderList);

            JasperPrint jasperPrint = JasperFillManager.fillReport(exe_rpt.getPath(), paramsMap,dataSource);


            // 加载模板
            //  JasperReport report = JasperCompileManager.compileReport(design);
            // 填充数据
            //   JasperPrint print = JasperFillManager.fillReport(jasperPrint, paramsMap);
            // 预览显示
            JasperViewer.viewReport(jasperPrint);

        } catch (Exception e) { 生产许可证
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
*/

        JRDataSource jrDataSource = new JRBeanCollectionDataSource(docQcHeaderList);
        model.addAttribute("url", "WEB-INF/jasper/report1Query.jasper");
        model.addAttribute("format", "pdf");
        model.addAttribute("jrMainDataSource", jrDataSource);
        //JasperPrint print = JasperFillManager.fillReport(report, paramsMap);

        return "iReportView";
    }
}