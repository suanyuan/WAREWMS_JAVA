package com.wms.controller.gsp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.query.GspBusinessLicenseQuery;
import com.wms.service.GspEnterpriceService;
import com.wms.utils.editor.CustomDateEditor;
import com.wms.vo.GspBusinessLicenseVO;
import com.wms.vo.form.GspEnterpriceFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.GspEnterpriseInfoService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.GspEnterpriseInfoVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspEnterpriseInfoForm;
import com.wms.query.GspEnterpriseInfoQuery;

@Controller
@RequestMapping("gspEnterpriseInfoController")
public class GspEnterpriseInfoController {

	@Autowired
	private GspEnterpriseInfoService gspEnterpriseInfoService;

	//企业信息主体业务
	@Autowired
	private GspEnterpriceService gspEnterpriceService;

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		binder.registerCustomEditor(Date.class,"addtime",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"edisendtime5",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"expectedarrivetime1",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"expectedarrivetime2",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"createDate",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"editDate",new CustomDateEditor(dateFormat, true));
	}


	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("gspEnterpriseInfo/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<GspEnterpriseInfoVO> showDatagrid(EasyuiDatagridPager pager, GspEnterpriseInfoQuery query) throws Exception{
		return gspEnterpriseInfoService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "addEnterprise")
	@ResponseBody
	public Json addEnterprise(GspEnterpriseInfo gspEnterpriseInfo) throws Exception {
		Json json = gspEnterpriseInfoService.addEnterprise(gspEnterpriseInfo);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}


	@Login
	@RequestMapping(params = "add",method = RequestMethod.POST)
	@ResponseBody
	public Json add(@RequestParam(value="enterpriseId",required=true) String enterpriceId,@RequestParam(value="gspEnterpriceFrom",required=true) String gspEnterpriceFromStr) throws Exception {
		GspEnterpriceFrom gspEnterpriceFrom = JSON.parseObject(gspEnterpriceFromStr,GspEnterpriceFrom.class);
		Json json = gspEnterpriceService.addGspEnterprice(gspEnterpriceFrom);
		if(json == null){
			json = new Json();

		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;

	}

	@Login
	@RequestMapping(params = "edit",method = RequestMethod.POST)
	@ResponseBody
	public Json edit(@RequestParam(value="enterpriseId",required=true) String enterpriceId,@RequestParam(value="gspEnterpriceFrom",required=true) String gspEnterpriceFromStr) throws Exception {
		GspEnterpriceFrom gspEnterpriceFrom = JSON.parseObject(gspEnterpriceFromStr,GspEnterpriceFrom.class);
		Json json = gspEnterpriceService.editGspEnterprice(enterpriceId,gspEnterpriceFrom);
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
		return gspEnterpriceService.deleteGspEnterprice(id);
		/*Json json = gspEnterpriseInfoService.deleteGspEnterpriseInfo(id);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;*/
	}

	@Login
	@RequestMapping(params = "deleteEnter")
	@ResponseBody
	public Json deleteEnter(String enterpriseId) {
		Json json = gspEnterpriceService.delete(enterpriseId);
		//Json json = gspEnterpriseInfoService.deleteGspEnterpriseInfo(id);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}

		 json.setMsg("解除成功！");
		return json;
	}


	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return gspEnterpriseInfoService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return gspEnterpriseInfoService.getGspEnterpriseInfoCombobox();
	}

	@Login
	@RequestMapping(params = "toDetail")
	public ModelAndView toDetail(@RequestParam(defaultValue = "") String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", id);
		return new ModelAndView("gspEnterpriseInfo/detail", model);
	}

	@Login
	@RequestMapping(params = "toInfo")
	public ModelAndView toInfo(@RequestParam(defaultValue = "") String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", id);
		Json json = gspEnterpriceService.getGspEnterpriceInfo(id);
		model.put("gspEnterpriseInfo",json.getObj());
		return new ModelAndView("gspEnterpriseInfo/info", model);
	}

	@Login
	@RequestMapping(params = "toBusinessLicense")
	public ModelAndView toBusinessLicense(@RequestParam(defaultValue = "") String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", id);
		Json json = gspEnterpriceService.getGspBusinessLicense(id);
		model.put("gspBusinessLicense",json.getObj());
		return new ModelAndView("gspEnterpriseInfo/businessLicense", model);
	}

	@Login
	@RequestMapping(params = "toOperateLicense")
	public ModelAndView toOperateLicense(@RequestParam(defaultValue = "") String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", id);
		Json json = gspEnterpriceService.getGspOperateLicense(id);
		model.put("gspOperateLicense",json.getObj());
		return new ModelAndView("gspEnterpriseInfo/operateLicense", model);
	}

	@Login
	@RequestMapping(params = "toSecondRecord")
	public ModelAndView toSecondRecord(@RequestParam(defaultValue = "") String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", id);
		Json json = gspEnterpriceService.getGspSecondRecord(id);
		model.put("gspSecondRecord",json.getObj());
		return new ModelAndView("gspEnterpriseInfo/secondRecord", model);
	}

	@Login
	@RequestMapping(params = "toSearchDialog")
	public ModelAndView toSearchDialog(@RequestParam(defaultValue = "") String target,@RequestParam(defaultValue = "") String type) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("target",target);
		model.put("type",type);
		return new ModelAndView("gspEnterpriseInfo/search", model);
	}

	@Login
	@RequestMapping(params = "getInfo")
	@ResponseBody
	public Object getInfo(String enterpriseId) {
		return gspEnterpriceService.getGspEnterpriceInfo(enterpriseId);
	}

	@Login
	@RequestMapping(params = "getBusness")
	@ResponseBody
	public Object getBusness(String enterpriseId) {
		return gspEnterpriceService.getGspBusinessLicense(enterpriseId);
	}

	@Login
	@RequestMapping(params = "getOperate")
	@ResponseBody
	public Object getOperate(String enterpriseId) {
		return gspEnterpriceService.getGspOperateLicense(enterpriseId);
	}

	@Login
	@RequestMapping(params = "getSecondRecord")
	@ResponseBody
	public Object getSecondRecord(String enterpriseId) {
		return gspEnterpriceService.getGspSecondRecord(enterpriseId);
	}

	@Login
	@RequestMapping(params = "addBusinessLicense")
	@ResponseBody
	public Object addBusinessLicense(@RequestParam(defaultValue = "")String enterpriseId,
									 @RequestParam(defaultValue = "") String businessFormStr,
									 @RequestParam(defaultValue = "") String operateDetailStr,
									 @RequestParam(defaultValue = "") String gspBusinessLicenseId,
									 @RequestParam(defaultValue = "") String opType){
		return gspEnterpriceService.addGspBusinessLicense(enterpriseId,businessFormStr,operateDetailStr,gspBusinessLicenseId,opType);
	}

	@Login
	@RequestMapping(params = "businessHistoryDatagridList")
	@ResponseBody
	public EasyuiDatagrid<GspBusinessLicenseVO> businessHistoryDatagridList(EasyuiDatagridPager pager, GspBusinessLicenseQuery query){
		return gspEnterpriceService.getGspBusinessLicenseHistory(pager,query);
	}
}