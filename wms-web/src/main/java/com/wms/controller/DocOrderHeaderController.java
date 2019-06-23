package com.wms.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.DocOrderHeaderService;
import com.wms.service.ExportOrderService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.utils.editor.CustomDateEditor;
import com.wms.vo.Json;
import com.wms.vo.DocOrderHeaderVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocOrderHeaderExportForm;
import com.wms.vo.form.DocOrderHeaderForm;
import com.wms.query.DocOrderHeaderQuery;

@Controller
@RequestMapping("docOrderHeaderController")
public class DocOrderHeaderController {

	@Autowired
	private DocOrderHeaderService docOrderHeaderService;
	
	@Autowired
	private ExportOrderService exportOrderService;

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		binder.registerCustomEditor(Date.class,"ordertime",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"ordertimeTo",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"lastshipmenttime",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"addtime",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"edittime",new CustomDateEditor(dateFormat, true));
	}

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("docOrderHeader/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<DocOrderHeaderVO> showDatagrid(EasyuiDatagridPager pager, DocOrderHeaderQuery query) {
		return docOrderHeaderService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(DocOrderHeaderForm docOrderHeaderForm) throws Exception {
		Json json = docOrderHeaderService.addDocOrderHeader(docOrderHeaderForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(DocOrderHeaderForm docOrderHeaderForm) throws Exception {
		Json json = docOrderHeaderService.editDocOrderHeader(docOrderHeaderForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String orderno) {
		Json json = docOrderHeaderService.deleteDocOrderHeader(orderno);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "allocation")
	@ResponseBody
	public Json allocation(String orderno) {
		Json json = docOrderHeaderService.allocationDocOrderHeader(orderno);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "deAllocation")
	@ResponseBody
	public Json deAllocation(String orderno) {
		Json json = docOrderHeaderService.deAllocationDocOrderHeader(orderno);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "picking")
	@ResponseBody
	public Json picking(String orderno) {
		Json json = docOrderHeaderService.pickingDocOrderHeader(orderno);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "unPicking")
	@ResponseBody
	public Json unPicking(String orderno) {
		Json json = docOrderHeaderService.unPickingDocOrderHeader(orderno);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "unPacking")
	@ResponseBody
	public Json unPacking(String orderno) {
		Json json = docOrderHeaderService.unPackingDocOrderHeader(orderno);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "shipment")
	@ResponseBody
	public Json shipment(String orderno) {
		Json json = docOrderHeaderService.shipmentDocOrderHeader(orderno);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "cancel")
	@ResponseBody
	public Json cancel(String orderno) {
		Json json = docOrderHeaderService.cancelDocOrderHeader(orderno);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "exportPdf")
	public void exportPdf(HttpServletResponse response, String orderList) throws Exception {
		try {
			docOrderHeaderService.exportPdf(response, orderList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Login
	@RequestMapping(params = "exportPickingPdf")
	public void exportPickingPdf(HttpServletResponse response, String orderList) throws Exception {
		try {
			docOrderHeaderService.exportPickingPdf(response, orderList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Login
	@RequestMapping(params = "exportOrderDataToExcel")
	public void exportOrderDataToExcel(HttpServletResponse response, DocOrderHeaderExportForm form) throws Exception {
		exportOrderService.exportOrderDataToExcel(response, form);
	}

	@Login
	@RequestMapping(params = "importExcelData")
	@ResponseBody
	public Json importExcelData( MultipartHttpServletRequest mhsr) throws Exception {
		return docOrderHeaderService.importExcelData(mhsr);
	}
	
	@Login
	@RequestMapping(params = "exportTemplate", method = RequestMethod.POST)
	public void exportTemplate(HttpServletResponse response, String token) throws Exception {
		docOrderHeaderService.exportTemplate(response, token);
	}

	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return docOrderHeaderService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getOrderTypeCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getOrderTypeCombobox() {
		return docOrderHeaderService.getOrderTypeCombobox();
	}

	@Login
	@RequestMapping(params = "getSostatusCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getSostatusCombobox() {
		return docOrderHeaderService.getSostatusCombobox();
	}

	@Login
	@RequestMapping(params = "getReleasestatusCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getReleasestatusCombobox() {
		return docOrderHeaderService.getReleasestatusCombobox();
	}

}