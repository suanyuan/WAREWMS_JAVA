package com.wms.service;

import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.mybatis.dao.GspProductRegisterMybatisDao;
import com.wms.mybatis.dao.GspProductRegisterSpecsMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.entity.pda.PdaGspProductRegister;
import com.wms.query.*;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.DateUtil;
import com.wms.utils.StringUtil;
import com.wms.vo.GspOperateDetailVO;
import com.wms.vo.Json;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    @Autowired
    private GspInstrumentCatalogService gspInstrumentCatalogService;
    @Autowired
    private GspProductRegisterMybatisDao gspProductRegisterMybatisDao;

    /**
     * 验证上架提交日期有效性
     */
    public Json verifyPaDateValidation(String lotatt01, String lotatt02) {
        return verifyGspDateValidation(lotatt01, lotatt02, "");
    }

    /**
     * 验证验收提交日期有效性
     */
    public Json verifyQcDateValidation(String lotatt01, String lotatt02, String lotatt06) {
        return verifyGspDateValidation(lotatt01, lotatt02, lotatt06);
    }

    /**
     * gsp申请经营范围校验
     * @param customerId 客户id（未申请的传enterpriseId）
     * @param supplierId 供应商id（未申请的传enterpriseId）
     * @param sku 产品(为""不校验产品)（未申请的传specsId）
     */
    public Json verifyOperate(String customerId,String supplierId,String sku){
        return this.verifyOperate(customerId,supplierId,sku,"","");
        //return Json.success("");
    }

    /**
     * gsp申请经营范围校验
     * @param customerId 客户id
     * @param supplierId 供应商id
     * @param sku 产品
     * @param lotatt01 生产日期
     * @param lotatt02 效期
     */
    public Json verifyOperate(String customerId,String supplierId,String sku,String lotatt01,String lotatt02){
        return this.verifyOperate(customerId, supplierId, sku, lotatt01, lotatt02, "");
        //return Json.success("");
    }

    /**
     * 验证产品入库的时间校验 上架 && 验收
     * @param lotatt01 生产日期
     * @param lotatt02 效期
     * @param lotatt06 注册证号
     */
    private Json verifyGspDateValidation(String lotatt01, String lotatt02, String lotatt06) {

        if (StringUtil.isEmpty(lotatt01)) {
            return Json.error("请选择生产日期");
        }else if (StringUtil.isEmpty(lotatt02   )) {
            return Json.error("请选择有效期/失效期");
        }

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date prdDate = format.parse(lotatt01);
            Date expiryDate = format.parse(lotatt02);
            if (prdDate.getTime() >= expiryDate.getTime()) {
                return Json.error("有效期/失效期不可小于生产日期");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Json.error("日期转换出错");
        }

        if (!StringUtil.isEmpty(lotatt06)) return Json.success("无注册证传入");

        // ↑↑↑↑↑上架↑↑↑↑↑

        // ↓↓↓↓↓验收↓↓↓↓↓

        //GSP-注册证：lotatt01 from 注册证.批准日期 to 注册证.有效期
        //通过注册证号 查询注册证，可能存在多个注册证 create_date asc
        List<PdaGspProductRegister> allRegister = gspProductRegisterMybatisDao.queryAllByNo(lotatt06);
        if (allRegister == null || allRegister.size() == 0) return Json.error("验证不通过，查无此证号[" + lotatt06 + "]的注册证数据，请联系质量部进行排查");

        Date beginDate;
        Date endDate;
        beginDate = allRegister.get(0).getApproveDate();
        endDate = allRegister.get(allRegister.size() - 1).getProductRegisterExpiryDate();
        if(checkDate(lotatt01,beginDate)<0){
            return Json.error("生产日期小于注册证批准日期");
        }
        if(checkDate(lotatt01,endDate)>0){
            return Json.error("生产日期超出注册证失效日期");
        }
        return Json.success("验证通过");
    }

    /**
     * gsp申请经营范围校验
     * @param customerId 客户id
     * @param supplierId 供应商id
     * @param sku 产品
     * @param lotatt01 生产日期
     * @param lotatt02 效期
     * @param lotatt06 注册证号
     */
    public Json verifyOperate(String customerId,String supplierId,String sku,String lotatt01,String lotatt02, String lotatt06){

        Json json = new Json();

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
                return Json.error("查询不到有效的货主");
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
                return Json.error("查询不到有效的供应商");
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
            //return Json.success("国外企业没有营业执照信息");
        }else{

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
                return Json.error("货主营业执照过期");
            }
        }

        if(!gspBusinessLicenseSupplier.getIsLong().equals("1")){
            if(checkDate(gspBusinessLicenseSupplier.getBusinessEndDate(),new Date())<0){
                return Json.error("供应商营业执照过期");
            }
        }

        if(prodLicenseCustomer!=null){
            if(checkDate(prodLicenseCustomer.getLicenseExpiryDate(),new Date())<0){
                return Json.error("货主生产许可证过期");
            }
            operateDetailVOSCustomer.addAll(getOperateDetail(prodLicenseCustomer.getOperateId()));
        }

        if(prodLicenseSupplier!=null){
            if(checkDate(prodLicenseSupplier.getLicenseExpiryDate(),new Date())<0){
                return Json.error("供应商生产许可证过期");
            }
            operateDetailVOSSupplier.addAll(getOperateDetail(prodLicenseSupplier.getOperateId()));
        }

        if(operLicenseCustomer!=null){
            if(checkDate(operLicenseCustomer.getLicenseExpiryDate(),new Date())<0){
                return Json.error("货主经营许可证过期");
            }
            operateDetailVOSCustomer.addAll(getOperateDetail(operLicenseCustomer.getOperateId()));
        }

        if(operLicenseSupplier!=null){
            if(checkDate(operLicenseSupplier.getLicenseExpiryDate(),new Date())<0){
                return Json.error("供应商经营许可证过期");
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
                return Json.error("货主医疗机构执业许可证过期");
            }
        }

        if(medicalRecordSupplier!=null){
            if(checkDate(medicalRecordSupplier.getLicenseExpiryDateEnd(),new Date())<0){
                return Json.error("供应商医疗机构执业许可证过期");
            }
        }

        //如果有产品需要判断注册证
        List<PdaGspProductRegister> allRegister = new ArrayList<>();
        if(!StringUtils.isEmpty(sku)){
            String registerNo = "";
            if (StringUtil.isNotEmpty(lotatt06)) {

                registerNo = lotatt06;
            }else {
                BasSku basSku = basSkuService.getSkuInfo(customerId,sku);
                if(basSku == null){
                    //如果为空查询是否是未下发产品
                    GspProductRegisterSpecs specs = gspProductRegisterSpecsMybatisDao.queryById(sku);
                    if(specs!=null){
                        if(!StringUtil.isEmpty(specs.getProductRegisterId())){
                            GspProductRegister register = gspProductRegisterService.queryById(specs.getProductRegisterId());
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
            }

            GspProductRegister gspProductRegister;
            List<PdaGspProductRegister> pdaGspProductRegisterList = gspProductRegisterMybatisDao.queryAllByNo(registerNo);
            if(pdaGspProductRegisterList != null && pdaGspProductRegisterList.size() > 0){

                gspProductRegister = pdaGspProductRegisterList.get(0);
//                if(checkDate(gspProductRegister.getProductRegisterExpiryDate(),new Date())<0){
//                    return Json.error("关联产品注册证已过期："+sku);
//                }

                if(!StringUtil.isEmpty(lotatt01)){
                    //生产日期需要在最老的注册证发证日期和最新的注册证过期时间之内
//                    List<PdaGspProductRegister> allRegister = gspProductRegisterService.queryAllByRegisterNo(registerNo);
                    MybatisCriteria mybatisCriteria = new MybatisCriteria();
                    GspProductRegisterQuery historyQuery = new GspProductRegisterQuery();
                    historyQuery.setVersion(gspProductRegister.getVersion());
                    mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(historyQuery));
                    mybatisCriteria.setOrderByClause("create_date desc");
                    allRegister = gspProductRegisterMybatisDao.queryByList(mybatisCriteria);
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

                //获取注册证器械目录
                List<GspOperateDetailVO> operateDetailList = getOperateDetail(gspProductRegister.getProductRegisterId());
                if(operateDetailList!=null && operateDetailList.size()>0){
                    GspInstrumentCatalog catalog = gspInstrumentCatalogService.getGspInstrumentCatalog(operateDetailList.get(0).getOperateId());

                    //产品与货主供应商匹配
                    List<GspOperateDetailVO> productVo = new ArrayList<>();
                    productVo.add(operateDetailList.get(0));

                    boolean customerFlag = checkOperateIsRight(operateDetailVOSCustomer,operateDetailVOSSupplier);
                    if(customerFlag == false){
                        return Json.error("货主与供应商经营范围没有交集");
                    }

                    if(catalog!=null){
                        if(catalog.getVersion().equals(Constant.CODE_CATALOG_CLASSIFY_ONE)){
                            json = Json.success("一类不需要匹配经营范围");
                            json.setObj(allRegister);
                            return json;
                        }
                    }

                    boolean skuFlag = checkOperateIsRight(operateDetailVOSCustomer,productVo);
                    if(skuFlag == false){
                        return Json.error("货主与产品经营范围不匹配");
                    }
                    if(!checkOperateIsRight(operateDetailVOSSupplier,productVo)){
                        return Json.error("供应商与产品经营范围不匹配");
                    }
                }else {
                    return Json.error("产品注册证没有关联器械目录");
                }

                json = Json.success("");
                json.setObj(allRegister);
                return json;
            }else {
                json = Json.success("产品没有注册证号");
                json.setObj(allRegister);
                return json;
            }
        }else{
             if(checkOperateIsRight(operateDetailVOSCustomer,operateDetailVOSSupplier)){
                 json = Json.success("");
                 json.setObj(allRegister);
                 return json;
             }else {
                 return Json.error("货主与供应商经营范围没有交集");
             }
        }
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
            if(arroperate.toString().indexOf(s)!=-1){
                return true;
            }
        }
        return false;
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
            //System.out.println(checkDate("2019-09-08",new Date()));
//            List<String> arr = new ArrayList<>();
//            arr.add("1");
//            arr.add("2");

//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            Date start = dateFormat.parse("2019-08-11");
//
//            System.out.println(betweenOn("2019-08-11", start, "2006-09-11"));


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}