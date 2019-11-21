package com.wms.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wms.query.BasCustomerQuery;
import com.wms.vo.BasCustomerVO;
import com.wms.vo.form.BasCustomerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.BasCustomerHistoryService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;


@Controller
@RequestMapping("basCustomerHistoryController")
public class BasCustomerHistoryController {

	@Autowired
	private BasCustomerHistoryService basCustomerHistoryService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("basCustomerHistory/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<BasCustomerVO> showDatagrid(EasyuiDatagridPager pager, BasCustomerQuery query) {
		return basCustomerHistoryService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(BasCustomerForm basCustomerHistoryForm) throws Exception {
		Json json = basCustomerHistoryService.addBasCustomerHistory(basCustomerHistoryForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(BasCustomerForm basCustomerHistoryForm) throws Exception {
		Json json = basCustomerHistoryService.editBasCustomerHistory(basCustomerHistoryForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String id) {
		Json json = basCustomerHistoryService.deleteBasCustomerHistory(id);
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
		return basCustomerHistoryService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return basCustomerHistoryService.getBasCustomerHistoryCombobox();
	}

	@Login
	@RequestMapping(params = "exportBasCustomerDataToExcel")
	public void exportBasCustomerDataToExcel(HttpServletResponse response, BasCustomerForm form) throws Exception {
		basCustomerHistoryService.exportBasCustomerDataToExcel(response, form);
	}
}