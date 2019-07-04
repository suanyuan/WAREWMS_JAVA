package com.wms.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.BasCarrierLicenseService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.BasCarrierLicenseVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.BasCarrierLicenseForm;
import com.wms.query.BasCarrierLicenseQuery;

@Controller
@RequestMapping("basCarrierLicenseController")
public class BasCarrierLicenseController {

	@Autowired
	private BasCarrierLicenseService basCarrierLicenseService;




	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("basCarrierLicense/main", model);
	}

	@Login
	@RequestMapping(params = "toDetail")
	public ModelAndView toDetail(String enterpriseId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", enterpriseId);
		return new ModelAndView("basCarrierLicense/detail", model);
	}

	@Login
	@RequestMapping(params = "toInfo")
	public ModelAndView toInfo(String enterpriseId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", enterpriseId);
		return new ModelAndView("basCarrierLicense/info", model);
	}
	@Login
	@RequestMapping(params = "toBusinessLicense")
	public ModelAndView toBusinessLicense(String enterpriseId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", enterpriseId);
		return new ModelAndView("basCarrierLicense/businessLicense", model);
	}
	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<BasCarrierLicenseVO> showDatagrid(EasyuiDatagridPager pager, BasCarrierLicenseQuery query) {
		return basCarrierLicenseService.getPagedDatagrid(pager, query);
	}

	/*@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(@Param(value = "basCarrierLicenseForm")String basCarrierLicenseForm) throws Exception {
		Json json = basCarrierLicenseService.addBasCarrierLicense(basCarrierLicenseForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}*/
	@Login
	@RequestMapping(params = "add",method = RequestMethod.POST)
	@ResponseBody
	public Json add(@RequestParam(value="enterpriseId",required=true) String enterpriseId,@RequestParam(value="basCarrierLicenseFormstr",required=true) String basCarrierLicenseFormstr) throws Exception {
		BasCarrierLicenseForm basCarrierLicenseForm = JSON.parseObject(basCarrierLicenseFormstr, BasCarrierLicenseForm.class);
		Json json = basCarrierLicenseService.addBasCarrierLicense(basCarrierLicenseForm);
		if(json == null){
			json = new Json();

		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;

	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(@RequestParam(value="enterpriseId",required=true) String enterpriseId, @RequestParam(value="basCarrierLicenseFormstr",required=true) String basCarrierLicenseFormstr) throws Exception {
		BasCarrierLicenseForm basCarrierLicenseForm = JSON.parseObject(basCarrierLicenseFormstr, BasCarrierLicenseForm.class);
		Json json = basCarrierLicenseService.editBasCarrierLicense(basCarrierLicenseForm);
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
		Json json = basCarrierLicenseService.deleteBasCarrierLicense(id);
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
		return basCarrierLicenseService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return basCarrierLicenseService.getBasCarrierLicenseCombobox();
	}


	@Login
	@RequestMapping(params = "getInfo")
	@ResponseBody
	public Object getInfo(String enterpriseId) {
		return basCarrierLicenseService.getBasCarrierLicenseInfo(enterpriseId);
	}

}