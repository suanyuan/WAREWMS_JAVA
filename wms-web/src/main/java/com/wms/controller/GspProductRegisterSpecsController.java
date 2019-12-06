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
import com.wms.service.GspProductRegisterSpecsService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.GspProductRegisterSpecsVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspProductRegisterSpecsForm;
import com.wms.query.GspProductRegisterSpecsQuery;

@Controller
@RequestMapping("gspProductRegisterSpecsController")
public class GspProductRegisterSpecsController {

	@Autowired
	private GspProductRegisterSpecsService gspProductRegisterSpecsService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("gspProductRegisterSpecs/main", model);
	}

	@Login
	@RequestMapping(params = "toAdd")
	public ModelAndView toAdd(@RequestParam(defaultValue = "") String specsId,
							  @RequestParam(defaultValue = "") String type) {
		Map<String, Object> model = new HashMap<String, Object>();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		System.out.println(df.format(new Date()));
		model.put("createId", SfcUserLoginUtil.getLoginUser().getId());
		model.put("createDate",df.format(new Date()));
		model.put("specsId", specsId);
		model.put("type", type);
		model.put("isUse", 1);
		return new ModelAndView("gspProductRegisterSpecs/add", model);
	}
	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<GspProductRegisterSpecsVO> showDatagrid(EasyuiDatagridPager pager, GspProductRegisterSpecsQuery query) {
		return gspProductRegisterSpecsService.getPagedDatagrid(pager, query);
	}



	@Login
	@RequestMapping(params = "showProductSUPDatagrid")
	@ResponseBody
	public EasyuiDatagrid<GspProductRegisterSpecsVO> showProductSUPDatagrid(EasyuiDatagridPager pager, GspProductRegisterSpecsQuery query) {
		return gspProductRegisterSpecsService.getPagedProductSUPDatagrid(pager, query);
	}
	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(@RequestParam(value="gspProductRegisterSpecsForm",required=true) String gspProductRegisterSpecsFormStr) throws Exception {

		GspProductRegisterSpecsForm gspProductRegisterSpecsForm = JSON.parseObject(gspProductRegisterSpecsFormStr,GspProductRegisterSpecsForm.class);
		Json json = gspProductRegisterSpecsService.addGspProductRegisterSpecs(gspProductRegisterSpecsForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));


		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(@RequestParam(value="gspProductRegisterSpecsForm",required=true) String gspProductRegisterSpecsFormStr) throws Exception {
		System.out.println();
		GspProductRegisterSpecsForm gspProductRegisterSpecsForm = JSON.parseObject(gspProductRegisterSpecsFormStr,GspProductRegisterSpecsForm.class);
		Json json = gspProductRegisterSpecsService.editGspProductRegisterSpecs(gspProductRegisterSpecsForm,"");
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
		Json json = gspProductRegisterSpecsService.deleteGspProductRegisterSpecs(id);
		if(json == null){
			json = new Json();

		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "toSearchDialog")
	public ModelAndView toSearchDialog(@RequestParam(defaultValue = "") String target,
									   @RequestParam(defaultValue = "") String type,
									   @RequestParam(defaultValue = "") String enterpriseType) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("target",target);
		model.put("type",type);
		model.put("enterpriseType",enterpriseType);
		return new ModelAndView("gspProductRegisterSpecs/search", model);
	}
//主页grid点击编辑页面获取数据
	@Login
	@RequestMapping(params = "getInfo")
	@ResponseBody
	public Object getInfo(String specsId) {
		return gspProductRegisterSpecsService.getGspProductRegisterSpecsInfo(specsId);
	}

	@Login
	@RequestMapping(params = "getInfoByProductCode")
	@ResponseBody
	public Object getInfoByProductCode(String productCode) {
		return gspProductRegisterSpecsService.getInfoByProductCode(productCode);
	}


	//下载导入模板
    @Login
    @RequestMapping(params = "exportTemplate", method = RequestMethod.POST)
    public void exportTemplate(HttpServletResponse response, String token) throws Exception {
        gspProductRegisterSpecsService.exportTemplate(response, token);
    }
	//导入
	@Login
	@RequestMapping(params = "importExcelData")
	@ResponseBody
	public Json importExcelData(MultipartHttpServletRequest mhsr) throws Exception {
		return gspProductRegisterSpecsService.importExcelData(mhsr);
	}

	//导出
	@Login
	@RequestMapping(params = "exportDataToExcel")
	public void exportDataToExcel(HttpServletResponse response, GspProductRegisterSpecsQuery form) throws Exception {
		gspProductRegisterSpecsService.exportDataToExcel(response, form);
	}



	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return gspProductRegisterSpecsService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return gspProductRegisterSpecsService.getGspProductRegisterSpecsCombobox();
	}

}