package com.wms.controller;

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.BasCodesQuery;
import com.wms.service.BasCodesExportService;
import com.wms.service.BasCodesService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.BasCodesVO;
import com.wms.vo.Json;
import com.wms.vo.form.BasCodesForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("basCodesController")
public class BasCodesController {

	@Autowired
	private BasCodesService basCodesService;
    @Autowired
    private BasCodesExportService basCodesExportService;


	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("basCodes/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<BasCodesVO> showDatagrid(EasyuiDatagridPager pager, BasCodesQuery query) {
		return basCodesService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(BasCodesForm basCodesForm) throws Exception {
		Json json = basCodesService.addBasCodes(basCodesForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(BasCodesForm basCodesForm) throws Exception {
/*        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
	    basCodesForm.setEdittime(date);*/
		Json json = basCodesService.editBasCodes(basCodesForm);
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
		Json json = basCodesService.deleteBasCodes(id);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}
	@Login
	@RequestMapping(params = "getTransactionTypeCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getTransactionTypeCombobox() {
		return basCodesService.getTransactionTypeCombobox();
	}
    @Login
    @RequestMapping(params = "exportBasCodesDataToExcel")
    public void exportBasCodesDataToExcel(HttpServletResponse response, BasCodesForm form) throws Exception {
        basCodesExportService.exportViewInvTranDataToExcel(response, form);
    }

}