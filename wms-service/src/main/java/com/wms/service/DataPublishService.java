package com.wms.service;

import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.query.BasPackageQuery;
import com.wms.utils.DateUtil;
import com.wms.utils.PinyinUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.*;
import com.wms.vo.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
    private FirstBusinessProductApplyService firstBusinessProductApplyService;
    @Autowired
    private GspEnterpriseInfoService gspEnterpriseInfoService;
    @Autowired
    private GspReceivingService gspReceivingService;
    @Autowired
    private BasPackageService basPackageService;

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
            form.setIsChineseLabel(Long.parseLong(customer.getIsChineseLabel()));
            //form.setContractNo();
            form.setSupContractNo(customer.getContractNo());
            form.setContractUrl(customer.getContractUrl());
            form.setClientContent(customer.getClientContent());
            form.setClientStartDate(customer.getClientStartDate());
            form.setClientEndDate(customer.getClientEndDate());
//            form.setClientTerm(Long.parseLong(customer.getClientTerm()));

            form.setActiveFlag(Constant.IS_USE_YES);
            form.setBankaccount(no);
            form.setBillclass(gspCustomerForm.getFirstState());
            return basCustomerService.clientAddCustomer(form);
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
            form.setCustomerid(commonService.generateSeq(Constant.BASSUPNO, SfcUserLoginUtil.getLoginUser().getWarehouse().getId()));
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

            return basCustomerService.clientAddCustomer(form);
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

            return basCustomerService.clientAddCustomer(form);
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

                if(register == null){
                    return Json.error("产品没有绑定产品注册证");
                }

                BasCustomer basCustomer = basCustomerService.selectCustomerById(firstBusinessApply.getSupplierId(),Constant.CODE_CUS_TYP_VE);//basCustomerService.selectCustomer(register.getEnterpriseId(),Constant.CODE_CUS_TYP_VE);
                BasCustomer customerId =  basCustomerService.selectCustomerById(firstBusinessApply.getClientId(),Constant.CODE_CUS_TYP_OW);//basCustomerService.selectCustomer(firstBusinessApply.getClientId(),Constant.CODE_CUS_TYP_OW);

                skuForm.setDefaultreceivinguom(specObj.getUnit());
                skuForm.setDescrC(specObj.getSpecsName());
                skuForm.setDescrE(specObj.getProductModel());

                BasPackageQuery query = new BasPackageQuery();
                query.setDescr(specObj.getPackingUnit());
                BasPackage basPackage = basPackageService.queryBasPackBy(query);
                skuForm.setPackid(basPackage == null ? "6" : basPackage.getPackid());

                skuForm.setReservedfield01(specObj.getProductName());
                skuForm.setReservedfield02(specObj.getProductRemark());
                skuForm.setReservedfield03(specObj.getProductRegisterNo());
                skuForm.setReservedfield04(register.getClassifyId());
                skuForm.setReservedfield05(register.getClassifyCatalog());
                skuForm.setSku(specObj.getProductCode());
                skuForm.setSkuGroup1(firstBusinessApply.getProductLine());//specObj.getProductLine()产品线
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
            form.setCustomerid(supplier.getSupplierId());
            form.setEnterpriseId(supplier.getEnterpriseId());
            form.setOperateType(supplier.getOperateType());
            form.setActiveFlag(Constant.IS_USE_NO);
            form.setBillclass(updateSupplierForm.getFirstState());
            form.setCustomerid(supplier.getSupplierId());
            form.setCustomerType(Constant.CODE_CUS_TYP_VE);

            return basCustomerService.editBasCustomer(form);

        }else if(no.indexOf(Constant.APLRECNO)!=-1){

            GspReceiving gspReceiving = gspReceivingService.getGspReceiving(no);
            if(gspReceiving == null){
                return Json.error("查询不到对应的委托客户申请");
            }

            GspReceivingForm gspReceivingForm = new GspReceivingForm();
            gspReceivingForm.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
            gspReceivingForm.setIsUse(Constant.IS_USE_NO);
            gspReceivingForm.setClientId(no);

            gspReceivingService.editGspReceiving(gspReceivingForm);

            BasCustomerForm form = new BasCustomerForm();
            form.setCustomerid(gspReceiving.getReceivingId());
            form.setEnterpriseId(gspReceiving.getEnterpriseId());
            form.setOperateType(gspReceiving.getEnterpriseType());
            form.setActiveFlag(Constant.IS_USE_NO);
            form.setBillclass(gspReceivingForm.getFirstState());
            form.setCustomerid(gspReceiving.getReceivingId());
            form.setCustomerType(Constant.CODE_CUS_TYP_CO);
            return basCustomerService.editBasCustomer(form);
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

        return basCustomerService.clientAddCustomer(form);
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

        return basCustomerService.clientAddCustomer(form);
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
}