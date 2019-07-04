package com.wms.api;

import com.wms.constant.Constant;
import com.wms.mybatis.entity.pda.PdaDocAsnDetailForm;
import com.wms.mybatis.entity.pda.PdaDocAsnEndForm;
import com.wms.query.pda.PdaDocAsnDetailQuery;
import com.wms.result.PdaResult;
import com.wms.service.DocAsnDetailService;
import com.wms.service.DocAsnHeaderService;
import com.wms.vo.form.pda.PageForm;
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
     * @param form 页码
     * @return 任务单list
     */
    @RequestMapping(params = "undoneList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryUndoneList(PageForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        List<PdaDocAsnHeaderVO> pdaDocAsnHeaderVOList = docAsnHeaderService.queryUndoneList(form);

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
        PdaDocAsnHeaderVO pdaDocAsnHeaderVO = docAsnHeaderService.queryByAsnno(asnno);

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, pdaDocAsnHeaderVO);
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }

    /**
     * 获取收货任务detail数据
     * @param query 查询条件
     * @return detail数据
     */
    @RequestMapping(params = "docAsnDetail", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryDocAsnDetail(PdaDocAsnDetailQuery query) {

        Map<String, Object> resultMap = new HashMap<>();
        PdaDocAsnDetailVO pdaDocAsnDetailVO = docAsnDetailService.queryDocAsnDetail(query);

        if (pdaDocAsnDetailVO.getBasPackage() == null || pdaDocAsnDetailVO.getBasSku() == null) {

            PdaResult result =
                    new PdaResult(PdaResult.CODE_FAILURE,
                            pdaDocAsnDetailVO.getBasPackage() == null ?
                                    "无包装信息" :
                                    "无产品信息");
            resultMap.put(Constant.RESULT, result);
            return resultMap;
        }

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, pdaDocAsnDetailVO);
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }

    /**
     * 收货提交 单次收货 + 连续收货
     * @param form 扫码结果
     * @return ~
     */
    @RequestMapping(params = "submit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> submit(PdaDocAsnDetailForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(Constant.RESULT, docAsnDetailService.receiveGoods(form));
        return resultMap;
    }

    /**
     * 结束收货
     * @param form 收货任务单号
     * @return ~
     */
    @RequestMapping(params = "endTask", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> endTask(PdaDocAsnEndForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(Constant.RESULT, docAsnHeaderService.endTask(form));
        return resultMap;
    }
}
