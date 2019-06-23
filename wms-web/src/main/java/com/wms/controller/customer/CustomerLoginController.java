package com.wms.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerLoginController {
	@RequestMapping("/cusMain.html")
	public ModelAndView toHome() throws Exception{
		return new ModelAndView("customer/login");
	}
}
