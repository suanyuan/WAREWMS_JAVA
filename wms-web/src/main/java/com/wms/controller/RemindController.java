package com.wms.controller;

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasCodes;
import com.wms.entity.InvLotAtt;
import com.wms.entity.Remind;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.BasCodesQuery;
import com.wms.query.BasGtnQuery;
import com.wms.service.BasCodesService;
import com.wms.service.BasGtnService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.BasGtnVO;
import com.wms.vo.InvLotAttVO;
import com.wms.vo.Json;
import com.wms.vo.form.BasCodesForm;
import com.wms.vo.form.BasGtnForm;
import com.wms.vo.form.InvLotAttForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("remindController")
public class RemindController {

	@Autowired
	private BasGtnService basGtnService;

	@Autowired
	private BasCodesService basCodesService;
	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("remind/main", model);
	}

//	@Login
//	@RequestMapping(params = "showDatagrid")
//	@ResponseBody
//	public EasyuiDatagrid<BasGtnVO> showDatagrid(EasyuiDatagridPager pager, BasGtnQuery query) {
//		return basGtnService.getPagedDatagrid(pager, query);
//	}

	@Login
	@RequestMapping(params = "showDatagrid1")
	@ResponseBody
	public EasyuiDatagrid<BasCodes> showDatagrid1(EasyuiDatagridPager pager, BasCodesQuery query) {
		return basGtnService.getPagedDatagrid1(pager, query);
	}


	@Login
	@RequestMapping(params = "showInvLotLocDatagrid")
	@ResponseBody
	public EasyuiDatagrid<InvLotAtt> showInvLotLocDatagrid(EasyuiDatagridPager pager, BasCodesQuery query) {
		return basCodesService.getInvLotLocPagedDatagrid(pager, query);
	}


	//查询赋值接口  查询提醒模块信息   赋值给提醒模块表
	@Login
	@RequestMapping(params = "remind")
	@ResponseBody
	public void remind() throws Exception {
		 basCodesService.remind();
	}


	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(BasGtnForm basGtnForm) throws Exception {
		Json json = basGtnService.addBasGtn(basGtnForm);
		if(json == null){
			json = new Json();

		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(BasGtnForm basGtnForm) throws Exception {
		Json json = basGtnService.editBasGtn(basGtnForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "exportTemplate", method = RequestMethod.POST)
	public void exportTemplate(HttpServletResponse response, String token) throws Exception {
		basGtnService.exportTemplate(response, token);
	}

	@Login
	@RequestMapping(params = "importExcelData")
	@ResponseBody
	public Json importExcelData(MultipartHttpServletRequest mhsr) throws Exception {
		return basGtnService.importExcelData(mhsr);
	}

	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String id) {
		Json json = basGtnService.deleteBasGtn(id);
		if(json == null){
			json = new Json();
			//json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

    @Login
    @RequestMapping(params = "exportInvLotAttDataToExcel")
    public void exportBasCustomerDataToExcel(HttpServletResponse response, BasCodesQuery form) throws Exception {
        basCodesService.exportInvLotAttDataToExcel(response, form);
    }


	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return basGtnService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return basGtnService.getBasGtnCombobox();
	}

}