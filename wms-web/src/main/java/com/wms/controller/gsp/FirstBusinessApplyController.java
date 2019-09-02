package com.wms.controller.gsp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.wms.easyui.EasyuiCombobox;
import com.wms.query.FirstBusinessProductApplyQuery;
import com.wms.vo.FirstBusinessProductApplyPageVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.FirstBusinessApplyService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.FirstBusinessApplyVO;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.FirstBusinessApplyForm;
import com.wms.query.FirstBusinessApplyQuery;

@Controller
@RequestMapping("firstBusinessApplyController")
public class FirstBusinessApplyController {

	@Autowired
	private FirstBusinessApplyService firstBusinessApplyService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("firstBusinessApply/main", model);
	}

	@Login
	@RequestMapping(params = "toDetail")
	public ModelAndView toDetail(String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		Json json = firstBusinessApplyService.queryFirstBusinessApply(id);
		if(json.isSuccess()){
			model.put("firstBusinessApply",json.getObj());
		}
		return new ModelAndView("firstBusinessApply/detail", model);
	}
	@Login
	@RequestMapping(params = "toEdit")
	public ModelAndView toEdit(String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		Json json = firstBusinessApplyService.queryFirstBusinessApply(id);

		if(json.isSuccess()){
			model.put("firstBusinessApply1",json.getObj());
		}
		return new ModelAndView("firstBusinessApply/edit", model);
	}

	@Login
	@RequestMapping(params = "showBusinessDetail")
	public ModelAndView showBusinessDetail(String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		Json json = firstBusinessApplyService.queryFirstBusinessApply(id);

		if(json.isSuccess()){
			model.put("firstBusinessApply1",json.getObj());
		}
		return new ModelAndView("firstBusinessApply/skuDetail", model);
	}


	@Login
	@RequestMapping(params = "toDialogProductAndSupplier")
	public ModelAndView toDialogAddress(String id) {
		Map<String, Object> model = new HashMap<String, Object>();
//		GspReceivingAddress gspReceivingAddress = gspReceivingAddressMybatisDao.queryByAddressId(receivingAddressId);
//		model.put("receivingId", receivingId);
//		model.put("gspReceivingAddress", gspReceivingAddress);
		return new ModelAndView("firstBusinessApply/dialogProductASupplier", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<FirstBusinessApplyVO> showDatagrid(EasyuiDatagridPager pager, FirstBusinessApplyQuery query) {
		return firstBusinessApplyService.getPagedDatagrid(pager, query);
	}





	/**
	 * 查询首营申请产品
	 * @param pager
	 * @param query
	 * @return
	 */
	@Login
	@RequestMapping(params = "showSpecsDatagrid")
	@ResponseBody
	public EasyuiDatagrid<FirstBusinessProductApplyPageVO> showSpecsDatagrid(EasyuiDatagridPager pager, FirstBusinessProductApplyQuery query){
		return firstBusinessApplyService.queryFirstBusinessApplyProduct(pager,query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(FirstBusinessApplyForm firstBusinessApplyForm) throws Exception {
		Json json = firstBusinessApplyService.addFirstBusinessApply(firstBusinessApplyForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(FirstBusinessApplyForm firstBusinessApplyForm) throws Exception {
		Json json = firstBusinessApplyService.editFirstBusinessApply(firstBusinessApplyForm);
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
		Json json = firstBusinessApplyService.deleteFirstBusinessApply(id);
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
		return firstBusinessApplyService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}




    @Login
    @RequestMapping(params = "getInfo")
    @ResponseBody
    public Object getInfo(String applyId) {
        return firstBusinessApplyService.getInfo(applyId);
    }

	@Login
	@RequestMapping(params = "apply")
	@ResponseBody
	public Json apply(String id,String clientId,String supplierArr,String productArr,String productLine) throws Exception {
		if(!StringUtils.isEmpty(id)){
			return firstBusinessApplyService.editApply(id,clientId,supplierArr,productArr,productLine);
		}
		return firstBusinessApplyService.addApply(clientId,supplierArr,productArr,productLine);
	}

	@Login
	@RequestMapping(params = "confirmApply")
	@ResponseBody
	public Json confirmApply(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			return Json.error("请选择需要确认的申请");
		}
		return firstBusinessApplyService.addConfirmApply(id);
	}

	@Login
	@RequestMapping(params = "reApply")
	@ResponseBody
	public Json reApply(String id) {
		try{
			if(StringUtils.isEmpty(id)){
				return Json.error("请选择需要确认的申请");
			}
			return firstBusinessApplyService.addReApply(id);
		}catch (Exception e){
			e.printStackTrace();
			return Json.error("");
		}

	}

	@Login
	@RequestMapping(params = "getProductLineByEnterpriseId")
	@ResponseBody
	public List<EasyuiCombobox> getProductLineByEnterpriseId(String customerId){
		return firstBusinessApplyService.getProductLineByEnterpriseId(customerId);
	}
}