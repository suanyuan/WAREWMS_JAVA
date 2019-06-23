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
import com.wms.service.AsnDailyExportService;
import com.wms.service.RptSoDailyService;
import com.wms.service.SoDailyExportService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.RptSoDailyVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.AsnDailyExportForm;
import com.wms.vo.form.RptSoDailyForm;
import com.wms.vo.form.SoDailyExportForm;
import com.wms.query.RptSoDailyQuery;

@Controller
@RequestMapping("rptSoDailyController")
public class RptSoDailyController {

	@Autowired
	private RptSoDailyService rptSoDailyService;
	
	@Autowired
	private SoDailyExportService soDailyExportService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("rptSoDaily/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<RptSoDailyVO> showDatagrid(EasyuiDatagridPager pager, RptSoDailyQuery query) {
		return rptSoDailyService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(RptSoDailyForm rptSoDailyForm) throws Exception {
		Json json = rptSoDailyService.addRptSoDaily(rptSoDailyForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(RptSoDailyForm rptSoDailyForm) throws Exception {
		Json json = rptSoDailyService.editRptSoDaily(rptSoDailyForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String id) {
		Json json = rptSoDailyService.deleteRptSoDaily(id);
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
		return rptSoDailyService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return rptSoDailyService.getRptSoDailyCombobox();
	}

	@Login
	@RequestMapping(params = "exportSoDataToExcel")
	public void exportSodailyDataToExcel(HttpServletResponse response, SoDailyExportForm form) throws Exception {
		soDailyExportService.exportSoDataToExcel(response, form);
	}
}