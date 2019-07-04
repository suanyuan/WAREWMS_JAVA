package com.wms.api;

import com.wms.constant.Constant;
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
    public Map<String, Object> queryDocAsnHeader(String pano) {

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

        if (docPaDetailVO.getBasPackage() == null || docPaDetailVO.getBasSku() == null) {

            PdaResult result =
                    new PdaResult(PdaResult.CODE_FAILURE,
                            docPaDetailVO.getBasPackage() == null ?
                                    "无包装信息" :
                                    "无产品信息");
            resultMap.put(Constant.RESULT, result);
            return resultMap;
        }

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, docPaDetailVO);
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }


}
