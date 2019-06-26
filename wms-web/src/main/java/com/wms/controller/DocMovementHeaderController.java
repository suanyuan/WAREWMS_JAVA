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
import com.wms.service.DocMovementHeaderService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.DocMovementHeaderVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocMovementHeaderForm;
import com.wms.query.DocMovementHeaderQuery;

@Controller
@RequestMapping("docMovementHeaderController")
public class DocMovementHeaderController {

	@Autowired
	private DocMovementHeaderService docMovementHeaderService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("docMovementHeader/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<DocMovementHeaderVO> showDatagrid(EasyuiDatagridPager pager, DocMovementHeaderQuery query) {
		return docMovementHeaderService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(DocMovementHeaderForm docMovementHeaderForm) throws Exception {
		Json json = docMovementHeaderService.addDocMovementHeader(docMovementHeaderForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(DocMovementHeaderForm docMovementHeaderForm) throws Exception {
		Json json = docMovementHeaderService.editDocMovementHeader(docMovementHeaderForm);
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
		Json json = docMovementHeaderService.deleteDocMovementHeader(id);
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
		return docMovementHeaderService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

//	@Login
//	@RequestMapping(params = "getCombobox")
//	@ResponseBody
//	public List<EasyuiCombobox> getCombobox() {
//		return docMovementHeaderService.getDocMovementHeaderCombobox();
//	}

}