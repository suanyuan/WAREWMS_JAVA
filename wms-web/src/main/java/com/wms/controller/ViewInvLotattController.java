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

import com.wms.entity.UserLogin;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.BasSkuExportService;
import com.wms.service.ViewInvLotattExportService;
import com.wms.service.ViewInvLotattService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.ViewInvLotattVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.BasSkuExportForm;
import com.wms.vo.form.ViewInvLotattExportForm;
import com.wms.vo.form.ViewInvLotattForm;
import com.wms.query.ViewInvLotattQuery;

@Controller
@RequestMapping("viewInvLotattController")
public class ViewInvLotattController {

	@Autowired
	private ViewInvLotattService viewInvLotattService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("viewInvLotatt/main", model);
	}
	
	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<ViewInvLotattVO> showDatagrid(EasyuiDatagridPager pager, ViewInvLotattQuery query) {
		return viewInvLotattService.getPagedDatagrid(pager, query);
	}
	
	@Autowired
	private ViewInvLotattExportService viewInvLotattExportService;
	
	@Login
	@RequestMapping(params = "adj")
	@ResponseBody
	public Json adj(ViewInvLotattForm viewInvLotattForm) throws Exception {
		Json json = viewInvLotattService.adjViewInvLotatt(viewInvLotattForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	/**
	 * 单条库位移动
 	 * @param viewInvLotattForm
	 * @return
	 * @throws Exception
	 */
	@Login
	@RequestMapping(params = "mov")
	@ResponseBody
	public Json mov(ViewInvLotattForm viewInvLotattForm) throws Exception {
		Json json = viewInvLotattService.movViewInvLotatt(viewInvLotattForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}
	/**
	 * 多条库位移动
 	 * @param forms
	 * @return
	 * @throws Exception
	 */
	@Login
	@RequestMapping(params = "movList")
	@ResponseBody
	public Json mov(String forms) throws Exception {
		Json json = viewInvLotattService.movViewInvLotattList(forms);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}
	
	@Login
	@RequestMapping(params = "hold")
	@ResponseBody
	public Json hold(ViewInvLotattForm viewInvLotattForm) throws Exception {
		Json json = viewInvLotattService.holdViewInvLotatt(viewInvLotattForm);
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
		return viewInvLotattService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "exportViewInvLotattDataToExcel")
	public void exportViewInvLotattDataToExcel(HttpServletResponse response, ViewInvLotattExportForm form) throws Exception {
		viewInvLotattExportService.exportViewInvLotattDataToExcel(response, form);
	}
	
	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return viewInvLotattService.getViewInvLotattCombobox();
	}

}