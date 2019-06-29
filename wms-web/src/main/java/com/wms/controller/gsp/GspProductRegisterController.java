package com.wms.controller.gsp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.wms.entity.GspProductRegister;
import com.wms.query.GspProductRegisterSpecsQuery;
import com.wms.vo.GspProductRegisterSpecsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.GspProductRegisterService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.GspProductRegisterVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspProductRegisterForm;
import com.wms.query.GspProductRegisterQuery;

@Controller
@RequestMapping("gspProductRegisterController")
public class GspProductRegisterController {

	@Autowired
	private GspProductRegisterService gspProductRegisterService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("gspProductRegister/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<GspProductRegisterVO> showDatagrid(EasyuiDatagridPager pager, GspProductRegisterQuery query) {
		return gspProductRegisterService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(GspProductRegisterForm gspProductRegisterForm) throws Exception {
		Json json = gspProductRegisterService.addGspProductRegister(gspProductRegisterForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "addSpec",method = RequestMethod.POST)
	@ResponseBody
	public Json add(@RequestParam(value="gspProductRegisterId",required=true) String gspProductRegisterId,@RequestParam(value="specId",required=true) String specId) throws Exception {
		Json json = gspProductRegisterService.bindProduct(gspProductRegisterId,specId);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;

	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(GspProductRegisterForm gspProductRegisterForm) throws Exception {
		Json json = gspProductRegisterService.editGspProductRegister(gspProductRegisterForm);
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
		Json json = gspProductRegisterService.deleteGspProductRegister(id);
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
		return gspProductRegisterService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return gspProductRegisterService.getGspProductRegisterCombobox();
	}


	@Login
	@RequestMapping(params = "toDetail")
	public ModelAndView toDetail(@RequestParam(defaultValue = "") String id){
		Map<String,Object> map = new HashMap<>();
		if(!"".equals(id)){
			GspProductRegister gspProductRegister = gspProductRegisterService.queryById(id);
			map.put("gspProductRegister",gspProductRegister);
		}
		map.put("gspProductRegisterId",id);
		return new ModelAndView("gspProductRegister/detail",map);
	}

	/**
	 * 查询产品注册证规格子表
	 * @param pager
	 * @param query
	 * @return
	 */
	@Login
	@RequestMapping(params = "showSpecsList")
	@ResponseBody
	public EasyuiDatagrid<GspProductRegisterSpecsVO> showSpecsList(EasyuiDatagridPager pager, GspProductRegisterSpecsQuery query){
		return gspProductRegisterService.queryProductPageListByRegisterId(pager,query);
	}
}