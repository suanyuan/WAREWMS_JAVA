package com.wms.controller;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.DocQsmDetailsQuery;
import com.wms.service.DocQsmDetailsService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.DocQsmDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocQsmDetailsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("docQsmDetailsController")
public class DocQsmDetailsController {

	@Autowired
	private DocQsmDetailsService docQsmDetailsService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("docQsmDetails/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<DocQsmDetailsVO> showDatagrid(EasyuiDatagridPager pager, DocQsmDetailsQuery query) {
		return docQsmDetailsService.getPagedDatagrid(pager, query);
	}
//生成库存信息
    @Login
	@RequestMapping(params = "commitQualityStatus")
	@ResponseBody
	public Json commitQualityStatus(DocQsmDetailsForm form) {

		return docQsmDetailsService.commitQualityStatus(form);
	}
//提交质量状态管理
    @Login
	@RequestMapping(params = "commitQualityStatusWork")
	@ResponseBody
	public Json commitQualityStatusWork(DocQsmDetailsForm form) {

		return docQsmDetailsService.commitQualityStatusWork(form);
	}

	//关闭计划单
	@Login
	@RequestMapping(params = "close")
	@ResponseBody
	public Json close(DocQsmDetailsForm form) {
		return docQsmDetailsService.close(form);
	}
	//取消计划单
	@Login
	@RequestMapping(params = "cancel")
	@ResponseBody
	public Json cancel(DocQsmDetailsForm form) {
		return docQsmDetailsService.cancel(form);
	}
	//删除计划单
	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String id) {
		Json json = docQsmDetailsService.deleteDocQsmDetails(id);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}

		return json;
	}




}