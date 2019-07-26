package com.wms.controller;

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.DocAsnHeader;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.DocAsnHeaderQuery;
import com.wms.service.DocAsnHeaderService;
import com.wms.service.DocPaService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.utils.editor.CustomDateEditor;
import com.wms.vo.DocAsnHeaderVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocAsnHeaderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("docAsnHeaderController")
public class DocAsnHeaderController {

	@Autowired
	private DocAsnHeaderService docAsnHeaderService;
	@Autowired
	private DocPaService docPaService;

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		binder.registerCustomEditor(Date.class,"addtime",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"edisendtime5",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"expectedarrivetime1",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"expectedarrivetime2",new CustomDateEditor(dateFormat, true));
	}

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("docAsnHeader/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<DocAsnHeaderVO> showDatagrid(EasyuiDatagridPager pager, DocAsnHeaderQuery query) {
		return docAsnHeaderService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "showAsnHeader")
	@ResponseBody
	public DocAsnHeader showAsnHeader(DocAsnHeaderQuery query) {
		return docAsnHeaderService.getAsnHeader(query);
	}
	
	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(DocAsnHeaderForm docAsnHeaderForm) throws Exception {
		Json json = docAsnHeaderService.addDocAsnHeader(docAsnHeaderForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(DocAsnHeaderForm docAsnHeaderForm) throws Exception {
		Json json = docAsnHeaderService.editDocAsnHeader(docAsnHeaderForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "cancel")
	@ResponseBody
	public Json cancel(String id) {
		Json json = docAsnHeaderService.cancelDocAsnHeader(id);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}
	
	@Login
	@RequestMapping(params = "close")
	@ResponseBody
	public Json close(String id) {
		Json json = docAsnHeaderService.closeDocAsnHeader(id);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "exportPdf")
	public void exportPdf(HttpServletResponse response, String orderList) throws Exception {
		try {
//			docAsnHeaderService.exportPdf(response, orderList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Login
	@RequestMapping(params = "importExcelData")
	@ResponseBody
	public Json importExcelData( MultipartHttpServletRequest mhsr) throws Exception {
		return docAsnHeaderService.importExcelData(mhsr);
	}
	
	@Login
	@RequestMapping(params = "exportTemplate", method = RequestMethod.POST)
	public void exportTemplate(HttpServletResponse response, String token) throws Exception {
		docAsnHeaderService.exportTemplate(response, token);
	}
	
	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return docAsnHeaderService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getAsnTypeCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getAsnTypeCombobox() {
		return docAsnHeaderService.getAsnTypeCombobox();
	}

	@Login
	@RequestMapping(params = "getAsnstatusCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getAsnstatusCombobox() {
		return docAsnHeaderService.getAsnstatusCombobox();
	}

	@Login
	@RequestMapping(params = "getReleasestatusCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getReleasestatusCombobox() {
		return docAsnHeaderService.getReleasestatusCombobox();
	}

	@Login
	@RequestMapping(params = "addDocPa",method = RequestMethod.POST)
	@ResponseBody
	public Json addDocPa(String asnNos){
		return docPaService.mergeDocPa(asnNos);
	}

	@Login
	@RequestMapping(params = "confirmReveiving",method = RequestMethod.POST)
	@ResponseBody
	public Json confirmReveiving(String asnNos){
		return docPaService.confirmReceiving(asnNos);
	}

}