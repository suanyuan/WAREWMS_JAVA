package com.wms.api;

import com.wms.constant.Constant;
import com.wms.mybatis.entity.pda.PdaDocPaDetailForm;
import com.wms.mybatis.entity.pda.PdaDocPaEndForm;
import com.wms.query.pda.PdaDocPaDetailQuery;
import com.wms.result.PdaResult;
import com.wms.service.DocPaDetailsService;
import com.wms.service.DocPaHeaderService;
import com.wms.vo.form.pda.PageForm;
import com.wms.vo.pda.PdaDocPaDetailVO;
import com.wms.vo.pda.PdaDocPaHeaderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("mPutaway")
public class PdaPutawayController {

    @Autowired
    private DocPaHeaderService docPaHeaderService;

    @Autowired
    private DocPaDetailsService docPaDetailsService;

    /**
     * 获取未上架任务列表
     * @param form 分页
     * @return ~
     */
    @RequestMapping(params = "undoneList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryUndoneList(PageForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        List<PdaDocPaHeaderVO> pdaDocPaHeaderVOList = docPaHeaderService.queryUndoneList(form);

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, pdaDocPaHeaderVOList);
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }

    /**
     * 获取上架任务单header信息
     * @param pano 上架任务单号
     * @return header信息
     */
    @RequestMapping(params = "docPaHeader", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryDocPaHeader(String pano) {

        Map<String, Object> resultMap = new HashMap<>();
        PdaDocPaHeaderVO pdaDocAsnHeaderVO = docPaHeaderService.queryByPano(pano);

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, pdaDocAsnHeaderVO);
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }

    /**
     * 获取上架任务detail数据
     * @param query 查询条件
     * @return detail数据
     */
    @RequestMapping(params = "docPaDetail", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryDocPaDetail(PdaDocPaDetailQuery query) {

        Map<String, Object> resultMap = new HashMap<>();
        PdaDocPaDetailVO docPaDetailVO = docPaDetailsService.queryDocPaDetail(query);

        if (docPaDetailVO == null || docPaDetailVO.getPano() == null) {
            PdaResult result =
                    new PdaResult(PdaResult.CODE_FAILURE, "无任务详情信息");
            resultMap.put(Constant.RESULT, result);
            return resultMap;
        }

        if (docPaDetailVO.getBasSku() == null
                || docPaDetailVO.getInvLotAtt() == null) {

            PdaResult result =
                    new PdaResult(PdaResult.CODE_FAILURE,
                            docPaDetailVO.getBasSku() == null ?
                                    "无产品信息" :
                                    "无批次信息");
            resultMap.put(Constant.RESULT, result);
            return resultMap;
        }

        resultMap.put(Constant.DATA, docPaDetailVO);
        resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG));
        return resultMap;
    }

    /**
     * 上架提交 单次上架 + 连续上架
     * @param form 扫码结果
     * @return ~
     */
    @RequestMapping(params = "submit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> submit(PdaDocPaDetailForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(Constant.RESULT, docPaDetailsService.putawayGoods(form));
        return resultMap;
    }

    /**
     * 结束上架
     * @param form 上架任务单号
     * @return ~
     */
    @RequestMapping(params = "endTask", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> endTask(PdaDocPaEndForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(Constant.RESULT, docPaHeaderService.endTask(form));
        return resultMap;
    }

    /**
     * 结束上架可行性校验
     * @param form 上架任务单号
     * @return ~
     */
    @RequestMapping(params = "queryEndAvailable", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> endAvailableQuery(PdaDocPaEndForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG));
        resultMap.put(Constant.DATA, docPaHeaderService.endAvailableQuery(form));
        return resultMap;
    }

    /**
     * 获取上架进度明细列表
     * @param pano ~
     * @return ~
     */
    @RequestMapping(params = "docPaList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryDocPaList(String pano) {

        Map<String, Object> resultMap = new HashMap<>();
        if (pano == null || pano.length() == 0) {
            resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "订单号缺失"));
            return resultMap;
        }
        List<PdaDocPaDetailVO> detailVOList = docPaDetailsService.queryDocPaList(pano);
        resultMap.put(Constant.DATA, detailVOList);
        resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG));
        return resultMap;
    }
}
