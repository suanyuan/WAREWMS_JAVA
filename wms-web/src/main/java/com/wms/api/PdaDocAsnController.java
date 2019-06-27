package com.wms.api;

import com.wms.service.DocAsnDetailService;
import com.wms.service.DocAsnHeaderService;
import com.wms.utils.pda_utils.Constants;
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

    @RequestMapping(params = "undoneList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryUndoneList(@RequestParam(defaultValue = "1") int pageNum) {

        Map<String, Object> resultMap = new HashMap<>();
        List<PdaDocAsnHeaderVO> pdaDocAsnHeaderVOList = docAsnHeaderService.queryUndoneList(pageNum, 10);
        resultMap.put("datas", pdaDocAsnHeaderVOList);
        resultMap.put(Constants.SUCCESS, true);
        resultMap.put(Constants.MSG, "获取成功");
        return resultMap;
    }

//    public Map<String, Object> query
}
