package com.wms.api;

import com.wms.constant.Constant;
import com.wms.query.pda.PdaDocPkQuery;
import com.wms.result.PdaResult;
import com.wms.service.BasCodesService;
import com.wms.service.DocPkRecordsService;
import com.wms.service.OrderHeaderForNormalService;
import com.wms.vo.Json;
import com.wms.vo.OrderHeaderForNormalVO;
import com.wms.vo.form.pda.PageForm;
import com.wms.vo.pda.PdaActAllocationDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("mDocPk")
@SuppressWarnings("unchecked")
public class PdaDocPkRecordsController {

	@Autowired
	private BasCodesService basCodesService;

	@Autowired
	private DocPkRecordsService docPkRecordsService;

	@Autowired
	private OrderHeaderForNormalService orderHeaderForNormalService;

	/**
	 * 获取需要拣货的出库单任务列表
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
			case Constant.CODE_SO_STS_PART_ALLOCATED:
			case Constant.CODE_SO_STS_ALLOCATED:
			case Constant.CODE_SO_STS_PART_PICKED:
				PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
				resultMap.put(Constant.DATA, headerVO);
				resultMap.put(Constant.RESULT, result);
				return resultMap;
			case Constant.CODE_SO_STS_PICKED:
				resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "此单已拣货完成"));
				return resultMap;
			default:
				resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "此单不处于拣货阶段"));
				return resultMap;
		}
	}

	/**
	 * 需拣货的库位、货品明细
	 */
	@RequestMapping(params = "pickingLocations", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> pickingLocations(PageForm form, String orderno) {

		Map<String, Object> resultMap = new HashMap<>();
		Json json = basCodesService.verifyRequestValidation(form.getVersion());
		if (!json.isSuccess()) {

			return (Map<String, Object>) json.getObj();
		}
		List<PdaActAllocationDetailVO> voList = docPkRecordsService.pickingLocations(form, orderno);

		PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
		resultMap.put(Constant.DATA, voList);
		resultMap.put(Constant.RESULT, result);
		return resultMap;
	}

	/**
	 * 拣货提交(单次)
	 */
	@RequestMapping(params = "singlePkCommit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> singlePkCommit(PdaDocPkQuery query) {

		Json json = basCodesService.verifyRequestValidation(query.getVersion());
		if (!json.isSuccess()) {

			return (Map<String, Object>) json.getObj();
		}
		return docPkRecordsService.singlePkCommit(query);
	}

//	/**
//	 * 结束拣货
//	 */
//	@RequestMapping(params = "endpicking", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> endpicking(PdaDocPkQuery query) {
//
//		Map<String, Object> resultMap = new HashMap<>();
//		Json json = basCodesService.verifyRequestValidation(query.getVersion());
//		if (!json.isSuccess()) {
//
//			return (Map<String, Object>) json.getObj();
//		}
//		resultMap.put(Constant.RESULT, docPkRecordsService.endPicking(query));
//		return resultMap;
//	}
}