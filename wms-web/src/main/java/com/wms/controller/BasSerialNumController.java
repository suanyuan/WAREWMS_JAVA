package com.wms.controller;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.BasSerialNumQuery;
import com.wms.service.BasSerialNumService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.BasSerialNumVO;
import com.wms.vo.Json;
import com.wms.vo.form.BasSerialNumForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("basSerialNumController")
public class BasSerialNumController {

	@Autowired
	private BasSerialNumService basSerialNumService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("basSerialNum/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<BasSerialNumVO> showDatagrid(EasyuiDatagridPager pager, BasSerialNumQuery query) {
		return basSerialNumService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(BasSerialNumForm basSerialNumForm) throws Exception {
		Json json = basSerialNumService.addBasSerialNum(basSerialNumForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(BasSerialNumForm basSerialNumForm) throws Exception {
		Json json = basSerialNumService.editBasSerialNum(basSerialNumForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String id) {
		Json json = basSerialNumService.deleteBasSerialNum(id);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return basSerialNumService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

//	@Login
//	@RequestMapping(params = "getCombobox")
//	@ResponseBody
//	public List<EasyuiCombobox> getCombobox() {
//		return basSerialNumService.getBasSerialNumCombobox();
//	}

}