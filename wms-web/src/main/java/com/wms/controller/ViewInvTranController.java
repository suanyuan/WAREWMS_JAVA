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
import com.wms.service.ViewInvTranExportService;
import com.wms.service.ViewInvTranService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.ViewInvTranVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.ViewInvLotattForm;
import com.wms.vo.form.ViewInvTranExportForm;
import com.wms.vo.form.ViewInvTranForm;
import com.wms.query.ViewInvTranQuery;

@Controller
@RequestMapping("viewInvTranController")
public class ViewInvTranController {

	@Autowired
	private ViewInvTranService viewInvTranService;
	
	@Autowired
	private ViewInvTranExportService viewInvTranExportService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("viewInvTran/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<ViewInvTranVO> showDatagrid(EasyuiDatagridPager pager, ViewInvTranQuery query) {
		return viewInvTranService.getPagedDatagrid(pager, query);
	}
	
	@Login
	@RequestMapping(params = "exportViewInvTranDataToExcel")
	public void exportViewInvLotattDataToExcel(HttpServletResponse response, ViewInvTranExportForm form) throws Exception {
		viewInvTranExportService.exportViewInvTranDataToExcel(response, form);
	}
	
	@Login
	@RequestMapping(params = "cancelReceive")
	@ResponseBody
	public Json cancelReceive(ViewInvTranForm viewInvTranForm) throws Exception {
		Json json = viewInvTranService.cancelReceive(viewInvTranForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}
	
	@Login
	@RequestMapping(params = "cancelShipment")
	@ResponseBody
	public Json cancelShipment(ViewInvTranForm viewInvTranForm) throws Exception {
		Json json = viewInvTranService.cancelShipment(viewInvTranForm);
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
		return viewInvTranService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getTransactionTypeCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getTransactionTypeCombobox() {
		return viewInvTranService.getTransactionTypeCombobox();
	}
	
	@Login
	@RequestMapping(params = "getDocTypeCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getDocTypeCombobox() {
		return viewInvTranService.getDocTypeCombobox();
	}
	
	@Login
	@RequestMapping(params = "getStatusCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getStatusCombobox() {
		return viewInvTranService.getStatusCombobox();
	}

}