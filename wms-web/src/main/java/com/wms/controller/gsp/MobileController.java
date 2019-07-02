package com.wms.controller.gsp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/7/1
 */
@Controller
@RequestMapping("mobileController")
public class MobileController {

    @RequestMapping(params = "toLogin")
    public ModelAndView toLogin(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mobile/login");
        return modelAndView;
    }
}