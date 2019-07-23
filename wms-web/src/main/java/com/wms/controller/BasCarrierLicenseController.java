package com.wms.controller;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.wms.entity.BasCarrierLicense;
import com.wms.entity.GspBusinessLicense;
import com.wms.service.GspEnterpriceService;
import com.wms.service.GspOperateDetailService;
import com.wms.utils.editor.CustomDateEditor;
import com.wms.vo.GspOperateDetailVO;
import com.wms.vo.form.BasCarrierLicenseFormString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
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
	@Autowired
	private GspOperateDetailService gspOperateDetailService;

	@Autowired
	private GspEnterpriceService gspEnterpriceService;
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		binder.registerCustomEditor(Date.class,"addtime",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"edisendtime5",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"expectedarrivetime1",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"expectedarrivetime2",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"createDate",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"editDate",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}


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
		BasCarrierLicense basCarrierLicense = basCarrierLicenseService.queryByEnterId(enterpriseId);
		Json gspEnterpriceInfo = gspEnterpriceService.getGspEnterpriceInfo(enterpriseId);
		model.put("enterpriseId", enterpriseId);
		model.put("basCarrierLicense", basCarrierLicense);
		model.put("enterpriseInFo", gspEnterpriceInfo.getObj());
		return new ModelAndView("basCarrierLicense/info", model);
	}
	/*@Login
	@RequestMapping(params = "toClient")
	public ModelAndView toClient(String enterpriseId) {
		Map<String, Object> model = new HashMap<String, Object>();
		BasCarrierLicense basCarrierLicense = basCarrierLicenseService.queryByEnterId(enterpriseId);

		model.put("enterpriseId", enterpriseId);
		model.put("basCarrierLicense", basCarrierLicense);
		return new ModelAndView("basCarrierLicense/client", model);
	}*/
	@Login
	@RequestMapping(params = "toBusinessLicense")
	public ModelAndView toBusinessLicense(@RequestParam(value = "enterpriseId",defaultValue = "") String enterpriseId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", enterpriseId);
		if(!"".equals(enterpriseId)){
			Json json = gspEnterpriceService.getGspBusinessLicense(enterpriseId);
			if(json.isSuccess() && json.getObj()!=null){
				GspBusinessLicense businessLicense = (GspBusinessLicense)json.getObj();
				List<GspOperateDetailVO> detailVOS = gspOperateDetailService.queryOperateDetailByLicense(businessLicense.getBusinessId());
				if(detailVOS!=null){
					model.put("choseScope",initOperateDetail(detailVOS));
				}
				model.put("gspBusinessLicense",businessLicense);
			}
		}
		return new ModelAndView("basCarrierLicense/businessLicense", model);
	}
	private String initOperateDetail(List<GspOperateDetailVO> list){
		List<String> arr = new ArrayList<>();
		for(GspOperateDetailVO vo : list){
			arr.add(vo.getOperateId());
		}
		String result = arr.toString();
		return result.substring(1,result.length()-1);
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
	public Json add(@RequestParam(value="basCarrierLicenseFormstr",required=true) String basCarrierLicenseFormstr) throws Exception {
		BasCarrierLicenseFormString basCarrierLicenseForm = JSON.parseObject(basCarrierLicenseFormstr, BasCarrierLicenseFormString.class);
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
	public Json edit(@RequestParam(value="basCarrierLicenseFormstr",required=true) String basCarrierLicenseFormstr) throws Exception {
		BasCarrierLicenseFormString basCarrierLicenseForm = JSON.parseObject(basCarrierLicenseFormstr, BasCarrierLicenseFormString.class);
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