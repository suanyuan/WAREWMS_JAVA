package com.wms.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.GspProductRegisterSpecsService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.GspProductRegisterSpecsVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspProductRegisterSpecsForm;
import com.wms.query.GspProductRegisterSpecsQuery;

@Controller
@RequestMapping("gspProductRegisterSpecsController")
public class GspProductRegisterSpecsController {

	@Autowired
	private GspProductRegisterSpecsService gspProductRegisterSpecsService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("gspProductRegisterSpecs/main", model);
	}

	@Login
	@RequestMapping(params = "toAdd")
	public ModelAndView toAdd(String specsId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("specsId", specsId);
		return new ModelAndView("gspProductRegisterSpecs/add", model);
	}
	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<GspProductRegisterSpecsVO> showDatagrid(EasyuiDatagridPager pager, GspProductRegisterSpecsQuery query) {
		return gspProductRegisterSpecsService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(@RequestParam(value="gspProductRegisterSpecsForm",required=true) String gspProductRegisterSpecsFormStr) throws Exception {

		GspProductRegisterSpecsForm gspProductRegisterSpecsForm = JSON.parseObject(gspProductRegisterSpecsFormStr,GspProductRegisterSpecsForm.class);
		Json json = gspProductRegisterSpecsService.addGspProductRegisterSpecs(gspProductRegisterSpecsForm);
		if(json == null){
			json = new Json();

		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(@RequestParam(value="gspProductRegisterSpecsForm",required=true) String gspProductRegisterSpecsFormStr) throws Exception {
		GspProductRegisterSpecsForm gspProductRegisterSpecsForm = JSON.parseObject(gspProductRegisterSpecsFormStr,GspProductRegisterSpecsForm.class);
		Json json = gspProductRegisterSpecsService.editGspProductRegisterSpecs(gspProductRegisterSpecsForm);
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
		Json json = gspProductRegisterSpecsService.deleteGspProductRegisterSpecs(id);
		if(json == null){
			json = new Json();

		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "getInfo")
	@ResponseBody
	public Object getInfo(String specsId) {
		return gspProductRegisterSpecsService.getGspProductRegisterSpecsInfo(specsId);
	}

	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return gspProductRegisterSpecsService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return gspProductRegisterSpecsService.getGspProductRegisterSpecsCombobox();
	}

}