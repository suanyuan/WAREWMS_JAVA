package com.wms.service;

import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.vo.GspCustomerVO;
import com.wms.vo.GspSupplierVO;
import com.wms.vo.Json;
import com.wms.vo.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

            BasCustomerForm form = new BasCustomerForm();
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
            //return basCustomerService.addBasCustomer(form);
            return Json.success("数据下发中");
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

            BasCustomerForm form = new BasCustomerForm();
            form.setEnterpriseId(supplier.getEnterpriseId());
            form.setOperateType(supplier.getOperateType());
            form.setActiveFlag(Constant.IS_USE_YES);

            //return basCustomerService.addBasCustomer(form);
            return Json.success("数据下发中");
        }else if(no.indexOf(Constant.APLRECNO)!=-1){
			/*Json json = gspReceivingService
			return Constant.APLRECNO;*/
			/*BasCustomerForm form = new BasCustomerForm();
			form.setEnterpriseId();
			form.setOperateType(supplier.getOperateType());
			form.setActiveFlag(Constant.IS_USE_YES);
			basCustomerService.addBasCustomer();*/
            return Json.error("待开发");
        }else if(no.indexOf(Constant.APLPRONO)!=-1){
            Json json = firstBusinessApplyService.queryFirstBusinessApply(no);
            if(!json.isSuccess()){
                return Json.error("查询不到对应的产品申请");
            }
            FirstBusinessApply firstBusinessApply = (FirstBusinessApply)json.getObj();

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
                skuForm.setSkuGroup6(register.getEnterpriseId());//TODO 改为bas_customer主键
                skuForm.setSkuGroup7(specObj.getIsDoublec());//是否需要双证
                skuForm.setSkuGroup8(specObj.getIsCertificate());//是否需要产品合格证
                skuForm.setSkuGroup9(specObj.getProductionAddress());
                skuForm.setActiveFlag(Constant.IS_USE_YES);
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
        //TODO 关联数据需要从基础数据中查询
        //委托客户数据
        if(no.indexOf(Constant.APLCUSNO)!=-1){
            Json json = gspCustomerService.getGspCustomerById(no);
            if(!json.isSuccess()){
                return Json.error("查询不到对应的委托客户申请");
            }
            GspCustomer customer = (GspCustomer)json.getObj();
            BasCustomerForm form = new BasCustomerForm();
            form.setEnterpriseId(customer.getEnterpriseId());
            form.setOperateType(customer.getOperateType());
            form.setActiveFlag(Constant.IS_USE_NO);
            return basCustomerService.editBasCustomer(form);
        }else if(no.indexOf(Constant.APLSUPNO)!=-1){
            Json json = gspSupplierService.getGspSupplierInfo(no);
            if(!json.isSuccess()){
                return Json.error("查询不到对应的供应商申请");
            }
            GspSupplier supplier = (GspSupplier)json.getObj();
            BasCustomerForm form = new BasCustomerForm();
            form.setEnterpriseId(supplier.getEnterpriseId());
            form.setOperateType(supplier.getOperateType());
            form.setActiveFlag(Constant.IS_USE_NO);
            return basCustomerService.editBasCustomer(form);
        }else if(no.indexOf(Constant.APLRECNO)!=-1){
			/*Json json = gspReceivingService
			return Constant.APLRECNO;*/
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
                //skuForm
                basSkuService.editBasSku(skuForm);
            }
            return Json.error("操作成功");
        }
        return Json.error("单据号无效："+no);
    }
}