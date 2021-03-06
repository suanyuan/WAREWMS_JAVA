package com.wms.api;

import com.wms.constant.Constant;
import com.wms.query.pda.PdaDocPackageQuery;
import com.wms.result.PdaResult;
import com.wms.service.BasCodesService;
import com.wms.service.DocOrderPackingService;
import com.wms.service.OrderHeaderForNormalService;
import com.wms.vo.Json;
import com.wms.vo.OrderHeaderForNormalVO;
import com.wms.vo.form.DocOrderPackingForm;
import com.wms.vo.form.pda.PageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("mPackage")
@SuppressWarnings("unchecked")
public class PdaPackageController {

    @Autowired
    private DocOrderPackingService docOrderPackingService;

    @Autowired
    private OrderHeaderForNormalService orderHeaderForNormalService;

    @Autowired
    private BasCodesService basCodesService;

    /**
     * 获取包装复核任务列表、收货任务列表
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

        List<OrderHeaderForNormalVO> orderHeaderForNormalVOS = orderHeaderForNormalService.getUndoneList(form);

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, orderHeaderForNormalVOS);
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }

    /**
     * 获取出库任务单header信息,for pda 包装复核任务列表，任务状态区间
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
            case Constant.CODE_SO_STS_PICKED:
            case Constant.CODE_SO_STS_PART_PACKED:
                PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
                resultMap.put(Constant.DATA, headerVO);
                resultMap.put(Constant.RESULT, result);
                return resultMap;
            case Constant.CODE_SO_STS_PACKED:
                resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "此单已包装复核完毕"));
                return resultMap;
                default:
                    resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "此单不处于包装复核阶段"));
                    return resultMap;
        }
    }

    /**
     * 获取包装复核任务明细
     * @param query ~
     * @return ~
     */
    @RequestMapping(params = "docPackage", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryDocPackage(PdaDocPackageQuery query) {

        Json json = basCodesService.verifyRequestValidation(query.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }
        return docOrderPackingService.queryDocPackage(query);
    }

    /**
     * 包装复核提交
     * @param form ~
     * @return ~
     */
    @RequestMapping(params = "docPackageCommit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> docPackageCommit(DocOrderPackingForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(form.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        resultMap.put(Constant.RESULT, docOrderPackingService.packageCommit(form));
        return resultMap;
    }

    /**
     * 包装复核提交(单次)
     * @param query ~
     * @return ~
     */
    @RequestMapping(params = "singlePackageCommit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> singlePackageCommit(PdaDocPackageQuery query) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(query.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        resultMap.put(Constant.RESULT, docOrderPackingService.singlePackageCommit(query));
        return resultMap;
    }

    /**
     *
     */
    @RequestMapping(params = "getOrderPackingInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getOrderPackingInfo(String orderNo, String version) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(version);
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        json = docOrderPackingService.getOrderPackingInfo(orderNo);
        if (json.isSuccess()) {
            resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, json.getMsg()));
            return resultMap;
        }
        resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, json.getMsg()));
        resultMap.put(Constant.DATA, json.getObj());//List<DocOrderPacking>
        return resultMap;
    }

    /**
     * 包装复核装箱结束提交
     * @param form ~
     * @return ~
     */
    @RequestMapping(params = "commitCartonType", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> commitCartonType(DocOrderPackingForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(form.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        resultMap.put(Constant.RESULT, docOrderPackingService.commitCartonType(form));
        return resultMap;
    }

    /**
     * 包装复核结束提交
     * 先查询包装复核是否完全装箱（根据分配明细来）
     * 完全才能结束
     * @return ~
     */
    @RequestMapping(params = "endPacking", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> endPacking(DocOrderPackingForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(form.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        json = docOrderPackingService.getOrderPackingInfo(form.getOrderno());
        if (json.isSuccess()) {
            resultMap.put(Constant.RESULT, docOrderPackingService.endPacking(form));
            return resultMap;
        }
        resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, json.getMsg()));
        resultMap.put(Constant.DATA, json.getObj());//List<DocOrderPacking>
        return resultMap;
    }
}
