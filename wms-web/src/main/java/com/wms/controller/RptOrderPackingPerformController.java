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
import com.wms.service.OrderpackPerformExportService;
import com.wms.service.RptOrderPackingPerformService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.RptOrderPackingPerformVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.OrderPackExportForm;
import com.wms.vo.form.OrderpackPerformExportForm;
import com.wms.vo.form.RptOrderPackingPerformForm;
import com.wms.query.RptOrderPackingPerformQuery;

@Controller
@RequestMapping("rptOrderPackingPerformController")
public class RptOrderPackingPerformController {

	@Autowired
	private RptOrderPackingPerformService rptOrderPackingPerformService;
	
	@Autowired
	private OrderpackPerformExportService orderpackPerformExportService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("rptOrderPackingPerform/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<RptOrderPackingPerformVO> showDatagrid(EasyuiDatagridPager pager, RptOrderPackingPerformQuery query) {
		return rptOrderPackingPerformService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(RptOrderPackingPerformForm rptOrderPackingPerformForm) throws Exception {
		Json json = rptOrderPackingPerformService.addRptOrderPackingPerform(rptOrderPackingPerformForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(RptOrderPackingPerformForm rptOrderPackingPerformForm) throws Exception {
		Json json = rptOrderPackingPerformService.editRptOrderPackingPerform(rptOrderPackingPerformForm);
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
		Json json = rptOrderPackingPerformService.deleteRptOrderPackingPerform(id);
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
		return rptOrderPackingPerformService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return rptOrderPackingPerformService.getRptOrderPackingPerformCombobox();
	}
	
	@Login
	@RequestMapping(params = "exportOrderpackPerformDataToExcel")
	public void exportOrderpackPerformDataToExcel(HttpServletResponse response, OrderpackPerformExportForm form) throws Exception {
		orderpackPerformExportService.exportOrderPackDataToExcel(response, form);
	}

}