package com.wms.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.ViewInvHoldService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.ViewInvHoldVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.ViewInvHoldForm;
import com.wms.vo.form.ViewInvLotattForm;
import com.wms.query.ViewInvHoldQuery;

@Controller
@RequestMapping("viewInvHoldController")
public class ViewInvHoldController {

	@Autowired
	private ViewInvHoldService viewInvHoldService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("viewInvHold/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<ViewInvHoldVO> showDatagrid(EasyuiDatagridPager pager, ViewInvHoldQuery query) {
		return viewInvHoldService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "hold")
	@ResponseBody
	public Json hold(ViewInvHoldForm viewInvHoldForm) throws Exception {
		Json json = viewInvHoldService.holdViewInvHold(viewInvHoldForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}
	
	@Login
	@RequestMapping(params = "release")
	@ResponseBody
	public Json release(ViewInvHoldForm viewInvHoldForm) throws Exception {
		Json json = viewInvHoldService.releaseViewInvHold(viewInvHoldForm);
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
		return viewInvHoldService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	
}