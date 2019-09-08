package com.wms.controller.gsp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.wms.entity.GspCustomer;
import com.wms.mybatis.dao.GspCustomerMybatisDao;
import com.wms.utils.DateUtil;
import com.wms.utils.SfcUserLoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.GspCustomerService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.GspCustomerVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspCustomerForm;
import com.wms.query.GspCustomerQuery;

@Controller
@RequestMapping("gspCustomerController")
public class GspCustomerController {

	@Autowired
	private GspCustomerService gspCustomerService;
	@Autowired
	private GspCustomerMybatisDao gspCustomerMybatisDao;
//返回主页面
	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("gspCustomer/main", model);
	}
//返回增加或者修改界面
	@Login
	@RequestMapping(params = "toDetail")
	public ModelAndView toDetail(String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		Json json = gspCustomerService.getGspCustomerById(id);
		if(json.isSuccess()){
			model.put("customer", json.getObj());
		}
		model.put("createId", SfcUserLoginUtil.getLoginUser().getId());
		model.put("createDate", DateUtil.format(new Date()));
		return new ModelAndView("gspCustomer/detail", model);
	}

	//客户档案查询 委托客户详情返回增加或者修改界面
	@Login
	@RequestMapping(params = "basCustomerToDetail")
	public ModelAndView basCustomerToDetail(String id) {
		Map<String, Object> model = new HashMap<String, Object>();

		GspCustomer gspCustomer=gspCustomerMybatisDao.queryByEnterpriseId(id);
		if(gspCustomer==null){
			return new ModelAndView("gspCustomer/detail", model);
		}
		id = gspCustomer.getClientId();

		Json json = gspCustomerService.getGspCustomerById(id);
		if(json.isSuccess()){
			model.put("customer", json.getObj());
		}
		model.put("createId", SfcUserLoginUtil.getLoginUser().getId());
		model.put("createDate", DateUtil.format(new Date()));
		return new ModelAndView("gspCustomer/detail", model);
	}

	@Login
	@RequestMapping(params = "toInfo")
	public ModelAndView toInfo(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("gspCustomer/info", model);
	}
//返回主页datagird
	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<GspCustomerVO> showDatagrid(EasyuiDatagridPager pager, GspCustomerQuery query) {
		return gspCustomerService.getPagedDatagrid(pager, query);
	}
//增加
	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(GspCustomerForm gspCustomerForm) throws Exception {
		Json json = gspCustomerService.addGspCustomer(gspCustomerForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}

		return json;
	}
//修改
	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(GspCustomerForm gspCustomerForm) throws Exception {
		Json json = gspCustomerService.editGspCustomer(gspCustomerForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}

		return json;
	}
//删除
	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String id) {
		Json json = gspCustomerService.deleteGspCustomer(id);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}

		return json;
	}

	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return gspCustomerService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return gspCustomerService.getGspCustomerCombobox();
	}

	@Login
	@RequestMapping(params = "confirmSubmit")
	@ResponseBody
	public Json confirmSubmit(String id){
		return gspCustomerService.confirmSubmit(id);
	}
//发起新申请
	@Login
	@RequestMapping(params = "reApply")
	@ResponseBody
	public Json reApply(String id){
		return gspCustomerService.reApply(id);
	}
}