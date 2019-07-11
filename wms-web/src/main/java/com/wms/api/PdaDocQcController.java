package com.wms.api;

import com.wms.constant.Constant;
import com.wms.mybatis.dao.DocQcDetailsMybatisDao;
import com.wms.mybatis.entity.pda.PdaDocQcEndForm;
import com.wms.query.pda.PdaDocQcDetailQuery;
import com.wms.result.PdaResult;
import com.wms.service.DocQcDetailsService;
import com.wms.service.DocQcHeaderService;
import com.wms.vo.form.pda.PageForm;
import com.wms.vo.pda.PdaDocQcDetailVO;
import com.wms.vo.pda.PdaDocQcHeaderVO;
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
@RequestMapping("mDocQc")
public class PdaDocQcController {

    @Autowired
    private DocQcHeaderService docQcHeaderService;

    @Autowired
    private DocQcDetailsService docQcDetailsService;

    /**
     * 获取未验收任务列表
     * @param form 分页
     * @return ~
     */
    @RequestMapping(params = "undoneList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryUndoneList(PageForm form) {

        Map<String, Object> resultMap = new HashMap<>();
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
    public Map<String, Object> queryDocQcHeader(String qcno) {

        Map<String, Object> resultMap = new HashMap<>();
        PdaDocQcHeaderVO pdaDocQcHeaderVO = docQcHeaderService.queryByQcno(qcno);

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, pdaDocQcHeaderVO);
        resultMap.put(Constant.RESULT, result);
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

        Map<String, Object> resultMap = new HashMap<>();
        List<PdaDocQcDetailVO> detailVOList = docQcDetailsService.queryDocQcDetail(query);

        resultMap.put(Constant.DATA, detailVOList);
        resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG));
        return resultMap;
    }

//    /**
//     * 上架提交 单次上架 + 连续上架
//     * @param form 扫码结果
//     * @return ~
//     */
//    @RequestMapping(params = "submit", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> submit(PdaDocPaDetailForm form) {
//
//        Map<String, Object> resultMap = new HashMap<>();
//        resultMap.put(Constant.RESULT, docPaDetailsService.putawayGoods(form));
//        return resultMap;
//    }

    /**
     * 结束验收
     * @param form 验收任务单号
     * @return ~
     */
    @RequestMapping(params = "endTask", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> endTask(PdaDocQcEndForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(Constant.RESULT, docQcHeaderService.endTask(form));
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
    public Map<String, Object> queryDocPaList(String qcno,@RequestParam(defaultValue = "1") int pageNum) {

        Map<String, Object> resultMap = new HashMap<>();
        if (qcno == null || qcno.length() == 0) {
            resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "任务单号缺失"));
            return resultMap;
        }
        List<PdaDocQcDetailVO> detailVOList = docQcDetailsService.queryDocQcList(qcno, pageNum);
        resultMap.put(Constant.DATA, detailVOList);
        resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG));
        return resultMap;
    }
}
