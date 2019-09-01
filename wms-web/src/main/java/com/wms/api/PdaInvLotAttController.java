package com.wms.api;

import com.wms.query.pda.PdaInventoryQuery;
import com.wms.vo.form.ViewInvLotattForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("mInvLotAtt")
public class PdaInvLotAttController {

    /**
     * 通过库位和条码信息获取产品信息
     * @param query 条码信息 + locationid
     * @return ~
     */
    @RequestMapping(value = "queryInventory", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryInventory(PdaInventoryQuery query) {

        return null;
    }

    @RequestMapping(value = "movViewInvLotatt", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> movViewInvLotatt(ViewInvLotattForm form) {

        return null;
    }
}
