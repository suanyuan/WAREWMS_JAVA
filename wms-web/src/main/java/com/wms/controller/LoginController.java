package com.wms.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wms.easyui.EasyuiCombobox;
import com.wms.service.LoginService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.utils.editor.CustomDateEditor;
import com.wms.vo.Json;
import com.wms.vo.form.ForgetPwdForm;
import com.wms.vo.form.LoginForm;
import com.wms.vo.form.SfcUserLoginForm;
import com.wms.vo.form.UserOwenerForm;

@Controller
@RequestMapping("loginController")
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@Login
	@RequestMapping(params = "editUser")
	@ResponseBody
	public Json editUser(HttpSession session, UserOwenerForm adminUserOwenerForm) throws Exception {
		Json json = loginService.editUser(session, adminUserOwenerForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(false));
		}
		return json;
	}
	
	@RequestMapping(params = "login")
	@ResponseBody
	public Json login(HttpSession session, LoginForm form){
		return loginService.login(session, form);
	}
	
	@RequestMapping(params = "forgetPwd")
	@ResponseBody
	public Json forgetPwd(HttpSession session, ForgetPwdForm form) throws Exception{
		Json json = loginService.forgetPwd(session, form);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(false));
		}
		return json;
	}
	
	@RequestMapping(params = "getCaptchaImage")
	public void getCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		loginService.getCaptchaImage(request, response);
	}

	@RequestMapping(params = "getWarehouseCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getWarehouseCombobox(SfcUserLoginForm form) {
		return loginService.getWarehouseCombobox(form);
	}
}
