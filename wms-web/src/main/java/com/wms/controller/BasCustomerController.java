package com.wms.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.BasCustomerService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.utils.editor.CustomDateEditor;
import com.wms.vo.Json;
import com.wms.vo.BasCustomerVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.BasCustomerForm;
import com.wms.query.BasCustomerQuery;

@Controller
@RequestMapping("basCustomerController")
public class BasCustomerController {

	@Autowired
	private BasCustomerService basCustomerService;
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class,"addtime",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"edittime",new CustomDateEditor(dateFormat, true));
	}

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("basCustomer/main", model);
	}

	@Login
	@RequestMapping(params = "toCustomer")
	public ModelAndView toCustomer(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("basCustomer/customer", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<BasCustomerVO> showDatagrid(EasyuiDatagridPager pager, BasCustomerQuery query) {
		return basCustomerService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(BasCustomerForm basCustomerForm) throws Exception {
		Json json = basCustomerService.addBasCustomer(basCustomerForm);
		if(json == null){
			json = new Json();
		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(BasCustomerForm basCustomerForm) throws Exception {
		Json json = basCustomerService.editBasCustomer(basCustomerForm);
		if(json == null){
			json = new Json();
		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String enterpriseId, String customerType) {
		Json json = basCustomerService.deleteBasCustomer(enterpriseId,customerType);
		if(json == null){
			json = new Json();
		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}
	@Login
	@RequestMapping(params = "goon")
	@ResponseBody
	public Json goon(String enterpriseId, String customerType) {
		Json json = basCustomerService.goonBasCustomer(enterpriseId,customerType);
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
		return basCustomerService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCustomerTypeCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCustomerTypeCombobox() {
		return basCustomerService.getCustomerTypeCombobox();
	}
	@Login
	@RequestMapping(params = "getOperateTypeCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getOperateTypeCombobox() {
		return basCustomerService.getOperateTypeCombobox();
	}


    @Login
    @RequestMapping(params = "toDetail")
    public ModelAndView toDetail(String enterpriseId) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("enterpriseId", enterpriseId);
        return new ModelAndView("basCustomer/detail", model);
    }

    @Login
    @RequestMapping(params = "toInfo")
    public ModelAndView toInfo(String enterpriseId) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("enterpriseId", enterpriseId);
        return new ModelAndView("basCustomer/info", model);
    }

    @Login
    @RequestMapping(params = "toBusinessLicense")
    public ModelAndView toBusinessLicense(String enterpriseId) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("enterpriseId", enterpriseId);
        return new ModelAndView("basCustomer/businessLicense", model);
    }

    @Login
    @RequestMapping(params = "toOperateLicense")
    public ModelAndView toOperateLicense(String enterpriseId) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("enterpriseId", enterpriseId);
        return new ModelAndView("basCustomer/operateLicense", model);
    }

    @Login
    @RequestMapping(params = "toSecondRecord")
    public ModelAndView toSecondRecord(String enterpriseId) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("enterpriseId", enterpriseId);
        return new ModelAndView("basCustomer/secondRecord", model);
    }
	@Login
	@RequestMapping(params = "toReceivingAddress")
	public ModelAndView toReceivingAddress(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("basCustomer/receivingAddress", model);
	}

	@Login
	@RequestMapping(params = "getReceivingAddress")
	@ResponseBody
	public Object getReceivingAddress(String enterpriseId,String receivingAddressId) {
		return basCustomerService.getReceivingAddressInfo(enterpriseId,receivingAddressId);
	}

}