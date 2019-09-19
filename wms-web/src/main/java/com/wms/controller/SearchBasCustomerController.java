package com.wms.controller;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.SearchBasCustomer;
import com.wms.mybatis.dao.GspReceivingMybatisDao;
import com.wms.service.SearchBasCustomerService;
import com.wms.utils.annotation.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("searchBasCustomerController")
public class SearchBasCustomerController {

	@Autowired
	private SearchBasCustomerService searchBasCustomerService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("SearchBasCustomer/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<SearchBasCustomer> showDatagrid(EasyuiDatagridPager pager, SearchBasCustomer query) {
		return searchBasCustomerService.getPagedDatagrid(pager, query);
	}


	@Login
	@RequestMapping(params = "exportBasCustomerDataToExcel")
	public void exportBasCustomerDataToExcel(HttpServletResponse response, SearchBasCustomer form) throws Exception {
		searchBasCustomerService.exportBasCustomerDataToExcel(response, form);
	}
}