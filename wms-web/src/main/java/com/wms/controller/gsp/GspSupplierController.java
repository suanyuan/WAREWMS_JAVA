package com.wms.controller.gsp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.wms.utils.SfcUserLoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.GspSupplierService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.GspSupplierVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspSupplierForm;
import com.wms.query.GspSupplierQuery;

@Controller
@RequestMapping("gspSupplierController")
public class GspSupplierController {

	@Autowired
	private GspSupplierService gspSupplierService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("gspSupplier/main", model);
	}

	@Login
	@RequestMapping(params = "toAdd")
	public ModelAndView toAdd(String menuId) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		model.put("createId", SfcUserLoginUtil.getLoginUser().getId());
		model.put("createDate",df.format(new Date()));
		model.put("isCheck","是");

		//model.put("specsId", specsId);
		model.put("isUse", 1);
		return new ModelAndView("gspSupplier/info", model);
	}
	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<GspSupplierVO> showDatagrid(EasyuiDatagridPager pager, GspSupplierQuery query) {

		return gspSupplierService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(@RequestParam(value="gspSupplierForm",required=true) String gspSupplierFormStr) throws Exception {
		GspSupplierForm gspSupplierForm = JSON.parseObject(gspSupplierFormStr,GspSupplierForm.class);

		Json json = gspSupplierService.addGspSupplier(gspSupplierForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(@RequestParam(value="gspSupplierForm",required=true) String gspSupplierFormStr) throws Exception {

		GspSupplierForm gspProductRegisterSpecsForm = JSON.parseObject(gspSupplierFormStr,GspSupplierForm.class);


		Json json = gspSupplierService.editGspSupplier(gspProductRegisterSpecsForm);
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
		Json json = gspSupplierService.deleteGspSupplier(id);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "getInfo")
	@ResponseBody
	public Object getInfo(String supplierId) {
		return gspSupplierService.getGspSupplierInfo(supplierId);
	}

	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return gspSupplierService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return gspSupplierService.getGspSupplierCombobox();
	}

}