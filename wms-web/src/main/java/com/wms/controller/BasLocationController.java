package com.wms.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.BasLocationService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.utils.editor.CustomDateEditor;
import com.wms.vo.Json;
import com.wms.vo.BasLocationVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.BasLocationForm;
import com.wms.vo.form.BasLocationExportForm;
import com.wms.query.BasLocationQuery;
import com.wms.service.BasLocationExportService;

@Controller
@RequestMapping("basLocationController")
public class BasLocationController {

	@Autowired
	private BasLocationService basLocationService;
	
	@Autowired
	private BasLocationExportService basLocationExportService;
	
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
		return new ModelAndView("basLocation/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<BasLocationVO> showDatagrid(EasyuiDatagridPager pager, BasLocationQuery query) {
		return basLocationService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(BasLocationForm basLocationForm) throws Exception {
		Json json = basLocationService.addBasLocation(basLocationForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(BasLocationForm basLocationForm) throws Exception {
		Json json = basLocationService.editBasLocation(basLocationForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String locationid) { 
		Json json = basLocationService.deleteBasLocation(locationid);
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
		return basLocationService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return basLocationService.getBasLocationCombobox();
	}

	@Login
	@RequestMapping(params = "exportLocationData")
	public void exportLocationData(HttpServletResponse response, BasLocationExportForm form) throws Exception {
		basLocationExportService.exportLocationData(response, form);
	}
	
	@Login
	@RequestMapping(params = "exportLocationDataToExcel")
	public void exportLocationDataToExcel(HttpServletResponse response, BasLocationExportForm form) throws Exception {
		basLocationExportService.exportLocationDataToExcel(response, form);
	}

	@Login
	@RequestMapping(params = "importData")
	@ResponseBody
	public Json importData( MultipartHttpServletRequest mhsr) throws Exception {
		return basLocationService.importData(mhsr);
	}
	
	@Login
	@RequestMapping(params = "importExcelData")
	@ResponseBody
	public Json importExcelData( MultipartHttpServletRequest mhsr) throws Exception {
		return basLocationService.importExcelData(mhsr);
	}
	
	@Login
	@RequestMapping(params = "exportTemplate", method = RequestMethod.POST)
	public void exportTemplate(HttpServletResponse response, String token) throws Exception {
		basLocationService.exportTemplate(response, token);
	}
	
	
	@Login
	@RequestMapping(params = "exportPdf")
	public void exportPdf(HttpServletResponse response, String locationid) throws Exception {
		try {
			basLocationService.exportPdf(response, locationid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Login
	@RequestMapping(params = "getUsgTypeCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getUsgTypeCombobox() {
		return basLocationService.getUsgTypeCombobox();
	}
	
	@Login
	@RequestMapping(params = "getCatTypeCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCatTypeCombobox() {
		return basLocationService.getCatTypeCombobox();
	}
	
	@Login
	@RequestMapping(params = "getAttTypeCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getAttTypeCombobox() {
		return basLocationService.getAttTypeCombobox();
	}
	
	
	@Login
	@RequestMapping(params = "getHdlTypeCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getHdlTypeCombobox() {
		return basLocationService.getHdlTypeCombobox();
	}
	
	@Login
	@RequestMapping(params = "getDmdTypeCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getDmdTypeCombobox() {
		return basLocationService.getDmdTypeCombobox();
	}
	
	@Login
	@RequestMapping(params = "getPtzoneTypeCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getPtzoneTypeCombobox() {
		return basLocationService.getPtzoneTypeCombobox();
	}
	
	@Login
	@RequestMapping(params = "getPizoneTypeCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getPizoneTypeCombobox() {
		return basLocationService.getPizoneTypeCombobox();
	}

}