package com.wms.service;

import com.wms.constant.Constant;
import com.wms.entity.GspBusinessLicense;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.entity.GspOperateLicense;
import com.wms.entity.GspSecondRecord;
import com.wms.mybatis.dao.GspBusinessLicenseMybatisDao;
import com.wms.mybatis.dao.GspEnterpriseInfoMybatisDao;
import com.wms.mybatis.dao.GspOperateLicenseMybatisDao;
import com.wms.query.GspBusinessLicenseQuery;
import com.wms.query.GspOperateLicenseQuery;
import com.wms.query.GspSecondRecordQuery;
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
 * 企业总业务处理
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
    @Autowired
    private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;
    @Autowired
    private GspOperateDetailService gspOperateDetailService;
    @Autowired
    private GspBusinessLicenseMybatisDao gspBusinessLicenseMybatisDao;
    @Autowired
    private GspOperateLicenseMybatisDao gspOperateLicenseMybatisDao;


    /**
     * 新增企业信息
     * @param gspEnterpriceFrom 提交数据
     * @return
     */
    public Json addGspEnterprice(GspEnterpriceFrom gspEnterpriceFrom){
        try{
            GspEnterpriseInfoForm gspEnterpriseInfoForm = gspEnterpriceFrom.getGspEnterpriseInfoForm();
            GspBusinessLicenseForm gspBusinessLicenseForm = gspEnterpriceFrom.getGspBusinessLicenseForm();
            GspOperateLicenseForm gspOperateLicenseForm = gspEnterpriceFrom.getGspOperateLicenseForm();
            GspSecondRecordForm gspSecondRecordForm = gspEnterpriceFrom.getGspSecondRecordForm();
            String enterpriseId = RandomUtil.getUUID();
            gspEnterpriseInfoForm.setEnterpriseId(enterpriseId);
            if(gspEnterpriceFrom == null || BeanUtils.isEmptyFrom(gspEnterpriseInfoForm)){
                return Json.error("企业基础信息不能为空");
            }
            gspEnterpriseInfoService.addGspEnterpriseInfo(gspEnterpriseInfoForm);
            if(gspBusinessLicenseForm != null && !BeanUtils.isEmptyFrom(gspBusinessLicenseForm)){
                gspBusinessLicenseForm.setEnterpriseId(enterpriseId);
                gspBusinessLicenseService.addGspBusinessLicense(enterpriseId,gspBusinessLicenseForm,gspOperateLicenseForm.getScopArr(),gspBusinessLicenseForm.getBusinessId(),gspBusinessLicenseForm.getOpType());
            }
            if(gspOperateLicenseForm != null && !BeanUtils.isEmptyFrom(gspOperateLicenseForm)){
                gspOperateLicenseForm.setEnterpriseId(enterpriseId);
                gspOperateLicenseService.addGspOperateLicense(enterpriseId,gspOperateLicenseForm,gspOperateLicenseForm.getScopArr(),gspOperateLicenseForm.getOperateId(),gspOperateLicenseForm.getOpType());
            }
            if(gspSecondRecordForm != null && !BeanUtils.isEmptyFrom(gspSecondRecordForm)){
                gspSecondRecordForm.setEnterpriseId(enterpriseId);
                gspSecondRecordService.addGspSecondRecord(enterpriseId,gspSecondRecordForm,gspSecondRecordForm.getScopArr(),gspSecondRecordForm.getRecordId(),gspSecondRecordForm.getOpType());
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

    /**
     * 编辑企业信息
     * @param gspEnterpriceFrom
     * @return
     */
    public Json editGspEnterprice(String enterpriceId,GspEnterpriceFrom gspEnterpriceFrom){
        try{
            GspEnterpriseInfoForm gspEnterpriseInfoForm = gspEnterpriceFrom.getGspEnterpriseInfoForm();
            GspBusinessLicenseForm gspBusinessLicenseForm = gspEnterpriceFrom.getGspBusinessLicenseForm();
            GspOperateLicenseForm gspOperateLicenseForm = gspEnterpriceFrom.getGspOperateLicenseForm();
            GspSecondRecordForm gspSecondRecordForm = gspEnterpriceFrom.getGspSecondRecordForm();
            GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoService.getGspEnterpriseInfo(enterpriceId);
            if(gspEnterpriseInfo == null){
                return Json.error("查询不到对应的企业信息");
            }
            if(BeanUtils.isEmptyFrom(gspEnterpriseInfoForm)){
                return Json.error("企业基础信息不能为空");
            }
            Json json = gspEnterpriseInfoService.editGspEnterpriseInfo(gspEnterpriseInfoForm);
            if(gspBusinessLicenseForm != null && !BeanUtils.isEmptyFrom(gspBusinessLicenseForm)){
                gspBusinessLicenseService.editGspBusinessLicense(gspBusinessLicenseForm);
            }
            if(gspOperateLicenseForm != null && !BeanUtils.isEmptyFrom(gspOperateLicenseForm)){
                gspOperateLicenseService.editGspOperateLicense(gspOperateLicenseForm);
            }
            if(gspSecondRecordForm != null && !BeanUtils.isEmptyFrom(gspSecondRecordForm)){
                gspSecondRecordService.editGspSecondRecord(gspSecondRecordForm);
            }
            return json;
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Json.error("编辑失败");
        }
    }

    /**
     * 删除企业信息（至为无效）
     * @param enterpriceId
     * @return
     */
    public Json deleteGspEnterprice(String enterpriceId){
        try{

            GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoService.getGspEnterpriseInfo(enterpriceId);
            if(gspEnterpriseInfo == null){
                return Json.error("企业信息不存在！");
            }
            if(gspEnterpriseInfo.getIsUse().equals(Constant.IS_USE_NO)){
                return Json.error("该数据已失效！");
            }

            gspEnterpriseInfoService.updateGspEnterpriseInfoActiveTag(enterpriceId, Constant.IS_USE_NO);

            //查询营业执照
            GspBusinessLicenseQuery gspBusinessLicenseQuery = new GspBusinessLicenseQuery();
            gspBusinessLicenseQuery.setEnterpriseId(enterpriceId);
            GspBusinessLicense gspBusinessLicense = gspBusinessLicenseService.getGspBusinessLicenseBy(gspBusinessLicenseQuery);
            if(gspBusinessLicense!=null){
                gspBusinessLicenseService.updateGspBusinessLicenseActiveTag(gspBusinessLicense.getBusinessId()+"",Constant.IS_USE_NO);
            }

            //查询许可证
            GspOperateLicenseQuery gspOperateLicenseQuery = new GspOperateLicenseQuery();
            gspOperateLicenseQuery.setEnterpriseId(enterpriceId);
            GspOperateLicense gspOperateLicense = gspOperateLicenseService.getGspOperateLicenseBy(gspOperateLicenseQuery);
            if(gspOperateLicense!=null){
                gspOperateLicenseService.updateGspOperateLicenseActiveTag(gspOperateLicense.getOperateId()+"",Constant.IS_USE_NO);
            }

            //查询备案
            GspSecondRecordQuery gspSecondRecordQuery = new GspSecondRecordQuery();
            gspSecondRecordQuery.setEnterpriseId(enterpriceId);
            GspSecondRecord gspSecondRecord = gspSecondRecordService.getGspSecondRecordBy(gspSecondRecordQuery);
            if(gspSecondRecord!=null){
                gspSecondRecordService.updateGspSecondRecordActiveTag(gspSecondRecord.getRecordId()+"",Constant.IS_USE_NO);
            }

            //TODO 需要补上企业关联的所有单据信息失效
            return Json.error("删除成功，数据已失效");
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return Json.error("删除失败");
    }
    /**
     * 删除企业信息
     * @param enterpriceId 企业信息流水号
     * @return
     */
    public Json delete(String enterpriceId){
        GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoService.getGspEnterpriseInfo(enterpriceId);
        if(gspEnterpriseInfo == null){
            return Json.error("企业信息不存在！");
        }
        gspEnterpriseInfoMybatisDao.deleteGspEnterprise(enterpriceId);
        return Json.success("",true);
    }

    /**
     * 获取企业基础信息
     * @param enterpriceId 企业信息流水号
     * @return
     */
    public Json getGspEnterpriceInfo(String enterpriceId){
        GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoService.getGspEnterpriseInfo(enterpriceId);
        if(gspEnterpriseInfo == null){
            return Json.error("企业信息不存在！");
        }
        return Json.success("",gspEnterpriseInfo);
    }

    /**
     * 获取企业营业执照信息
     * @param enterpriceId 企业信息流水号
     * @return
     */
    public Json getGspBusinessLicense(String enterpriceId){
        GspBusinessLicenseQuery query = new GspBusinessLicenseQuery();
        query.setEnterpriseId(enterpriceId);
        query.setIsUse(Constant.IS_USE_YES);
        GspBusinessLicense gspBusinessLicense = gspBusinessLicenseService.getGspBusinessLicenseBy(query);
        return Json.success("",gspBusinessLicense);
    }

    /**
     * 获取企业经营/生产许可证信息
     * @param enterpriceId 企业信息流水号
     * @return
     */
    public Json getGspOperateLicense(String enterpriceId){
        GspOperateLicenseQuery query = new GspOperateLicenseQuery();
        query.setEnterpriseId(enterpriceId);
        query.setIsUse(Constant.IS_USE_YES);
        GspOperateLicense gspOperateLicense = gspOperateLicenseService.getGspOperateLicenseBy(query);
        return Json.success("",gspOperateLicense);
    }

    /**
     * 获取备案信息
     * @param enterpriceId 企业信息流水号
     * @return
     */
    public Json getGspSecondRecord(String enterpriceId){
        GspSecondRecordQuery query = new GspSecondRecordQuery();
        query.setEnterpriseId(enterpriceId);
        query.setIsUse(Constant.IS_USE_YES);
        GspSecondRecord gspSecondRecord = gspSecondRecordService.getGspSecondRecordBy(query);
        return Json.success("",gspSecondRecord);
    }

}