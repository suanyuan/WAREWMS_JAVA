package com.wms.controller;

import com.wms.entity.BasSku;
import com.wms.entity.GspOperteLicenseTime;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
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

    @RequestMapping(params = "testReport")
    public String toMain(Model model) {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("PANO","有点僵");
        map.put("PALINENO",System.currentTimeMillis());
        list.add(map);
        JRDataSource jrDataSource = new JRMapArrayDataSource(list.toArray());
        model.addAttribute("url","WEB-INF/jasper/report1.jasper");
        model.addAttribute("format","pdf");
        model.addAttribute("jrMainDataSource",jrDataSource);
        return "iReportView";
    }

    @RequestMapping(params = "testSf")
    public String toTestSf(Model model) {

        Map<String, Object> parameters=new HashMap<String, Object>();
        List<GspOperteLicenseTime> lists = new ArrayList<GspOperteLicenseTime>();
        for (int i = 1; i < 10; i++) {
            GspOperteLicenseTime gspOperteLicenseTime = new GspOperteLicenseTime();
            gspOperteLicenseTime.setLincenseType("666");
            lists.add(gspOperteLicenseTime);
        }

        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        //map.put("PANO","有点僵");
        //map.put("PALINENO",System.currentTimeMillis());
        map.put("remainDay",lists);
        list.add(map);
        JRDataSource jrDataSource = new JRMapArrayDataSource(list.toArray());

        //JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource();
        model.addAttribute("url","WEB-INF/jasper/Pdfreport1.jasper");
        model.addAttribute("format","pdf");
        model.addAttribute("jrMainDataSource",jrDataSource);
        //JasperPrint print = JasperFillManager.fillReport(report, paramsMap);
        return "iReportView";
    }
}