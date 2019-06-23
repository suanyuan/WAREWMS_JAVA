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
import com.wms.service.BasSkuExportService;
import com.wms.service.RptAsnDailyService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.RptAsnDailyVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.AsnDailyExportForm;
import com.wms.vo.form.InOutExportForm;
import com.wms.vo.form.RptAsnDailyForm;
import com.wms.query.RptAsnDailyQuery;

@Controller
@RequestMapping("rptAsnDailyController")
public class RptAsnDailyController {

	@Autowired
	private RptAsnDailyService rptAsnDailyService;
	
	@Autowired
	private AsnDailyExportService asnDailyExportService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("rptAsnDaily/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<RptAsnDailyVO> showDatagrid(EasyuiDatagridPager pager, RptAsnDailyQuery query) {
		return rptAsnDailyService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(RptAsnDailyForm rptAsnDailyForm) throws Exception {
		Json json = rptAsnDailyService.addRptAsnDaily(rptAsnDailyForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(RptAsnDailyForm rptAsnDailyForm) throws Exception {
		Json json = rptAsnDailyService.editRptAsnDaily(rptAsnDailyForm);
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
		Json json = rptAsnDailyService.deleteRptAsnDaily(id);
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
		return rptAsnDailyService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return rptAsnDailyService.getRptAsnDailyCombobox();
	}

	@Login
	@RequestMapping(params = "exportAsndailyDataToExcel")
	public void exportAsndailyDataToExcel(HttpServletResponse response, AsnDailyExportForm form) throws Exception {
		asnDailyExportService.exportAsnDataToExcel(response, form);
	}
	
}