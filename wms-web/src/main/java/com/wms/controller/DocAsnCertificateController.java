package com.wms.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.wms.utils.SfcUserLoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.DocAsnCertificateService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.DocAsnCertificateVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocAsnCertificateForm;
import com.wms.query.DocAsnCertificateQuery;

@Controller
@RequestMapping("docAsnCertificateController")
public class DocAsnCertificateController {

	@Autowired
	private DocAsnCertificateService docAsnCertificateService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("docAsnCertificate/main", model);
	}
	@Login
	@RequestMapping(params = "toInfo")
	public ModelAndView toInfo(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

		model.put("menuId", menuId);
		model.put("editwho", SfcUserLoginUtil.getLoginUser().getId());
		model.put("edittime",df.format(new Date()));

		return new ModelAndView("docAsnCertificate/info", model);
	}
	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<DocAsnCertificateVO> showDatagrid(EasyuiDatagridPager pager, DocAsnCertificateQuery query) {
		return docAsnCertificateService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(@RequestParam(value="docAsnCertificateForm",required=true) String docAsnCertificateFormStr) throws Exception {


		DocAsnCertificateForm docAsnCertificateForm = JSON.parseObject(docAsnCertificateFormStr,DocAsnCertificateForm.class);

		Json json = docAsnCertificateService.addDocAsnCertificate(docAsnCertificateForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(@RequestParam(value="docAsnCertificateForm",required=true) String docAsnCertificateFormStr) throws Exception {
		DocAsnCertificateForm docAsnCertificateForm = JSON.parseObject(docAsnCertificateFormStr,DocAsnCertificateForm.class);

		Json json = docAsnCertificateService.editDocAsnCertificate(docAsnCertificateForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(DocAsnCertificateQuery query) {
		Json json = docAsnCertificateService.deleteDocAsnCertificate(query);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "getInfo")
	@ResponseBody
	public Object getInfo(DocAsnCertificateQuery query) {
		return docAsnCertificateService.getDocAsnCertificateInfo(query);
	}
	@Login
	@RequestMapping(params = "exportTemplate", method = RequestMethod.POST)
	public void exportTemplate(HttpServletResponse response, String token) throws Exception {
		docAsnCertificateService.exportTemplate(response, token);
	}

	@Login
	@RequestMapping(params = "importExcelData")
	@ResponseBody
	public Json importExcelData(MultipartHttpServletRequest mhsr) throws Exception {
		return docAsnCertificateService.importExcelData(mhsr);
	}
	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return docAsnCertificateService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return docAsnCertificateService.getDocAsnCertificateCombobox();
	}

}