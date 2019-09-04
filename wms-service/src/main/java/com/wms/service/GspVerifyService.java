package com.wms.service;

import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.query.*;
import com.wms.utils.DateUtil;
import com.wms.vo.GspOperateDetailVO;
import com.wms.vo.Json;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/9/1
 */
@Service("gspVerifyService")
public class GspVerifyService {

    @Autowired
    private GspEnterpriseInfoService gspEnterpriseInfoService;
    @Autowired
    private GspBusinessLicenseService gspBusinessLicenseService;
    @Autowired
    private GspOperateLicenseService gspOperateLicenseService;
    @Autowired
    private GspFirstRecordService gspFirstRecordService;
    @Autowired
    private GspSecondRecordService gspSecondRecordService;
    @Autowired
    private GspOperateDateTimeService gspOperateDateTimeService;
    @Autowired
    private BasCustomerService basCustomerService;
    @Autowired
    private BasSkuService basSkuService;
    @Autowired
    private GspProductRegisterService gspProductRegisterService;
    @Autowired
    private GspOperateDetailService gspOperateDetailService;
    @Autowired
    private GspMedicalRecordService gspMedicalRecordService;


    /**
     * gsp入库校验
     * @param customerId
     * @param sku
     * @return
     */
    public Json verifyOperate(String customerId,String sku,String lotatt2) throws Exception{
        return Json.success("");
        /*BasCustomer customer = basCustomerService.selectCustomerById(customerId, Constant.CODE_CUS_TYP_OW);
        if(customer == null){
            return Json.error("查询不到对应的货主："+customerId);
        }
        BasSku basSku = basSkuService.getSkuInfo(customerId,sku);
        if(basSku == null){
            return Json.error("查询不到对应的产品："+sku);
        }

        GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoService.getGspEnterpriseInfo(customer.getEnterpriseId());
        if(gspEnterpriseInfo == null){
            return Json.error("查询不到对应的企业信息："+customerId);
        }

        if(gspEnterpriseInfo.getEnterpriseType().equals(Constant.CODE_ENT_TYP_GW) || gspEnterpriseInfo.getEnterpriseType().equals(Constant.CODE_ENT_TYP_GWSC)){
            return Json.success("国外企业没有营业执照信息");
        }

        //效期超过当前时间
        if(checkDate(lotatt2,new Date()) == -1){
            return Json.error("该产品已超过效期");
        }

        //营业执照
        GspBusinessLicenseQuery businessLicenseQuery = new GspBusinessLicenseQuery();
        businessLicenseQuery.setEnterpriseId(gspEnterpriseInfo.getEnterpriseId());
        businessLicenseQuery.setIsUse(Constant.IS_USE_YES);
        GspBusinessLicense gspBusinessLicense = gspBusinessLicenseService.getGspBusinessLicenseBy(businessLicenseQuery);
        //判断证照期限
        if(gspBusinessLicense != null){
            List<GspOperteLicenseTime> businessOperateTime = gspOperateDateTimeService.queryGspOperateDateTime(gspBusinessLicense.getBusinessId(),"");
            if(businessOperateTime == null){
                return Json.error("没有查询到营业执照信息:"+customerId);
            }else {
                if(businessOperateTime.get(0).getRemainDay()!=null && Integer.parseInt(businessOperateTime.get(0).getRemainDay()) <0 ){
                    return Json.error("营业执照过期:"+customerId);
                }
            }
        }


        //生产
        GspOperateLicenseQuery prodQuery = new GspOperateLicenseQuery();
        prodQuery.setEnterpriseId(gspEnterpriseInfo.getEnterpriseId());
        prodQuery.setIsUse(Constant.IS_USE_YES);
        prodQuery.setOperateType(Constant.LICENSE_TYPE_PROD);
        GspOperateLicense gspProdLicense = gspOperateLicenseService.getGspOperateLicenseBy(prodQuery);
        if( gspProdLicense != null){
            //判断证照期限
            List<GspOperteLicenseTime> prodOperateTime = gspOperateDateTimeService.queryGspOperateDateTime(gspProdLicense.getOperateId(),"");
            if(prodOperateTime != null){
                if(Integer.parseInt(prodOperateTime.get(0).getRemainDay()) <0 ){
                    return Json.error("生产许可证过期:"+customerId);
                }else {
                    if(!StringUtils.isEmpty(lotatt2)){
                        if(checkDate(lotatt2,gspProdLicense.getLicenseExpiryDate())<0 || checkDate(lotatt2,gspProdLicense.getApproveDate())>0){
                            return Json.error("生产日期超过生产许可证有效期");
                        }
                    }
                }
            }
        }


        //经营
        GspOperateLicenseQuery operateLicenseQuery = new GspOperateLicenseQuery();
        operateLicenseQuery.setEnterpriseId(gspEnterpriseInfo.getEnterpriseId());
        operateLicenseQuery.setIsUse(Constant.IS_USE_YES);
        operateLicenseQuery.setOperateType(Constant.LICENSE_TYPE_OPERATE);
        GspOperateLicense gspOperateLicense = gspOperateLicenseService.getGspOperateLicenseBy(operateLicenseQuery);
        if(gspOperateLicense != null){
            //判断证照期限
            List<GspOperteLicenseTime> operOperateTime = gspOperateDateTimeService.queryGspOperateDateTime(gspOperateLicense.getOperateId(),"");
            if(operOperateTime != null){
                if(Integer.parseInt(operOperateTime.get(0).getRemainDay()) <0 ){
                    return Json.error("经营可证过期:"+customerId);
                }else {
                    if(!StringUtils.isEmpty(lotatt2)){
                        if(checkDate(lotatt2,gspOperateLicense.getLicenseExpiryDate())<0 || checkDate(lotatt2,gspOperateLicense.getApproveDate())>0){
                            return Json.error("经营许可证已过期");
                        }
                    }
                }
            }
        }

        //一类
        GspFirstRecordQuery firstRecordQuery = new GspFirstRecordQuery();
        firstRecordQuery.setEnterpriseId(gspEnterpriseInfo.getEnterpriseId());
        firstRecordQuery.setIsUse(Constant.IS_USE_YES);
        GspFirstRecord gspFirstRecord = gspFirstRecordService.getGspFirstRecordBy(firstRecordQuery);


        //二类
        GspSecondRecordQuery secondRecordQuery = new GspSecondRecordQuery();
        secondRecordQuery.setEnterpriseId(gspEnterpriseInfo.getEnterpriseId());
        secondRecordQuery.setIsUse(Constant.IS_USE_YES);
        GspSecondRecord gspSecondRecord = gspSecondRecordService.getGspSecondRecordBy(secondRecordQuery);

        //医疗
        GspMedicalRecordQuery gspMedicalRecordQuery = new GspMedicalRecordQuery();
        gspMedicalRecordQuery.setEnterpriseId(gspEnterpriseInfo.getEnterpriseId());
        gspMedicalRecordQuery.setIsUse(Constant.IS_USE_YES);
        GspMedicalRecord medicalRecord = gspMedicalRecordService.getGspMedicalRecordBy(gspMedicalRecordQuery);

        //判断生产日期和产品注册证日期
        GspProductRegister register = gspProductRegisterService.queryByRegisterNo(basSku.getReservedfield03());
        if(register!=null){
            if(!StringUtils.isEmpty(lotatt2)){
                //TODO 取最早的注册证批准日期
                if(checkDate(lotatt2,register.getProductRegisterExpiryDate())<0 || checkDate(lotatt2,register.getApproveDate())>0){
                    return Json.error("生产日期超过注册证有效期");
                }
            }
            //判断证照经营范围和sku器械目录
            List<GspOperateDetailVO> listAllDetails = new ArrayList<>();
            if(gspProdLicense!=null){
                List<GspOperateDetailVO> prodDetail = gspOperateDetailService.queryOperateDetailByLicense(gspProdLicense.getOperateId());
                listAllDetails.addAll(prodDetail);
            }
            if(gspOperateLicense!=null){
                List<GspOperateDetailVO> operDetail = gspOperateDetailService.queryOperateDetailByLicense(gspOperateLicense.getOperateId());
                listAllDetails.addAll(operDetail);
            }
            if(gspFirstRecord!=null){
                List<GspOperateDetailVO> firstDetail = gspOperateDetailService.queryOperateDetailByLicense(gspFirstRecord.getRecordId());
                listAllDetails.addAll(firstDetail);
            }
            if(gspSecondRecord!=null){
                List<GspOperateDetailVO> secondDetail = gspOperateDetailService.queryOperateDetailByLicense(gspSecondRecord.getRecordId());
                listAllDetails.addAll(secondDetail);
            }
            if(medicalRecord!=null){
                List<GspOperateDetailVO> medicalDetail = gspOperateDetailService.queryOperateDetailByLicense(medicalRecord.getMedicalId());
                listAllDetails.addAll(medicalDetail);
            }

            List<GspOperateDetailVO> registerOperate = gspOperateDetailService.queryOperateDetailByLicense(register.getProductRegisterId());
            List<String> arrregister = new ArrayList<>();
            List<String> arroperate = new ArrayList<>();

            for(GspOperateDetailVO v : registerOperate){
                arrregister.add(v.getOperateId());
            }
            for(GspOperateDetailVO v : listAllDetails){
                arroperate.add(v.getOperateId());
            }
            for(String s : arrregister){
                if(arroperate.toString().indexOf(s)==-1){
                    return Json.error("经营范围不匹配");
                }
            }
        }

        return Json.success("");*/
    }


    private static int checkDate(String lotatt2,Date endDate) throws Exception{
        Date dateLotatt2 = DateUtil.parse(lotatt2,"yyyy-MM-dd");
        return dateLotatt2.compareTo(endDate);
    }

    public static void main(String[] args){
        try {
            Date date = DateUtil.parse("2023-01-01","yyyy-MM-dd");
            System.out.println(checkDate("2019-01-01",new Date()));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}