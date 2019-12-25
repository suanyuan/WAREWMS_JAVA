package com.wms.service;

import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.query.BasCustomerQuery;
import com.wms.query.BasPackageQuery;
import com.wms.query.BasSkuQuery;
import com.wms.result.FirstBusinessApplyResult;
import com.wms.utils.BeanUtils;
import com.wms.utils.DateUtil;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.*;
import com.wms.vo.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Client;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/7/16
 */
@Service("dataPublishService")
public class DataPublishService extends BaseService {
    @Autowired
    private BasSkuService basSkuService;
    @Autowired
    private BasCustomerService basCustomerService;
    @Autowired
    private GspCustomerService gspCustomerService;
    @Autowired
    private GspSupplierService gspSupplierService;
    @Autowired
    private GspProductRegisterSpecsService gspProductRegisterSpecsService;
    @Autowired
    private GspProductRegisterService gspProductRegisterService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private FirstBusinessApplyService firstBusinessApplyService;
    @Autowired
    private FirstReviewLogService firstReviewLogService;
    @Autowired
    private FirstBusinessProductApplyService firstBusinessProductApplyService;
    @Autowired
    private GspEnterpriseInfoService gspEnterpriseInfoService;
    @Autowired
    private GspReceivingService gspReceivingService;
    @Autowired
    private BasPackageService basPackageService;
    @Autowired
    private GspProductRegisterSpecsMybatisDao gspProductRegisterSpecsMybatisDao;
    @Autowired
    private BasSkuHistoryMybatisDao basSkuHistoryMybatisDao;
    @Autowired
    private BasSkuMybatisDao basSkuMybatisDao;
    @Autowired
    private GspProductRegisterMybatisDao gspProductRegisterMybatisDao;
    @Autowired
    private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;
    @Autowired
    private BasCustomerMybatisDao basCustomerMybatisDao;
    @Autowired
    private FirstBusinessApplyMybatisDao firstBusinessApplyMybatisDao;
//    @Autowired
//    private ProductRegisterRelationMybatisDao productRegisterRelationMybatisDao;
    /**
     * 下发数据
     * @param no
     * @return
     * @throws Exception
     */
    public Json publishData(String no) throws Exception{
        //TODO bascustomer下发有问题需要修改
        //下发委托客户数据
        if(no.indexOf(Constant.APLCUSNO)!=-1){
            Json json = gspCustomerService.getGspCustomerById(no);
            if(!json.isSuccess()){
                return Json.error("查询不到对应的委托客户申请");
            }
            GspCustomerVO customer = (GspCustomerVO)json.getObj();
            GspCustomerForm gspCustomerForm = new GspCustomerForm();
            gspCustomerForm.setClientId(no);
            gspCustomerForm.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_PASS);
            gspCustomerService.editGspCustomer(gspCustomerForm);
            GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoService.getGspEnterpriseInfo(customer.getEnterpriseId());

            BasCustomerForm form = new BasCustomerForm();
            form.setCustomerid(gspEnterpriseInfo.getEnterpriseNo());
            form.setDescrC(gspEnterpriseInfo.getEnterpriseName());
            form.setCustomerType(Constant.CODE_CUS_TYP_OW);
            form.setEnterpriseId(customer.getEnterpriseId());
            form.setOperateType(customer.getOperateType());
//            form.setContractNo(customer.getContractNo());
            form.setContractUrl(customer.getContractUrl());
            form.setClientContent(customer.getClientContent());
            form.setClientStartDate(customer.getClientStartDate());
            form.setClientEndDate(customer.getClientEndDate());
            form.setClientTerm(customer.getClientTerm());
            form.setIsChineseLabel(customer.getIsChineseLabel());
            form.setNotes(customer.getRemark());//备注
            //form.setContractNo();
            form.setSupContractNo(customer.getContractNo());
            form.setContractUrl(customer.getContractUrl());
            form.setClientContent(customer.getClientContent());
            form.setClientStartDate(customer.getClientStartDate());
            form.setClientEndDate(customer.getClientEndDate());
//            form.setBillclass(customer.getFirstState());//用作首营状态

//            form.setClientTerm(Long.parseLong(customer.getClientTerm()));

            form.setActiveFlag(Constant.IS_USE_YES);
            form.setBankaccount(no);
            form.setBillclass(gspCustomerForm.getFirstState());

            String flag = "Client";

            return basCustomerService.clientAddCustomer(form,flag);
        }else if(no.indexOf(Constant.APLSUPNO)!=-1){
            Json json = gspSupplierService.getGspSupplierInfo(no);
            if(!json.isSuccess()){
                return Json.error("查询不到对应的供应商申请");
            }

            GspSupplierVO supplier = (GspSupplierVO)json.getObj();
            GspSupplierForm supplierForm = new GspSupplierForm();
            supplierForm.setSupplierId(no);
            supplierForm.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_PASS);
            gspSupplierService.editGspSupplier(supplierForm);

