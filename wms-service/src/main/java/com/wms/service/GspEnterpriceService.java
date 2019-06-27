package com.wms.service;

import com.wms.utils.BeanUtils;
import com.wms.utils.RandomUtil;
import com.wms.vo.Json;
import com.wms.vo.form.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * 企业信息
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/6/25
 */
@Service
public class GspEnterpriceService extends BaseService {
    Logger logger = LoggerFactory.getLogger(GspEnterpriceService.class);

    //企业基础信息
    @Autowired
    private GspEnterpriseInfoService gspEnterpriseInfoService;
    //营业执照
    @Autowired
    private GspBusinessLicenseService gspBusinessLicenseService;
    //生产许可证
    @Autowired
    private GspOperateLicenseService gspOperateLicenseService;
    //备案
    @Autowired
    private GspSecondRecordService gspSecondRecordService;

    public Json addGspEnterprice(GspEnterpriceFrom gspEnterpriceFrom){
        try{
            GspEnterpriseInfoForm gspEnterpriseInfoForm = gspEnterpriceFrom.getGspEnterpriseInfoForm();
            GspBusinessLicenseForm gspBusinessLicenseForm = gspEnterpriceFrom.getGspBusinessLicenseForm();
            GspOperateLicenseForm gspOperateLicenseForm = gspEnterpriceFrom.getGspOperateLicenseForm();
            GspSecondRecordForm gspSecondRecordForm = gspEnterpriceFrom.getGspSecondRecordForm();
            String enterpriseId = RandomUtil.getUUID();
            gspEnterpriseInfoForm.setEnterpriseId(enterpriseId);
            if(BeanUtils.isEmptyFrom(gspEnterpriseInfoForm)){
                return Json.error("企业基础信息不能为空");
            }
            gspEnterpriseInfoService.addGspEnterpriseInfo(gspEnterpriseInfoForm);
            if(!BeanUtils.isEmptyFrom(gspBusinessLicenseForm)){
                gspBusinessLicenseForm.setEnterpriseId(enterpriseId);
                gspBusinessLicenseService.addGspBusinessLicense(gspBusinessLicenseForm);
            }
            if(!BeanUtils.isEmptyFrom(gspOperateLicenseForm)){
                gspOperateLicenseForm.setEnterpriseId(enterpriseId);
                gspOperateLicenseService.addGspOperateLicense(gspOperateLicenseForm);
            }
            if(!BeanUtils.isEmptyFrom(gspSecondRecordForm)){
                gspSecondRecordForm.setEnterpriseId(enterpriseId);
                gspSecondRecordService.addGspSecondRecord(gspSecondRecordForm);
            }
            Json.success("保存成功");
        }catch (Exception e){
            logger.info("企业信息新增失败"+e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Json.error("保存失败");
        }

        return Json.success("保存成功");
    }

    public Json deleteGspEnterprice(String enterprice){
        try{
            gspEnterpriseInfoService.getGspEnterpriseInfo(enterprice);
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

        }
        return Json.error("删除失败");
    }
}