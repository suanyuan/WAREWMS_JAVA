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
import com.wms.service.OrderHisExportService;
import com.wms.service.RptOrderdetailHisService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.RptOrderdetailHisVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.InOutExportForm;
import com.wms.vo.form.OrderHisExportForm;
import com.wms.vo.form.RptOrderdetailHisForm;
import com.wms.query.RptOrderdetailHisQuery;

@Controller
@RequestMapping("rptOrderdetailHisController")
public class RptOrderdetailHisController {

	@Autowired
	private RptOrderdetailHisService rptOrderdetailHisService;
	
	@Autowired
	private OrderHisExportService orderHisExportService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("rptOrderdetailHis/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<RptOrderdetailHisVO> showDatagrid(EasyuiDatagridPager pager, RptOrderdetailHisQuery query) {
		return rptOrderdetailHisService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(RptOrderdetailHisForm rptOrderdetailHisForm) throws Exception {
		Json json = rptOrderdetailHisService.addRptOrderdetailHis(rptOrderdetailHisForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(RptOrderdetailHisForm rptOrderdetailHisForm) throws Exception {
		Json json = rptOrderdetailHisService.editRptOrderdetailHis(rptOrderdetailHisForm);
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
		Json json = rptOrderdetailHisService.deleteRptOrderdetailHis(id);
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
		return rptOrderdetailHisService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return rptOrderdetailHisService.getRptOrderdetailHisCombobox();
	}
	
	@Login
	@RequestMapping(params = "exportOrderHisDataToExcel")
	public void exportOrderHisDataToExcel(HttpServletResponse response, OrderHisExportForm form) throws Exception {
		orderHisExportService.exportOrderHisDataToExcel(response, form);
	}

}