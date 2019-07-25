package com.wms.api;

import com.wms.constant.Constant;
import com.wms.query.DocOrderDetailQuery;
import com.wms.result.PdaResult;
import com.wms.service.DocOrderDetailService;
import com.wms.service.DocOrderHeaderService;
import com.wms.service.DocOrderPackingService;
import com.wms.vo.DocOrderHeaderVO;
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
public class PdaPackageController {

    @Autowired
    private DocOrderHeaderService docOrderHeaderService;

    @Autowired
    private DocOrderDetailService docOrderDetailService;

    @Autowired
    private DocOrderPackingService docOrderPackingService;

    /**
     * 获取出库任务列表
     * @param form 分页
     * @return ~
     */
    @RequestMapping(params = "undoneList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryUndoneList(PageForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        List<DocOrderHeaderVO> pdaDocQcHeaderVOS = docOrderHeaderService.getUndoneList(form);

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, pdaDocQcHeaderVOS);
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }

    /**
     * 获取出库任务单header信息
     * @param orderno 出库任务单号
     * @return header信息
     */
    @RequestMapping(params = "docOrderHeader", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryDocOrderHeader(String orderno) {

        Map<String, Object> resultMap = new HashMap<>();

        DocOrderHeaderVO docOrderHeaderVO = docOrderHeaderService.queryByOrderNo(orderno);
        if (docOrderHeaderVO == null) {

            resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "查无出库单头档数据"));
            return resultMap;
        }

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, docOrderHeaderVO);
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }

//    @RequestMapping(params = "docOrderDetail", method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String, Object> queryDocOrderDetail(DocOrderDetailQuery query) {
//
//    }
}
