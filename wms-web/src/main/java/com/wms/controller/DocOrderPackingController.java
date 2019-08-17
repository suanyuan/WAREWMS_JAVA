package com.wms.controller;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wms.constant.Constant;
import com.wms.query.pda.PdaDocPackageQuery;
import com.wms.result.PdaResult;
import com.wms.utils.SfcUserLoginUtil;
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
	public Json orderStatusCheck(String orderNo) {
		Json json = docOrderPackingService.orderStatusCheck(orderNo);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "generateCartonNo")
	@ResponseBody
	public Json generateCartonNo(String orderNo) {
		Json json = docOrderPackingService.generateCartonNo(orderNo);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "skuScanCheck")
	@ResponseBody
	public Json skuScanCheck(String orderNo, String skuCode) {
		Json json = docOrderPackingService.skuScanCheck(orderNo, skuCode);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "skuScan")
	@ResponseBody
	public Json skuScan(String orderNo, Integer cartonNo, String skuCode, Integer qty) {
		Json json = docOrderPackingService.skuScan(orderNo, cartonNo, skuCode, qty);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "packingCommit")
	@ResponseBody
	public Json packingCommit(String orderNo) {
		Json json = docOrderPackingService.packingCommit(orderNo);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "orderCommit")
	@ResponseBody
	public Json orderCommit(String orderNo, String cartonNo, String cartontype) {
		Json json = docOrderPackingService.orderCommit(orderNo, cartonNo, cartontype);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "singlePackingCancel")
	@ResponseBody
	public Json singlePackingCancel(String orderNo, Integer cartonNo) {
		Json json = docOrderPackingService.singlePackingCancel(orderNo, cartonNo);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "orderPackingCancel")
	@ResponseBody
	public Json orderPackingCancel(String orderNo) {
		Json json = docOrderPackingService.orderPackingCancel(orderNo);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "getPackingInfo")
	@ResponseBody
	public Json getPackingInfo(String orderNo, Integer cartonNo) {
		Json json = docOrderPackingService.getPackingInfo(orderNo, cartonNo);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "getOrderPackingInfo")
	@ResponseBody
	public Json getOrderPackingInfo(String orderNo) {
		Json json = docOrderPackingService.getOrderPackingInfo(orderNo);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "exportPackingLabelPdf")
	public void exportPackingLabelPdf(HttpServletResponse response, String orderNo, String orderCode, Integer cartonNo) throws Exception {
		try {
			docOrderPackingService.exportPackingLabelPdf(response, orderNo, orderCode, cartonNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Login
	@RequestMapping(params = "exportPackingListPdf")
	public void exportPackingListPdf(HttpServletResponse response, String orderNo, String orderCode, Integer cartonNo) throws Exception {
		try {
			docOrderPackingService.exportPackingListPdf(response, orderNo, orderCode, cartonNo);
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