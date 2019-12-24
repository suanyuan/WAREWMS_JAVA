package com.wms.controller.gsp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.wms.service.GspOperateDetailService;
import com.wms.vo.GspOperateDetailVO;
import com.wms.vo.GspOperateLicenseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.GspInstrumentCatalogService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.GspInstrumentCatalogVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspInstrumentCatalogForm;
import com.wms.query.GspInstrumentCatalogQuery;

@Controller
@RequestMapping("gspInstrumentCatalogController")
public class GspInstrumentCatalogController {

	@Autowired
	private GspInstrumentCatalogService gspInstrumentCatalogService;
	@Autowired
	private GspOperateDetailService gspOperateDetailService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("gspInstrumentCatalog/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<GspInstrumentCatalogVO> showDatagrid(EasyuiDatagridPager pager, GspInstrumentCatalogQuery query) {
		return gspInstrumentCatalogService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(GspInstrumentCatalogForm gspInstrumentCatalogForm) throws Exception {
		Json json = gspInstrumentCatalogService.addGspInstrumentCatalog(gspInstrumentCatalogForm);
		if(json == null){
			json = new Json();
		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(GspInstrumentCatalogForm gspInstrumentCatalogForm) throws Exception {
		Json json = gspInstrumentCatalogService.editGspInstrumentCatalog(gspInstrumentCatalogForm);
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
		Json json = gspInstrumentCatalogService.deleteGspInstrumentCatalog(id);
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
		return gspInstrumentCatalogService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox(@RequestParam(value="version",required = false,defaultValue = "") String version) {
		return gspInstrumentCatalogService.getGspInstrumentCatalogCombobox(version);
	}

	@Login
	@RequestMapping(params = "showCatalogEnterpriseDatagrid")
	@ResponseBody
	public EasyuiDatagrid<GspInstrumentCatalogVO> queryGspInstrumentCatalogEnterprise(EasyuiDatagridPager pager, GspInstrumentCatalogQuery query){
		return gspInstrumentCatalogService.getPagedDatagridEnterprise(pager,query);
	}

	@Login
	@RequestMapping(params = "toSearch")
	public ModelAndView toSearch(String target,String id,String version,String classifyId){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", id);
		model.put("target",target);
		model.put("version",version);
		model.put("classifyId",classifyId);

		return new ModelAndView("gspInstrumentCatalog/search",model);
	}

	@Login
	@RequestMapping(params = "searchCheckByLicenseId")
	@ResponseBody
	public Object searchCheckByLicenseId(String licenseId){
		if(StringUtils.isEmpty(licenseId)){
			return new ArrayList<GspOperateDetailVO>();
		}
		return gspOperateDetailService.queryOperateDetailByLicense(licenseId);
	}
}