            /*BasCustomerForm form = new BasCustomerForm();
            form.setEnterpriseId(supplier.getEnterpriseId());
            form.setOperateType(supplier.getOperateType());
            form.setActiveFlag(Constant.IS_USE_YES);*/

            GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoService.getGspEnterpriseInfo(supplier.getEnterpriseId());

            BasCustomerForm form = new BasCustomerForm();
            BasCustomer basCustomer1 = new BasCustomer();
            basCustomer1.setEnterpriseId(supplier.getEnterpriseId());
            basCustomer1.setCustomerType(Constant.CODE_CUS_TYP_VE);
            BasCustomer basCustomer =  basCustomerMybatisDao.queryByenterId(basCustomer1);
            if(basCustomer!=null){
                //供应商重新下发   供应商代码不变
                form.setCustomerid(basCustomer.getCustomerid());
            }else{
                //第一次下发   供应商代码自增
                form.setCustomerid(commonService.generateSeq(Constant.BASSUPNO, SfcUserLoginUtil.getLoginUser().getWarehouse().getId()));
            }
            form.setDescrC(gspEnterpriseInfo.getEnterpriseName());
            form.setCustomerType(Constant.CODE_CUS_TYP_VE);
            form.setEnterpriseId(supplier.getEnterpriseId());
            form.setOperateType(supplier.getOperateType());
            form.setSupContractNo(supplier.getContractNo());
            form.setContractUrl(supplier.getContractUrl());
            form.setClientContent(supplier.getClientContent());
            form.setClientStartDate(DateUtil.parse(supplier.getClientStartDate(),"yyyy-MM-dd"));
            form.setClientEndDate(DateUtil.parse(supplier.getClientEndDate(),"yyyy-MM-dd"));
//            Long clientTerm = 0l;
//            if(null == supplier.getClientTerm() || "".equals(supplier.getClientTerm())){
//                clientTerm = Long.valueOf(supplier.getClientTerm());
//            }
            form.setClientTerm(supplier.getClientTerm());
            form.setActiveFlag(Constant.IS_USE_YES);
            form.setBankaccount(no);
            form.setBillclass(supplier.getFirstState());

            String flag = "Supplier";

