package com.wms.service;

import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.mybatis.dao.GspProductRegisterSpecsMybatisDao;
import com.wms.mybatis.entity.pda.PdaGspProductRegister;
import com.wms.query.*;
import com.wms.utils.DateUtil;
import com.wms.utils.StringUtil;
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
    private BasCustomerService basCustomerService;
    @Autowired
    private BasSkuService basSkuService;
    @Autowired
    private GspProductRegisterService gspProductRegisterService;
    @Autowired
    private GspOperateDetailService gspOperateDetailService;
    @Autowired
    private GspMedicalRecordService gspMedicalRecordService;
    @Autowired
    private GspProductRegisterSpecsMybatisDao gspProductRegisterSpecsMybatisDao;


    /**
     * gsp申请经营范围校验
     * @param customerId 客户id
     * @param supplierId 供应商id
     * @param sku 产品(为""不校验产品)
     */
    public Json verifyOperate(String customerId,String supplierId,String sku){
        return this.verifyOperate(customerId,supplierId,sku,"","");
    }

    /**
     * gsp申请经营范围校验
     * @param customerId 客户id
     * @param supplierId 供应商id
     * @param sku 产品
     * @param lotatt01 生产日期
     * @param lotatt02 效期
     * @return
     */
    public Json verifyOperate(String customerId,String supplierId,String sku,String lotatt01,String lotatt02){

        if(StringUtils.isEmpty(customerId)){
            return Json.error("缺少货主信息，首营审核不通过");
        }

        if(StringUtils.isEmpty(supplierId)){
            return Json.error("缺少供应商信息，首营审核不通过");
        }

        GspEnterpriseInfo gspEnterpriseInfoCustomer;
        GspEnterpriseInfo gspEnterpriseInfoSupplier;
        BasCustomer customer = basCustomerService.selectCustomerById(customerId, Constant.CODE_CUS_TYP_OW);
        if(customer == null){
            //如果没有下发查询是否企业id
            gspEnterpriseInfoCustomer = gspEnterpriseInfoService.getGspEnterpriseInfo(customerId);
            if(gspEnterpriseInfoCustomer == null){
                //return Json.error("查询不到货主对应的企业信息："+customerId);
                return Json.error("查询不到对应的货主："+customerId);
            }
        }else {
            gspEnterpriseInfoCustomer = gspEnterpriseInfoService.getGspEnterpriseInfo(customer.getEnterpriseId());
        }

        BasCustomer supplier = basCustomerService.selectCustomerById(supplierId, Constant.CODE_CUS_TYP_VE);
        if(supplier == null){
            //如果没有下发查询是否企业id
            gspEnterpriseInfoSupplier = gspEnterpriseInfoService.getGspEnterpriseInfo(supplierId);
            if(gspEnterpriseInfoSupplier == null){
                //return Json.error("查询不到供应商对应的企业信息："+supplierId);
                return Json.error("查询不到对应的供应商："+supplierId);
            }
        }else {
            gspEnterpriseInfoSupplier = gspEnterpriseInfoService.getGspEnterpriseInfo(supplier.getEnterpriseId());
        }

        //如果是医疗机构不判断
        if(Constant.CODE_ENT_TYP_YL.equals(gspEnterpriseInfoCustomer.getEnterpriseType())){
            return Json.success("医疗机构不判断经营范围");
        }

        if(gspEnterpriseInfoCustomer.getEnterpriseType().equals(Constant.CODE_ENT_TYP_GW)
                || gspEnterpriseInfoCustomer.getEnterpriseType().equals(Constant.CODE_ENT_TYP_GWSC)
                || gspEnterpriseInfoSupplier.getEnterpriseType().equals(Constant.CODE_ENT_TYP_GW)
                || gspEnterpriseInfoSupplier.getEnterpriseType().equals(Constant.CODE_ENT_TYP_GWSC)

        ){
            return Json.success("国外企业没有营业执照信息");
        }

        //营业执照
        GspBusinessLicense gspBusinessLicenseCustomer = getGspBusinessLicense(gspEnterpriseInfoCustomer);
        if(gspBusinessLicenseCustomer == null){
            return Json.error("货主没有营业执照信息");
        }

        GspBusinessLicense gspBusinessLicenseSupplier = getGspBusinessLicense(gspEnterpriseInfoSupplier);
        if(gspBusinessLicenseSupplier== null){
            return Json.error("供应商没有营业执照信息");
        }

        //生产
        GspOperateLicense prodLicenseCustomer = getProdLicense(gspEnterpriseInfoCustomer);
        GspOperateLicense prodLicenseSupplier = getProdLicense(gspEnterpriseInfoSupplier);

        //经营
        GspOperateLicense operLicenseCustomer = getOperLicense(gspEnterpriseInfoCustomer);
        GspOperateLicense operLicenseSupplier = getOperLicense(gspEnterpriseInfoSupplier);

        //一类
        GspFirstRecord firstRecordCustomer = getFirstRecord(gspEnterpriseInfoCustomer);
        GspFirstRecord firstRecordSupplier = getFirstRecord(gspEnterpriseInfoSupplier);

        //二类
        GspSecondRecord secondRecordCustomer = getSecondRecord(gspEnterpriseInfoCustomer);
        GspSecondRecord secondRecordSupplier = getSecondRecord(gspEnterpriseInfoSupplier);

        //医疗
        GspMedicalRecord medicalRecordCustomer = getGspMedicalRecord(gspEnterpriseInfoCustomer);
        GspMedicalRecord medicalRecordSupplier = getGspMedicalRecord(gspEnterpriseInfoSupplier);

        List<GspOperateDetailVO> operateDetailVOSCustomer = new ArrayList<>();
        List<GspOperateDetailVO> operateDetailVOSSupplier = new ArrayList<>();

        //判断时间有没有过期
        if(!gspBusinessLicenseCustomer.getIsLong().equals("1")){
            if(checkDate(gspBusinessLicenseCustomer.getBusinessEndDate(),new Date())<0){
                return Json.error("货主营业执照过期:"+customerId);
            }
        }

        if(!gspBusinessLicenseSupplier.getIsLong().equals("1")){
            if(checkDate(gspBusinessLicenseSupplier.getBusinessEndDate(),new Date())<0){
                return Json.error("供应商营业执照过期:"+supplierId);
            }
        }

        if(prodLicenseCustomer!=null){
            if(checkDate(prodLicenseCustomer.getLicenseExpiryDate(),new Date())<0){
                return Json.error("货主生产许可证过期:"+customerId);
            }
            operateDetailVOSCustomer.addAll(getOperateDetail(prodLicenseCustomer.getOperateId()));
        }

        if(prodLicenseSupplier!=null){
            if(checkDate(prodLicenseSupplier.getLicenseExpiryDate(),new Date())<0){
                return Json.error("供应商生产许可证过期:"+supplierId);
            }
            operateDetailVOSSupplier.addAll(getOperateDetail(prodLicenseSupplier.getOperateId()));
        }

        if(operLicenseCustomer!=null){
            if(checkDate(operLicenseCustomer.getLicenseExpiryDate(),new Date())<0){
                return Json.error("货主经营许可证过期:"+customerId);
            }
            operateDetailVOSCustomer.addAll(getOperateDetail(operLicenseCustomer.getOperateId()));
        }

        if(operLicenseSupplier!=null){
            if(checkDate(operLicenseSupplier.getLicenseExpiryDate(),new Date())<0){
                return Json.error("供应商经营许可证过期:"+supplierId);
            }
            operateDetailVOSSupplier.addAll(getOperateDetail(operLicenseSupplier.getOperateId()));
        }

        if(firstRecordCustomer!=null){
            operateDetailVOSCustomer.addAll(getOperateDetail(firstRecordCustomer.getRecordId()));
        }

        if(firstRecordSupplier!=null){
            operateDetailVOSSupplier.addAll(getOperateDetail(firstRecordSupplier.getRecordId()));
        }

        if(secondRecordCustomer!=null){
            operateDetailVOSCustomer.addAll(getOperateDetail(secondRecordCustomer.getRecordId()));
        }

        if(secondRecordSupplier!=null){
            operateDetailVOSSupplier.addAll(getOperateDetail(secondRecordSupplier.getRecordId()));
        }

        if(medicalRecordCustomer!=null){
            if(checkDate(medicalRecordCustomer.getLicenseExpiryDateEnd(),new Date())<0){
                return Json.error("货主医疗机构执业许可证过期:"+customerId);
            }
        }

        if(medicalRecordSupplier!=null){
            if(checkDate(medicalRecordSupplier.getLicenseExpiryDateEnd(),new Date())<0){
                return Json.error("供应商医疗机构执业许可证过期:"+supplierId);
            }
        }

        //如果有产品需要判断注册证
        if(!StringUtils.isEmpty(sku)){
            String registerNo = "";
            BasSku basSku = basSkuService.getSkuInfo(customerId,sku);
            if(basSku == null){
                //如果为空查询是否是未下发产品
                GspProductRegisterSpecs specs = gspProductRegisterSpecsMybatisDao.queryById(sku);
                if(specs!=null){
                    if(!StringUtil.isEmpty(specs.getProductRegisterNo())){
                        GspProductRegister register = gspProductRegisterService.queryById(specs.getProductRegisterNo());
                        if(register != null){
                            registerNo = register.getProductRegisterNo();
                        }
                    }
                }else {
                    return Json.error("查询不到对应的产品："+sku);
                }
            }else {
                registerNo = basSku.getReservedfield03();
            }
            GspProductRegister gspProductRegister = getGspProductRegister(registerNo);
            if(gspProductRegister != null){
                if(checkDate(gspProductRegister.getProductRegisterExpiryDate(),new Date())<0){
                    return Json.error("关联产品注册证已过期："+sku);
                }

                if(!StringUtil.isEmpty(lotatt01)){
                    //生产日期需要在最老的注册证发证日期和最新的注册证过期时间之内
                    List<PdaGspProductRegister> allRegister = gspProductRegisterService.queryAllByRegisterNo(registerNo);
                    Date beginDate = null;
                    Date endDate = null;
                    if(allRegister!=null && allRegister.size()>1){
                        beginDate = allRegister.get(allRegister.size()-1).getApproveDate();
                        endDate = allRegister.get(0).getProductRegisterExpiryDate();
                    }else{
                        beginDate = allRegister.get(0).getApproveDate();
                        endDate = allRegister.get(0).getProductRegisterExpiryDate();
                    }
                    if(checkDate(lotatt01,beginDate)<0){
                        return Json.error("生产日期小于注册证批准日期："+sku);
                    }
                    if(checkDate(lotatt01,endDate)>0){
                        return Json.error("生产日期超出注册证失效日期："+sku);
                    }
                }

                if(!StringUtil.isEmpty(lotatt02)){
                    try{
                        //效期当天或者超期
                        if(checkDate(lotatt02,new Date())<0){
                            return Json.error("产品已超过效期："+sku);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        return Json.error("效期校验出错："+sku);
                    }

                }
            }
        }else{
             if(checkOperateIsRight(operateDetailVOSCustomer,operateDetailVOSSupplier)){
                 return Json.success("");
             }else {
                 return Json.error("货主与供应商经营范围没有交集");
             }
        }
        return Json.success("");
    }

    /**
     * 判断两个经营范围是否有交集
     * @param left
     * @param right
     * @return
     */
    private boolean checkOperateIsRight(List<GspOperateDetailVO> left,List<GspOperateDetailVO> right){
        List<String> arrregister = new ArrayList<>();
        List<String> arroperate = new ArrayList<>();

        for(GspOperateDetailVO v : left){
            arrregister.add(v.getOperateId());
        }
        for(GspOperateDetailVO v : right){
            arroperate.add(v.getOperateId());
        }
        for(String s : arrregister){
            if(arroperate.toString().indexOf(s)==-1){
                return false;
            }
        }
        return true;
    }

    private List<GspOperateDetailVO> getOperateDetail(String operateId){
        List<GspOperateDetailVO> detailVOS = gspOperateDetailService.queryOperateDetailByLicense(operateId);
        return detailVOS;
    }

    private GspBusinessLicense getGspBusinessLicense(GspEnterpriseInfo gspEnterpriseInfo){
        GspBusinessLicenseQuery businessLicenseQuery = new GspBusinessLicenseQuery();
        businessLicenseQuery.setEnterpriseId(gspEnterpriseInfo.getEnterpriseId());
        businessLicenseQuery.setIsUse(Constant.IS_USE_YES);
        GspBusinessLicense gspBusinessLicense = gspBusinessLicenseService.getGspBusinessLicenseBy(businessLicenseQuery);
        return gspBusinessLicense;
    }

    private GspOperateLicense getProdLicense(GspEnterpriseInfo gspEnterpriseInfo){
        GspOperateLicenseQuery prodQuery = new GspOperateLicenseQuery();
        prodQuery.setEnterpriseId(gspEnterpriseInfo.getEnterpriseId());
        prodQuery.setIsUse(Constant.IS_USE_YES);
        prodQuery.setOperateType(Constant.LICENSE_TYPE_PROD);
        GspOperateLicense gspProdLicense = gspOperateLicenseService.getGspOperateLicenseBy(prodQuery);
        return gspProdLicense;
    }

    private GspOperateLicense getOperLicense(GspEnterpriseInfo gspEnterpriseInfo){
        GspOperateLicenseQuery prodQuery = new GspOperateLicenseQuery();
        prodQuery.setEnterpriseId(gspEnterpriseInfo.getEnterpriseId());
        prodQuery.setIsUse(Constant.IS_USE_YES);
        prodQuery.setOperateType(Constant.LICENSE_TYPE_OPERATE);
        GspOperateLicense gspProdLicense = gspOperateLicenseService.getGspOperateLicenseBy(prodQuery);
        return gspProdLicense;
    }

    private GspFirstRecord getFirstRecord(GspEnterpriseInfo gspEnterpriseInfo){
        GspFirstRecordQuery firstRecordQuery = new GspFirstRecordQuery();
        firstRecordQuery.setEnterpriseId(gspEnterpriseInfo.getEnterpriseId());
        firstRecordQuery.setIsUse(Constant.IS_USE_YES);
        GspFirstRecord gspFirstRecord = gspFirstRecordService.getGspFirstRecordBy(firstRecordQuery);
        return gspFirstRecord;
    }

    private GspSecondRecord getSecondRecord(GspEnterpriseInfo gspEnterpriseInfo){
        GspSecondRecordQuery secondRecordQuery = new GspSecondRecordQuery();
        secondRecordQuery.setEnterpriseId(gspEnterpriseInfo.getEnterpriseId());
        secondRecordQuery.setIsUse(Constant.IS_USE_YES);
        GspSecondRecord gspSecondRecord = gspSecondRecordService.getGspSecondRecordBy(secondRecordQuery);
        return gspSecondRecord;
    }

    private GspMedicalRecord getGspMedicalRecord(GspEnterpriseInfo gspEnterpriseInfo){
        GspMedicalRecordQuery gspMedicalRecordQuery = new GspMedicalRecordQuery();
        gspMedicalRecordQuery.setEnterpriseId(gspEnterpriseInfo.getEnterpriseId());
        gspMedicalRecordQuery.setIsUse(Constant.IS_USE_YES);
        GspMedicalRecord medicalRecord = gspMedicalRecordService.getGspMedicalRecordBy(gspMedicalRecordQuery);
        return medicalRecord;
    }

    private GspProductRegister getGspProductRegister(String registerNo){
        GspProductRegister register = gspProductRegisterService.queryByRegisterNo(registerNo);
        return register;
    }

    private static int checkDate(String lotatt2,Date endDate){
        try{
            Date dateLotatt2 = DateUtil.parse(lotatt2,"yyyy-MM-dd");
            return dateLotatt2.compareTo(endDate);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }

    }

    private static int checkDate(Date startDate,Date endDate){
        return startDate.compareTo(endDate);
    }

    public static void main(String[] args){
        try {
            //Date date = DateUtil.parse("20190907","yyyy-MM-dd");
            System.out.println(checkDate("2019-09-08",new Date()));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}