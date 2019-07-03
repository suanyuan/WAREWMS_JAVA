package com.wms.controller.gsp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

	@Login
	@RequestMapping(params = "toReceivingAddress")
	public ModelAndView toReceivingAddress(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("basCustomer/receivingAddress", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<GspReceivingAddressVO> showDatagrid(EasyuiDatagridPager pager, GspReceivingAddressQuery query) {
		EasyuiDatagrid<GspReceivingAddressVO> pagedDatagrid = gspReceivingAddressService.getPagedDatagrid(pager, query);
		return pagedDatagrid;
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(GspReceivingAddressForm gspReceivingAddressForm) throws Exception {
		Json json = gspReceivingAddressService.addGspReceivingAddress(gspReceivingAddressForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(GspReceivingAddressForm gspReceivingAddressForm) throws Exception {
		Json json = gspReceivingAddressService.editGspReceivingAddress(gspReceivingAddressForm);
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
		Json json = gspReceivingAddressService.deleteGspReceivingAddress(id);
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
		return gspReceivingAddressService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return gspReceivingAddressService.getGspReceivingAddressCombobox();
	}

}