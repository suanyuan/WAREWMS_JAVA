package com.wms.controller;

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.UserStatisticsPerformance;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.UserStatisticsPerformanceQuery;
import com.wms.service.UserStatisticsPerformanceService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.UserStatisticsPerformanceVO;
import com.wms.vo.form.UserStatisticsPerformanceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("userStatisticsPerformanceController")
public class UserStatisticsPerformanceController {

	@Autowired
	private UserStatisticsPerformanceService userStatisticsPerformanceService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("userStatisticsPerformance/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<UserStatisticsPerformanceVO> showDatagrid(EasyuiDatagridPager pager, UserStatisticsPerformanceQuery query) throws ParseException {
		return userStatisticsPerformanceService.getPagedDatagrid(pager, query);
	}
	/**
	 * 导出上架任务明细
	 */
	@Login
	@RequestMapping(params = "exportDocPaDataToExcel")
	public void exportDocPaDataToExcel(HttpServletResponse response, String token, UserStatisticsPerformanceQuery usp) throws Exception {
		userStatisticsPerformanceService.exportDocPaDataToExcel(response, token, usp);
	}
	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(UserStatisticsPerformanceForm userStatisticsPerformanceForm) throws Exception {
		Json json = userStatisticsPerformanceService.addUserStatisticsPerformance(userStatisticsPerformanceForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(UserStatisticsPerformanceForm userStatisticsPerformanceForm) throws Exception {
		Json json = userStatisticsPerformanceService.editUserStatisticsPerformance(userStatisticsPerformanceForm);
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
		Json json = userStatisticsPerformanceService.deleteUserStatisticsPerformance(id);
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
		return userStatisticsPerformanceService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return userStatisticsPerformanceService.getUserStatisticsPerformanceCombobox();
	}

	/**
	 * 用于统计效绩  接口被调用
	 * @return
	 */
	@Login
	@RequestMapping(params = "performanceStatistics")
	@ResponseBody
	public  String performanceStatistics() {
		userStatisticsPerformanceService.performanceStatistics();
		return "OK";
	}
}