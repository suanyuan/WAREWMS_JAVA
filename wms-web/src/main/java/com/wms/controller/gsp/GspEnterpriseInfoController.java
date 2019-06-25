package com.wms.controller.gsp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.wms.service.GspEnterpriceService;
import com.wms.vo.form.GspEnterpriceFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.GspEnterpriseInfoService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.GspEnterpriseInfoVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspEnterpriseInfoForm;
import com.wms.query.GspEnterpriseInfoQuery;

@Controller
@RequestMapping("gspEnterpriseInfoController")
public class GspEnterpriseInfoController {

	@Autowired
	private GspEnterpriseInfoService gspEnterpriseInfoService;

	//企业信息主题业务
	@Autowired
	private GspEnterpriceService gspEnterpriceService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("gspEnterpriseInfo/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<GspEnterpriseInfoVO> showDatagrid(EasyuiDatagridPager pager, GspEnterpriseInfoQuery query) throws Exception{
		return gspEnterpriseInfoService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add",method = RequestMethod.POST)
	@ResponseBody
	public Json add(@RequestParam(value="gspEnterpriceFrom",required=true) String gspEnterpriceFromStr) throws Exception {
		GspEnterpriceFrom gspEnterpriceFrom = JSON.parseObject(gspEnterpriceFromStr,GspEnterpriceFrom.class);
		Json json = gspEnterpriceService.addGspEnterprice(gspEnterpriceFrom);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;

	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(GspEnterpriseInfoForm gspEnterpriseInfoForm) throws Exception {
		Json json = gspEnterpriseInfoService.editGspEnterpriseInfo(gspEnterpriseInfoForm);
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
		Json json = gspEnterpriseInfoService.deleteGspEnterpriseInfo(id);
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
		return gspEnterpriseInfoService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return gspEnterpriseInfoService.getGspEnterpriseInfoCombobox();
	}

	@Login
	@RequestMapping(params = "toDetail")
	public ModelAndView toDetail(String enterpriseId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", enterpriseId);
		return new ModelAndView("gspEnterpriseInfo/detail", model);
	}

	@Login
	@RequestMapping(params = "toInfo")
	public ModelAndView toInfo(String enterpriseId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", enterpriseId);
		return new ModelAndView("gspEnterpriseInfo/info", model);
	}

	@Login
	@RequestMapping(params = "toBusinessLicense")
	public ModelAndView toBusinessLicense(String enterpriseId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", enterpriseId);
		return new ModelAndView("gspEnterpriseInfo/businessLicense", model);
	}

	@Login
	@RequestMapping(params = "toOperateLicense")
	public ModelAndView toOperateLicense(String enterpriseId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", enterpriseId);
		return new ModelAndView("gspEnterpriseInfo/operateLicense", model);
	}

	@Login
	@RequestMapping(params = "toSecondRecord")
	public ModelAndView toSecondRecord(String enterpriseId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", enterpriseId);
		return new ModelAndView("gspEnterpriseInfo/secondRecord", model);
	}
}