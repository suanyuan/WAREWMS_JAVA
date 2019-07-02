package com.wms.api;

import com.wms.constant.Constant;
import com.wms.entity.DocTransPutway;
import com.wms.result.PdaResult;
import com.wms.service.DocTransPutwayService;
import com.wms.vo.form.pda.PageForm;
import com.wms.vo.pda.PdaDocTransPutwayVO;
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
    private DocTransPutwayService docTransPutwayService;

    @RequestMapping(params = "undoneList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryUndoneList(@RequestParam(defaultValue = "1") int pageNum) {

        Map<String, Object> resultMap = new HashMap<>();
        List<PdaDocTransPutwayVO> pdaDocAsnHeaderVOList = docTransPutwayService.queryUndoneList(pageNum, Constant.pageSize);

        PdaResult result = new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG);
        resultMap.put(Constant.DATA, pdaDocAsnHeaderVOList);
        resultMap.put(Constant.RESULT, result);
        return resultMap;
    }
}