            return basCustomerService.clientAddCustomer(form,flag);
        }else if(no.indexOf(Constant.APLRECNO)!=-1){
			GspReceiving gspReceiving = gspReceivingService.getGspReceiving(no);

            GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoService.getGspEnterpriseInfo(gspReceiving.getEnterpriseId());


            BasCustomerForm form = new BasCustomerForm();
            form.setCustomerid(commonService.generateSeq(Constant.BASRECNO, SfcUserLoginUtil.getLoginUser().getWarehouse().getId()));
            form.setDescrC(gspEnterpriseInfo.getEnterpriseName());
            form.setCustomerType(Constant.CODE_CUS_TYP_CO);
            form.setEnterpriseId(gspReceiving.getEnterpriseId());
            form.setBankaccount(no);
            form.setBillclass(gspReceiving.getFirstState());

            form.setActiveFlag(Constant.IS_USE_YES);


            String flag = "Receving";
            return basCustomerService.clientAddCustomer(form,flag);
        }else if(no.indexOf(Constant.APLPRONO)!=-1){
            Json json = firstBusinessApplyService.queryFirstBusinessApply(no);
            if(!json.isSuccess()){
                return Json.error("查询不到对应的产品申请");
            }
            FirstBusinessApplyVO firstBusinessApply = (FirstBusinessApplyVO)json.getObj();

            FirstBusinessApplyForm form = new FirstBusinessApplyForm();
            form.setApplyId(firstBusinessApply.getApplyId());
            form.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_PASS);
            firstBusinessApplyService.editFirstBusinessApply(form);

            Json productJson = firstBusinessProductApplyService.getListByApplyId(firstBusinessApply.getApplyId());
            if(!productJson.isSuccess()){
                return productJson;
            }
            List<FirstBusinessProductApply> productApplyList = (List<FirstBusinessProductApply>)productJson.getObj();
            for(FirstBusinessProductApply f:productApplyList){
                BasSkuForm skuForm = new BasSkuForm();
                Json spec = gspProductRegisterSpecsService.getGspProductRegisterSpecsInfo(f.getSpecsId());
                GspProductRegisterSpecsVO specObj = (GspProductRegisterSpecsVO)spec.getObj();
                GspProductRegister register = gspProductRegisterService.queryById(specObj.getProductRegisterId());

                if(register == null  && specObj.getMedicalDeviceMark()=="1"){
                    return Json.error("该医疗器械产品没有绑定产品注册证");
                }

                BasCustomer basCustomer = basCustomerService.selectCustomerById(firstBusinessApply.getSupplierId(),Constant.CODE_CUS_TYP_VE);//basCustomerService.selectCustomer(register.getEnterpriseId(),Constant.CODE_CUS_TYP_VE);
                if(basCustomer==null){
                    return Json.error("供应商不存在");
                }
                BasCustomer customerId =  basCustomerService.selectCustomerById(firstBusinessApply.getClientId(),Constant.CODE_CUS_TYP_OW);//basCustomerService.selectCustomer(firstBusinessApply.getClientId(),Constant.CODE_CUS_TYP_OW);
                if(customerId==null){
                    return Json.error("货主不存在");
                }
                skuForm.setDefaultreceivinguom(specObj.getUnit());
                skuForm.setDescrC(specObj.getSpecsName());
                skuForm.setDescrE(specObj.getProductModel());



                if("".equals(specObj.getPackingUnit()) || specObj.getPackingUnit()==null ){
                    skuForm.setPackid("STANDARD");
                }else{
                    BasPackageQuery query = new BasPackageQuery();
                    query.setDescr(specObj.getPackingUnit());
                    BasPackage basPackage = basPackageService.queryBasBydescr(query);
                    skuForm.setPackid(basPackage.getPackid());
                }






                skuForm.setAlternateSku1(specObj.getAlternatName1());
                skuForm.setAlternateSku2(specObj.getAlternatName2());
                skuForm.setAlternateSku3(specObj.getAlternatName3());
                skuForm.setAlternateSku4(specObj.getAlternatName4());
                skuForm.setAlternateSku5(specObj.getAlternatName5());

                if (!"".equals(specObj.getHight()) && specObj.getHight() != null){
                    skuForm.setSkuhigh(new BigDecimal(specObj.getHight()));
                }else{
                    skuForm.setSkuhigh(new BigDecimal(0));
                }
                if (!"".equals(specObj.getLlong()) && specObj.getLlong() != null){
                    skuForm.setSkulength(new BigDecimal(specObj.getLlong()));
                }else{
                    skuForm.setSkulength(new BigDecimal(0));
                }
                if (!"".equals(specObj.getWide()) && specObj.getWide() != null){
                    skuForm.setSkuwidth(new BigDecimal(specObj.getWide()));
                }else{
                    skuForm.setSkuwidth(new BigDecimal(0));
                }

                skuForm.setReservedfield01(specObj.getProductName());
                skuForm.setReservedfield02(specObj.getProductRemark());
                skuForm.setReservedfield03(specObj.getProductRegisterNo());
                if(register!=null){
                    skuForm.setReservedfield04(register.getClassifyId());
                    skuForm.setReservedfield05(register.getClassifyCatalog());
                    skuForm.setOrderbysql(register.getProductRegisterId());

                    //下发注册证关系
//                    ProductRegisterRelation relation = new ProductRegisterRelation();
//                    relation.setSpecsId(f.getSpecsId());
//                    relation.setProductRegisterId(register.getProductRegisterId());
//                    relation.setActiveFlag("1");
//                    productRegisterRelationMybatisDao.updateSelectiveByspecsIdAndproductRegisterId(relation);
                }
                skuForm.setReservedfield06(specObj.getLicenseOrRecordNo());
                skuForm.setReservedfield07(specObj.getColdHainMark());
                skuForm.setReservedfield08(specObj.getSterilizationMarkers());
                skuForm.setReservedfield09(specObj.getMedicalDeviceMark());
                skuForm.setReservedfield10(specObj.getMaintenanceCycle());
                skuForm.setReservedfield11(specObj.getWight());
                skuForm.setReservedfield12(specObj.getBarCode());
                skuForm.setReservedfield13(specObj.getPackagingUnit());//包装单位
                skuForm.setReservedfield14(specObj.getEnterpriseName());
                skuForm.setSku(specObj.getProductCode());

                skuForm.setSkuGroup1(firstBusinessApply.getProductline());//specObj.getProductLine()产品线
                skuForm.setSkuGroup2(specObj.getAttacheCardCategory());//附卡类别
                skuForm.setSkuGroup3(specObj.getPackingRequire());//包装要求
                skuForm.setSkuGroup4(specObj.getStorageCondition());
                skuForm.setSkuGroup5(specObj.getTransportCondition());
                skuForm.setSkuGroup6(basCustomer.getCustomerid());//TODO 改为bas_customer主键
                skuForm.setSkuGroup7(specObj.getIsDoublec());//是否需要双证
                skuForm.setSkuGroup8(specObj.getIsCertificate());//是否需要产品合格证
                skuForm.setSkuGroup9(specObj.getProductionAddress());
                skuForm.setActiveFlag(Constant.IS_USE_YES);
                skuForm.setFirstop(form.getFirstState());
                skuForm.setPutawayrule(no);//申请单号
                skuForm.setCustomerid(customerId.getCustomerid());



                //skuForm
                basSkuService.addBasSku(skuForm);
            }
            return Json.error("操作成功");
        }
        return Json.error("单据号无效："+no);
    }

    //作废已下发数据
    public Json cancelData(String no) throws Exception{
        //委托客户数据
        if(no.indexOf(Constant.APLCUSNO)!=-1){
            Json json = gspCustomerService.getGspCustomerById(no);
            if(!json.isSuccess()){
                return Json.error("查询不到对应的委托客户申请");
            }
            GspCustomerVO customer = (GspCustomerVO)json.getObj();

            GspCustomerForm updateGspCustomer = new GspCustomerForm();
            updateGspCustomer.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
            updateGspCustomer.setIsUse(Constant.IS_USE_NO);
            updateGspCustomer.setClientId(no);

            gspCustomerService.editGspCustomer(updateGspCustomer);

            GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoService.getGspEnterpriseInfo(customer.getEnterpriseId());

            BasCustomerForm form = new BasCustomerForm();
            form.setCustomerid(gspEnterpriseInfo.getEnterpriseNo());
            form.setEnterpriseId(customer.getEnterpriseId());
            form.setOperateType(customer.getOperateType());
            form.setActiveFlag(Constant.IS_USE_NO);
            form.setBillclass(updateGspCustomer.getFirstState());
            form.setCustomerType(Constant.CODE_CUS_TYP_OW);

            return basCustomerService.editBasCustomerByCustomerId(form);
        }else if(no.indexOf(Constant.APLSUPNO)!=-1){
            Json json = gspSupplierService.getGspSupplierInfo(no);
            if(!json.isSuccess()){
                return Json.error("查询不到对应的供应商申请");
            }
            GspSupplierVO supplier = (GspSupplierVO)json.getObj();
            GspSupplierForm updateSupplierForm = new GspSupplierForm();
            updateSupplierForm.setSupplierId(no);
            updateSupplierForm.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
            updateSupplierForm.setIsUse(Constant.IS_USE_NO);
            gspSupplierService.editGspSupplier(updateSupplierForm);

            BasCustomerForm form = new BasCustomerForm();
            form.setBankaccount(supplier.getSupplierId());
            form.setEnterpriseId(supplier.getEnterpriseId());
            form.setOperateType(supplier.getOperateType());
            form.setActiveFlag(Constant.IS_USE_NO);
            form.setBillclass(updateSupplierForm.getFirstState());
//            form.setCustomerid(supplier.getSupplierId());
            form.setCustomerType(Constant.CODE_CUS_TYP_VE);
            //换证报废 customerId 给错 不是APSUP0000000087申请单号  应是GYS000000072

            //更新产品申请单中的
            BasCustomerQuery basCustomerQuery = new BasCustomerQuery();
            basCustomerQuery.setCustomerType(Constant.CODE_CUS_TYP_VE);
            basCustomerQuery.setEnterpriseId(supplier.getEnterpriseId());

//          BasCustomer basCustomer = basCustomerMybatisDao.querySupplierByBankaccount(no);
            BasCustomer basCustomer = basCustomerMybatisDao.queryByenterId(basCustomerQuery);
            if(basCustomer==null){
                return Json.error("供应商不存在");
            }
//            firstReviewLogService.updateFirstReviewBySupplierId(Constant.CODE_CATALOG_CHECKSTATE_FAIL,basCustomer.getCustomerid());
            firstBusinessApplyService.updateCheckingApplySupplierNo(basCustomer.getCustomerid());

            return basCustomerService.editBasCustomerSupByEnterpriseId(form);

        }else if(no.indexOf(Constant.APLRECNO)!=-1){

            GspReceiving gspReceiving = gspReceivingService.getGspReceiving(no);
            if(gspReceiving == null){
                return Json.error("查询不到对应的收货单位申请");
            }
            String enterpriseId = gspReceiving.getEnterpriseId();
            GspReceivingForm gspReceivingForm = new GspReceivingForm();
            gspReceivingForm.setReceivingId(gspReceiving.getReceivingId());
            gspReceivingForm.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
            gspReceivingForm.setIsUse(Constant.IS_USE_NO);
//            gspReceivingForm.setClientId(no);

            gspReceivingService.editGspReceiving(gspReceivingForm);

            BasCustomerForm form = new BasCustomerForm();
            form.setBankaccount(gspReceiving.getReceivingId());
//            form.setCustomerid(gspReceiving.getReceivingId());
            form.setEnterpriseId(enterpriseId);
            form.setOperateType(gspReceiving.getEnterpriseType());
            form.setActiveFlag(Constant.IS_USE_NO);
            form.setBillclass(gspReceivingForm.getFirstState());
//            form.setCustomerid(gspReceiving.getReceivingId());
            form.setCustomerType(Constant.CODE_CUS_TYP_CO);
            return basCustomerService.editBasCustomerSupByEnterpriseId(form);
        }else if(no.indexOf(Constant.APLPRONO)!=-1){
            Json json = firstBusinessApplyService.queryFirstBusinessApply(no);
            if(!json.isSuccess()){
                return Json.error("查询不到对应的产品申请");
            }
            FirstBusinessApplyVO firstBusinessApply = (FirstBusinessApplyVO)json.getObj();
            Json productJson = firstBusinessProductApplyService.getListByApplyId(firstBusinessApply.getApplyId());
            if(!productJson.isSuccess()){
                return productJson;
            }

            FirstBusinessApplyForm updateForm = new FirstBusinessApplyForm();
            updateForm.setApplyId(no);
            updateForm.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
            updateForm.setIsUse(Constant.IS_USE_NO);
            firstBusinessApplyService.editFirstBusinessApply(updateForm);

            List<FirstBusinessProductApply> productApplyList = (List<FirstBusinessProductApply>)productJson.getObj();
            for(FirstBusinessProductApply f:productApplyList){
                BasSkuForm skuForm = new BasSkuForm();
                Json spec = gspProductRegisterSpecsService.getGspProductRegisterSpecsInfo(f.getSpecsId());
                GspProductRegisterSpecsVO specObj = (GspProductRegisterSpecsVO)spec.getObj();
                //GspProductRegister register = gspProductRegisterService.queryById(specObj.getProductRegisterId());
                /*skuForm.setDefaultreceivinguom(specObj.getUnit());
                skuForm.setDescrC(specObj.getSpecsName());
                skuForm.setDescrE(specObj.getProductModel());
                skuForm.setPackid(specObj.getPackingUnit());
                skuForm.setReservedfield01(specObj.getProductName());
                skuForm.setReservedfield02(specObj.getProductRemark());
                skuForm.setReservedfield03(specObj.getProductRegisterNo());
                skuForm.setReservedfield04(register.getClassifyId());
                skuForm.setReservedfield05(register.getClassifyCatalog());
                skuForm.setSku(specObj.getProductCode());
                skuForm.setSkuGroup1(firstBusinessApply.getProductLine());
                //skuForm.setSkuGroup2();//附卡类别
                skuForm.setSkuGroup3(specObj.getPackingRequire());//包装要求
                skuForm.setSkuGroup4(specObj.getStorageCondition());
                skuForm.setSkuGroup5(specObj.getTransportCondition());
                skuForm.setSkuGroup6(register.getEnterpriseId());
                skuForm.setSkuGroup7(specObj.getIsDoublec());//是否需要双证
                skuForm.setSkuGroup8(specObj.getIsCertificate());//是否需要产品合格证
                skuForm.setSkuGroup9(specObj.getProductionAddress());*/


                skuForm.setActiveFlag(Constant.IS_USE_NO);
                skuForm.setFirstop(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
                skuForm.setSku(specObj.getProductCode());
                skuForm.setCustomerid(firstBusinessApply.getClientId());

                BasSkuQuery skuQuery = new BasSkuQuery();
                skuQuery.setCustomerid(skuForm.getCustomerid());
                skuQuery.setSku(skuForm.getSku());
                BasSku basSku = basSkuMybatisDao.queryById(skuQuery);

                if(basSku!=null){
                    BasSkuHistory basSkuHistory = new BasSkuHistory();
                    BeanUtils.copyProperties(basSku,basSkuHistory);
                    basSkuHistory.setActiveFlag(Constant.IS_USE_NO);
                    basSkuHistory.setFirstop(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
//                  basSkuHistory.setAddtime();
                    basSkuHistoryMybatisDao.add(basSkuHistory);
                }

                //skuForm
                basSkuService.editBasSku(skuForm);
            }
            return Json.error("操作成功");
        }
        return Json.error("单据号无效："+no);
    }

    /**
     * 直接下发主体信息
     * @param gspEnterpriseInfoForm
     * @return
     */
    public Json publishDataWithOutApply(GspEnterpriseInfoForm gspEnterpriseInfoForm){

        BasCustomerForm form = new BasCustomerForm();
        form.setCustomerid(gspEnterpriseInfoForm.getEnterpriseNo());
        form.setDescrC(gspEnterpriseInfoForm.getEnterpriseName());
        form.setCustomerType(Constant.CODE_CUS_TYP_WH);
        form.setEnterpriseId(gspEnterpriseInfoForm.getEnterpriseId());
        form.setOperateType(gspEnterpriseInfoForm.getEnterpriseType());
        form.setActiveFlag(Constant.IS_USE_YES);
        form.setBankaccount("");
        form.setBillclass(Constant.CODE_CATALOG_FIRSTSTATE_PASS);
        String flag = "";
        return basCustomerService.clientAddCustomer(form,flag);
    }
    /**
     * 直接下发主体信息
     * @param gspEnterpriseInfoForm
     * @return
     */
    public Json publishGWDataWithOutApply(GspEnterpriseInfoForm gspEnterpriseInfoForm){

        BasCustomerForm form = new BasCustomerForm();
        form.setCustomerid(gspEnterpriseInfoForm.getEnterpriseNo());
        form.setDescrC(gspEnterpriseInfoForm.getEnterpriseName());
        form.setCustomerType(Constant.CODE_CUS_TYP_PR);
        form.setEnterpriseId(gspEnterpriseInfoForm.getEnterpriseId());
        form.setOperateType(gspEnterpriseInfoForm.getEnterpriseType());
        form.setActiveFlag(Constant.IS_USE_YES);
        form.setBankaccount("");
        form.setBillclass(Constant.CODE_CATALOG_FIRSTSTATE_PASS);

        String flag = "";
        return basCustomerService.clientAddCustomer(form,flag);
    }

    /**
     * 更新单据首营状态
     * @param no
     * @param state
     * @return
     */
    public Json updateFirstState(String no,String state){
        //委托客户数据
        if(no.indexOf(Constant.APLCUSNO)!=-1){
            return gspCustomerService.updateFirstState(no,state);
        }else if(no.indexOf(Constant.APLSUPNO)!=-1){
            return gspSupplierService.updateFirstState(no,state);
        }else if(no.indexOf(Constant.APLRECNO)!=-1){
            return gspReceivingService.updateFirstState(no,state);
        }else if(no.indexOf(Constant.APLPRONO)!=-1){
            return firstBusinessApplyService.updateFirstState(no,state);
        }
        return Json.error("没有查询到对应的申请单");
    }

    /**
     * 产品注册证换证
     * @param registerId 老证号
     * @param newRegisterId 新证号
     * @return
     * @throws Exception
     */
    public Json cancelPubilseDataByRegisterId(String registerId,String newRegisterId) throws Exception{
        //老证
        GspProductRegister gspProductRegister = gspProductRegisterService.queryById(registerId);
        if(gspProductRegister == null){
            return Json.error("产品注册证不存在");
        }

        //1.失效bas_sku   老注册证下关联的已下发产品
        List<BasSku> basSkuList = basSkuService.queryBasSkuBySku(gspProductRegister.getProductRegisterNo());
        for(BasSku b : basSkuList){
            //TODO
             if(b.getActiveFlag()=="1"){
                 //失效bas_sku
             }
            BasSkuForm form = new BasSkuForm();
            BeanUtils.copyProperties(b,form);
            form.setActiveFlag(Constant.IS_USE_NO);
            form.setOrderbysql(gspProductRegister.getProductRegisterId());
            basSkuService.editBasSku(form);

            //如果已下发产品上一层的产品首营被删了  那就跳过
            FirstBusinessApply firstBusinessApply =  firstBusinessApplyMybatisDao.queryById(b.getPutawayrule());
            if(firstBusinessApply==null){
                continue;
            }
            firstBusinessApplyService.updateFirstState(b.getPutawayrule(),Constant.CODE_CATALOG_FIRSTSTATE_USELESS);

            //2.失效产品首营申请
            Json json = firstBusinessProductApplyService.getListByApplyId(b.getPutawayrule());
            List<FirstBusinessProductApply> list = (List<FirstBusinessProductApply>)json.getObj();
            if(list!=null && list.size()>0){
                //失效产品首营申请明细
                FirstBusinessProductApply productApply = (FirstBusinessProductApply)list.get(0);
                FirstBusinessProductApplyForm fbpaForm = new FirstBusinessProductApplyForm();
                fbpaForm.setApplyId(productApply.getApplyId());
                fbpaForm.setProductApplyId(productApply.getProductApplyId());
                fbpaForm.setSpecsId(productApply.getSpecsId());
                fbpaForm.setIsUse(Constant.IS_USE_NO);
                firstBusinessProductApplyService.editFirstBusinessProductApply(fbpaForm);
                //失效产品首营申请表头
                FirstBusinessApplyForm fbaHeadForm = new FirstBusinessApplyForm();
                fbaHeadForm.setIsUse(Constant.IS_USE_NO);
                fbaHeadForm.setApplyId(productApply.getApplyId());
                firstBusinessApplyService.editFirstBusinessApply(fbaHeadForm);

                /*FirstReviewLogForm reviewLogForm = new FirstReviewLogForm();
                reviewLogForm.setReviewId(productApply.getApplyId());
                reviewLogForm.setApplyState(Constant.CODE_CATALOG_CHECKSTATE_FAIL);
                reviewLogForm.setEditId("System");
                reviewLogForm.setEditDate(new Date());
                firstReviewLogService.updateByReviewTypeId(reviewLogForm);*/
            }
        }

        //3.更新产品基础信息关联产品注册证    老注册证下的产品
        List<GspProductRegisterSpecs> list = gspProductRegisterSpecsService.querySpecByRegisterId(gspProductRegister.getProductRegisterId());
        if(list!=null && list.size()>0){
            for(GspProductRegisterSpecs specs : list){
                //报废原产品
                GspProductRegisterSpecsForm form = new GspProductRegisterSpecsForm();
                //BeanUtils.copyProperties(specs,form);
                form.setSpecsId(specs.getSpecsId());
                form.setIsUse(Constant.IS_USE_NO);
                //form.setProductRegisterId(newRegisterId);
//                gspProductRegisterSpecsService.editGspProductRegisterSpecs(form,newRegisterId);//?????
                gspProductRegisterSpecsMybatisDao.updateBySelective(form);


                //保存新基础信息
                GspProductRegisterSpecs formNew = new GspProductRegisterSpecs();
                BeanUtils.copyProperties(specs,formNew);
                formNew.setIsUse(Constant.IS_USE_YES);
                formNew.setProductRegisterId(newRegisterId);
                GspProductRegister gPR = gspProductRegisterMybatisDao.queryById(newRegisterId);
                if(gPR==null){
                    return Json.error("注册证不存在");
                }
                GspEnterpriseInfo g = gspEnterpriseInfoMybatisDao.queryByEnterpriseId(gPR.getEnterpriseId());
//                formNew.setProductEnterpriseName(g.getEnterpriseName());
                formNew.setLicenseOrRecordNo(gPR.getLicenseOrRecordNol());
                formNew.setEnterpriseName(g.getEnterpriseName());
                formNew.setStorageCondition(gPR.getStorageConditions());
                formNew.setSpecsId(RandomUtil.getUUID());
                if(specs.getProductName() ==null || specs.getProductName().equals("")){
                    formNew.setProductName(specs.getProductNameMain());
                }
                gspProductRegisterSpecsMybatisDao.add(formNew);
                //gspProductRegisterSpecsService.addGspProductRegisterSpecs(formNew);
            }
        }

        return Json.success("");
    }

    public Json cancelDataBySpecsId(GspProductRegisterSpecs oldSpecs){
        //1.失效bas_sku
        List<BasSku> basSkuList = basSkuService.getSkuListBySku(oldSpecs.getProductCode());
        if(basSkuList!=null && basSkuList.size()>0){
            for(BasSku b : basSkuList){
                BasSkuForm form = new BasSkuForm();
                BeanUtils.copyProperties(b,form);

                form.setActiveFlag(Constant.IS_USE_NO);
                form.setFirstop(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
                basSkuService.editBasSkuRegister(form);
                if(b!=null){
                    BasSkuHistory basSkuHistory = new BasSkuHistory();
                    BeanUtils.copyProperties(b,basSkuHistory);
                    basSkuHistory.setAddtime(new Date());
                    basSkuHistory.setActiveFlag(Constant.IS_USE_NO);
                    basSkuHistory.setFirstop(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
                    basSkuHistoryMybatisDao.add(basSkuHistory);
                }


                firstBusinessApplyService.updateFirstState(b.getPutawayrule(),Constant.CODE_CATALOG_FIRSTSTATE_USELESS);

                //2.失效产品首营申请
                Json json = firstBusinessProductApplyService.getListByApplyId(b.getPutawayrule());
                List<FirstBusinessProductApply> list = (List<FirstBusinessProductApply>)json.getObj();
                if(list!=null && list.size()>0){
                    //失效产品首营申请明细
                    FirstBusinessProductApply productApply = (FirstBusinessProductApply)list.get(0);
                    FirstBusinessProductApplyForm fbpaForm = new FirstBusinessProductApplyForm();
                    fbpaForm.setApplyId(productApply.getApplyId());
                    fbpaForm.setProductApplyId(productApply.getProductApplyId());
                    fbpaForm.setSpecsId(productApply.getSpecsId());
                    fbpaForm.setIsUse(Constant.IS_USE_NO);
                    firstBusinessProductApplyService.editFirstBusinessProductApply(fbpaForm);
                    //失效产品首营申请表头
                    FirstBusinessApplyForm fbaHeadForm = new FirstBusinessApplyForm();
                    fbaHeadForm.setIsUse(Constant.IS_USE_NO);
                    fbaHeadForm.setApplyId(productApply.getApplyId());
                    fbaHeadForm.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
                    firstBusinessApplyService.editFirstBusinessApply(fbaHeadForm);

                    //失效审核日志
//                    firstReviewLogService.updateFirstReviewByNo(productApply.getApplyId(),Constant.CODE_CATALOG_CHECKSTATE_FAIL);
                }
            }
        }else{

            List<FirstBusinessApplyResult> firstBusinessApplyList = firstBusinessApplyMybatisDao.selectByProductRegisterIdAll(oldSpecs.getProductRegisterId());
            if(firstBusinessApplyList!=null && firstBusinessApplyList.size()>0){
                for(FirstBusinessApplyResult apply : firstBusinessApplyList){
                    //失效产品首营申请明细

                    FirstBusinessProductApplyForm fbpaForm = new FirstBusinessProductApplyForm();
                    fbpaForm.setApplyId(apply.getApplyId());
                    fbpaForm.setSpecsId(apply.getSpecsId());
                    fbpaForm.setIsUse(Constant.IS_USE_NO);
                    fbpaForm.setProductApplyId(apply.getProductApplyId());
                    firstBusinessProductApplyService.editFirstBusinessProductApply(fbpaForm);
                    //失效产品首营申请表头
                    FirstBusinessApplyForm fbaHeadForm = new FirstBusinessApplyForm();
                    fbaHeadForm.setIsUse(Constant.IS_USE_NO);
                    fbaHeadForm.setApplyId(apply.getApplyId());
                    fbaHeadForm.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
                    firstBusinessApplyService.editFirstBusinessApply(fbaHeadForm);

                    //失效审核日志
//                    firstReviewLogService.updateFirstReviewByNo(apply.getApplyId(),Constant.CODE_CATALOG_CHECKSTATE_FAIL);
                }
            }

        }
        return Json.success("");
    }
}