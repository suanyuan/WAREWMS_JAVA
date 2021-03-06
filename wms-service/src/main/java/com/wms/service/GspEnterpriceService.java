package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.wms.constant.Constant;
import com.wms.dto.GspEnterpriseBusinessDTO;
import com.wms.dto.GspEnterpriseTypeDTO;
import com.wms.entity.*;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.*;
import com.wms.query.*;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.utils.ExcelUtil;
import com.wms.utils.RandomUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.GspOperateDetailVO;
import com.wms.vo.Json;
import com.wms.vo.form.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Autowired
    private GspFirstRecordService gspFirstRecordService;
    @Autowired
    private GspMedicalRecordService gspMedicalRecordService;
    @Autowired
    private GspOperateDetailMybatisDao gspOperateDetailMybatisDao;

    @Autowired
    private FirstBusinessApplyMybatisDao firstBusinessApplyMybatisDao;

    @Autowired
    private GspInstrumentCatalogMybatisDao gspInstrumentCatalogMybatisDao;


//    @Autowired
//    private GspBusinessLicenseMybatisDao gspBusinessLicenseMybatisDao;
    @Autowired
    private GspOperateLicenseMybatisDao gspOperateLicenseMybatisDao;

    @Autowired
    private GspFirstRecordMybatisDao gspFirstRecordMybatisDao;
    @Autowired
    private GspSecondRecordMybatisDao gspSecondRecordMybatisDao;
    @Autowired
    private GspMedicalRecordMybatisDao gspMedicalRecordMybatisDao;

    /**
     * 新增企业信息
     * @param gspEnterpriceFrom 提交数据
     * @return
     */
    public Json addGspEnterprice(GspEnterpriceFrom gspEnterpriceFrom) throws Exception{
        //try{

            GspEnterpriseInfoForm gspEnterpriseInfoForm = gspEnterpriceFrom.getGspEnterpriseInfoForm();
            String  textContent =gspEnterpriseInfoForm.getEnterpriseNo().trim();
            while (textContent.startsWith("　")) {//这里判断是不是全角空格
                textContent = textContent.substring(1, textContent.length()).trim();
            }
            while (textContent.endsWith("　")) {
                textContent = textContent.substring(0, textContent.length() - 1).trim();
            }
            gspEnterpriseInfoForm.setEnterpriseNo(textContent);

            GspBusinessLicenseForm gspBusinessLicenseForm = gspEnterpriceFrom.getGspBusinessLicenseForm();
            GspOperateLicenseForm gspOperateLicenseForm = gspEnterpriceFrom.getGspOperateLicenseForm();
            GspSecondRecordForm gspSecondRecordForm = gspEnterpriceFrom.getGspSecondRecordForm();
            GspOperateLicenseForm gspProdLicenseForm = gspEnterpriceFrom.getGspProdLicenseForm(); //生产
            GspMedicalRecordForm gspMedicalRecordForm  =  gspEnterpriceFrom.getGspMedicalRecordForm();

            GspFirstRecordForm gspFirstRecordForm = gspEnterpriceFrom.getGspFirstRecordForm();

            //是否要创建新版本
            boolean enterpriseIsNewVersion = true;
            if(gspEnterpriceFrom == null || BeanUtils.isEmptyFrom(gspEnterpriseInfoForm)){
                return Json.error("企业基础信息不能为空");
            }
            String enterpriseId = gspEnterpriceFrom.getGspEnterpriseInfoForm().getEnterpriseId();

            String oldEnterpriseId = enterpriseId;

            if(StringUtils.isEmpty(enterpriseId)) {
                //新增判断企业编号是否重复
                if(!checkGspEnterpriceByEnterpriseNo(gspEnterpriseInfoForm.getEnterpriseNo())){
                    return Json.error("企业信息代码不能重复");
                }
                String code = gspEnterpriseInfoForm.getEnterpriseNo();


                Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                Matcher m = p.matcher(code );
                if (m.find()) {
                    return Json.error("企业信息代码不能有中文");
                }
//                String reg="^[a-zA-Z0-9\\s\\/\\&]{2,100}$";
//                String reg = "[\\u4e00-\\u9fa5]{0,100}$";
//                String pwd="1234567890abcd";
//                boolean f=code.matches(reg);
//                if(f){
//                    return Json.error("企业信息代码只能为2到16位数字字母");
//                }
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

                //查询是否减少了分类目录 或者 修改了证照编号
                List<GspOperateDetail> gspProdDetailList =null;
                List<GspOperateDetail> gspOperateDetailList =null;
                List<GspOperateDetail> gspFirstRecordDetailList =null;
                List<GspOperateDetail> gspSecondRecordDetailList =null;


                GspBusinessLicense gspBusinessLicense = gspBusinessLicenseMybatisDao.selectCompareByEnterpriseId(oldEnterpriseId);

                GspOperateLicense oldGspProdLicense = gspOperateLicenseMybatisDao.selectByEnterprisId(oldEnterpriseId,Constant.LICENSE_TYPE_PROD);
                if(oldGspProdLicense!=null){
                    gspProdDetailList = gspOperateDetailMybatisDao.queryListByLicenseId(oldGspProdLicense.getOperateId());
                }
                GspOperateLicense oldGspOperateLicense = gspOperateLicenseMybatisDao.selectByEnterprisId(oldEnterpriseId,Constant.LICENSE_TYPE_OPERATE);
                if(oldGspOperateLicense!=null){
                    gspOperateDetailList = gspOperateDetailMybatisDao.queryListByLicenseId(oldGspOperateLicense.getOperateId());
                }
                GspFirstRecord oldGspFirstRecord = gspFirstRecordMybatisDao.selectByEnterprisId(oldEnterpriseId);
                if(oldGspFirstRecord!=null){
                    gspFirstRecordDetailList = gspOperateDetailMybatisDao.queryListByLicenseId(oldGspFirstRecord.getRecordId());

                }
                GspSecondRecord oldGspSecondRecord = gspSecondRecordMybatisDao.selectByEnterprisId(oldEnterpriseId);
                if(oldGspSecondRecord!=null){
                    gspSecondRecordDetailList = gspOperateDetailMybatisDao.queryListByLicenseId(oldGspSecondRecord.getRecordId());
                }

                GspMedicalRecord oldGspMedicalRecord = gspMedicalRecordMybatisDao.selectCompareByEnterprisId(oldEnterpriseId);


                boolean BusinessResult = false;
                boolean ProdResult = false;
                boolean OperateResult = false;
                boolean FirstResult = false;
                boolean SecondResult = false;
                boolean MedicalResult = false;
//                            String st=new String(request.getParameter("st").getBytes("ISO-8859-1").trim());
//                            String stt = new String("借阅".getBytes("ISO-8859-1"),"UTF-8");
                //减少的分类目录集合
                List<String> Flist = new ArrayList<String>();

                //TODO 判断是否（分类目录减少，证号变更）
                if(gspBusinessLicense!=null){
                    if(gspBusinessLicense.getLicenseNumber().equals(gspBusinessLicenseForm.getLicenseNumber())){
//                                    flag = false;
//                                    BusinessResult = false;
                    }else{
                        //证号变更   报废关联
                        BusinessResult = true;
                    }

                }

                if(oldGspProdLicense!=null){
                    if(oldGspProdLicense.getLicenseNo().equals(gspProdLicenseForm.getLicenseNo())){

                    }else{
                        //证号变更
                        ProdResult = true;
                    }
                    String scope =  gspProdLicenseForm.getScopArr();
                    String[] scopeArr = scope.split(",");//新分类目录

//                                if(gspProdDetailList!=null){
//
//                                }
                    for(GspOperateDetail s : gspProdDetailList){//老分类目录
                        boolean flag = true;
                        String operateId = s.getOperateId();
                        for(String str : scopeArr){             //新分类目录

                            if(str.trim().equals(operateId.trim())){
                                flag = false;
                            }
                        }
                        if(flag){
                            //分类目录减少了  创建新版本  正常换证流程
//                                    enterpriseIsNewVersion = true;
                            Flist.add(operateId);
                            ProdResult = true;
//                            break;
                        }else{
                            //不报废企业不报废关联申请不创建新版本   创建新企业证照版本
//                                    enterpriseIsNewVersion = false;
                        }
                    }


                }

                if(oldGspOperateLicense!=null){
                    if(oldGspOperateLicense.getLicenseNo().equals(gspOperateLicenseForm.getLicenseNo())){

                    }else{
                        //证号变更
                        OperateResult = true;
                    }
                    String scope =  gspOperateLicenseForm.getScopArr();
                    String[] scopeArr = scope.split(",");//新分类目录

                    for(GspOperateDetail s : gspOperateDetailList){//老分类目录
                        boolean flag = true;
                        String operateId = s.getOperateId();
                        for(String str : scopeArr){             //新分类目录
                            if(str.trim().equals(operateId.trim())){
                                flag = false;
                                System.out.println();
                            }
                        }
                        if(flag){
                            //分类目录减少了  创建新版本  正常换证流程
//                                    enterpriseIsNewVersion = true;
                            Flist.add(operateId);
                            OperateResult = true;
//                            break;
                        }else{
                            //不报废企业不报废关联申请不创建新版本   创建新企业证照版本
//                                    enterpriseIsNewVersion = false;
                        }
                    }





                }
                if(oldGspFirstRecord!=null){
                    if(oldGspFirstRecord.getRecordNo().equals(gspFirstRecordForm.getRecordNo())){

                    }else{
                        //证号变更
                        FirstResult = true;
                    }
                    String scope =  gspFirstRecordForm.getScopArr();
                    String[] scopeArr = scope.split(",");//新分类目录

                    for(GspOperateDetail s : gspFirstRecordDetailList){//老分类目录
                        boolean flag = true;
                        String operateId = s.getOperateId();
                        System.out.println();
                        for(String str : scopeArr){             //新分类目录
                            if(str.trim().equals(operateId.trim())){
                                flag = false;
                            }
                        }
                        if(flag){
                            //分类目录减少了  创建新版本  正常换证流程
//                          enterpriseIsNewVersion = true;
                            Flist.add(operateId);
                            ProdResult = true;
//                            break;
                        }else{
                            //不报废企业不报废关联申请不创建新版本   创建新企业证照版本
//                          enterpriseIsNewVersion = false;
                        }

                    }




                }
                if(oldGspSecondRecord!=null){
                    if(oldGspSecondRecord.getRecordNo().equals(gspSecondRecordForm.getRecordNo())){

                    }else{
                        //证号变更
                        SecondResult = true;
                    }
                    String scope =  gspSecondRecordForm.getScopArr();
                    String[] scopeArr = scope.split(",");//新分类目录

                    for(GspOperateDetail s : gspSecondRecordDetailList){//老分类目录
                        boolean flag = true;
                        String operateId = s.getOperateId();
                        for(String str : scopeArr){             //新分类目录
                            if(str.trim().equals(operateId.trim())){
                                flag = false;
                            }
                        }
                        if(flag){
                            System.out.println();
                            //分类目录减少了  创建新版本  正常换证流程
//                                    enterpriseIsNewVersion = true;
                            Flist.add(operateId);
                            OperateResult = true;
//                            break;
                        }else{
                            //不报废企业不报废关联申请不创建新版本   创建新企业证照版本
//                                    enterpriseIsNewVersion = false;
                        }
                    }



                }
                if(oldGspMedicalRecord!=null){
                    if(oldGspMedicalRecord.getMedicalRegisterNo().equals(gspMedicalRecordForm.getMedicalRegisterNo())){

                    }else{
                        MedicalResult = true;
                    }
                }




                //判断首营状态
                GspEnterpriseInfo info = gspEnterpriseInfoService.getGspEnterpriseInfo(enterpriseId);
                if(info == null){
                    return Json.error("企业信息不存在");
                }

                List<GspEnterpriseTypeDTO> enterpriseTypeDTOS = queryEnterpriseType(info.getEnterpriseId());
                if(enterpriseTypeDTOS!=null && enterpriseTypeDTOS.size()>0){ //判断是否有首营申请
                    int n=0;
                    for(GspEnterpriseTypeDTO ent : enterpriseTypeDTOS){
                        if(ent.getFirstState().equals(Constant.CODE_CATALOG_FIRSTSTATE_CHECKING)){//首营申请审核中的数据不能修改
                            return Json.error("首营申请中的企业信息不能修改");
                        }else if(ent.getFirstState().equals(Constant.CODE_CATALOG_FIRSTSTATE_NEW)){
                            n++;
                            if(n==enterpriseTypeDTOS.size()){
                                enterpriseIsNewVersion = false;
                            }
                        }else if(ent.getFirstState().equals(Constant.CODE_CATALOG_FIRSTSTATE_PASS)){
//                            if("update".equals(gspOperateLicenseForm.getOpType())
//                                    ||  "update".equals(gspProdLicenseForm.getOpType())
//                                    ||  "update".equals(gspSecondRecordForm.getOpType())
//                                    ||  "update".equals(gspFirstRecordForm.getOpType())
//                            ){
//                                //TODO 重新下发新申请
//                                //TODO 修改首营状态为已报废

//                                dataPublishService.cancelData(ent.getApplyNo());
//                            }else{
//                                return Json.error("请换证修改");
//                            }

                            if(BusinessResult||ProdResult||OperateResult||FirstResult||SecondResult||MedicalResult){
                                //创建新版本  报废关联申请、档案  新增企业证照版本
//                                if(flag){
                                enterpriseIsNewVersion = true;
                                if(ent.getApplyNo().indexOf(Constant.APLPRONO)!=-1){
                                    //产品报废  报废具体减少的分类目录下的注册证下的产品
                                    List<String> listNew = new ArrayList<String>(new HashSet(Flist));   //去重                                 System.out.println(uniqueList.toString());
                                    for(String operateId:listNew){   //分类目录id
//                                        GspInstrumentCatalog catalog1 = gspInstrumentCatalogMybatisDao.queryById(operateId)
//                                        if(catalog1!=null){
//                                            if(Constant.CODE_CATALOG_CLASSIFY_ONE.equals(catalog1.getClassifyId())){
//
//                                            }
//                                        }
                                        //判断产品首营申请的 产品 是否是在减少了的分类目录下
                                        List<FirstBusinessApply> firstBusinessApplyLista =  firstBusinessApplyMybatisDao.selectByOperateId(operateId,ent.getApplyNo());
                                        if(firstBusinessApplyLista.size()>0){
                                            dataPublishService.cancelData(ent.getApplyNo());
                                        }
                                    }



                                }else{
                                    //供应商   货主    收货单位   直接报废
                                    dataPublishService.cancelData(ent.getApplyNo());
                                }
//                                }
                            }else{
                                //不创建新版本 不报废关联申请、档案  新增企业证照版本
//                                enterpriseIsNewVersion = false;
                                n++;
                                if(n==enterpriseTypeDTOS.size()){
                                    enterpriseIsNewVersion = false;
                                }
                            }
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
                    enterpriseIsNewVersion = false;   //新建状态   没有首营申请过或首营状态为新建的企业
//

                }
                if(enterpriseIsNewVersion == false){
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
            if(gspProdLicenseForm.getScopArr()!=null && !"".equals(gspProdLicenseForm.getScopArr())){
                gspProdLicenseForm.setScopArr(initScope(gspProdLicenseForm.getScopArr()));
            }
            if(gspSecondRecordForm.getScopArr()!=null && !"".equals(gspSecondRecordForm.getScopArr())){
                gspSecondRecordForm.setScopArr(initScope(gspSecondRecordForm.getScopArr()));
            }
            if(gspFirstRecordForm.getScopArr()!=null && !"".equals(gspFirstRecordForm.getScopArr())){
                gspFirstRecordForm.setScopArr(initScope(gspFirstRecordForm.getScopArr()));
            }

            if(!BeanUtils.isEmptyFrom(gspBusinessLicenseForm)){
                gspBusinessLicenseForm.setEnterpriseId(enterpriseId);
                String is_h = gspBusinessLicenseForm.getOpType();
                if(enterpriseIsNewVersion == true){
                    gspBusinessLicenseForm.setOpType(Constant.LICENSE_SUBMIT_UPDATE);
                }
                gspBusinessLicenseService.addGspBusinessLicense(enterpriseId,is_h,enterpriseIsNewVersion,oldEnterpriseId,gspBusinessLicenseForm,gspBusinessLicenseForm.getScopArr(),gspBusinessLicenseForm.getBusinessId(),gspBusinessLicenseForm.getOpType());
            }
            if(!BeanUtils.isEmptyFrom(gspOperateLicenseForm)){      //经营
                String is_h = gspOperateLicenseForm.getOpType();
                gspOperateLicenseForm.setEnterpriseId(enterpriseId);
                if(enterpriseIsNewVersion == true){
                    gspOperateLicenseForm.setOpType(Constant.LICENSE_SUBMIT_UPDATE);
                }
//                gspProdLicenseForm.setOpType(Constant.LICENSE_TYPE_OPERATE);
                gspOperateLicenseForm.setOperateType(Constant.LICENSE_TYPE_OPERATE);
                gspOperateLicenseService.addGspOperateLicense(enterpriseId,is_h,enterpriseIsNewVersion,oldEnterpriseId,gspOperateLicenseForm,gspOperateLicenseForm.getScopArr(),gspOperateLicenseForm.getOperateId(),gspOperateLicenseForm.getOpType());
            }
            if(!BeanUtils.isEmptyFrom(gspProdLicenseForm)){         //生产
                String is_h = gspProdLicenseForm.getOpType();
                if(enterpriseIsNewVersion == true){
                    gspProdLicenseForm.setOpType(Constant.LICENSE_SUBMIT_UPDATE);
                }
                gspProdLicenseForm.setEnterpriseId(enterpriseId);
                gspProdLicenseForm.setOperateType(Constant.LICENSE_TYPE_PROD);
                gspOperateLicenseService.addGspOperateLicense(enterpriseId,is_h,enterpriseIsNewVersion,oldEnterpriseId,gspProdLicenseForm,gspProdLicenseForm.getScopArr(),gspProdLicenseForm.getOperateId(),gspProdLicenseForm.getOpType());
            }
            //医疗
            if(!BeanUtils.isEmptyFrom(gspMedicalRecordForm)){
                String is_h = gspMedicalRecordForm.getOpType();
                if(enterpriseIsNewVersion == true){
                    gspMedicalRecordForm.setOpType(Constant.LICENSE_SUBMIT_UPDATE);
                }
                gspMedicalRecordForm.setEnterpriseId(enterpriseId);
//                gspMedicalLicenseForm.setOperateType(Constant.LICENSE_TYPE_MEDICAL);
                gspMedicalRecordService.addGspMedicalRecord(enterpriseId,is_h,enterpriseIsNewVersion,oldEnterpriseId,gspMedicalRecordForm,gspMedicalRecordForm.getScopArr(),gspMedicalRecordForm.getMedicalId(),gspMedicalRecordForm.getOpType());
            }
            //一类
            if(!BeanUtils.isEmptyFrom(gspFirstRecordForm)){         //一类生产
                String is_h = gspFirstRecordForm.getOpType();
                if(enterpriseIsNewVersion == true){
                    gspFirstRecordForm.setOpType(Constant.LICENSE_SUBMIT_UPDATE);
                }
                System.out.println();
                gspFirstRecordForm.setEnterpriseId(enterpriseId);
//                gspFirstRecordForm.setOperateType(Constant.LICENSE_TYPE_FIRSTRECORD);
                gspFirstRecordService.addGspFirstRecord(enterpriseId,is_h,enterpriseIsNewVersion,oldEnterpriseId,gspFirstRecordForm,gspFirstRecordForm.getScopArr(),gspFirstRecordForm.getRecordId(),gspFirstRecordForm.getOpType());
            }

            if(!BeanUtils.isEmptyFrom(gspSecondRecordForm)){        //二类经营
                String is_h = gspSecondRecordForm.getOpType();
                if(enterpriseIsNewVersion == true){
                    gspSecondRecordForm.setOpType(Constant.LICENSE_SUBMIT_UPDATE);
                }
                gspSecondRecordForm.setEnterpriseId(enterpriseId);
                gspSecondRecordService.addGspSecondRecord(enterpriseId,is_h,enterpriseIsNewVersion,oldEnterpriseId,gspSecondRecordForm,gspSecondRecordForm.getScopArr(),gspSecondRecordForm.getRecordId(),gspSecondRecordForm.getOpType());
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
     * 获取企业是否存在 首营申请
     * @param enterpriceId 企业信息流水号
     * @return
     */
    public Json verify(String enterpriceId){
        String flag = "0";
        GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoService.getGspEnterpriseInfo(enterpriceId);
        if(gspEnterpriseInfo == null){
            return Json.error("企业信息不存在！");
        }


        List<GspEnterpriseTypeDTO> enterpriseTypeDTOS = queryEnterpriseType(enterpriceId);
        if(enterpriseTypeDTOS!=null && enterpriseTypeDTOS.size()>0) {
            flag = "1";
        }

        return Json.success("",flag);
    }

    /**
     * 查询6个证照是否存在,提示手动初始化（点击）
     * @param enterpriceId 企业信息流水号
     * @return
     */
    public Json selectSixLicense(String enterpriceId){
//        String flag = "0";
        GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoService.getGspEnterpriseInfo(enterpriceId);
        if(gspEnterpriseInfo == null){
            return Json.error("企业信息不存在！");
        }
        GspEnterpriseInfo flag = new GspEnterpriseInfo();

        GspOperateLicense prodLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(enterpriceId,Constant.LICENSE_TYPE_PROD);
        GspOperateLicense operateLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(enterpriceId,Constant.LICENSE_TYPE_OPERATE);
        GspFirstRecord firstRecord = gspFirstRecordMybatisDao.selectCompareByEnterprisId(enterpriceId);
        GspSecondRecord secondRecord = gspSecondRecordMybatisDao.selectCompareByEnterprisId(enterpriceId);
        GspMedicalRecord medicalRecord = gspMedicalRecordMybatisDao.selectCompareByEnterprisId(enterpriceId);
        if(prodLicense!=null){
            flag.setProdLicenseFlag("1");
        }else{
            flag.setProdLicenseFlag("0");
        }
        if(operateLicense!=null){
            flag.setOperateLicenseFlag("1");
        }else{
            flag.setOperateLicenseFlag("0");
        }
        if(firstRecord!=null){
            flag.setFirstRecordFlag("1");
        }else{
            flag.setFirstRecordFlag("0");
        }
        if(secondRecord!=null){
            flag.setSecondRecordFlag("1");
        }else{
            flag.setSecondRecordFlag("0");
        }
        if(medicalRecord!=null){
            flag.setMedicalRecordFlag("1");
        }else{
            flag.setMedicalRecordFlag("0");
        }
        return Json.success("",flag);
    }



    /**
     * 获取企业营业执照信息
     * @param enterpriceId 企业信息流水号
     * @return
     */
    public Json getGspBusinessLicense(String enterpriceId){
        GspBusinessLicenseQuery query = new GspBusinessLicenseQuery();
        query.setEnterpriseId(enterpriceId);

//        GspEnterpriseInfo gspEnterpriseInfo =gspEnterpriseInfoMybatisDao.queryById(query.getEnterpriseId());
//        if("1".equals(gspEnterpriseInfo.getIsUse())){
//            query.setIsUse(Constant.IS_USE_YES);
//        }else{
//            query.setIsUse(Constant.IS_USE_NO);
////            criteria.setTotalCount(1);
//        }

        GspBusinessLicense gspBusinessLicense = gspBusinessLicenseService.getGspBusinessLicenseBy(query);
        if(gspBusinessLicense==null){
            query.setIsUse(Constant.IS_USE_NO);
            gspBusinessLicense = gspBusinessLicenseService.getGspBusinessLicenseBy(query);
        }
        return Json.success("",gspBusinessLicense);
    }
    /**
     * 获取医疗执照信息
     * @param enterpriceId 企业信息流水号
     * @return
     */
    public Json getGspMedicalRecord(String enterpriceId){
        GspMedicalRecordQuery query = new GspMedicalRecordQuery();
        query.setEnterpriseId(enterpriceId);
        query.setIsUse(Constant.IS_USE_YES);
//        GspEnterpriseInfo gspEnterpriseInfo =gspEnterpriseInfoMybatisDao.queryById(query.getEnterpriseId());
//        if("1".equals(gspEnterpriseInfo.getIsUse())){
//            query.setIsUse(Constant.IS_USE_YES);
//        }else{
//            query.setIsUse(Constant.IS_USE_NO);
////            criteria.setTotalCount(1);
//        }
        GspMedicalRecord gspMedicalRecord = gspMedicalRecordService.getGspMedicalRecordBy(query);
        if(gspMedicalRecord==null){
            query.setIsUse(Constant.IS_USE_NO);
            gspMedicalRecord = gspMedicalRecordService.getGspMedicalRecordBy(query);
        }
        return Json.success("",gspMedicalRecord);
    }
    /**
     * 获取企业经营许可证信息
     * @param enterpriceId 企业信息流水号
     * @return
     */
    public Json getGspOperateLicense(String enterpriceId){
        GspOperateLicenseQuery query = new GspOperateLicenseQuery();
        query.setEnterpriseId(enterpriceId);
        query.setIsUse(Constant.IS_USE_YES);
//        GspEnterpriseInfo gspEnterpriseInfo =gspEnterpriseInfoMybatisDao.queryById(query.getEnterpriseId());
//        if("1".equals(gspEnterpriseInfo.getIsUse())){
//            query.setIsUse(Constant.IS_USE_YES);
//        }else{
//            query.setIsUse(Constant.IS_USE_NO);
////            criteria.setTotalCount(1);
//        }

        query.setOperateType(Constant.LICENSE_TYPE_OPERATE);
        GspOperateLicense gspOperateLicense = gspOperateLicenseService.getGspOperateLicenseBy(query);
        if(gspOperateLicense==null){
            query.setIsUse(Constant.IS_USE_NO);
            gspOperateLicense = gspOperateLicenseService.getGspOperateLicenseBy(query);
        }
        return Json.success("",gspOperateLicense);
    }



    /**
     * 获取企业生产许可证信息
     * @param enterpriceId 企业信息流水号
     * @return
     */
    public Json getGspProdLicense(String enterpriceId){
        GspOperateLicenseQuery query = new GspOperateLicenseQuery();
        query.setEnterpriseId(enterpriceId);
        query.setIsUse(Constant.IS_USE_YES);

//        GspEnterpriseInfo gspEnterpriseInfo =gspEnterpriseInfoMybatisDao.queryById(query.getEnterpriseId());
//        if("1".equals(gspEnterpriseInfo.getIsUse())){
//            query.setIsUse(Constant.IS_USE_YES);
//        }else{
//            query.setIsUse(Constant.IS_USE_NO);
////            criteria.setTotalCount(1);
//        }

        query.setOperateType(Constant.LICENSE_TYPE_PROD);

        GspOperateLicense gspOperateLicense = gspOperateLicenseService.getGspOperateLicenseBy(query);
        if(gspOperateLicense==null){
            query.setIsUse(Constant.IS_USE_NO);
            gspOperateLicense = gspOperateLicenseService.getGspOperateLicenseBy(query);
        }


        return Json.success("",gspOperateLicense);
    }




    /**
     * 获取一类备案信息
     * @param enterpriceId 企业信息流水号
     * @return
     */
    public Json getGspFirstRecord(String enterpriceId){
        GspFirstRecordQuery query = new GspFirstRecordQuery();
        query.setEnterpriseId(enterpriceId);
        query.setIsUse(Constant.IS_USE_YES);
//        GspEnterpriseInfo gspEnterpriseInfo =gspEnterpriseInfoMybatisDao.queryById(query.getEnterpriseId());


        GspFirstRecord gspFirstRecord = gspFirstRecordService.getGspFirstRecordBy(query);
//        GspOperateLicense gspOperateLicense = gspOperateLicenseService.getGspOperateLicenseBy(query);
        if(gspFirstRecord==null){
            query.setIsUse(Constant.IS_USE_NO);
            gspFirstRecord = gspFirstRecordService.getGspFirstRecordBy(query);
        }




        return Json.success("",gspFirstRecord);
    }

    /**
     * 获取二类备案信息
     * @param enterpriceId 企业信息流水号
     * @return
     */
    public Json getGspSecondRecord(String enterpriceId){
        GspSecondRecordQuery query = new GspSecondRecordQuery();
        query.setEnterpriseId(enterpriceId);
        query.setIsUse(Constant.IS_USE_YES);
//        GspEnterpriseInfo gspEnterpriseInfo =gspEnterpriseInfoMybatisDao.queryById(query.getEnterpriseId());
//        if("1".equals(gspEnterpriseInfo.getIsUse())){
//            query.setIsUse(Constant.IS_USE_YES);
//        }else{
//            query.setIsUse(Constant.IS_USE_NO);
////            criteria.setTotalCount(1);
//        }
        GspSecondRecord gspSecondRecord = gspSecondRecordService.getGspSecondRecordBy(query);
        if(gspSecondRecord==null){
            query.setIsUse(Constant.IS_USE_NO);
            gspSecondRecord = gspSecondRecordService.getGspSecondRecordBy(query);
        }
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
     * 企业过期

     * @param
     * @return
     */
    public Object enterpriseOutTime() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat();
        int num = 0;
        boolean flag = true;
        List<GspEnterpriseInfo> list = gspEnterpriseInfoMybatisDao.queryIsUse1ByAll();
        for(GspEnterpriseInfo s:list){
            String enterpriseid = s.getEnterpriseId();
//            GspEnterpriseInfo GYS = gspEnterpriseInfoMybatisDao.selectEnterpriseByCompare(enterpriseid);
            GspBusinessLicense ClientBusiness = gspBusinessLicenseMybatisDao.selectCompareByEnterpriseId(enterpriseid);
            GspOperateLicense GspProdLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(enterpriseid,Constant.LICENSE_TYPE_PROD);
            GspOperateLicense GspOperateLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(enterpriseid,Constant.LICENSE_TYPE_OPERATE);
            GspFirstRecord GspFirstRecord = gspFirstRecordMybatisDao.selectCompareByEnterprisId(enterpriseid);
            GspSecondRecord GspSecondRecord = gspSecondRecordMybatisDao.selectCompareByEnterprisId(enterpriseid);
            GspMedicalRecord GspMedicalRecord = gspMedicalRecordMybatisDao.selectCompareByEnterprisId(enterpriseid);
            if(ClientBusiness!=null){
                if(ClientBusiness.getBusinessEndDate()!=null){
                    flag = ClientBusiness.getBusinessEndDate().getTime() >= new Date().getTime();
                }
            }
            if(GspProdLicense!=null){
                flag = GspProdLicense.getLicenseExpiryDate().getTime() >= new Date().getTime();
            }
            if(GspOperateLicense!=null){
                flag = GspOperateLicense.getLicenseExpiryDate().getTime() >=new Date().getTime();
            }
            //备案没有有效期
//            if(GspFirstRecord!=null){
//                flag = GspFirstRecord.getApproveDate().getTime() >=new Date().getTime();
//            }
//            if(GspSecondRecord!=null){
//                flag = GspSecondRecord.getApproveDate().getTime() >=new Date().getTime();
//            }
            if(GspMedicalRecord!=null){
                flag = GspMedicalRecord.getLicenseExpiryDateEnd().getTime() >=new Date().getTime();
            }
            if(!flag){
                //报废企业
                List<GspEnterpriseTypeDTO> enterpriseTypeDTOS = queryEnterpriseType(enterpriseid);
                if(enterpriseTypeDTOS!=null && enterpriseTypeDTOS.size()>0){ //判断是否有首营申请
//                    int n=0;
                    for(GspEnterpriseTypeDTO ent : enterpriseTypeDTOS){
                        dataPublishService.cancelData(ent.getApplyNo());
                    }
                }
                num++;
                gspEnterpriseInfoService.updateGspEnterpriseInfoActiveTag(enterpriseid,Constant.IS_USE_NO);
            }

        }



        return num;
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
        query.setSelect("enterprise");
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
            str = str.trim();
            resultArr.append("{");
            resultArr.append("enterpriseId:''");
            resultArr.append(",operateId:'"+str+"'");
            resultArr.append("},");

        }
        return "["+resultArr.substring(0,resultArr.length()-1)+"]";
    }


//    /**
//     * 判断是否创建新版本报废关联首营申请和档案
//     * @param newfenlei
//     * @return
//     */
//    private boolean inithuanzheng(String newfenlei,String oldfenlei,boolean result){
//        result = false;
//        String scope =  newfenlei.getScopArr();
//        String[] scopeArr = scope.split(",");//新分类目录
//        boolean flag = true;
//        for(GspOperateDetail s : oldfenlei){//老分类目录
//            String operateId = s.getOperateId();
//            for(String str : scopeArr){             //新分类目录
//                if(!str.equals(operateId)){
//                    flag = false;
//                }
//            }
//        }
//        if(!flag){
//            //分类目录减少了  创建新版本  正常换证流程
////                                    enterpriseIsNewVersion = true;
//            result = true;
//        }else{
//            //不报废企业不报废关联申请不创建新版本   创建新企业证照版本
////                                    enterpriseIsNewVersion = false;
//        }
//        return result;
//    }

}