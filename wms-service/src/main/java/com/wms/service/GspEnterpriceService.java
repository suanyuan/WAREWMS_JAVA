package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.wms.constant.Constant;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.GspBusinessLicense;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.entity.GspOperateLicense;
import com.wms.entity.GspSecondRecord;
import com.wms.mybatis.dao.GspBusinessLicenseMybatisDao;
import com.wms.mybatis.dao.GspEnterpriseInfoMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.GspBusinessLicenseQuery;
import com.wms.query.GspOperateLicenseQuery;
import com.wms.query.GspSecondRecordQuery;
import com.wms.utils.BeanUtils;
import com.wms.utils.RandomUtil;
import com.wms.vo.GspBusinessLicenseVO;
import com.wms.vo.Json;
import com.wms.vo.form.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

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
            if(BeanUtils.isEmptyFrom(gspEnterpriseInfoForm)){
                return Json.error("企业基础信息不能为空");
            }
            gspEnterpriseInfoService.addGspEnterpriseInfo(gspEnterpriseInfoForm);
            if(gspBusinessLicenseForm != null && !BeanUtils.isEmptyFrom(gspBusinessLicenseForm)){
                gspBusinessLicenseForm.setEnterpriseId(enterpriseId);
                gspBusinessLicenseService.addGspBusinessLicense(gspBusinessLicenseForm);
            }
            if(gspOperateLicenseForm != null && !BeanUtils.isEmptyFrom(gspOperateLicenseForm)){
                gspOperateLicenseForm.setEnterpriseId(enterpriseId);
                gspOperateLicenseService.addGspOperateLicense(gspOperateLicenseForm);
            }
            if(gspSecondRecordForm != null && !BeanUtils.isEmptyFrom(gspSecondRecordForm)){
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

    /**
     * 营业执照新增
     * @param enterpriceId 企业id
     * @param businessFormStr
     * @param operateDetailStr
     * @param gspBusinessLicenseId 营业执照id
     * @return
     */
    public Json addGspBusinessLicense(String enterpriceId,String businessFormStr,String operateDetailStr,String gspBusinessLicenseId,String opType){
        try{
            GspBusinessLicenseForm gspBusinessLicenseForm = JSON.parseObject(businessFormStr,GspBusinessLicenseForm.class);
            List<GspOperateDetailForm> gspOperateDetailForm = JSON.parseArray(operateDetailStr,GspOperateDetailForm.class);
            if(StringUtils.isEmpty(enterpriceId)){
                return Json.error("请先保存企业基础信息");
            }
            if(gspBusinessLicenseForm == null || BeanUtils.isEmptyFrom(gspBusinessLicenseForm)){
                return Json.error("营业执照信息不全！");
            }
            if(gspOperateDetailForm == null || BeanUtils.isEmptyFrom(gspOperateDetailForm)){
                return Json.error("必须选择营业执照经营范围！");
            }
            //提交
            if(opType.equals(Constant.LICENSE_SUBMIT_ADD)){
                //新增
                if("".equals(gspBusinessLicenseId)){
                    gspBusinessLicenseId = RandomUtil.getUUID();
                    gspBusinessLicenseForm.setEnterpriseId(enterpriceId);
                    gspBusinessLicenseForm.setBusinessId(gspBusinessLicenseId);
                    gspBusinessLicenseService.addGspBusinessLicense(gspBusinessLicenseForm);

                    if(gspOperateDetailForm.size()>0){
                        for(GspOperateDetailForm g : gspOperateDetailForm){
                            g.setEnterpriseId(gspBusinessLicenseId);
                            gspOperateDetailService.addGspOperateDetail(g,Constant.LICENSE_TYPE_BUSINESS);
                        }
                    }
                }else{//更新
                    gspBusinessLicenseForm.setBusinessId(gspBusinessLicenseId);
                    gspBusinessLicenseService.editGspBusinessLicense(gspBusinessLicenseForm);
                    gspOperateDetailService.deleteGspOperateDetail(gspBusinessLicenseId,Constant.LICENSE_TYPE_BUSINESS);
                    if(gspOperateDetailForm.size()>0){
                        for(GspOperateDetailForm g : gspOperateDetailForm){
                            g.setEnterpriseId(gspBusinessLicenseId);
                            gspOperateDetailService.addGspOperateDetail(g,Constant.LICENSE_TYPE_BUSINESS);
                        }
                    }
                }
            }else if(opType.equals(Constant.LICENSE_SUBMIT_UPDATE)){//换证
                //把旧证数据作废
                gspBusinessLicenseService.updateGspBusinessLicenseTagById(gspBusinessLicenseId,Constant.IS_USE_NO);
                //保存新证数据
                String newBusinessLicenseId = RandomUtil.getUUID();
                gspBusinessLicenseForm.setEnterpriseId(enterpriceId);
                gspBusinessLicenseForm.setBusinessId(newBusinessLicenseId);
                gspBusinessLicenseService.addGspBusinessLicense(gspBusinessLicenseForm);

                if(gspOperateDetailForm.size()>0){
                    for(GspOperateDetailForm g : gspOperateDetailForm){
                        g.setEnterpriseId(newBusinessLicenseId);
                        gspOperateDetailService.addGspOperateDetail(g,Constant.LICENSE_TYPE_BUSINESS);
                    }
                }
            }
            return Json.error("提交营业执照失败");
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Json.error("系统错误");
        }
    }

    /**
     * 查询营业执照历史记录
     * @param pager
     * @param query
     * @return
     */
    public EasyuiDatagrid<GspBusinessLicenseVO> getGspBusinessLicenseHistory(EasyuiDatagridPager pager, GspBusinessLicenseQuery query){
        EasyuiDatagrid<GspBusinessLicenseVO> datagrid = new EasyuiDatagrid<>();
        List<GspBusinessLicenseVO> gspBusinessLicenseVOList = new ArrayList<>();
        if(query.getEnterpriseId().equals("")){
            MybatisCriteria criteria = new MybatisCriteria();
            criteria.setCondition(query);
            criteria.setCurrentPage(pager.getPage());
            criteria.setPageSize(pager.getRows());
            criteria.setOrderByClause("create_date desc");

            List<GspBusinessLicense> businessLicenseVOS = gspBusinessLicenseMybatisDao.queryByList(criteria);
            for(GspBusinessLicense g : businessLicenseVOS){
                GspBusinessLicenseVO vo = new GspBusinessLicenseVO();
                org.springframework.beans.BeanUtils.copyProperties(g,vo);
                gspBusinessLicenseVOList.add(vo);
            }
            int total = gspBusinessLicenseMybatisDao.queryByCount(criteria);
            datagrid.setTotal(Long.parseLong(total+""));
            datagrid.setRows(gspBusinessLicenseVOList);
        }else {
            datagrid.setTotal(0L);
            datagrid.setRows(gspBusinessLicenseVOList);
        }
        return datagrid;
    }
}