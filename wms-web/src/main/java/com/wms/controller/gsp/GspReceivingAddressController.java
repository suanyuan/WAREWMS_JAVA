package com.wms.controller.gsp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wms.entity.GspReceivingAddress;
import com.wms.entity.PCD;
import com.wms.utils.editor.CustomDateEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.GspReceivingAddressService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.GspReceivingAddressVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspReceivingAddressForm;
import com.wms.query.GspReceivingAddressQuery;

@Controller
@RequestMapping("gspReceivingAddressController")
public class GspReceivingAddressController {

	@Autowired
	private GspReceivingAddressService gspReceivingAddressService;


	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		binder.registerCustomEditor(Date.class,"addtime",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"edisendtime5",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"expectedarrivetime1",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"expectedarrivetime2",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"createDate",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"editDate",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}


	@Login
	@RequestMapping(params = "toReceivingAddress")
	public ModelAndView toReceivingAddress(String enterpriseId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", enterpriseId);
		return new ModelAndView("basCustomer/receivingAddress", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<GspReceivingAddressVO> showDatagrid(@RequestParam(value = "receivingId",required = false)String receivingId, EasyuiDatagridPager pager) {
		EasyuiDatagrid<GspReceivingAddressVO> pagedDatagrid = gspReceivingAddressService.getPagedDatagrid(pager, receivingId);
		return pagedDatagrid;
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(@RequestParam(value = "gspReceivingAddressFormStr") String gspReceivingAddressFormStr) throws Exception {
        GspReceivingAddressForm gspReceivingAddressForm = JSON.parseObject(gspReceivingAddressFormStr, GspReceivingAddressForm.class);
        Json json = gspReceivingAddressService.addGspReceivingAddress(gspReceivingAddressForm);
		if(json == null){
			json = new Json();
		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(GspReceivingAddressForm gspReceivingAddressForm) throws Exception {
		Json json = gspReceivingAddressService.editGspReceivingAddress(gspReceivingAddressForm);
		if(json == null){
			json = new Json();
		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(@RequestParam(value = "receivingAddressId") String receivingAddressId) {
		Json json = gspReceivingAddressService.deleteGspReceivingAddress(receivingAddressId);
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
		return gspReceivingAddressService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return gspReceivingAddressService.getGspReceivingAddressCombobox();
	}


	@Login
	@RequestMapping(params = "getArea")
	@ResponseBody
	public String getArea(@RequestParam(value = "pid") String pid) {
		String json=null;
		if (pid!=null&&pid!="") {
			int i = Integer.parseInt(pid);
			List<PCD> pcdList = gspReceivingAddressService.findPCDByPid(i);
			json = JSON.toJSONString(pcdList);

		}
		return json;
	}
}