package com.wms.controller;

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.DocMtDetailsQuery;
import com.wms.service.DocMtDetailsService;
import com.wms.service.DocMtSearchService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.DocMtDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocMtDetailsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("docMtSearchController")
public class DocMtSearchController {

	@Autowired
	private DocMtSearchService docMtSearchService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("docMtSearch/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<DocMtDetailsVO> showDatagrid(EasyuiDatagridPager pager, DocMtDetailsQuery query) {
		return docMtSearchService.getPagedDatagrid(pager, query);
	}
//	/**
//	 * 批量养护 传json集合
//	 * @param forms
//	 * @return
//	 * @throws Exception
//	 */
//	@Login
//	@RequestMapping(params = "submitDocMtList")
//	@ResponseBody
//	public Json submitDocMtList(String forms) throws Exception {
//		return docMtDetailsService.submitDocMtList(forms);
//	}
//	@Login
//	@RequestMapping(params = "add")
//	@ResponseBody
//	public Json add(DocMtDetailsForm docMtDetailsForm) throws Exception {
//		Json json = docMtDetailsService.addDocMtDetails(docMtDetailsForm);
//		if(json == null){
//			json = new Json();
//		}
//			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
//		return json;
//	}
//
//	@Login
//	@RequestMapping(params = "edit")
//	@ResponseBody
//	public Json edit(DocMtDetailsForm docMtDetailsForm) throws Exception {
//		Json json = docMtDetailsService.editDocMtDetails(docMtDetailsForm);
//		if(json == null){
//			json = new Json();
//		}
//			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
//		return json;
//	}
//
//	@Login
//	@RequestMapping(params = "delete")
//	@ResponseBody
//	public Json delete(String id) {
//		Json json = docMtDetailsService.deleteDocMtDetails(id);
//		if(json == null){
//			json = new Json();
//		}
//			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
//		return json;
//	}
//
//	@Login
//	@RequestMapping(params = "getBtn")
//	@ResponseBody
//	public Json getBtn(String id, HttpSession session) {
//		return docMtDetailsService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
//	}
//
//	@Login
//	@RequestMapping(params = "getCombobox")
//	@ResponseBody
//	public List<EasyuiCombobox> getCombobox() {
//		return docMtDetailsService.getDocMtDetailsCombobox();
//	}

}