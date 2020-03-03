package com.wms.service;

import com.wms.entity.GspProductRegister;
import com.wms.entity.GspProductRegisterSpecs;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class GspProductRegisterSpecsServiceTest {

    @Autowired
    private GspProductRegisterSpecsService gspProductRegisterSpecsService;
    @Test
    public void deleteGspProductRegisterSpecs() {
        String specsid = "123123";
        gspProductRegisterSpecsService.deleteGspProductRegisterSpecs("12312321");
    }



}