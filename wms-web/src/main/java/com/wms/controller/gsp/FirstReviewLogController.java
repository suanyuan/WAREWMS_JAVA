package com.wms.controller.gsp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wms.entity.FirstReviewLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.FirstReviewLogService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.FirstReviewLogVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.FirstReviewLogForm;
import com.wms.query.FirstReviewLogQuery;

@Controller
@RequestMapping("firstReviewLogController")
public class FirstReviewLogController {

	@Autowired
	private FirstReviewLogService firstReviewLogService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("firstReviewLog/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<FirstReviewLogVO> showDatagrid(EasyuiDatagridPager pager, FirstReviewLogQuery query) {
		return firstReviewLogService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(FirstReviewLogForm firstReviewLogForm) throws Exception {
		Json json = firstReviewLogService.addFirstReviewLog(firstReviewLogForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(FirstReviewLogForm firstReviewLogForm) throws Exception {
		Json json = firstReviewLogService.editFirstReviewLog(firstReviewLogForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "updateByReviewTypeId")
	@ResponseBody
	public Json updateByReviewTypeId(FirstReviewLogForm firstReviewLogForm) throws Exception {
		Json json = firstReviewLogService.updateByReviewTypeId(firstReviewLogForm);
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
		Json json = firstReviewLogService.deleteFirstReviewLog(id);
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
		return firstReviewLogService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "check")
	@ResponseBody
	public Json check(String id,String remark) {
		return firstReviewLogService.checkFirstReview(id,remark);
	}

	@Login
	@RequestMapping(params = "returnCheck")
	@ResponseBody
	public Json returnCheck(String id,String remark) {
		return firstReviewLogService.returnCheck(id,remark);
	}

	@Login
	@RequestMapping(params = "exportFirstReviewLogDataToExcel")
	public void exportFirstReviewLogDataToExcel(HttpServletResponse response, FirstReviewLogQuery form) throws Exception {
		firstReviewLogService.exportFirstReviewLogDataToExcel(response, form);
	}


}