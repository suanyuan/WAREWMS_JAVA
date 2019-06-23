package com.wms.service;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.wms.easyui.EasyuiCombobox;
import com.wms.mybatis.dao.SfcCountryMybatisDao;
import com.wms.mybatis.dao.SfcUserLoginMybatisDao;
import com.wms.mybatis.dao.SfcUserMybatisDao;
import com.wms.mybatis.entity.SfcCountry;
import com.wms.mybatis.entity.SfcUser;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.mybatis.entity.SfcWarehouse;
import com.wms.query.SfcUserLoginQuery;
import com.wms.utils.EncryptUtil;
import com.wms.utils.MailUtil;
import com.wms.utils.RandomUtil;
import com.wms.utils.ResourceUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.vo.MailVO;
import com.wms.vo.Json;
import com.wms.vo.form.ForgetPwdForm;
import com.wms.vo.form.LoginForm;
import com.wms.vo.form.SfcUserLoginForm;
import com.wms.vo.form.UserOwenerForm;

@Service("loginService")
public class LoginService extends BaseService{
	
	@Autowired
	private SfcUserMybatisDao sfcUserMybatisDao;
	@Autowired
	private SfcUserLoginMybatisDao sfcUserLoginMybatisDao;
	@Autowired
	private SfcCountryMybatisDao sfcCountryMybatisDao;
	@Autowired
	private Producer captchaProducer;
	@Autowired(required = true)
	private JavaMailSender mailSender;
	
	/**
	 * 登入驗證
	 * @param session 
	 * @param form
	 * @return
	 */
	public Json login(HttpSession session, LoginForm form) {
		Json json = new Json();
		SfcUserLoginQuery sfcUserLoginQuery = new SfcUserLoginQuery();
		sfcUserLoginQuery.setId(form.getUsername());
		sfcUserLoginQuery.setWarehouseId(form.getWarehouseId());
		SfcUserLogin sfcUserLogin = sfcUserLoginMybatisDao.queryById(sfcUserLoginQuery);
		if(sfcUserLogin != null && sfcUserLogin.getPwd().equals(EncryptUtil.md5AndSha(form.getPassword()))){
			if(sfcUserLogin.getEnable().intValue() == 0){
				json.setMsg("此帐号已停止使用");
			}else{
				session.setAttribute(ResourceUtil.getUserInfo(), sfcUserLogin);
				sfcUserLogin.setLastLoginTime(new Date());
				sfcUserLogin.setSessionId(session.getId());
				sfcUserLoginMybatisDao.updateBySelective(sfcUserLogin);
				json.setObj(sfcUserLogin);
				json.setSuccess(true);
				json.setMsg("登入成功！");
			}
		}else{
			json.setMsg("帐号或密码输入错误，登入失败！");
		}
		return json;
	}

	public Json editUser(HttpSession session, UserOwenerForm userOwenerForm) throws Exception {
		Json json = new Json();
		SfcUserLoginQuery sfcUserLoginQuery = new SfcUserLoginQuery();
		sfcUserLoginQuery.setId(userOwenerForm.getUserId());
		sfcUserLoginQuery.setWarehouseId(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		SfcUserLogin sfcUserLogin = new SfcUserLogin();
		sfcUserLogin = sfcUserLoginMybatisDao.queryById(sfcUserLoginQuery);
		if(sfcUserLogin.getPwd().equals(EncryptUtil.md5AndSha(userOwenerForm.getPwd()))){
			String oldPwd = sfcUserLogin.getPwd();
			byte enable = sfcUserLogin.getEnable();
			BeanUtils.copyProperties(userOwenerForm, sfcUserLogin);
			if(!StringUtils.isEmpty(userOwenerForm.getNewPwd()) && 
				!StringUtils.isEmpty(userOwenerForm.getRePwd()) &&
				 userOwenerForm.getNewPwd().equals(userOwenerForm.getRePwd())){
				
				sfcUserLogin.setPwd(EncryptUtil.md5AndSha(userOwenerForm.getNewPwd()));
			}else{
				sfcUserLogin.setPwd(oldPwd);
			}
			sfcUserLogin.setCountry((SfcCountry) sfcCountryMybatisDao.queryById(userOwenerForm.getCountryId()));
			sfcUserLogin.setEnable(enable);
			sfcUserLoginMybatisDao.updateBySelective(sfcUserLogin);
			
			sfcUserLogin = (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo());
			session.setAttribute(ResourceUtil.getUserInfo(), sfcUserLogin);
			json.setSuccess(true);
			json.setMsg(ResourceUtil.getProcessResultMsg(true));
		}else{
			json.setMsg("密码输入错误！");
		}
		json.setObj(sfcUserLogin);
		return json;
	}

	public Json forgetPwd(HttpSession session, ForgetPwdForm form) throws Exception {
		Json json = new Json();
		if(form.getCode().equals(session.getAttribute(Constants.KAPTCHA_SESSION_KEY).toString())){
			SfcUserLogin sfcUserLogin = new SfcUserLogin();
			sfcUserLogin.setId(form.getUserId());
			SfcUser sfcUser = sfcUserMybatisDao.queryListById(sfcUserLogin);
			if(sfcUser == null){
				json.setMsg("查无此帐号！");
			}else{
				String pwd = RandomUtil.genPassword(5);
				sfcUser.setPwd(EncryptUtil.md5AndSha(pwd));
				sfcUserMybatisDao.updateBySelective(sfcUser);
				
				MailVO mailVO = new MailVO();
				mailVO.setTo(sfcUser.getEmail());
				mailVO.setSubject("密码信");
				mailVO.setContent(String.format(ResourceUtil.getMailPwdTemplate(), sfcUser.getUserName(), pwd));
				MailUtil.sendMail(mailVO, mailSender);
				
				json.setSuccess(true);
				json.setMsg("请至注册时使用的信箱收取密码信！");
			}
		}else{
			json.setMsg("验证码输入错误！");
		}
		return json;
	}
	
	public void getCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		String capText = captchaProducer.createText();
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			if(out != null){
				out.close();
			}
		}
	}

	public List<EasyuiCombobox> getWarehouseCombobox(SfcUserLoginForm form) {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<SfcWarehouse> warehouseIdList = sfcUserLoginMybatisDao.queryWarehouseByUser(form);
		if(warehouseIdList != null && warehouseIdList.size() > 0){
			for(SfcWarehouse warehouseId : warehouseIdList){
				combobox = new EasyuiCombobox();
				combobox.setId(warehouseId.getId());
				combobox.setValue(warehouseId.getWarehouseName());
				if (warehouseId.getDefaultFlag().equals("Y")) {
					combobox.setSelected(true);
				} else {
					combobox.setSelected(false);
				}
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
}
