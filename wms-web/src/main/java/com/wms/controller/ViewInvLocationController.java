package com.wms.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.ViewInvLocationExportService;
import com.wms.service.ViewInvLocationService;
import com.wms.service.ViewInvLotattExportService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.ViewInvLocationVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.ViewInvLocationExportForm;
import com.wms.vo.form.ViewInvLocationForm;
import com.wms.vo.form.ViewInvLotattExportForm;
import com.wms.query.ViewInvLocationQuery;

@Controller
@RequestMapping("viewInvLocationController")
public class ViewInvLocationController {

	@Autowired
	private ViewInvLocationService viewInvLocationService;
	
	@Autowired
	private ViewInvLocationExportService viewInvLocationExportService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("viewInvLocation/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<ViewInvLocationVO> showDatagrid(EasyuiDatagridPager pager, ViewInvLocationQuery query) {
		return viewInvLocationService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return viewInvLocationService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "exportViewInvLocationDataToExcel")
	public void exportViewInvLotattDataToExcel(HttpServletResponse response, ViewInvLocationExportForm form) throws Exception {
		viewInvLocationExportService.exportViewInvLocationDataToExcel(response, form);
	}
	
}