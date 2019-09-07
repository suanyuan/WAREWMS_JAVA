package com.wms.api;

import com.wms.constant.Constant;
import com.wms.query.pda.PdaInventoryQuery;
import com.wms.result.PdaResult;
import com.wms.service.InvLotLocIdService;
import com.wms.service.ViewInvLotattService;
import com.wms.vo.Json;
import com.wms.vo.form.ViewInvLotattForm;
import com.wms.vo.form.pda.PageForm;
import com.wms.vo.form.pda.PdaInventoryMoveForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("mInvLotAtt")
public class PdaInvLotAttController {

    @Autowired
    private ViewInvLotattService viewInvLotattService;

    @Autowired
    private InvLotLocIdService invLotLocIdService;

    /**
     * 通过库位和条码信息获取产品信息
     * @param query 条码信息 + locationid
     * @return ~
     */
    @RequestMapping(params = "queryInventorys", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryInventorys(PdaInventoryQuery query) {

        Map<String, Object> map = new HashMap<>();

        Json json = invLotLocIdService.queryInventorys(query);
        if (!json.isSuccess()) {

            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, json.getMsg()));
            return map;
        }

        map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, json.getMsg()));
        map.put(Constant.DATA, json.getObj());
        return map;
    }

    @RequestMapping(params = "invMoveSubmit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> invMoveSubmit(PdaInventoryMoveForm form) {

        Map<String, Object> map = new HashMap<>();

        ViewInvLotattForm invLotattForm = new ViewInvLotattForm();
        invLotattForm.setWarehouseid(form.getWarehouseid());
        invLotattForm.setEditwho(form.getEditwho());
        invLotattForm.setFmcustomerid(form.getCustomerid());
        invLotattForm.setFmsku(form.getSku());
        invLotattForm.setFmlotnum(form.getLotnum());
        invLotattForm.setFmlocation(form.getFmlocationid());
        invLotattForm.setFmqty(BigDecimal.valueOf(Integer.valueOf(form.getFmqty())));
        invLotattForm.setLotatt11text(form.getTolocationid());
        invLotattForm.setLotatt11(form.getToqty());
        invLotattForm.setLotatt12(form.getReasoncode());
        invLotattForm.setLotatt12text(form.getReasontext());

        Json json = viewInvLotattService.movViewInvLotatt(invLotattForm);
        if (!json.isSuccess()) {

            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, json.getMsg()));
            return map;
        }

        map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, json.getMsg()));
        return map;
    }

    /**
     * PDA扫描产品条码获取库存数据（GS1条码、SKU、自赋码、GTIN码）
     * @param query ~
     * @return ~
     */
    @RequestMapping(params = "queryInventoryForScan", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryInventoryForScan(PdaInventoryQuery query, PageForm pageForm) {

        Map<String, Object> map = new HashMap<>();

        Json json = invLotLocIdService.queryInventoryForScan(query, pageForm);
        if (!json.isSuccess()) {

            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, json.getMsg()));
            return map;
        }

        map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, json.getMsg()));
        map.put(Constant.DATA, json.getObj());
        return map;
    }

    /**
     * PDA扫描库位获取库位上产品的库存数据
     * @param query locationid
     * @param pageForm ~
     * @return ~
     */
    @RequestMapping(params = "queryInventoryForLocation", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryInventoryForLocation(PdaInventoryQuery query, PageForm pageForm) {

        Map<String, Object> map = new HashMap<>();

        Json json = invLotLocIdService.queryInventoryForLocation(query, pageForm);
        if (!json.isSuccess()) {

            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, json.getMsg()));
            return map;
        }

        map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, json.getMsg()));
        map.put(Constant.DATA, json.getObj());
        return map;
    }
}
