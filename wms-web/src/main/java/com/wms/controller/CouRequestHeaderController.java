package com.wms.controller;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.InvLotLocId;
import com.wms.query.CouRequestDetailsQuery;
import com.wms.query.CouRequestHeaderQuery;
import com.wms.service.CouRequestExportService;
import com.wms.service.CouRequestHeaderService;
import com.wms.utils.annotation.Login;
import com.wms.vo.CouRequestHeaderVO;
import com.wms.vo.Json;
import com.wms.vo.form.CouRequestExportForm;
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
@RequestMapping("couRequestHeaderController")
public class CouRequestHeaderController {

	@Autowired
	private CouRequestHeaderService couRequestHeaderService;
	@Autowired
	private CouRequestExportService couRequestExportService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("couRequestHeader/main", model);
	}
//分页查询
	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<CouRequestHeaderVO> showDatagrid(EasyuiDatagridPager pager, CouRequestHeaderQuery query) {
		return couRequestHeaderService.getPagedDatagrid(pager, query);
	}
//获得盘点任务
	@Login
	@RequestMapping(params = "getcouRequestInfo")
	@ResponseBody
	public List<InvLotLocId> getInvCheckInfo(CouRequestDetailsQuery query) {
		return couRequestHeaderService.getcouRequestInfo(query);
	}
//生成盘点信息
	@Login
	@RequestMapping(params = "ToGenerateInventoryPlan")
	@ResponseBody
	public Json ToGenerationInfo(String forms) {
		return couRequestHeaderService.ToGenerateInventoryPlan(forms);
	}
//导出
	@Login
	@RequestMapping(params = "exportCouRequestDataToExcel")
public void exportViewInvLotattDataToExcel(HttpServletResponse response, CouRequestExportForm form) throws Exception {
		couRequestExportService.exportCouRequestDataToExcel(response, form);
	}
////关闭计划单
//	@Login
//	@RequestMapping(params = "closegenerationPlan")
//	@ResponseBody
//	public Json closegenerationPlan(DocMtHeaderForm form) {
//		return couRequestHeaderService.endDocMtJson(form);
//	}
////增加
//	@Login
//	@RequestMapping(params = "add")
//	@ResponseBody
//	public Json add(DocMtHeaderForm docMtHeaderForm) throws Exception {
//		Json json = couRequestHeaderService.addDocMtHeader(docMtHeaderForm);
//		if(json == null){
//			json = new Json();
//		}
//			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
//		return json;
//	}
////编辑
//	@Login
//	@RequestMapping(params = "edit")
//	@ResponseBody
//	public Json edit(DocMtHeaderForm docMtHeaderForm) throws Exception {
//		Json json = couRequestHeaderService.editDocMtHeader(docMtHeaderForm);
//		if(json == null){
//			json = new Json();
//		}
//			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
//		return json;
//	}
//删除
	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String cycleCountno) {
		Json json = couRequestHeaderService.deleteCouRequstHeader(cycleCountno);
		return json;
	}
//
//	@Login
//	@RequestMapping(params = "getBtn")
//	@ResponseBody
//	public Json getBtn(String id, HttpSession session) {
//		return couRequestHeaderService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
//	}
//
//	@Login
//	@RequestMapping(params = "getCombobox")
//	@ResponseBody
//	public List<EasyuiCombobox> getCombobox() {
//		return couRequestHeaderService.getDocMtHeaderCombobox();
//	}

}