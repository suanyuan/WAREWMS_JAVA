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
import com.wms.service.OrderSkuExportService;
import com.wms.service.RptOrdershipmentSkuService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.RptOrdershipmentSkuVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.AsnDailyExportForm;
import com.wms.vo.form.OrderSkuExportForm;
import com.wms.vo.form.RptOrdershipmentSkuForm;
import com.wms.query.RptOrdershipmentSkuQuery;

@Controller
@RequestMapping("rptOrdershipmentSkuController")
public class RptOrdershipmentSkuController {

	@Autowired
	private RptOrdershipmentSkuService rptOrdershipmentSkuService;
	
	@Autowired
	private OrderSkuExportService orderSkuExportService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("rptOrdershipmentSku/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<RptOrdershipmentSkuVO> showDatagrid(EasyuiDatagridPager pager, RptOrdershipmentSkuQuery query) {
		return rptOrdershipmentSkuService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(RptOrdershipmentSkuForm rptOrdershipmentSkuForm) throws Exception {
		Json json = rptOrdershipmentSkuService.addRptOrdershipmentSku(rptOrdershipmentSkuForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(RptOrdershipmentSkuForm rptOrdershipmentSkuForm) throws Exception {
		Json json = rptOrdershipmentSkuService.editRptOrdershipmentSku(rptOrdershipmentSkuForm);
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
		Json json = rptOrdershipmentSkuService.deleteRptOrdershipmentSku(id);
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
		return rptOrdershipmentSkuService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return rptOrdershipmentSkuService.getRptOrdershipmentSkuCombobox();
	}
	
	@Login
	@RequestMapping(params = "exportOrderSkuDataToExcel")
	public void exportOrderSkuDataToExcel(HttpServletResponse response, OrderSkuExportForm form) throws Exception {
		orderSkuExportService.exportOrderSkuDataToExcel(response, form);
	}

}