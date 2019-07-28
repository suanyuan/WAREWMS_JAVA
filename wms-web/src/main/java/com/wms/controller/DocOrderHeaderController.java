package com.wms.controller;

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.OrderHeaderForNormalQuery;
import com.wms.service.OrderHeaderForNormalService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.utils.editor.CustomDateEditor;
import com.wms.vo.Json;
import com.wms.vo.OrderHeaderForNormalVO;
import com.wms.vo.form.OrderHeaderForNormalForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("docOrderHeaderController")
public class DocOrderHeaderController {

	@Autowired
	private OrderHeaderForNormalService orderHeaderForNormalService;

//	@Autowired
//	private ExportOrderService exportOrderService;

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class,"orderStartTime",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"orderEndTime",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"requiredDeliveryTime",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"orderTime",new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(Date.class,"lastShipmentTime",new CustomDateEditor(dateTimeFormat, true));
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
	public EasyuiDatagrid<OrderHeaderForNormalVO> showDatagrid(EasyuiDatagridPager pager, OrderHeaderForNormalQuery query) {
		return orderHeaderForNormalService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(OrderHeaderForNormalForm orderHeaderForNormalForm) throws Exception {
		Json json = orderHeaderForNormalService.add(orderHeaderForNormalForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(OrderHeaderForNormalForm orderHeaderForNormalForm) throws Exception {
		Json json = orderHeaderForNormalService.edit(orderHeaderForNormalForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String orderNo) {
		Json json = orderHeaderForNormalService.delete(orderNo);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "allocation")
	@ResponseBody
	public Json allocation(String orderNo) {
		Json json = orderHeaderForNormalService.allocation(orderNo);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "deAllocation")
	@ResponseBody
	public Json deAllocation(String orderNo) {
		Json json = orderHeaderForNormalService.deAllocation(orderNo);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "shipment")
	@ResponseBody
	public Json shipment(OrderHeaderForNormalForm orderHeaderForNormalForm) throws Exception {
		Json json = orderHeaderForNormalService.shipment(orderHeaderForNormalForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "cancel")
	@ResponseBody
	public Json cancel(String orderNo) {
		Json json = orderHeaderForNormalService.cancel(orderNo);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	/*@Login
	@RequestMapping(params = "exportPickingPdf")
	public void exportPickingPdf(HttpServletResponse response, String orderNo) throws Exception {
		try {
			orderHeaderForNormalService.exportPickingPdf(response, orderNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

//	@Login
//	@RequestMapping(params = "exportReceiptPdf")
//	public void exportReceiptPdf(HttpServletResponse response, String orderNo) throws Exception {
//		try {
//			orderHeaderForNormalService.exportReceiptPdf(response, orderNo);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	@Login
//	@RequestMapping(params = "exportOrderDataToExcel")
//	public void exportOrderDataToExcel(HttpServletResponse response, OrderHeaderForNormalExportForm form) throws Exception {
//		exportOrderService.exportOrderDataToExcel(response, form);
//	}

	@Login
	@RequestMapping(params = "importExcelData")
	@ResponseBody
	public Json importExcelData(MultipartHttpServletRequest mhsr) throws Exception {
		return orderHeaderForNormalService.importExcelData(mhsr);
	}
	
	@Login
	@RequestMapping(params = "exportTemplate", method = RequestMethod.POST)
	public void exportTemplate(HttpServletResponse response, String token) throws Exception {
		orderHeaderForNormalService.exportTemplate(response, token);
	}

	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return orderHeaderForNormalService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getOrderTypeCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getOrderTypeCombobox() {
		return orderHeaderForNormalService.getOrderTypeCombobox();
	}

	@Login
	@RequestMapping(params = "getOrderStatusCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getOrderStatusCombobox() {
		return orderHeaderForNormalService.getOrderStatusCombobox();
	}

	@Login
	@RequestMapping(params = "getReleasestatusCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getReleasestatusCombobox() {
		return orderHeaderForNormalService.getReleasestatusCombobox();
	}

	/**
	 * 打印拣货
	 * @param response
	 * @param orderCodeList
	 * @throws Exception
	 */
	@Login
	@RequestMapping(params = "exportPackingPdf")
	public void exportPackingPdf(HttpServletResponse response, String orderCodeList) throws Exception {
		try {
			orderHeaderForNormalService.exportPickingPdf(response,orderCodeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打印拣货
	 * @param response
	 * @param orderCodeList
	 * @throws Exception
	 */
	@Login
	@RequestMapping(params = "exportAccompanyingPdf")
	public void exportAccompanyingPdf(HttpServletResponse response, String orderCodeList) throws Exception {
		try {
			orderHeaderForNormalService.exportAccompanyingPdf(response,orderCodeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}