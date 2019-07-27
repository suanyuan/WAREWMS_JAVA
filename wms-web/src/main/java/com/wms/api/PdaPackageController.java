package com.wms.api;

import com.wms.constant.Constant;
import com.wms.result.PdaResult;
import com.wms.service.DocOrderPackingService;
import com.wms.service.OrderHeaderForNormalService;
import com.wms.vo.OrderHeaderForNormalVO;
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
    private DocOrderPackingService docOrderPackingService;

    @Autowired
    private OrderHeaderForNormalService orderHeaderForNormalService;

    /**
     * TODO 等header改好了加个任务状态的条件，再加个排序
     * 获取包装复核任务列表、收货任务列表
     * @param form 分页
     * @return ~
     */
    @RequestMapping(params = "undoneList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryUndoneList(PageForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        List<OrderHeaderForNormalVO> orderHeaderForNormalVOS = orderHeaderForNormalService.getUndoneList(form);

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, orderHeaderForNormalVOS);
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

//        OrderHeaderForNormalVO headerVO = orderHeaderForNormalService.queryByOrderno(orderno);
//        if (headerVO == null || headerVO.getOrderNo() == null) {
//
//            resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "查无出库单头档数据"));
//            return resultMap;
//        }else if (headerVO.getOrderStatus())
//
//        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
//        resultMap.put(Constant.DATA, headerVO);
//        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }

//    @RequestMapping(params = "docOrderDetail", method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String, Object> queryDocOrderDetail(DocOrderDetailQuery query) {
//
//    }
}
