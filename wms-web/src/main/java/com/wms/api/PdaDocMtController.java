package com.wms.api;

import com.wms.constant.Constant;
import com.wms.entity.DocMtProgressDetail;
import com.wms.query.DocMtDetailsQuery;
import com.wms.result.PdaResult;
import com.wms.service.BasCodesService;
import com.wms.service.DocMtDetailsService;
import com.wms.service.DocMtHeaderService;
import com.wms.utils.StringUtil;
import com.wms.vo.DocMtDetailsVO;
import com.wms.vo.DocMtHeaderVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocMtDetailsForm;
import com.wms.vo.form.DocMtHeaderForm;
import com.wms.vo.form.pda.PageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("mDocMt")
@SuppressWarnings("unchecked")
public class PdaDocMtController {

    @Autowired
    private DocMtHeaderService docMtHeaderService;

    @Autowired
    private DocMtDetailsService docMtDetailsService;

    @Autowired
    private BasCodesService basCodesService;

    /**
     * 获取未完成的养护任务单
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

        List<DocMtHeaderVO> pdaDocAsnHeaderVOList = docMtHeaderService.queryUndoneList(form);

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, pdaDocAsnHeaderVOList);
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }

    /**
     * 获取养护任务单header信息
     * @param form 收货任务单号
     * @return header信息
     */
    @RequestMapping(params = "docMtHeader", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryDocMtHeader(DocMtHeaderForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(form.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }
        DocMtHeaderVO docMtHeaderVO = docMtHeaderService.queryByMtno(form.getMtno());

        PdaResult result;
        if (StringUtil.isEmpty(docMtHeaderVO.getMtno())) {
            result = new PdaResult(PdaResult.CODE_FAILURE, "查无养护任务单数据");
        }else {
            result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        }
        resultMap.put(Constant.DATA, docMtHeaderVO);
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }

    /**
     * 获取养护任务detail数据
     * @param query 查询条件
     * @return detail数据
     */
    @RequestMapping(params = "docMtDetail", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryDocMtDetail(DocMtDetailsQuery query) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(query.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }
        json = docMtDetailsService.queryMtDetail(query);

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
     * 养护提交
     * @param form mtno mtlineno mtqtyCompleted checkflag conclusion remark
     * @return ~
     */
    @RequestMapping(params = "submit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> submit(DocMtDetailsForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(form.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        resultMap.put(Constant.RESULT, docMtDetailsService.mtSubmit(form));
        return resultMap;
    }

    /**
     * 结束养护
     * @param form 养护任务单号
     * @return ~
     */
    @RequestMapping(params = "endTask", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> endTask(DocMtHeaderForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(form.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        json = basCodesService.verifyRequestValidation(form.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        json = basCodesService.verifyRequestValidation(form.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        resultMap.put(Constant.RESULT, docMtHeaderService.endDocMt(form));
        return resultMap;
    }

    /**
     * 获取养护进度明细列表
     * @param mtno ~
     * @return ~
     */
    @RequestMapping(params = "docMtList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryDocPaList(DocMtHeaderForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(form.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        if (StringUtil.isEmpty(form.getMtno())) {
            resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "计划单号缺失"));
            return resultMap;
        }
        List<DocMtProgressDetail> docMtProgressDetails = docMtDetailsService.queryDocMtList(form.getMtno());
        resultMap.put(Constant.DATA, docMtProgressDetails);
        resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG));
        return resultMap;
    }

    /**
     * 获取养护指导明细列表
     * @param mtno ~
     * @return ~
     */
    @RequestMapping(params = "docMtGuides", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryDocMtGuides(String mtno,@RequestParam(defaultValue = "1") int pageNum, String version) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(version);
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        if (StringUtil.isEmpty(mtno)) {
            resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "计划单号缺失"));
            return resultMap;
        }
        List<DocMtDetailsVO> docMtDetailsVOList = docMtDetailsService.getGuidanceList(mtno, pageNum);
        resultMap.put(Constant.DATA, docMtDetailsVOList);
        resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG));
        return resultMap;
    }
}
