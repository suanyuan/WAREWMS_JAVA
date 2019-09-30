package com.wms.controller;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.DocQsmHeader;
import com.wms.service.DocQsmHeaderService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("docQsmHeaderController")
public class DocQsmHeaderController {

	@Autowired
	private DocQsmHeaderService docQsmHeaderService;



	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<DocQsmHeader> showDatagrid(EasyuiDatagridPager pager, DocQsmHeader query) {
		return docQsmHeaderService.getPagedDatagrid(pager, query);
	}

//增加头档
	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(DocQsmHeader from) {
		Json json = docQsmHeaderService.add(from);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}

		return json;
	}
//编辑头档
	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(DocQsmHeader from) {
		Json json = docQsmHeaderService.edit(from);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}

		return json;
	}
//删除计划单
	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String id) {
		Json json = docQsmHeaderService.delete(id);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}

		return json;
	}

//关闭计划单
	@Login
	@RequestMapping(params = "close")
	@ResponseBody
	public Json close(DocQsmHeader form) {
		return docQsmHeaderService.close(form);
	}
//取消计划单
	@Login
	@RequestMapping(params = "cancel")
	@ResponseBody
	public Json cancel(DocQsmHeader form) {
		return docQsmHeaderService.cancel(form);
	}
//生成库存信息
//    @Login
//	@RequestMapping(params = "commitQualityStatus")
//	@ResponseBody
//	public Json commitQualityStatus(DocQsmHeader form) {
//
//		return docQsmHeaderService.commitQualityStatus(form);
//	}
//提交质量状态管理
//    @Login
//	@RequestMapping(params = "commitQualityStatusWork")
//	@ResponseBody
//	public Json commitQualityStatusWork(DocQsmHeader form) {
//
//		return docQsmHeaderService.commitQualityStatusWork(form);
//	}



////导出
//	@Login
//	@RequestMapping(params = "exportDocQsmDataToPdf")
//	public String exportDocQsmDataToPdf(Model model, String qcudocno){
//		List<DocQsmHeader> docQsmHeaderList=docQsmHeaderService.docQsmToPdf(qcudocno);
//		JRDataSource jrDataSource = new JRBeanCollectionDataSource(docQsmHeaderList);
//		model.addAttribute("url", "WEB-INF/jasper/reportDocQsmHeader.jasper");
//		model.addAttribute("format", Constant.JASPER_PDF);
//		model.addAttribute("jrMainDataSource", jrDataSource);
//		return "iReportView";
//	}


}