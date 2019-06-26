package com.wms.controller;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.ActTransactionLogQuery;
import com.wms.service.ActTransactionLogService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.ActTransactionLogVO;
import com.wms.vo.Json;
import com.wms.vo.form.ActTransactionLogForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("actTransactionLogController")
public class ActTransactionLogController {

	@Autowired
	private ActTransactionLogService actTransactionLogService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("actTransactionLog/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<ActTransactionLogVO> showDatagrid(EasyuiDatagridPager pager, ActTransactionLogQuery query) {
		return actTransactionLogService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(ActTransactionLogForm actTransactionLogForm) throws Exception {
		Json json = actTransactionLogService.addActTransactionLog(actTransactionLogForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(ActTransactionLogForm actTransactionLogForm) throws Exception {
		Json json = actTransactionLogService.editActTransactionLog(actTransactionLogForm);
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
		Json json = actTransactionLogService.deleteActTransactionLog(id);
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
		return actTransactionLogService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

//	@Login
//	@RequestMapping(params = "getCombobox")
//	@ResponseBody
//	public List<EasyuiCombobox> getCombobox() {
//		return actTransactionLogService.getActTransactionLogCombobox();
//	}

}