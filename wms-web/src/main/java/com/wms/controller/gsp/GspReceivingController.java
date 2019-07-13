package com.wms.controller.gsp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.wms.constant.Constant;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.entity.GspReceiving;
import com.wms.entity.GspReceivingAddress;
import com.wms.mybatis.dao.GspEnterpriseInfoMybatisDao;
import com.wms.mybatis.dao.GspReceivingAddressMybatisDao;
import com.wms.mybatis.dao.GspReceivingMybatisDao;
import com.wms.utils.DateUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.form.GspEnterpriceFrom;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.GspReceivingService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.GspReceivingVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspReceivingForm;
import com.wms.query.GspReceivingQuery;

@Controller
@RequestMapping("gspReceivingController")
public class GspReceivingController {

	@Autowired
	private GspReceivingService gspReceivingService;
	@Autowired
	private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;
	@Autowired
	private GspReceivingAddressMybatisDao gspReceivingAddressMybatisDao;

	@Autowired
	private GspReceivingMybatisDao gspReceivingMybatisDao;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("gspReceiving/main", model);
	}
	@Login
	@RequestMapping(params = "toDialogAddress")
	public ModelAndView toDialogAddress(String receivingId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("receivingId", receivingId);
		return new ModelAndView("gspReceiving/dialogAddress", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<GspReceivingVO> showDatagrid(EasyuiDatagridPager pager, GspReceivingQuery query) {
		return gspReceivingService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(GspReceivingForm gspReceivingForm) throws Exception {
		Json json = gspReceivingService.addGspReceiving(gspReceivingForm);
		if(json == null){
			json = new Json();
		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));

		return json;
	}

	@Login
	@RequestMapping(params = "confirmApply")
	@ResponseBody
	public Json confirmApply( @RequestParam(value="gspReceivingFormsttr",required=true) String gspReceivingFormstr) throws Exception {
		GspReceivingForm gspReceivingForm = JSON.parseObject(gspReceivingFormstr,GspReceivingForm.class);
		Json json = gspReceivingService.confirmApply(gspReceivingForm);
		if(json == null){
			json = new Json();
		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));

		return json;
	}

	@Login
	@RequestMapping(params = "validateReceiv")
	@ResponseBody
	public GspReceiving validateReceiv(@RequestParam(value = "receivingId") String receivingId) throws Exception {
		return gspReceivingService.validateReceiv(receivingId);
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(GspReceivingForm gspReceivingForm) throws Exception {
		Json json = gspReceivingService.editGspReceiving(gspReceivingForm);
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
		Json json = gspReceivingService.deleteGspReceiving(id);
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
		return gspReceivingService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return gspReceivingService.getGspReceivingCombobox();
	}



	@Login
	@RequestMapping(params = "toDetail")
	public ModelAndView toDetail(@RequestParam(required = false) String enterpriseId,@RequestParam(required = false) String receivingId) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(enterpriseId)){
			GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoMybatisDao.queryById(enterpriseId);
			model.put("gspEnterpriseInfo",gspEnterpriseInfo);
		}
		if (StringUtils.isNotEmpty(receivingId)){
			GspReceiving gspReceiving = gspReceivingMybatisDao.queryById(receivingId);
			GspReceivingAddress gspReceivingAddress =gspReceivingAddressMybatisDao.queryById(receivingId);
			model.put("gspReceiving",gspReceiving);
			model.put("gspReceivingAddress",gspReceivingAddress);
		}
		model.put("enterpriseId", enterpriseId);
		model.put("receivingId", receivingId);
		return new ModelAndView("gspReceiving/detail", model);
	}
}