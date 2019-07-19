package com.wms.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.wms.constant.Constant;
import com.wms.entity.QcMeteringDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.QcMeteringDeviceService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.QcMeteringDeviceVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.QcMeteringDeviceForm;
import com.wms.query.QcMeteringDeviceQuery;

@Controller
@RequestMapping("qcMeteringDeviceController")
public class QcMeteringDeviceController {

	@Autowired
	private QcMeteringDeviceService qcMeteringDeviceService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("qcMeteringDevice/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<QcMeteringDeviceVO> showDatagrid(EasyuiDatagridPager pager, QcMeteringDeviceQuery query) {
		return qcMeteringDeviceService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(QcMeteringDeviceForm qcMeteringDeviceForm) throws Exception {
		Json json = qcMeteringDeviceService.addQcMeteringDevice(qcMeteringDeviceForm);
		if(json == null){
			json = new Json();
		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(QcMeteringDeviceForm qcMeteringDeviceForm) throws Exception {
		Json json = qcMeteringDeviceService.editQcMeteringDevice(qcMeteringDeviceForm);
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
		Json json = qcMeteringDeviceService.deleteQcMeteringDevice(id);
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
		return qcMeteringDeviceService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return qcMeteringDeviceService.getQcMeteringDeviceCombobox();
	}

	/**
	 * 校期临近提醒
	 */
	@Login
	@RequestMapping(params = "getQcMeteringDeviceOutTime")
	@ResponseBody
	public Object getQcMeteringDeviceOutTime(){
		Json json = qcMeteringDeviceService.getQcMeteringDeviceOutTime(1);
		return json;
	}

}