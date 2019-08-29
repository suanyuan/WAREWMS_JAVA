package com.wms.api;

import com.wms.constant.Constant;
import com.wms.service.UserSessionService;
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
@RequestMapping("pdaLogin")
public class PdaLoginController {

    @Autowired
    private UserSessionService userSessionService;

    @RequestMapping(params = "login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(LoginForm form) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(Constant.RESULT, userSessionService.login(form));
        return resultMap;
    }

    @RequestMapping(params = "queryWarehouse", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryWarehouse(WereHouseForm form) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(Constant.RESULT, userSessionService.queryWereHouseByUser(form));
        return resultMap;
    }
}