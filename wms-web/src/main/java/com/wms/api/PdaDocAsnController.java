package com.wms.api;

import com.wms.constant.Constant;
import com.wms.result.PdaResult;
import com.wms.service.DocAsnDetailService;
import com.wms.service.DocAsnHeaderService;
import com.wms.vo.pda.PdaDocAsnDetailVO;
import com.wms.vo.pda.PdaDocAsnHeaderVO;
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
@RequestMapping("mDocAsn")
public class PdaDocAsnController {

    @Autowired
    private DocAsnHeaderService docAsnHeaderService;

    @Autowired
    private DocAsnDetailService docAsnDetailService;

    /**
     * 获取未完成的收货任务单
     * @param pageNum 页码
     * @return 任务单list
     */
    @RequestMapping(params = "undoneList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryUndoneList(@RequestParam(defaultValue = "1") int pageNum) {

        Map<String, Object> resultMap = new HashMap<>();
        List<PdaDocAsnHeaderVO> pdaDocAsnHeaderVOList = docAsnHeaderService.queryUndoneList(pageNum, Constant.pageSize);

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, pdaDocAsnHeaderVOList);
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }

    /**
     * 获取收货任务单header信息
     * @param asnno 收货任务单号
     * @return header信息
     */
    @RequestMapping(params = "docAsnHeader", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryDocAsnHeader(String asnno) {

        Map<String, Object> resultMap = new HashMap<>();
        PdaDocAsnHeaderVO pdaDocAsnHeaderVO = docAsnHeaderService.queryById(asnno);

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, pdaDocAsnHeaderVO);
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }

    /**
     * 获取收货任务detail数据
     * @param asnno 收货任务单号
     * @param lotatt 批次属性效期 - 06
     * @return detail数据
     */
    @RequestMapping(params = "docAsnDetail", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryDetailByLotatt(String asnno, String lotatt) {

        Map<String, Object> resultMap = new HashMap<>();
        PdaDocAsnDetailVO pdaDocAsnDetailVO = docAsnDetailService.queryDetailByLotatt(asnno, lotatt);

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, pdaDocAsnDetailVO);
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }
}
