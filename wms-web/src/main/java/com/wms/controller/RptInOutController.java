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
import com.wms.service.InOutExportService;
import com.wms.service.RptInOutService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.RptInOutVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.AsnDailyExportForm;
import com.wms.vo.form.InOutExportForm;
import com.wms.vo.form.RptInOutForm;
import com.wms.query.RptInOutQuery;

@Controller
@RequestMapping("rptInOutController")
public class RptInOutController {
	
	@Autowired
	private RptInOutService rptInOutService;

	@Autowired
	private InOutExportService inOutExportService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("rptInOut/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<RptInOutVO> showDatagrid(EasyuiDatagridPager pager, RptInOutQuery query) {
		return rptInOutService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(RptInOutForm rptInOutForm) throws Exception {
		Json json = rptInOutService.addRptInOut(rptInOutForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(RptInOutForm rptInOutForm) throws Exception {
		Json json = rptInOutService.editRptInOut(rptInOutForm);
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
		Json json = rptInOutService.deleteRptInOut(id);
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
		return rptInOutService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return rptInOutService.getRptInOutCombobox();
	}
	
	@Login
	@RequestMapping(params = "exportInOutDataToExcel")
	public void exportInOutDataToExcel(HttpServletResponse response, InOutExportForm form) throws Exception {
		inOutExportService.exportInOutDataToExcel(response, form);
	}

}