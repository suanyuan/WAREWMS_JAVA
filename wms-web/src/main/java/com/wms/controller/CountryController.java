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

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.CountryQuery;
import com.wms.service.CountryService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.CountryVO;
import com.wms.vo.Json;
import com.wms.vo.form.CountryForm;

@Controller
@RequestMapping("countryController")
public class CountryController {
	@Autowired
	private CountryService countryService;
	
	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("country/main", model);
	}
	
	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<CountryVO> showDatagrid(EasyuiDatagridPager pager, CountryQuery query) {
		return countryService.getCountryDatagrid(pager, query);
	}
	
	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(CountryForm countryForm) throws Exception {
		Json json = countryService.addCountry(countryForm);
		if(json == null){
			json = new Json();
		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}
	
	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(CountryForm countryForm) {
		Json json = countryService.editCountry(countryForm);
		if(json == null){
			json = new Json();
		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}
	
	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(int id) {
		Json json = countryService.deleteCountry(id);
		if(json == null){
			json = new Json();
		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}
	
	@RequestMapping(params = "getCountryCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCountryCombobox() {
		return countryService.getCountryCombobox();
	}
	
	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return countryService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}
}
