package com.wms.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.OrderDetailsForNormalService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.utils.editor.CustomDateEditor;
import com.wms.vo.Json;
import com.wms.vo.OrderDetailsForNormalVO;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.OrderDetailsForNormalForm;
import com.wms.query.OrderDetailsForNormalQuery;

@Controller
@RequestMapping("docOrderDetailsController")
public class DocOrderDetailsController {

	@Autowired
	private OrderDetailsForNormalService orderDetailsForNormalService;

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class,"lotatt01",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"lotatt02",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"lotatt03",new CustomDateEditor(dateFormat, true));
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<OrderDetailsForNormalVO> showDatagrid(EasyuiDatagridPager pager, OrderDetailsForNormalQuery query) {
		return orderDetailsForNormalService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(OrderDetailsForNormalForm orderDetailsForNormalForm) throws Exception {
		Json json = orderDetailsForNormalService.add(orderDetailsForNormalForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(OrderDetailsForNormalForm orderDetailsForNormalForm) throws Exception {
		Json json = orderDetailsForNormalService.edit(orderDetailsForNormalForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String orderNo, int orderLineNo) {
		Json json = orderDetailsForNormalService.delete(orderNo, orderLineNo);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "allocation")
	@ResponseBody
	public Json allocation(String orderNo, String orderLineNoList) {
		Json json = orderDetailsForNormalService.allocation(orderNo, orderLineNoList);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "deAllocation")
	@ResponseBody
	public Json deAllocation(String orderNo, String orderLineNoList) {
		Json json = orderDetailsForNormalService.deAllocation(orderNo, orderLineNoList);
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
		return orderDetailsForNormalService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}
}