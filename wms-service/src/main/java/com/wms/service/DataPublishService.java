package com.wms.service;

import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.utils.DateUtil;
import com.wms.utils.PinyinUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.*;
import com.wms.vo.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            form.setContractNo(customer.getContractNo());
            form.setContractUrl(customer.getContractUrl());
            form.setClientContent(customer.getClientContent());
            form.setClientStartDate(customer.getClientStartDate());
            form.setClientEndDate(customer.getClientEndDate());
            form.setClientTerm(Long.parseLong(customer.getClientTerm()));
            form.setIsChineseLabel(Long.parseLong(customer.getIsChineseLabel()));
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
            form.setContractNo(supplier.getContractNo());
            form.setContractUrl(supplier.getContractUrl());
            form.setClientContent(supplier.getClientContent());
            form.setClientStartDate(DateUtil.parse(supplier.getClientStartDate(),"yyyy-MM-dd"));
            form.setClientEndDate(DateUtil.parse(supplier.getClientEndDate(),"yyyy-MM-dd"));
            Long clientTerm = 0l;
            if(null == supplier.getClientTerm() || "".equals(supplier.getClientTerm())){
                clientTerm = Long.parseLong(supplier.getClientTerm());
            }
            form.setClientTerm(clientTerm);
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
           /* form.setOperateType(gspReceiving.getOperateType());
            form.setContractNo(gspReceiving.getContractNo());
            form.setContractUrl(gspReceiving.getContractUrl());
            form.setClientContent(gspReceiving.getClientContent());
            form.setClientStartDate(DateUtil.parse(supplier.getClientStartDate(),"yyyy-MM-dd"));
            form.setClientEndDate(DateUtil.parse(supplier.getClientEndDate(),"yyyy-MM-dd"));
            form.setClientTerm(Long.parseLong(supplier.getClientTerm()));*/
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

                BasCustomer basCustomer = basCustomerService.selectCustomerById(firstBusinessApply.getSupplierId(),Constant.CODE_CUS_TYP_VE);//basCustomerService.selectCustomer(register.getEnterpriseId(),Constant.CODE_CUS_TYP_VE);
                BasCustomer customerId =  basCustomerService.selectCustomerById(firstBusinessApply.getClientId(),Constant.CODE_CUS_TYP_OW);//basCustomerService.selectCustomer(firstBusinessApply.getClientId(),Constant.CODE_CUS_TYP_OW);

                skuForm.setDefaultreceivinguom(specObj.getUnit());
                skuForm.setDescrC(specObj.getSpecsName());
                skuForm.setDescrE(specObj.getProductModel());
                skuForm.setPackid(specObj.getPackingUnit());
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
                return Json.success("数据下发中");
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

            BasCustomerForm form = new BasCustomerForm();
            form.setEnterpriseId(customer.getEnterpriseId());
            form.setOperateType(customer.getOperateType());
            form.setActiveFlag(Constant.IS_USE_NO);
            form.setBillclass(updateGspCustomer.getFirstState());

            return basCustomerService.editBasCustomer(form);
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
            form.setEnterpriseId(supplier.getEnterpriseId());
            form.setOperateType(supplier.getOperateType());
            form.setActiveFlag(Constant.IS_USE_NO);
            form.setBillclass(updateSupplierForm.getFirstState());
            return basCustomerService.editBasCustomer(form);

        }else if(no.indexOf(Constant.APLRECNO)!=-1){
            return Json.error("待开发");
        }else if(no.indexOf(Constant.APLPRONO)!=-1){
            Json json = firstBusinessApplyService.queryFirstBusinessApply(no);
            if(!json.isSuccess()){
                return Json.error("查询不到对应的产品申请");
            }
            FirstBusinessApply firstBusinessApply = (FirstBusinessApply)json.getObj();
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
                GspProductRegisterSpecs specObj = (GspProductRegisterSpecs)spec.getObj();
                GspProductRegister register = gspProductRegisterService.queryById(specObj.getProductRegisterId());
                skuForm.setDefaultreceivinguom(specObj.getUnit());
                skuForm.setDescrC(specObj.getSpecsName());
                skuForm.setDescrE(specObj.getProductModel());
                skuForm.setPackid(specObj.getPackingUnit());
                skuForm.setReservedfield01(specObj.getProductName());
                skuForm.setReservedfield02(specObj.getProductRemark());
                skuForm.setReservedfield03(specObj.getProductRegisterNo());
                skuForm.setReservedfield04(register.getClassifyId());
                skuForm.setReservedfield05(register.getClassifyCatalog());
                skuForm.setSku(specObj.getProductCode());
                skuForm.setSkuGroup1(specObj.getProductLine());
                //skuForm.setSkuGroup2();//附卡类别
                skuForm.setSkuGroup3(specObj.getPackingRequire());//包装要求
                skuForm.setSkuGroup4(specObj.getStorageCondition());
                skuForm.setSkuGroup5(specObj.getTransportCondition());
                skuForm.setSkuGroup6(register.getEnterpriseId());
                skuForm.setSkuGroup7(specObj.getIsDoublec());//是否需要双证
                skuForm.setSkuGroup8(specObj.getIsCertificate());//是否需要产品合格证
                skuForm.setSkuGroup9(specObj.getProductionAddress());
                skuForm.setActiveFlag(Constant.IS_USE_NO);
                skuForm.setFirstop(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
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
}