package com.wms.controller;

import com.alibaba.fastjson.JSON;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasCodes;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.BasCodesQuery;
import com.wms.query.BasGtnQuery;
import com.wms.query.DocAsnCertificateQuery;
import com.wms.service.BasCodesService;
import com.wms.service.BasGtnService;
import com.wms.service.DocAsnCertificateService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.BasGtnVO;
import com.wms.vo.DocAsnCertificateVO;
import com.wms.vo.Json;
import com.wms.vo.form.BasCodesForm;
import com.wms.vo.form.DocAsnCertificateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("historyFileController")
public class HistoryFileController {

	@Autowired
	private DocAsnCertificateService docAsnCertificateService;

	@Autowired
	private BasCodesService basCodesService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("historyFile/main", model);
	}



	@Login
	@RequestMapping(params = "toInfo")
	public ModelAndView toInfo(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		System.out.println();
		model.put("menuId", menuId);
		model.put("editwho", SfcUserLoginUtil.getLoginUser().getId());
		model.put("edittime",df.format(new Date()));

		return new ModelAndView("historyFile/info", model);
	}




	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<BasCodes> showDatagrid(EasyuiDatagridPager pager, BasCodesQuery query) {
		return basCodesService.showHistoryFileDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "getInfo")
	@ResponseBody
	public Object getInfo(BasCodesQuery query) {
		return basCodesService.getHistoryFileInfo(query);
	}




	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(@RequestParam(value="basCodesForm",required=true) String basCodesFormStr) throws Exception {
		BasCodesForm basCodesForm = JSON.parseObject(basCodesFormStr,BasCodesForm.class);

		Json json = basCodesService.editDocAsnCertificate(basCodesForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}



//	@Login
//	@RequestMapping(params = "exportTemplate", method = RequestMethod.POST)
//	public void exportTemplate(HttpServletResponse response, String token) throws Exception {
//		docAsnCertificateService.exportTemplate(response, token);
//	}
//
//	@Login
//	@RequestMapping(params = "importExcelData")
//	@ResponseBody
//	public Json importExcelData(MultipartHttpServletRequest mhsr) throws Exception {
//		return docAsnCertificateService.importExcelData(mhsr);
//	}
//	@Login
//	@RequestMapping(params = "getBtn")
//	@ResponseBody
//	public Json getBtn(String id, HttpSession session) {
//		return docAsnCertificateService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
//	}

}