package com.wms.controller;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
}