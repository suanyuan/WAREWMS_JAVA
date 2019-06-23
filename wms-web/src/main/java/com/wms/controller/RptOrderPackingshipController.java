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
import com.wms.service.OrderShipExportService;
import com.wms.service.RptOrderPackingshipService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.RptOrderPackingshipVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.OrderPackExportForm;
import com.wms.vo.form.OrderShipExportForm;
import com.wms.vo.form.RptOrderPackingshipForm;
import com.wms.query.RptOrderPackingshipQuery;

@Controller
@RequestMapping("rptOrderPackingshipController")
public class RptOrderPackingshipController {

	@Autowired
	private RptOrderPackingshipService rptOrderPackingshipService;
	
	@Autowired
	private OrderShipExportService orderShipExportService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("rptOrderPackingship/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<RptOrderPackingshipVO> showDatagrid(EasyuiDatagridPager pager, RptOrderPackingshipQuery query) {
		return rptOrderPackingshipService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(RptOrderPackingshipForm rptOrderPackingshipForm) throws Exception {
		Json json = rptOrderPackingshipService.addRptOrderPackingship(rptOrderPackingshipForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(RptOrderPackingshipForm rptOrderPackingshipForm) throws Exception {
		Json json = rptOrderPackingshipService.editRptOrderPackingship(rptOrderPackingshipForm);
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
		Json json = rptOrderPackingshipService.deleteRptOrderPackingship(id);
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
		return rptOrderPackingshipService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return rptOrderPackingshipService.getRptOrderPackingshipCombobox();
	}

	@Login
	@RequestMapping(params = "exportOrderShipDataToExcel")
	public void exportOrderShipDataToExcel(HttpServletResponse response, OrderShipExportForm form) throws Exception {
		orderShipExportService.exportOrderShipDataToExcel(response, form);
	}
}