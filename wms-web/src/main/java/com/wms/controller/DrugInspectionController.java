package com.wms.controller;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.DrugControlService;
import com.wms.service.OrderHeaderForNormalService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("drugInspectionController")
public class DrugInspectionController {

	@Autowired
	private DrugControlService drugControlService;
	@Autowired
	private OrderHeaderForNormalService orderHeaderForNormalService;

	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return orderHeaderForNormalService.getBtn(id, (SfcUserLogin) session.getAttribute(ResourceUtil.getUserInfo()));
	}


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
	@RequestMapping(params = "toCustomerProductMainInfo")
	public ModelAndView toCustomerProductMainInfo(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("drugInspection/customerProduct_info", model);
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
	//库存信息-统计
	@Login
	@RequestMapping(params = "toSearchInvLocationMainInfo")
	public ModelAndView toSearchInvLocationMainInfo(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("searchInvLocation/main_info", model);
	}

	@Login
	@RequestMapping(params = "showSearchInvLocationDatagrid")
	@ResponseBody
	public EasyuiDatagrid<SearchInvLocation> showSearchInvLocationDatagrid(EasyuiDatagridPager pager, SearchInvLocation query) {
		return drugControlService.showSearchInvLocationDatagrid(pager, query);
	}
	//库存信息-统计
	@Login
	@RequestMapping(params = "showSearchInvLocationDatagridSum")
	@ResponseBody
	public EasyuiDatagrid<SearchInvLocation> showSearchInvLocationDatagridSum(EasyuiDatagridPager pager, SearchInvLocation query) {
		return drugControlService.showSearchInvLocationDatagridSum(pager, query);
	}

	@Login
	@RequestMapping(params = "exportSearchInvLocationDataToExcel")
	public void exportSearchInvLocationDataToExcel(HttpServletResponse response,SearchInvLocation form) throws Exception {
		drugControlService.exportSearchInvLocationDataToExcel(response, form);
	}
	//库存信息-统计
	@Login
	@RequestMapping(params = "exportSearchInvLocationDataToExcelSum")
	public void exportSearchInvLocationDataToExcelSum(HttpServletResponse response,SearchInvLocation form) throws Exception {
		drugControlService.exportSearchInvLocationDataToExcelSum(response, form);
	}

    /**************************************入库信息****************************************/

	/**
	 * 药监核查-入库信息-MV
	 */
	@Login
	@RequestMapping(params = "toSearchEnterInvLocationMain")
	public ModelAndView toSearchEnterInvLocationMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("searchEnterInvLocation/main", model);
	}

	/**
	 * 药监核查-入库信息-datagrid
	 */
	@Login
	@RequestMapping(params = "showSearchEnterInvLocationDatagrid")
	@ResponseBody
	public EasyuiDatagrid<SearchEnterInvLocation> showSearchEnterInvLocationDatagrid(EasyuiDatagridPager pager, SearchEnterInvLocation query) {
		return drugControlService.showSearchEnterInvLocationDatagrid(pager, query);
	}

	/**
	 * 药监核查-入库信息-导出
	 */
	@Login
	@RequestMapping(params = "exportSearchEnterInvLocationDataToExcel")
	public void exportSearchEnterInvLocationDataToExcel(HttpServletResponse response,SearchEnterInvLocation form) throws Exception {
		drugControlService.exportSearchEnterInvLocationDataToExcel(response, form);
	}

	/**
	 * 统计分析-入库信息-MV
	 */
	@Login
	@RequestMapping(params = "toSearchEnterInvLocationMainInfo")
	public ModelAndView toSearchEnterInvLocationMainInfo(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("searchEnterInvLocation/main_info", model);
	}

	/**
	 * 统计分析-入库信息-datagrid
	 */
	@Login
	@RequestMapping(params = "showSearchEnterInvLocationDatagridSum")
	@ResponseBody
	public EasyuiDatagrid<SearchEnterInvLocation> showSearchEnterInvLocationDatagridSum(EasyuiDatagridPager pager, SearchEnterInvLocation query) {
		return drugControlService.showSearchEnterInvLocationDatagridSum(pager, query);
	}

	/**
	 * 统计分析-入库信息-导出
	 */
	@Login
	@RequestMapping(params = "exportSearchEnterInvLocationDataToExcelSum")
	public void exportSearchEnterInvLocationDataToExcelSum(HttpServletResponse response,SearchEnterInvLocation form) throws Exception {
		drugControlService.exportSearchEnterInvLocationDataToExcelSum(response, form);
	}

    /**************************************出库信息****************************************/

    @Login
    @RequestMapping(params = "toSearchOutInvLocationMain")
    public ModelAndView toSearchOutInvLocationMain(String menuId) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("menuId", menuId);
        return new ModelAndView("searchOutInvLocation/main", model);
    }
    //统计分析-出库信息
	@Login
	@RequestMapping(params = "toSearchOutInvLocationMainInfo")
	public ModelAndView toSearchOutInvLocationMainInfo(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("searchOutInvLocation/main_info", model);
	}

    @Login
    @RequestMapping(params = "showSearchOutInvLocationDatagrid")
    @ResponseBody
    public EasyuiDatagrid<SearchOutInvLocation> showSearchOutInvLocationDatagrid(EasyuiDatagridPager pager, SearchOutInvLocation query) {
        return drugControlService.showSearchOutInvLocationDatagrid(pager, query);
    }
    //统计分析-出库信息
    @Login
    @RequestMapping(params = "showSearchOutInvLocationDatagridSum")
    @ResponseBody
    public EasyuiDatagrid<SearchOutInvLocation> showSearchOutInvLocationDatagridSum(EasyuiDatagridPager pager, SearchOutInvLocation query) {
        return drugControlService.showSearchOutInvLocationDatagridSum(pager, query);
    }


    @Login
    @RequestMapping(params = "exportSearchOutInvLocationDataToExcel")
    public void exportSearchOutInvLocationDataToExcel(HttpServletResponse response,SearchOutInvLocation form) throws Exception {
        drugControlService.exportSearchOutInvLocationDataToExcel(response, form);
    }
    //统计分析-出库信息
    @Login
    @RequestMapping(params = "exportSearchOutInvLocationDataToExcelSum")
    public void exportSearchOutInvLocationDataToExcelSum(HttpServletResponse response,SearchOutInvLocation form) throws Exception {
        drugControlService.exportSearchOutInvLocationDataToExcelSum(response, form);
    }














    /**
	 * 药监核查-历史数据
	 */




	/**************************************委托客户-历史数据****************************************/

	@Login
	@RequestMapping(params = "toMainHistory")
	public ModelAndView toMainHistory(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("SearchBasCustomer/main_history", model);
	}

	@Login
	@RequestMapping(params = "showDatagridBasCustomerHistory")
	@ResponseBody
	public EasyuiDatagrid<SearchBasCustomer> showDatagridBasCustomerHistory(EasyuiDatagridPager pager, SearchBasCustomer query) {
		return drugControlService.getPagedDatagridBasCustomerHistory(pager, query);
	}


	@Login
	@RequestMapping(params = "exportBasCustomerHistoryDataToExcel")
	public void exportBasCustomerHistoryDataToExcel(HttpServletResponse response, SearchBasCustomer form) throws Exception {
		drugControlService.exportBasCustomerHistoryDataToExcel(response, form);
	}



	/**************************************历史货主商品****************************************/

	@Login
	@RequestMapping(params = "toCustomerProductMainHistory")
	public ModelAndView toCustomerProductMainHistory(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("drugInspection/customerProduct_history", model);
	}

	@Login
	@RequestMapping(params = "showCustomerProductHistoryDatagrid")
	@ResponseBody
	public EasyuiDatagrid<CustomerProduct> showCustomerProductHistoryDatagrid(EasyuiDatagridPager pager, CustomerProduct query) {
		return drugControlService.getCustomerProductHistoryPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "exportCustomerProductHistoryDataToExcel")
	public void exportCustomerProductHistoryDataToExcel(HttpServletResponse response, CustomerProduct form) throws Exception {
		drugControlService.exportCustomerProductHistoryDataToExcel(response, form);
	}



	/**************************************历史入库信息****************************************/

	@Login
	@RequestMapping(params = "toSearchEnterInvLocationMainHistory")
	public ModelAndView toSearchEnterInvLocationMainHistory(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("searchEnterInvLocation/main_history", model);
	}

	@Login
	@RequestMapping(params = "showSearchEnterInvLocationHistoryDatagrid")
	@ResponseBody
	public EasyuiDatagrid<SearchEnterInvLocation> showSearchEnterInvLocationHistoryDatagrid(EasyuiDatagridPager pager, SearchEnterInvLocation query) {
		return drugControlService.showSearchEnterInvLocationHistoryDatagrid(pager, query);
	}


	@Login
	@RequestMapping(params = "exportSearchEnterInvLocationHistoryDataToExcel")
	public void exportSearchEnterInvLocationHistoryDataToExcel(HttpServletResponse response,SearchEnterInvLocation form) throws Exception {
		drugControlService.exportSearchEnterInvLocationHistoryDataToExcel(response, form);
	}



	/**************************************历史出库信息****************************************/

	@Login
	@RequestMapping(params = "toSearchOutInvLocationMainHistory")
	public ModelAndView toSearchOutInvLocationMainHistory(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("searchOutInvLocation/main_history", model);
	}

	@Login
	@RequestMapping(params = "showSearchOutInvLocationHistoryDatagrid")
	@ResponseBody
	public EasyuiDatagrid<SearchOutInvLocation> showSearchOutInvLocationHistoryDatagrid(EasyuiDatagridPager pager, SearchOutInvLocation query) {
		return drugControlService.showSearchOutInvLocationHistoryDatagrid(pager, query);
	}


	@Login
	@RequestMapping(params = "exportSearchOutInvLocationHistoryDataToExcel")
	public void exportSearchOutInvLocationHistoryDataToExcel(HttpServletResponse response,SearchOutInvLocation form) throws Exception {
		drugControlService.exportSearchOutInvLocationHistoryDataToExcel(response, form);
	}
}