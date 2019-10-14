package com.wms.controller;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.RptAsnList;
import com.wms.entity.RptSoAsnDailyLocation;
import com.wms.service.StatisticalAnalysisService;
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
@RequestMapping("statisticalAnalysisController")
public class StatisticalAnalysisController {

	@Autowired
	private StatisticalAnalysisService statisticalAnalysisService;



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


}