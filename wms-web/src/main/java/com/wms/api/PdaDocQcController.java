package com.wms.api;

import com.wms.constant.Constant;
import com.wms.mybatis.entity.pda.PdaDocQcDetailForm;
import com.wms.query.DocQcDetailsQuery;
import com.wms.query.pda.PdaDocQcDetailQuery;
import com.wms.result.PdaResult;
import com.wms.service.BasCodesService;
import com.wms.service.DocQcDetailsService;
import com.wms.service.DocQcHeaderService;
import com.wms.utils.StringUtil;
import com.wms.vo.Json;
import com.wms.vo.form.pda.PageForm;
import com.wms.vo.pda.PdaDocQcDetailVO;
import com.wms.vo.pda.PdaDocQcHeaderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.Version;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("mDocQc")
@SuppressWarnings("unchecked")
public class PdaDocQcController {

    @Autowired
    private DocQcHeaderService docQcHeaderService;

    @Autowired
    private DocQcDetailsService docQcDetailsService;

    @Autowired
    private BasCodesService basCodesService;

    /**
     * 获取未验收任务列表
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

        List<PdaDocQcHeaderVO> pdaDocQcHeaderVOS = docQcHeaderService.queryUndoneList(form);

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, pdaDocQcHeaderVOS);
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }

    /**
     * 获取验收任务单header信息
     * @param qcno 验收任务单号
     * @return header信息
     */
    @RequestMapping(params = "docQcHeader", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryDocQcHeader(String qcno, String version) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(version);
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        PdaDocQcHeaderVO pdaDocQcHeaderVO = docQcHeaderService.queryByQcno(qcno);

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, pdaDocQcHeaderVO);
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }

    /**
     * 通过上架单号获取验收任务单header信息
     * @param pano 验收任务单号
     * @return header信息
     */
    @RequestMapping(params = "docQcHeaderByPano", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryDocQcHeaderByPano(String pano, String version) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(version);
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        PdaDocQcHeaderVO pdaDocQcHeaderVO = docQcHeaderService.queryDocQcHeaderByPano(pano);
        if (pdaDocQcHeaderVO == null) {
            resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "查无此上架单对应的验收任务"));
            return resultMap;
        }
        resultMap.put(Constant.DATA, pdaDocQcHeaderVO);
        resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG));
        return resultMap;
    }

    /**
     * 获取验收任务detail数据
     * @param query 查询条件
     * @return detail数据
     */
    @RequestMapping(params = "docQcDetail", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryDocQcDetail(PdaDocQcDetailQuery query) {

        Json json = basCodesService.verifyRequestValidation(query.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        return  docQcDetailsService.queryDocQcDetail(query);
    }

    /**
     * 验收提交
     * @param form 扫码结果
     * @return ~
     */
    @RequestMapping(params = "submit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> submit(PdaDocQcDetailForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(form.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        resultMap.put(Constant.RESULT, docQcDetailsService.submitDocQc(form));
        return resultMap;
    }

    /**
     * 修改已验收的验收说明
     * @param query notes、qcno、qclineo传参
     * @return ~
     */
    @RequestMapping(params = "editQcDesc", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editDesc(DocQcDetailsQuery query) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(query.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        resultMap.put(Constant.RESULT, docQcDetailsService.editQcDesc(query));
        return resultMap;
    }

    /**
     * 获取验收进度明细列表
     * @param qcno ~
     * @param pageNum ~
     * @return ~
     */
    @RequestMapping(params = "docQcList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryDocPaList(String qcno,@RequestParam(defaultValue = "1") int pageNum, String version) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(version);
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }

        if (StringUtil.isEmpty(qcno)) {
            resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "任务单号缺失"));
            return resultMap;
        }
        List<PdaDocQcDetailVO> detailVOList = docQcDetailsService.queryDocQcList(qcno, pageNum);
        resultMap.put(Constant.DATA, detailVOList);
        resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG));
        return resultMap;
    }
}
