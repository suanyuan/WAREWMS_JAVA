package com.wms.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wms.entity.SearchOutInvLocation;
import com.wms.query.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiTree;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.UserService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.utils.editor.CustomDateEditor;
import com.wms.vo.Json;
import com.wms.vo.SfcUserVO;
import com.wms.vo.form.UserForm;

@Controller
@RequestMapping("userController")
public class UserController {
	@Autowired
	private UserService userService;
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("user/main", model);
	}
	
	@Login
	@RequestMapping(params = "getTree")
	@ResponseBody
	public Set<EasyuiTree> showTree() {
		return userService.getUserTree();
	}
	
	@Login
	@RequestMapping(params = "showTreegrid")
	@ResponseBody
	public Set<SfcUserVO> showTreegrid() {
		return userService.getUserTreegrid();
	}


	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(UserForm userForm) throws Exception {
		return userService.addUser(userForm);
	}
	
	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(UserForm userForm) {
		return userService.editUser(userForm);
	}
	
	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String id) {
		Json json = userService.deleteUser(id);
		if(json == null){
			json = new Json();
		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}
	
	@Login
	@RequestMapping(params = "getUser")
	@ResponseBody
	public SfcUserVO getUser(HttpSession session) {
		SfcUserLogin sfcUserLogin = (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo());
		return userService.getUser(sfcUserLogin);
	}
	
	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return userService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}
	
	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox(HttpSession session) {
		return userService.getWarehouseCombobox();
	}
	
	@Login
	@RequestMapping(params = "getDefaultCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getDefaultCombobox(UserForm userForm) {
		return userService.getDefaultWarehouseCombobox(userForm);
	}
	
	@Login
	@RequestMapping(params = "getCustomerCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCustomerCombobox(HttpSession session) {
		return userService.getCustomerCombobox();
	}
	
	@RequestMapping(params = "getSupervisorCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getSupervisorCombobox() {
		return userService.getSupervisorCombobox();
	}



	@Login
	@RequestMapping(params = "exportUserIdDataToExcel")
	public void exportUserIdDataToExcel(HttpServletResponse response, UserQuery query) throws Exception {
		userService.exportUserIdDataToExcel(response, query);
	}
}
