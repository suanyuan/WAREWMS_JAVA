package com.wms.api;

import com.wms.constant.Constant;
import com.wms.query.CouRequestDetailsQuery;
import com.wms.result.PdaResult;
import com.wms.service.BasCodesService;
import com.wms.service.CouRequestDetailsService;
import com.wms.service.CouRequestHeaderService;
import com.wms.utils.StringUtil;
import com.wms.vo.Json;
import com.wms.vo.form.CouRequestDetailsForm;
import com.wms.vo.form.pda.PageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("mInvCou")
@SuppressWarnings("unchecked")
public class PdaInvCouController {

    @Autowired
    private CouRequestHeaderService couRequestHeaderService;

    @Autowired
    private CouRequestDetailsService couRequestDetailsService;

    @Autowired
    private BasCodesService basCodesService;

    /**
     * 获取未完成的盘点任务单
     * @param form 页码
     * @return 任务单list
     */
    @RequestMapping(params = "undoneList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryUndoneList(PageForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(form.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        json = couRequestHeaderService.queryUndoneList(form);

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, json.getObj());
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }

    /**
     * 获取任务单header信息
     * @param cycleCountno 盘点任务单号
     * @return header信息
     */
    @RequestMapping(params = "couRequestHeader", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryCouRequestHeader(String cycleCountno, String version) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(version);
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        json = couRequestHeaderService.queryCouRequestHeader(cycleCountno);

        if (!json.isSuccess()) {

            resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, json.getMsg()));
            return resultMap;
        }
        resultMap.put(Constant.DATA, json.getObj());
        resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG));
        return resultMap;
    }

    /**
     * 获取盘点任务detail数据
     * @param query 查询条件
     * @return detail数据
     */
    @RequestMapping(params = "couRequestDetail", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> couRequestDetail(CouRequestDetailsQuery query) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(query.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        json = couRequestDetailsService.queryCouRequestDetail(query);

        if (!json.isSuccess()) {
            resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, json.getMsg()));
            return resultMap;
        }

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, json.getObj());
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }

    /**
     * 盘点提交
     * @param form cycleCountno cycleCountlineno qtyAct
     * @return ~
     */
    @RequestMapping(params = "submit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> submit(CouRequestDetailsForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(form.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        json = couRequestDetailsService.couSubmit(form);
        if (!json.isSuccess()) {
            resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, json.getMsg()));
        }else {
            resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, json.getMsg()));
        }
        return resultMap;
    }

    /**
     * 获取盘点进度明细列表
     * @param cycleCountno ~
     * @return ~
     */
    @RequestMapping(params = "couRequestList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> couRequestList(String cycleCountno, PageForm pageForm) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(pageForm.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        if (StringUtil.isEmpty(cycleCountno)) {
            resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "任务单号缺失"));
            return resultMap;
        }
        json = couRequestDetailsService.couRequestList(cycleCountno, pageForm);
        if (!json.isSuccess()) {
            resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, json.getMsg()));
            return resultMap;
        }

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, json.getObj());
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }
}
