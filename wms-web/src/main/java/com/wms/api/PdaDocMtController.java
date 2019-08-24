package com.wms.api;

import com.wms.constant.Constant;
import com.wms.query.DocMtDetailsQuery;
import com.wms.result.PdaResult;
import com.wms.service.DocMtDetailsService;
import com.wms.service.DocMtHeaderService;
import com.wms.utils.StringUtil;
import com.wms.vo.DocMtDetailsVO;
import com.wms.vo.DocMtHeaderVO;
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
@RequestMapping("mDocMt")
public class PdaDocMtController {

    @Autowired
    private DocMtHeaderService docMtHeaderService;

    @Autowired
    private DocMtDetailsService docMtDetailsService;

    /**
     * 获取未完成的养护任务单
     * @param form 页码
     * @return 任务单list
     */
    @RequestMapping(params = "undoneList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryUndoneList(PageForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        List<DocMtHeaderVO> pdaDocAsnHeaderVOList = docMtHeaderService.queryUndoneList(form);

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, pdaDocAsnHeaderVOList);
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }

    /**
     * 获取养护任务单header信息
     * @param mtno 收货任务单号
     * @return header信息
     */
    @RequestMapping(params = "docMtHeader", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryDocMtHeader(String mtno) {

        Map<String, Object> resultMap = new HashMap<>();
        DocMtHeaderVO docMtHeaderVO = docMtHeaderService.queryByMtno(mtno);

        PdaResult result;
        if (StringUtil.isEmpty(docMtHeaderVO.getMtno())) {
            result = new PdaResult(PdaResult.CODE_FAILURE, "查无养护任务单数据");
        }
        result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
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
        List<DocMtDetailsVO> docMtDetailsVOList = docMtDetailsService.queryMtDetail(query);

        if (docMtDetailsVOList.size() == 0) {
            resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "查无养护明细信息"));
            return resultMap;
        }

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, docMtDetailsVOList);
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }

//    /**
//     * 收货提交 单次收货 + 连续收货
//     * @param form 扫码结果
//     * @return ~
//     */
//    @RequestMapping(params = "submit", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> submit(PdaDocAsnDetailForm form) {
//
//        Map<String, Object> resultMap = new HashMap<>();
//        resultMap.put(Constant.RESULT, docAsnDetailService.receiveGoods(form));
//        return resultMap;
//    }
//
//    /**
//     * 结束收货
//     * @param form 收货任务单号
//     * @return ~
//     */
//    @RequestMapping(params = "endTask", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> endTask(PdaDocAsnEndForm form) {
//
//        Map<String, Object> resultMap = new HashMap<>();
//        resultMap.put(Constant.RESULT, docAsnHeaderService.endTask(form));
//        return resultMap;
//    }
}
