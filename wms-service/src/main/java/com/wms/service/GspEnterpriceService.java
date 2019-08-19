package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.wms.constant.Constant;
import com.wms.dto.GspEnterpriseBusinessDTO;
import com.wms.dto.GspEnterpriseTypeDTO;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.query.GspBusinessLicenseQuery;
import com.wms.query.GspEnterpriseInfoQuery;
import com.wms.query.GspOperateLicenseQuery;
import com.wms.query.GspSecondRecordQuery;
import com.wms.utils.BeanUtils;
import com.wms.utils.RandomUtil;
import com.wms.vo.GspOperateDetailVO;
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
    private GspBusinessLicenseMybatisDao gspBusinessLicenseMybatisDao;
    @Autowired
    private GspOperateDetailService gspOperateDetailService;
    @Autowired
    private DataPublishService dataPublishService;
    @Autowired
    private BasCustomerMybatisDao basCustomerMybatisDao;

    /**
     * 新增企业信息
     * @param gspEnterpriceFrom 提交数据
     * @return
     */
    public Json addGspEnterprice(GspEnterpriceFrom gspEnterpriceFrom) throws Exception{
        //try{

            GspEnterpriseInfoForm gspEnterpriseInfoForm = gspEnterpriceFrom.getGspEnterpriseInfoForm();
            GspBusinessLicenseForm gspBusinessLicenseForm = gspEnterpriceFrom.getGspBusinessLicenseForm();
            GspOperateLicenseForm gspOperateLicenseForm = gspEnterpriceFrom.getGspOperateLicenseForm();
            GspSecondRecordForm gspSecondRecordForm = gspEnterpriceFrom.getGspSecondRecordForm();
            //是否要创建新版本
            boolean enterpriseIsNewVersion = true;
            if(gspEnterpriceFrom == null || BeanUtils.isEmptyFrom(gspEnterpriseInfoForm)){
                return Json.error("企业基础信息不能为空");
            }
            String enterpriseId = gspEnterpriceFrom.getGspEnterpriseInfoForm().getEnterpriseId();
            if(StringUtils.isEmpty(enterpriseId)) {
                //新增判断企业编号是否重复
                if(!checkGspEnterpriceByEnterpriseNo(gspEnterpriseInfoForm.getEnterpriseNo())){
                    return Json.error("企业信息代码不能重复");
                }
                String code = gspEnterpriseInfoForm.getEnterpriseNo();
                String reg="^[a-zA-Z0-9]{6,16}$";
//                String pwd="1234567890abcd";
                boolean f=code.matches(reg);
                if(!f){
                    return Json.error("企业信息代码只能为数字字母");
                }
                gspEnterpriseInfoForm.setState(Constant.CODE_CATALOG_FIRSTSTATE_NEW);
                enterpriseId = RandomUtil.getUUID();
                gspEnterpriseInfoForm.setEnterpriseId(enterpriseId);
                gspEnterpriseInfoService.addGspEnterpriseInfo(gspEnterpriseInfoForm);

                //主体直接下发
                if(gspEnterpriseInfoForm.getEnterpriseType().equals(Constant.CODE_ENT_TYP_ZT)){
                    dataPublishService.publishDataWithOutApply(gspEnterpriseInfoForm);
                }
//                if(gspEnterpriseInfoForm.getEnterpriseType().equals(Constant.CODE_ENT_TYP_GW)){
////
////                }
            }else{
                //判断首营状态
                GspEnterpriseInfo info = gspEnterpriseInfoService.getGspEnterpriseInfo(enterpriseId);
                if(info == null){
                    return Json.error("企业信息不存在");
                }

                List<GspEnterpriseTypeDTO> enterpriseTypeDTOS = queryEnterpriseType(info.getEnterpriseId());
                if(enterpriseTypeDTOS!=null && enterpriseTypeDTOS.size()>0){
                    for(GspEnterpriseTypeDTO ent : enterpriseTypeDTOS){
                        if(ent.getFirstState().equals(Constant.CODE_CATALOG_FIRSTSTATE_CHECKING)){//首营申请审核中的数据不能修改
                            return Json.error("首营申请中的企业信息不能修改");
                        }else if(ent.getFirstState().equals(Constant.CODE_CATALOG_FIRSTSTATE_NEW)){
                            enterpriseIsNewVersion = false;
                        }else if(ent.getFirstState().equals(Constant.CODE_CATALOG_FIRSTSTATE_PASS)){
                            //TODO 重新下发新申请
                            //TODO 修改首营状态为已报废
                            dataPublishService.cancelData(ent.getApplyNo());
                        }
                    }

                    //创建新版本
                    if(enterpriseIsNewVersion){
                        gspEnterpriseInfoService.updateGspEnterpriseInfoActiveTag(enterpriseId,Constant.IS_USE_NO);

                        enterpriseId = RandomUtil.getUUID();
                        gspEnterpriseInfoForm.setState(Constant.CODE_CATALOG_FIRSTSTATE_NEW);
                        gspEnterpriseInfoForm.setEnterpriseId(enterpriseId);
                        gspEnterpriseInfoService.addGspEnterpriseInfo(gspEnterpriseInfoForm);


                        /**
                         * TODO 发起新申请
                         */
                    }

                }else{
                    enterpriseIsNewVersion = false;
                }
                if(enterpriseIsNewVersion == true){
                    //1.新建状态，更新原数据
                    gspEnterpriseInfoForm.setEnterpriseId(enterpriseId);
                    gspEnterpriseInfoService.editGspEnterpriseInfo(gspEnterpriseInfoForm);
                }

            }

            //判断经营范围 已作废不需要判断
            /*if(gspBusinessLicenseForm.getScopArr()!=null && gspOperateLicenseForm.getScopArr()!=null){
                if(operateDetailIsRight(initScope(gspBusinessLicenseForm.getScopArr()),initScope(gspOperateLicenseForm.getScopArr())) == false){
                    return Json.error("经营/生产许可证中经营范围与营业执照中经营范围不相等");
                }
            }*/

            //组装经营范围
            if(gspBusinessLicenseForm.getScopArr()!=null && !"".equals(gspBusinessLicenseForm.getScopArr())){
                gspBusinessLicenseForm.setScopArr(initScope(gspBusinessLicenseForm.getScopArr()));
            }
            if(gspOperateLicenseForm.getScopArr()!=null && !"".equals(gspOperateLicenseForm.getScopArr())){
                gspOperateLicenseForm.setScopArr(initScope(gspOperateLicenseForm.getScopArr()));
            }
            if(gspSecondRecordForm.getScopArr()!=null && !"".equals(gspSecondRecordForm.getScopArr())){
                gspSecondRecordForm.setScopArr(initScope(gspSecondRecordForm.getScopArr()));
            }

            if(gspBusinessLicenseForm != null){
                gspBusinessLicenseForm.setEnterpriseId(enterpriseId);
                if(enterpriseIsNewVersion == true){
                    gspBusinessLicenseForm.setOpType(Constant.LICENSE_SUBMIT_UPDATE);
                }
                gspBusinessLicenseService.addGspBusinessLicense(enterpriseId,gspBusinessLicenseForm,gspBusinessLicenseForm.getScopArr(),gspBusinessLicenseForm.getBusinessId(),gspBusinessLicenseForm.getOpType());
            }
            if(!BeanUtils.isEmptyFrom(gspOperateLicenseForm)){
                gspOperateLicenseForm.setEnterpriseId(enterpriseId);
                if(enterpriseIsNewVersion == true){
                    gspOperateLicenseForm.setOpType(Constant.LICENSE_SUBMIT_UPDATE);
                }
                gspOperateLicenseService.addGspOperateLicense(enterpriseId,gspOperateLicenseForm,gspOperateLicenseForm.getScopArr(),gspOperateLicenseForm.getOperateId(),gspOperateLicenseForm.getOpType());
            }
            if(!BeanUtils.isEmptyFrom(gspSecondRecordForm)){
                if(enterpriseIsNewVersion == true){
                    gspSecondRecordForm.setOpType(Constant.LICENSE_SUBMIT_UPDATE);
                }
                gspSecondRecordForm.setEnterpriseId(enterpriseId);
                gspSecondRecordService.addGspSecondRecord(enterpriseId,gspSecondRecordForm,gspSecondRecordForm.getScopArr(),gspSecondRecordForm.getRecordId(),gspSecondRecordForm.getOpType());
            }
            return Json.success("保存成功");
        /*}catch (Exception e){
            logger.info("企业信息新增失败"+e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Json.error("保存失败");
        }*/
    }

    /**
     * 编辑企业信息
     * @param gspEnterpriceFrom
     * @return
     */
    public Json editGspEnterprice(String enterpriceId,GspEnterpriceFrom gspEnterpriceFrom){
        System.out.println("==================="+gspEnterpriceFrom.getGspEnterpriseInfoForm().getEnterpriseType());
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
            //删除已下发的企业 需要把客户档案内记录置为不合作   所有关于该企业的首营申请全部报废
            basCustomerMybatisDao.delete(enterpriceId);

//            if(){
////
////            }

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
     * 获取营业执照过期数据
     * @param enterpriceId 企业id
     * @param type 类型
     * @return
     */
    public Json getBusinessLicenseOutTime(String enterpriceId,String type,Integer diffCount){
        List<GspEnterpriseBusinessDTO> list = gspEnterpriseInfoMybatisDao.queryBusinessLicenseOutTime(enterpriceId, type, diffCount);
        if(list!=null && list.size()>0){
            return Json.success("",list);
        }
        return Json.error("");
    }

    /**
     * 根据企业信息代码查询是否重复
     * @param enterpriseNo
     * @return
     */
    private boolean checkGspEnterpriceByEnterpriseNo(String enterpriseNo){
        GspEnterpriseInfoQuery query = new GspEnterpriseInfoQuery();
        query.setEnterpriseNo(enterpriseNo);
        query.setIsUse(Constant.IS_USE_YES);
        MybatisCriteria criteria = new MybatisCriteria();
        criteria.setCondition(query);
        List<GspEnterpriseInfo> enterpriseInfos = gspEnterpriseInfoMybatisDao.queryByList(criteria);
        if(enterpriseInfos!=null && enterpriseInfos.size()>0){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 查询企业身份信息
     * @param enterpriseId
     * @return
     */
    private List<GspEnterpriseTypeDTO> queryEnterpriseType(String enterpriseId){
        List<GspEnterpriseTypeDTO> list = gspEnterpriseInfoMybatisDao.queryEnterpriseApplyListById(enterpriseId);
        return list;
    }

    /**
     *
     * @param license,operateLicense
     * @return
     */
    private boolean operateDetailIsRight(String license,String operateLicense){
        if(operateLicense == null || operateLicense.equals("")){
            return true;
        }

        List<GspOperateDetailForm> licenseDetail = JSON.parseArray(license,GspOperateDetailForm.class);
        List<GspOperateDetailForm> operateDetail = JSON.parseArray(operateLicense,GspOperateDetailForm.class);
        List<String> arrlicense = new ArrayList<>();
        List<String> arroperate = new ArrayList<>();

        for(GspOperateDetailForm v : licenseDetail){
            arrlicense.add(v.getOperateId());
        }
        for(GspOperateDetailForm v : operateDetail){
            arroperate.add(v.getOperateId());
        }
        for(String s : arroperate){
            if(arrlicense.toString().indexOf(s)==-1){
                return false;
            }
        }
        return true;


    }

    /**
     * 初始化经营范围
     * @param scope
     * @return
     */
    private String initScope(String scope){
        String[] scopeArr = scope.split(",");
        StringBuffer resultArr = new StringBuffer();
        for(String str : scopeArr){
            resultArr.append("{");
            resultArr.append("enterpriseId:''");
            resultArr.append(",operateId:'"+str+"'");
            resultArr.append("},");

        }
        return "["+resultArr.substring(0,resultArr.length()-1)+"]";
    }

}