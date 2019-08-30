package com.wms.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.DocPaHeaderService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.DocPaHeaderVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocPaHeaderForm;
import com.wms.query.DocPaHeaderQuery;

@Controller
@RequestMapping("docPaHeaderController")
public class DocPaHeaderController {

	@Autowired
	private DocPaHeaderService docPaHeaderService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("docPaHeader/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<DocPaHeaderVO> showDatagrid(EasyuiDatagridPager pager, DocPaHeaderQuery query) {
		return docPaHeaderService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(DocPaHeaderForm docPaHeaderForm) throws Exception {
		Json json = docPaHeaderService.addDocPaHeader(docPaHeaderForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(DocPaHeaderForm docPaHeaderForm) throws Exception {
		Json json = docPaHeaderService.editDocPaHeader(docPaHeaderForm);
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
		Json json = docPaHeaderService.deleteDocPaHeader(id);
//		if(json == null){
//			json = new Json();
//		}
//			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return docPaHeaderService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	/**
	 * 打印上架任务单
	 * @param response
	 * @param orderCodeList
	 * @throws Exception
	 */
	@Login
	@RequestMapping(params = "exportBatchPdf")
	public void exportBatchPdf(HttpServletResponse response, String orderCodeList) throws Exception {
		try {
			//entryOrderHeaderForCrossDockingService.exportBatchPdf(response, orderCodeList);

			docPaHeaderService.exportBatchPdf(response,orderCodeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 收货回写
	 * @param orderNo
	 * @throws Exception
	 */
	@Login
	@RequestMapping(params = "resetDocPa")
	@ResponseBody
	public Json resetDocPa(String orderNo) throws Exception {
		return docPaHeaderService.resetDocPa(orderNo);
	}

}