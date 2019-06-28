package com.wms.controller;
import java.math.BigDecimal;
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

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasSku;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.BasSkuQuery;
import com.wms.service.BasSkuService;
import com.wms.service.BasSkuExportService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.utils.editor.CustomDateEditor;
import com.wms.vo.BasSkuVO;
import com.wms.vo.Json;
import com.wms.vo.form.BasSkuForm;
import com.wms.vo.form.BasSkuExportForm;

@Controller
@RequestMapping("basSkuController")
public class BasSkuController {

	@Autowired
	private BasSkuService basSkuService;
	
	@Autowired
	private BasSkuExportService basSkuExportService;

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
		return new ModelAndView("basSku/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<BasSkuVO> showDatagrid(EasyuiDatagridPager pager, BasSkuQuery query) {
		return basSkuService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(BasSkuForm basSkuForm) throws Exception {
		Json json = basSkuService.addBasSku(basSkuForm);
		if(json == null){
			json = new Json();

		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(BasSkuForm basSkuForm) throws Exception {
		Json json = basSkuService.editBasSku(basSkuForm);
		if(json == null){
			json = new Json();

		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String customerid, String sku) {
		Json json = basSkuService.deleteBasSku(customerid, sku);
		if(json == null){
			json = new Json();

		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "getSkuInfo")
	@ResponseBody
	public BasSku getSkuInfo(String customerid, String sku, BigDecimal qty) {
		return basSkuService.getSkuInfo(customerid, sku, qty);
	}

	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return basSkuService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return basSkuService.getBasSkuCombobox();
	}
	
	@Login
	@RequestMapping(params = "exportSkuData")
	public void exportSkuData(HttpServletResponse response, BasSkuExportForm form) throws Exception {
		basSkuExportService.exportSkuData(response, form);
	}
	
	@Login
	@RequestMapping(params = "exportSkuDataToExcel")
	public void exportSkuDataToExcel(HttpServletResponse response, BasSkuExportForm form) throws Exception {
		basSkuExportService.exportSkuDataToExcel(response, form);
	}

	@Login
	@RequestMapping(params = "importData")
	@ResponseBody
	public Json importData( MultipartHttpServletRequest mhsr) throws Exception {
		return basSkuService.importData(mhsr);
	}

	@Login
	@RequestMapping(params = "importExcelData")
	@ResponseBody
	public Json importExcelData( MultipartHttpServletRequest mhsr) throws Exception {
		return basSkuService.importExcelData(mhsr);
	}
	
	@Login
	@RequestMapping(params = "exportTemplate", method = RequestMethod.POST)
	public void exportTemplate(HttpServletResponse response, String token) throws Exception {
		basSkuService.exportTemplate(response, token);
	}
	
	@Login
	@RequestMapping(params = "exportPdf")
	public void exportPdf(HttpServletResponse response, String customerid, String sku) throws Exception {
		try {
			basSkuService.exportPdf(response, customerid, sku);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}