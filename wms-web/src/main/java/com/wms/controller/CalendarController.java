package com.wms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wms.utils.annotation.Login;

@Controller
@RequestMapping("calendarController")
public class CalendarController {
	
	@Login
	@RequestMapping(params = "toManageCalendar")
	public ModelAndView toManageCalendar() {
		return new ModelAndView("calendar/main");
	}
}
