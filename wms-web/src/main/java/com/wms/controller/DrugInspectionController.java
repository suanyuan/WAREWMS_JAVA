package com.wms.controller;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.service.DrugControlService;
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
@RequestMapping("drugInspectionController")
public class DrugInspectionController {

	@Autowired
	private DrugControlService drugControlService;



	/**************************************委托客户****************************************/

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
		return drugControlService.getPagedDatagrid(pager, query);
	}


	@Login
	@RequestMapping(params = "exportBasCustomerDataToExcel")
	public void exportBasCustomerDataToExcel(HttpServletResponse response, SearchBasCustomer form) throws Exception {
		drugControlService.exportBasCustomerDataToExcel(response, form);
	}


	/**************************************货主商品****************************************/

	@Login
	@RequestMapping(params = "toCustomerProductMain")
	public ModelAndView toCustomerProductMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("drugInspection/customerProduct", model);
	}

	@Login
	@RequestMapping(params = "showCustomerProductDatagrid")
	@ResponseBody
	public EasyuiDatagrid<CustomerProduct> showCustomerProductDatagrid(EasyuiDatagridPager pager, CustomerProduct query) {
		return drugControlService.getCustomerProductPagedDatagrid(pager, query);
	}

    @Login
    @RequestMapping(params = "exportCustomerProductDataToExcel")
    public void exportCustomerProductDataToExcel(HttpServletResponse response, CustomerProduct form) throws Exception {
        drugControlService.exportCustomerProductDataToExcel(response, form);
    }

	/**************************************库存信息****************************************/

	@Login
	@RequestMapping(params = "toSearchInvLocationMain")
	public ModelAndView toSearchInvLocationMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("searchInvLocation/main", model);
	}

	@Login
	@RequestMapping(params = "showSearchInvLocationDatagrid")
	@ResponseBody
	public EasyuiDatagrid<SearchInvLocation> showSearchInvLocationDatagrid(EasyuiDatagridPager pager, SearchInvLocation query) {
		return drugControlService.showSearchInvLocationDatagrid(pager, query);
	}


	@Login
	@RequestMapping(params = "exportSearchInvLocationDataToExcel")
	public void exportSearchInvLocationDataToExcel(HttpServletResponse response,SearchInvLocation form) throws Exception {
		drugControlService.exportSearchInvLocationDataToExcel(response, form);
	}



    /**************************************入库信息****************************************/

	@Login
	@RequestMapping(params = "toSearchEnterInvLocationMain")
	public ModelAndView toSearchEnterInvLocationMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("searchEnterInvLocation/main", model);
	}

	@Login
	@RequestMapping(params = "showSearchEnterInvLocationDatagrid")
	@ResponseBody
	public EasyuiDatagrid<SearchEnterInvLocation> showSearchEnterInvLocationDatagrid(EasyuiDatagridPager pager, SearchEnterInvLocation query) {
		return drugControlService.showSearchEnterInvLocationDatagrid(pager, query);
	}


	@Login
	@RequestMapping(params = "exportSearchEnterInvLocationDataToExcel")
	public void exportSearchEnterInvLocationDataToExcel(HttpServletResponse response,SearchEnterInvLocation form) throws Exception {
		drugControlService.exportSearchEnterInvLocationDataToExcel(response, form);
	}


    /**************************************出库信息****************************************/

    @Login
    @RequestMapping(params = "toSearchOutInvLocationMain")
    public ModelAndView toSearchOutInvLocationMain(String menuId) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("menuId", menuId);
        return new ModelAndView("searchOutInvLocation/main", model);
    }

    @Login
    @RequestMapping(params = "showSearchOutInvLocationDatagrid")
    @ResponseBody
    public EasyuiDatagrid<SearchOutInvLocation> showSearchOutInvLocationDatagrid(EasyuiDatagridPager pager, SearchOutInvLocation query) {
        return drugControlService.showSearchOutInvLocationDatagrid(pager, query);
    }


    @Login
    @RequestMapping(params = "exportSearchOutInvLocationDataToExcel")
    public void exportSearchOutInvLocationDataToExcel(HttpServletResponse response,SearchOutInvLocation form) throws Exception {
        drugControlService.exportSearchOutInvLocationDataToExcel(response, form);
    }
}