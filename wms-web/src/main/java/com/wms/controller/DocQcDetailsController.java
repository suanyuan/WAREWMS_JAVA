package com.wms.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.mybatis.entity.pda.PdaGspProductRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.DocQcDetailsService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.DocQcDetailsVO;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocQcDetailsForm;
import com.wms.query.DocQcDetailsQuery;

@Controller
@RequestMapping("docQcDetailsController")
public class DocQcDetailsController {

	@Autowired
	private DocQcDetailsService docQcDetailsService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("docQcDetails/main", model);
	}
	/**
	 * 显示细单 分页
	 * @param pager
	 * @param query
	 * @return
	 */
	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<DocQcDetailsVO> showDatagrid(EasyuiDatagridPager pager, DocQcDetailsQuery query) {
		return docQcDetailsService.getPagedDatagrid(pager, query);
	}

	/**
	 * 批量验收 传json集合
	 * @param forms
	 * @return
	 * @throws Exception
	 */
	@Login
	@RequestMapping(params = "submitDocQcList")
	@ResponseBody
	public Json submitDocQcList(String forms) throws Exception {
		return docQcDetailsService.submitDocQcList(forms);
	}
@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(DocQcDetailsForm docQcDetailsForm) throws Exception {
		Json json = docQcDetailsService.addDocQcDetails(docQcDetailsForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(DocQcDetailsForm docQcDetailsForm) throws Exception {
		Json json = docQcDetailsService.editDocQcDetails(docQcDetailsForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}
	/**
	 * 根据lotatt06查询所有注册证号
	 * @return
	 * */
	@RequestMapping(params = "getRgisterListBylotatt06")
	@ResponseBody
	public List<PdaGspProductRegister> getRgisterListBylotatt06(String lotatt06){
		return docQcDetailsService.getRgisterListBylotatt06(lotatt06);
	}

//	@Login
//	@RequestMapping(params = "delete")
//	@ResponseBody
//	public Json delete(String id) {
//		Json json = docQcDetailsService.deleteDocQcDetails(id);
//		if(json == null){
//			json = new Json();
//		}
//			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
//		return json;
//	}
	/*@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String id) {
		Json json = docQcDetailsService.deleteDocQcDetails(id);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}*/

	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return docQcDetailsService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

//	@Login
//	@RequestMapping(params = "getCombobox")
//	@ResponseBody
//	public List<EasyuiCombobox> getCombobox() {
//		return docQcDetailsService.getDocQcDetailsCombobox();
//	}

}