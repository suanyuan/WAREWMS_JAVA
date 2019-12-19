package com.wms.controller;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.OrderHeaderForNormalService;
import com.wms.service.StatisticalAnalysisService;
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
@RequestMapping("statisticalAnalysisController")
public class StatisticalAnalysisController {

	@Autowired
	private StatisticalAnalysisService statisticalAnalysisService;

	@Autowired
	private OrderHeaderForNormalService orderHeaderForNormalService;

	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return orderHeaderForNormalService.getBtn(id, (SfcUserLogin) session.getAttribute(ResourceUtil.getUserInfo()));
	}

	/**************************************出入库流水****************************************/

	@Login
	@RequestMapping(params = "toMainRptSoAsnDailyLocation")
	public ModelAndView toMainRptSoAsnDailyLocation(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("rptSoAsnDaily/main", model);
	}
	@Login
	@RequestMapping(params = "showDatagridRptSoAsnDailyLocation")
	@ResponseBody
	public EasyuiDatagrid<RptSoAsnDailyLocation> showDatagridRptSoAsnDailyLocation(EasyuiDatagridPager pager, RptSoAsnDailyLocation query) {
		return statisticalAnalysisService.getPagedDatagridRptSoAsnDailyLocation(pager, query);
	}
	@Login
	@RequestMapping(params = "exportSoAsnDailyDataToExcel")
	public void exportSoAsnDailyDataToExcel(HttpServletResponse response, RptSoAsnDailyLocation form) throws Exception {
		statisticalAnalysisService.exportSoAsnDailyDataToExcel(response, form);
	}



	/**************************************入库单列表****************************************/

	@Login
	@RequestMapping(params = "toMainRptAsnList")
	public ModelAndView toMainRptAsnList(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("rptAsnList/main", model);
	}
	@Login
	@RequestMapping(params = "showDatagridRptAsnList")
	@ResponseBody
	public EasyuiDatagrid<RptAsnList> showDatagridRptAsnListLocation(EasyuiDatagridPager pager, RptAsnList query) {
		return statisticalAnalysisService.getPagedDatagridRptAsnList(pager, query);
	}
	@Login
	@RequestMapping(params = "exportAsnListDataToExcel")
	public void exportAsnListDataToExcel(HttpServletResponse response, RptAsnList form) throws Exception {
		statisticalAnalysisService.exportAsnListDataToExcel(response, form);
	}



	/**************************************收发存汇总表****************************************/

	@Login
	@RequestMapping(params = "toMainRptSendReceiveAndSaveAllList")
	public ModelAndView toMainRptSendReceiveAndSaveAllList(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("rptSendReceiveAndSaveAllList/main", model);
	}
	@Login
	@RequestMapping(params = "showDatagridRptSendReceiveAndSaveAllList")
	@ResponseBody
	public EasyuiDatagrid<RptSendReceiveAndSave> showDatagridRptSendReceiveAndSaveAllList(EasyuiDatagridPager pager, RptSendReceiveAndSave query) {
		return statisticalAnalysisService.getPagedDatagridRptSendReceiveAndSaveAllList(pager, query);
	}
	@Login
	@RequestMapping(params = "exportSendReceiveAndSaveAllListDataToExcel")
	public void exportSendReceiveAndSaveAllListDataToExcel(HttpServletResponse response, RptSendReceiveAndSave form) throws Exception {
		statisticalAnalysisService.exportSendReceiveAndSaveAllListDataToExcel(response, form);
	}



	/**************************************销售出库单列表****************************************/

	@Login
	@RequestMapping(params = "toMainRptSalesSoList")
	public ModelAndView toMainRptSalesSoList(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("rptSalesSoList/main", model);
	}
	@Login
	@RequestMapping(params = "showDatagridRptSalesSoList")
	@ResponseBody
	public EasyuiDatagrid<RptSalesSo> showDatagridRptSalesSoList(EasyuiDatagridPager pager, RptSalesSo query) {
		return statisticalAnalysisService.getPagedDatagridRptSalesSoList(pager, query);
	}
	@Login
	@RequestMapping(params = "exportSalesSoListDataToExcel")
	public void exportSalesSoListDataToExcel(HttpServletResponse response, RptSalesSo form) throws Exception {
		statisticalAnalysisService.exportSalesSoListDataToExcel(response, form);
	}




	/**************************************订单装箱清单-按发运单号****************************************/

	@Login
	@RequestMapping(params = "toMainRptOrderPackingcartonByOrderNo")
	public ModelAndView toMainRptOrderPackingcartonByOrderNo(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("rptOrderPackingcartonByOrderNo/main", model);
	}
	@Login
	@RequestMapping(params = "showDatagridRptOrderPackingcartonByOrderNo")
	@ResponseBody
	public EasyuiDatagrid<RptOrderPackingcartonByOrderNo> showDatagridRptOrderPackingcartonByOrderNo(EasyuiDatagridPager pager, RptOrderPackingcartonByOrderNo query) {
		return statisticalAnalysisService.getPagedDatagridRptOrderPackingcartonByOrderNo(pager, query);
	}
	@Login
	@RequestMapping(params = "exportOrderPackingcartonByOrderNoDataToExcel")
	public void exportOrderPackingcartonByOrderNoDataToExcel(HttpServletResponse response, RptOrderPackingcartonByOrderNo form) throws Exception {
		statisticalAnalysisService.exportOrderPackingcartonByOrderNoDataToExcel(response, form);
	}

}