package com.wms.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.RptSortPerformanceService;
import com.wms.service.SortPerformExportService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.RptSortPerformanceVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.PackPerformExportForm;
import com.wms.vo.form.RptSortPerformanceForm;
import com.wms.vo.form.SortPerformExportForm;
import com.wms.query.RptSortPerformanceQuery;

@Controller
@RequestMapping("rptSortPerformanceController")
public class RptSortPerformanceController {

	@Autowired
	private RptSortPerformanceService rptSortPerformanceService;
	
	@Autowired
	private SortPerformExportService sortPerformExportService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("rptSortPerformance/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<RptSortPerformanceVO> showDatagrid(EasyuiDatagridPager pager, RptSortPerformanceQuery query) {
		return rptSortPerformanceService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(RptSortPerformanceForm rptSortPerformanceForm) throws Exception {
		Json json = rptSortPerformanceService.addRptSortPerformance(rptSortPerformanceForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(RptSortPerformanceForm rptSortPerformanceForm) throws Exception {
		Json json = rptSortPerformanceService.editRptSortPerformance(rptSortPerformanceForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String id) {
		Json json = rptSortPerformanceService.deleteRptSortPerformance(id);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return rptSortPerformanceService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return rptSortPerformanceService.getRptSortPerformanceCombobox();
	}
	
	@Login
	@RequestMapping(params = "exportSortPerformDataToExcel")
	public void exportSortPerformDataToExcel(HttpServletResponse response, SortPerformExportForm form) throws Exception {
		sortPerformExportService.exportSortPerformDataToExcel(response, form);
	}


}