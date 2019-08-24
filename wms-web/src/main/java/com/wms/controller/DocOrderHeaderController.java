package com.wms.controller;

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.ActAllocationDetails;
import com.wms.entity.order.OrderDetailsForNormal;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.ActAllocationDetailsQuery;
import com.wms.query.OrderHeaderForNormalQuery;
import com.wms.service.OrderHeaderForNormalService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.utils.editor.CustomDateEditor;
import com.wms.vo.ActAllocationDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.OrderHeaderForNormalVO;
import com.wms.vo.form.DocOrderHeaderExportForm;
import com.wms.vo.form.OrderHeaderForNormalForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	@RequestMapping(params = "showAllocation")
	@ResponseBody
	public EasyuiDatagrid<ActAllocationDetailsVO> showAllocation(EasyuiDatagridPager pager,String ordero) {
		ActAllocationDetailsQuery query = new ActAllocationDetailsQuery();
		query.setOrderno(ordero);
		return orderHeaderForNormalService.getPageAllocation(pager, query);
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
	public Json delete(String orderno) {
		Json json = orderHeaderForNormalService.delete(orderno);
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
	@RequestMapping(params = "picking")
	@ResponseBody
	public Json picking(String orderNo) {
		Json json = orderHeaderForNormalService.picking(orderNo);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "unPicking")
	@ResponseBody
	public Json unPicking(String orderNo) {
		Json json = orderHeaderForNormalService.unPicking(orderNo);
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
	public Json cancel(String orderno) {
		Json json = orderHeaderForNormalService.cancel(orderno);
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

	@Login
	@RequestMapping(params = "getRefOutOrder")
	@ResponseBody
	public List<EasyuiCombobox> getRefOutOrder() {
		return orderHeaderForNormalService.getRefOut();
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
	 * 随货清单
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

	@Login
	@RequestMapping(params = "getLotAttBySkuCustomerId",method = RequestMethod.POST)
	@ResponseBody
	public List<EasyuiCombobox> getLotAttBySkuCustomerId(String sku,String customerId){
		return orderHeaderForNormalService.getLotAttBySkuCustomerId(sku,customerId);
	}

	@Login
	@RequestMapping(params = "doRefOut",method = RequestMethod.POST)
	@ResponseBody
	public Json doRefOut(String orderno,String refOrderno) throws Exception{
		return orderHeaderForNormalService.doRefOut(orderno,refOrderno);
	}

	@Login
	@RequestMapping(params = "reqDouble",method = RequestMethod.POST)
	@ResponseBody
	public Json reqDouble(String orderno) throws Exception{
		return orderHeaderForNormalService.reqDouble(orderno);
	}

	@Login
	@RequestMapping(params = "printH")
	public void printH(HttpServletResponse response, String orderCodeList) throws Exception {
		try {
			orderHeaderForNormalService.printH(response,orderCodeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Login
	@RequestMapping(params = "printExpress")
	public String printExpress(HttpServletResponse response, String orderCodeList, Model model) throws Exception {
		try {
			orderHeaderForNormalService.printExpress(response,orderCodeList,model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "iReportView";
	}

	@Login
	@RequestMapping(params = "copyQueryOrderno")
	@ResponseBody
	public List<EasyuiCombobox>  copyQueryOrderno(String customerid)throws  Exception{
		return orderHeaderForNormalService.copyQueryOrderno(customerid);
	}

	@Login
	@RequestMapping(params = "copyDetailGo",method = RequestMethod.POST)
	@ResponseBody
	public Json copyDetailGo(String orderno ,String detailOrderno,String soreference2) throws  Exception{
		Json json = orderHeaderForNormalService.copyDetailGo(orderno,detailOrderno,soreference2);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}
}