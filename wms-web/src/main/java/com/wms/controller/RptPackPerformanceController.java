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
import com.wms.service.PackPerformExportService;
import com.wms.service.RptPackPerformanceService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.RptPackPerformanceVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.InOutExportForm;
import com.wms.vo.form.PackPerformExportForm;
import com.wms.vo.form.RptPackPerformanceForm;
import com.wms.query.RptPackPerformanceQuery;

@Controller
@RequestMapping("rptPackPerformanceController")
public class RptPackPerformanceController {

	@Autowired
	private RptPackPerformanceService rptPackPerformanceService;
	
	@Autowired
	private PackPerformExportService packPerformExportService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("rptPackPerformance/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<RptPackPerformanceVO> showDatagrid(EasyuiDatagridPager pager, RptPackPerformanceQuery query) {
		return rptPackPerformanceService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(RptPackPerformanceForm rptPackPerformanceForm) throws Exception {
		Json json = rptPackPerformanceService.addRptPackPerformance(rptPackPerformanceForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(RptPackPerformanceForm rptPackPerformanceForm) throws Exception {
		Json json = rptPackPerformanceService.editRptPackPerformance(rptPackPerformanceForm);
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
		Json json = rptPackPerformanceService.deleteRptPackPerformance(id);
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
		return rptPackPerformanceService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return rptPackPerformanceService.getRptPackPerformanceCombobox();
	}
	
	@Login
	@RequestMapping(params = "exportPackPerformDataToExcel")
	public void exportPackPerformDataToExcel(HttpServletResponse response, PackPerformExportForm form) throws Exception {
		packPerformExportService.exportPackPerformDataToExcel(response, form);
	}

}