package com.wms.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.wms.constant.Constant;
import com.wms.mybatis.dao.DocPkRecordsMybatisDao;
import com.wms.result.PdaResult;
import com.wms.service.BasCodesService;
import com.wms.service.OrderHeaderForNormalService;
import com.wms.vo.OrderHeaderForNormalVO;
import com.wms.vo.form.pda.PageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.DocPkRecordsService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.DocPkRecordsVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocPkRecordsForm;
import com.wms.query.DocPkRecordsQuery;

@Controller
@RequestMapping("docPkRecordsController")
public class DocPkRecordsController {

	@Autowired
	private DocPkRecordsService docPkRecordsService;

	@Autowired
	private DocPkRecordsMybatisDao docPkRecordsMybatisDao;

	@Autowired
	private OrderHeaderForNormalService orderHeaderForNormalService;
	@Autowired
	private BasCodesService basCodesService;

//	@Login
//	@RequestMapping(params = "toMain")
//	public ModelAndView toMain(String menuId) {
//		Map<String, Object> model = new HashMap<String, Object>();
//		model.put("menuId", menuId);
//		return new ModelAndView("docPkRecords/main", model);
//	}

//	/**
//	 * 获取拣货任务列表、收货任务列表
//	 * @param pager 分页
//	 * @return ~
//	 */
////	@Login
//	@RequestMapping(params = "showDatagrid")
//	@ResponseBody
//	public EasyuiDatagrid<DocPkRecordsVO> showDatagrid(EasyuiDatagridPager pager, DocPkRecordsQuery query) {
//		return docPkRecordsService.getPagedDatagrid(pager, query);
//	}






	/**
	 * 获取需要拣货的出库单任务列表、收货任务列表
	 * @param form 分页
	 * @return ~
	 */
	@RequestMapping(params = "undoneList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryUndoneList(PageForm form) {

		Map<String, Object> resultMap = new HashMap<>();
		Json json = basCodesService.verifyRequestValidation(form.getVersion());
		if (!json.isSuccess()) {
			return (Map<String, Object>) json.getObj();
		}
		System.out.println();
		List<OrderHeaderForNormalVO> orderHeaderForNormalVOS = docPkRecordsService.getUndoneList(form);

		PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
		resultMap.put(Constant.DATA, orderHeaderForNormalVOS);
		resultMap.put(Constant.RESULT, result);
		return resultMap;
	}

	/**
	 * 获取出库任务单header信息,for pda 拣货任务列表，任务状态区间
	 * @param orderno 出库任务单号
	 * @return header信息
	 */
	@RequestMapping(params = "docOrderHeader", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryDocOrderHeader(String orderno, String version) {

		Map<String, Object> resultMap = new HashMap<>();
		Json json = basCodesService.verifyRequestValidation(version);
		if (!json.isSuccess()) {
			return (Map<String, Object>) json.getObj();
		}
		OrderHeaderForNormalVO headerVO = orderHeaderForNormalService.queryByOrderno(orderno);
		if (headerVO == null || headerVO.getOrderno() == null) {

			resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "查无出库单头档数据"));
			return resultMap;
		}
		switch (headerVO.getSostatus()) {
			case "40":
			case "50":
				PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
				resultMap.put(Constant.DATA, headerVO);
				resultMap.put(Constant.RESULT, result);
				return resultMap;
			case "60":
				resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "此单已完全装箱"));
				return resultMap;
			default:
				resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "此单不处于拣货阶段"));
				return resultMap;
		}
	}

	/**
	 * 拣货(单次)   yao
	 * @param form ~
	 * @return ~
	 */
	@RequestMapping(params = "singlePackageCommit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> singlePackageCommit(DocPkRecordsForm form) {

		Map<String, Object> resultMap = new HashMap<>();
		Json json = basCodesService.verifyRequestValidation(form.getVersion());
		if (!json.isSuccess()) {
			return (Map<String, Object>) json.getObj();
		}
		resultMap.put(Constant.RESULT, docPkRecordsService.singlePkCommit(form));

		return resultMap;
	}
//	/**
//	 * 获取拣货任务列表、收货任务列表
//	 * @param form 分页
//	 * @return ~
//	 */
//	@RequestMapping(params = "undoneList", method = RequestMethod.GET)
//	@ResponseBody
//	public Map<String, Object> queryUndoneList(PageForm form) {
//
//		Map<String, Object> resultMap = new HashMap<>();
////		Json json = basCodesService.verifyRequestValidation(form.getVersion());
////		if (!json.isSuccess()) {
////			return (Map<String, Object>) json.getObj();
////		}
//		System.out.println();
////		List<OrderHeaderForNormalVO> orderHeaderForNormalVOS = docPkRecordsMybatisDao.queryByList(form);
//
//		PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
////		resultMap.put(Constant.DATA, orderHeaderForNormalVOS);
//		resultMap.put(Constant.RESULT, result);
//		return resultMap;
//	}





	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(DocPkRecordsForm docPkRecordsForm) throws Exception {
		Json json = docPkRecordsService.addDocPkRecords(docPkRecordsForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(DocPkRecordsForm docPkRecordsForm) throws Exception {
		Json json = docPkRecordsService.editDocPkRecords(docPkRecordsForm);
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
		Json json = docPkRecordsService.deleteDocPkRecords(id);
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
		return docPkRecordsService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return docPkRecordsService.getDocPkRecordsCombobox();
	}

}