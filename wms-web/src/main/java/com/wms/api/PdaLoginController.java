package com.wms.api;

import com.wms.constant.Constant;
import com.wms.result.PdaResult;
import com.wms.service.BasCodesService;
import com.wms.service.UserSessionService;
import com.wms.vo.Json;
import com.wms.vo.form.pda.LoginForm;
import com.wms.vo.form.pda.WereHouseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/8/28
 */
@Controller
@RequestMapping("mLogin")
@SuppressWarnings("unchecked")
public class PdaLoginController {

    @Autowired
    private UserSessionService userSessionService;

    @Autowired
    private BasCodesService basCodesService;

    @RequestMapping(params = "login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(LoginForm form) {

        Json json = basCodesService.verifyRequestValidation(form.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }
        return userSessionService.login(form);
    }

    @RequestMapping(params = "queryWarehouse", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryWarehouse(WereHouseForm form) {

        Map<String, Object> resultMap = new HashMap<>();
        Json json = basCodesService.verifyRequestValidation(form.getVersion());
        if (!json.isSuccess()) {
            return (Map<String, Object>) json.getObj();
        }
        json = userSessionService.queryWereHouseByUser(form);
        if (!json.isSuccess()) {

            resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, json.getMsg()));
            return resultMap;
        }
        resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, json.getMsg()));
        resultMap.put(Constant.DATA, json.getObj());
        return resultMap;
    }
}