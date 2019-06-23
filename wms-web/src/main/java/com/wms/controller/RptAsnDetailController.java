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
import com.wms.service.AsnDetailExportService;
import com.wms.service.RptAsnDetailService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.RptAsnDetailVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.AsnDetailExportForm;
import com.wms.vo.form.RptAsnDetailForm;
import com.wms.query.RptAsnDetailQuery;

@Controller
@RequestMapping("rptAsnDetailController")
public class RptAsnDetailController {

	@Autowired
	private RptAsnDetailService rptAsnDetailService;
	
	@Autowired
	private AsnDetailExportService asnDetailExportService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("rptAsnDetail/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<RptAsnDetailVO> showDatagrid(EasyuiDatagridPager pager, RptAsnDetailQuery query) {
		return rptAsnDetailService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(RptAsnDetailForm rptAsnDetailForm) throws Exception {
		Json json = rptAsnDetailService.addRptAsnDetail(rptAsnDetailForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(RptAsnDetailForm rptAsnDetailForm) throws Exception {
		Json json = rptAsnDetailService.editRptAsnDetail(rptAsnDetailForm);
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
		Json json = rptAsnDetailService.deleteRptAsnDetail(id);
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
		return rptAsnDetailService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return rptAsnDetailService.getRptAsnDetailCombobox();
	}
	
	@Login
	@RequestMapping(params = "exportAsnDetailDataToExcel")
	public void exportAsnDetailDataToExcel(HttpServletResponse response, AsnDetailExportForm form) throws Exception {
		asnDetailExportService.exportAsnDetailDataToExcel(response, form);
	}

}