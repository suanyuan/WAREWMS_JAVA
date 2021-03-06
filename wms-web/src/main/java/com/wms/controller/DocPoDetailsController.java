package com.wms.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.DocPoDetailsService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.DocPoDetailsVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocPoDetailsForm;
import com.wms.query.DocPoDetailsQuery;

@Controller
@RequestMapping("docPoDetailsController")
public class DocPoDetailsController {

	@Autowired
	private DocPoDetailsService docPoDetailsService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("docPoDetails/main", model);
	}
//根据主表pono显示datagrid
	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<DocPoDetailsVO> showDatagrid(EasyuiDatagridPager pager, DocPoDetailsQuery query) {
		return docPoDetailsService.getPagedDatagrid(pager, query);
	}
//之单明细增加
	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(DocPoDetailsForm docPoDetailsForm) throws Exception {
		Json json = docPoDetailsService.addDocPoDetails(docPoDetailsForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}
//之单明细编辑
	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(DocPoDetailsForm docPoDetailsForm) throws Exception {
		Json json = docPoDetailsService.editDocPoDetails(docPoDetailsForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}
//之单明细删除
	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(DocPoDetailsForm docPoDetailsForm) {
		Json json = docPoDetailsService.deleteDocPoDetails(docPoDetailsForm);
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
		return docPoDetailsService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return docPoDetailsService.getDocPoDetailsCombobox();
	}

}