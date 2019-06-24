package com.wms.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/6/23
 */
@Controller
@RequestMapping("testPdaApi")
public class TestPdaApi {

    @RequestMapping
    @ResponseBody
    public Object testApi(){
        return "{'a':'b','success':true}";
    }
}