package com.wms.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/6/23
 */
@Controller
@RequestMapping("testPdaApi")
public class TestPdaApi {

    @RequestMapping(params = "requestTest", method = RequestMethod.GET)
    @ResponseBody
    public Object testApi(){
        Map<String, String> map = new HashMap<>();
        map.put("name", "Gizmo");
        map.put("age", "18");
        map.put("sex", "male");
//        if (pageNum == 0) {
//
//            map.put("status", "Failed");
//        }else {
//
//            map.put("status", "Success");
//        }

        return map;
    }
}