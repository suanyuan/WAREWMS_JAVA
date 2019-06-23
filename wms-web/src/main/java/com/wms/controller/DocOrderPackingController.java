package com.wms.controller;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.DocOrderPackingService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.DocOrderPackingVO;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.DocOrderPackingQuery;

@Controller
@RequestMapping("docOrderPackingController")
public class DocOrderPackingController {

	@Autowired
	private DocOrderPackingService docOrderPackingService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("docOrderPacking/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<DocOrderPackingVO> showDatagrid(EasyuiDatagridPager pager, DocOrderPackingQuery query) {
		return docOrderPackingService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "orderStatusCheck")
	@ResponseBody
	public Json orderStatusCheck(String orderno) {
		Json json = docOrderPackingService.orderStatusCheck(orderno);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "addBoxno")
	@ResponseBody
	public Json addBoxno(String orderno) {
		Json json = docOrderPackingService.addBoxno(orderno);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "skuScanCheck")
	@ResponseBody
	public Json skuScanCheck(String orderno, String traceid, String sku) {
		Json json = docOrderPackingService.skuScanCheck(orderno, traceid, sku);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "skuScan")
	@ResponseBody
	public Json skuScan(String orderno, String traceid, String sku, Integer qty) {
		Json json = docOrderPackingService.skuScan(orderno, traceid, sku, qty);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "commit")
	@ResponseBody
	public Json commit(String orderno) {
		Json json = docOrderPackingService.commitDocOrderPacking(orderno);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "singleDelete")
	@ResponseBody
	public Json singleDelete(String traceid, String sku, String allocationdetailsid) {
		Json json = docOrderPackingService.packingSingleDelete(traceid, sku, allocationdetailsid);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "skuDelete")
	@ResponseBody
	public Json skuDelete(String traceid, String sku, String allocationdetailsid) {
		Json json = docOrderPackingService.packingSkuDelete(traceid, sku, allocationdetailsid);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "exportPackingLabelPdf")
	public void exportPackingLabelPdf(HttpServletResponse response, String orderno, String traceid) throws Exception {
		try {
			docOrderPackingService.exportPackingLabelPdf(response, orderno, traceid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Login
	@RequestMapping(params = "exportPackingListPdf")
	public void exportPackingListPdf(HttpServletResponse response, String orderno, String traceid) throws Exception {
		try {
			docOrderPackingService.exportPackingListPdf(response, orderno, traceid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return docOrderPackingService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

}