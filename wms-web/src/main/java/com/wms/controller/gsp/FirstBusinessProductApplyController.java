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
import com.wms.service.FirstBusinessProductApplyService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.FirstBusinessProductApplyVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.FirstBusinessProductApplyForm;
import com.wms.query.FirstBusinessProductApplyQuery;

@Controller
@RequestMapping("firstBusinessProductApplyController")
public class FirstBusinessProductApplyController {

	@Autowired
	private FirstBusinessProductApplyService firstBusinessProductApplyService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("firstBusinessProductApply/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<FirstBusinessProductApplyVO> showDatagrid(EasyuiDatagridPager pager, FirstBusinessProductApplyQuery query) {
		return firstBusinessProductApplyService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(FirstBusinessProductApplyForm firstBusinessProductApplyForm) throws Exception {
		Json json = firstBusinessProductApplyService.addFirstBusinessProductApply(firstBusinessProductApplyForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(FirstBusinessProductApplyForm firstBusinessProductApplyForm) throws Exception {
		Json json = firstBusinessProductApplyService.editFirstBusinessProductApply(firstBusinessProductApplyForm);
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
		Json json = firstBusinessProductApplyService.deleteFirstBusinessProductApply(id);
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
		return firstBusinessProductApplyService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}
}