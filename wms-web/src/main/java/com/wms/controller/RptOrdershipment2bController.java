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
import com.wms.service.OrderShip2bExportService;
import com.wms.service.RptOrdershipment2bService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.RptOrdershipment2bVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.OrderShip2bExportForm;
import com.wms.vo.form.OrderShipExportForm;
import com.wms.vo.form.RptOrdershipment2bForm;
import com.wms.query.RptOrdershipment2bQuery;

@Controller
@RequestMapping("rptOrdershipment2bController")
public class RptOrdershipment2bController {

	@Autowired
	private RptOrdershipment2bService rptOrdershipment2bService;
	
	@Autowired
	private OrderShip2bExportService orderShip2bExportService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("rptOrdershipment2b/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<RptOrdershipment2bVO> showDatagrid(EasyuiDatagridPager pager, RptOrdershipment2bQuery query) {
		return rptOrdershipment2bService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(RptOrdershipment2bForm rptOrdershipment2bForm) throws Exception {
		Json json = rptOrdershipment2bService.addRptOrdershipment2b(rptOrdershipment2bForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(RptOrdershipment2bForm rptOrdershipment2bForm) throws Exception {
		Json json = rptOrdershipment2bService.editRptOrdershipment2b(rptOrdershipment2bForm);
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
		Json json = rptOrdershipment2bService.deleteRptOrdershipment2b(id);
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
		return rptOrdershipment2bService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return rptOrdershipment2bService.getRptOrdershipment2bCombobox();
	}

	@Login
	@RequestMapping(params = "exportOrderShip2bDataToExcel")
	public void exportOrderShip2bDataToExcel(HttpServletResponse response, OrderShip2bExportForm form) throws Exception {
		orderShip2bExportService.exportOrderShip2bDataToExcel(response, form);
	}
	
}