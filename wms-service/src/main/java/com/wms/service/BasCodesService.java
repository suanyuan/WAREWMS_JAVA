package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.query.BasCodesQuery;
import com.wms.result.PdaResult;
import com.wms.utils.*;
import com.wms.vo.BasCodesVO;
import com.wms.vo.Json;
import com.wms.vo.form.BasCodesForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/6/27
 */
@Service("BasCodesService")
public class BasCodesService {

    @Autowired
    private BasCodesMybatisDao basCodesMybatisDao;

    @Autowired
    private GspReceivingMybatisDao gspReceivingMybatisDao;
    @Autowired
    private GspEnterpriceService gspEnterpriceService;
    @Autowired
    private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;
    @Autowired
    private GspBusinessLicenseMybatisDao gspBusinessLicenseMybatisDao;
    @Autowired
    private GspOperateLicenseMybatisDao gspOperateLicenseMybatisDao;

    @Autowired
    private GspFirstRecordMybatisDao gspFirstRecordMybatisDao;
    @Autowired
    private GspSecondRecordMybatisDao gspSecondRecordMybatisDao;
    @Autowired
    private GspMedicalRecordMybatisDao gspMedicalRecordMybatisDao;
    @Autowired
    private GspSupplierMybatisDao gspSupplierMybatisDao;
    @Autowired
    private BasCarrierLicenseMybatisDao basCarrierLicenseMybatisDao;
    @Autowired
    private QcMeteringDeviceMybatisDao qcMeteringDeviceMybatisDao;
    @Autowired
    private BasSkuLeakMybatisDao basSkuLeakMybatisDao;

    @Autowired
    private GspOperateDateTimeService gspOperateDateTimeService;
    @Autowired
    private InvLotAttMybatisDao invLotAttMybatisDao;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");


    public EasyuiDatagrid<BasCodesVO> getPagedDatagrid(EasyuiDatagridPager pager, BasCodesQuery query) {
        EasyuiDatagrid<BasCodesVO> datagrid = new EasyuiDatagrid<BasCodesVO>();
        query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<BasCodes> basCodesList = basCodesMybatisDao.queryByList(mybatisCriteria);

        List<BasCodesVO> basCodesVOList = new ArrayList<BasCodesVO>();
        for (BasCodes basCodes : basCodesList) {
            BasCodesVO basCodesVO = new BasCodesVO();
            BeanUtils.copyProperties(basCodes, basCodesVO);
            basCodesVOList.add(basCodesVO);
        }
        datagrid.setTotal((long)basCodesMybatisDao.queryByCount(mybatisCriteria));
        datagrid.setRows(basCodesVOList);
        return datagrid;
    }

    public Json addBasCodes(BasCodesForm basCodesForm) throws Exception {
        Json json = new Json();
        BasCodes basCodes = new BasCodes();
        Date date = new Date(System.currentTimeMillis());
        basCodesForm.setAddtime(date);
        basCodesForm.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
        BeanUtils.copyProperties(basCodesForm, basCodes);
        basCodesMybatisDao.add(basCodes);
        json.setSuccess(true);
        return json;
    }

    public Json editBasCodes(BasCodesForm basCodesForm) {
        Json json = new Json();
//        BasCodes basCodes = basCodesMybatisDao.queryById(basCodesForm.getCodeid());
//        BeanUtils.copyProperties(basCodesForm, basCodes);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        basCodesForm.setEdittime(date);
        basCodesForm.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
        basCodesMybatisDao.updateBySelective(basCodesForm);
        json.setSuccess(true);
        return json;
    }

    public Json deleteBasCodes(String codeid,String code) {
        Json json = new Json();
//        BasCodes basCodes = basCodesMybatisDao.queryById(id);
//        if(basCodes != null){
            basCodesMybatisDao.delByIDCode(codeid,code);
//        }
        json.setSuccess(true);
        return json;
    }

    public List<EasyuiCombobox> getTransactionTypeCombobox() {
        List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
        EasyuiCombobox combobox = null;
        List<BasCodes> bascodesList = basCodesMybatisDao.queryByTransactionType();
        if(bascodesList != null && bascodesList.size() > 0){
            for(BasCodes basCodes : bascodesList){
                combobox = new EasyuiCombobox();
                combobox.setId(basCodes.getCodeid());
                combobox.setValue(basCodes.getUdf2());
                comboboxList.add(combobox);
            }
        }
        return comboboxList;
    }

    /**
     * 查询码表内容
     */
    public BasCodes queryBasCode(String codeid, String code) {

        BasCodesQuery query = new BasCodesQuery();
        query.setCodeid(codeid);
        query.setCode(code);
        return basCodesMybatisDao.queryById(query);
    }



    /**
     * 分页查询 历史档案管理页面
     * @param
     * @return
     */
    //历史档案管理
    public EasyuiDatagrid<BasCodesVO> showHistoryFileDatagrid(EasyuiDatagridPager pager, BasCodesQuery query) {
        EasyuiDatagrid<BasCodesVO> datagrid = new EasyuiDatagrid<BasCodesVO>();
        MybatisCriteria criteria = new MybatisCriteria();
        criteria.setCurrentPage(pager.getPage());
        criteria.setPageSize(pager.getRows());
        query.setCodeid("HIS_FILE");
        criteria.setCondition(query);
        BasCodes basGtnVO = null;
//		List<Remind> basGtnVOList = new ArrayList<Remind>();
//		System.out.println();
//		List<Remind> remindList = .queryByList(criteria);
        List<BasCodesVO> basCodesVOList = new ArrayList<BasCodesVO>();
        List<BasCodes> list =  basCodesMybatisDao.queryByList(criteria);
        for (BasCodes basCodes : list) {
            BasCodesVO basCodesVO = new BasCodesVO();
//            BeanUtils.copyProperties(basCodes, basCodesVO);
            basCodesVO.setCodenameC(basCodes.getCodenameC());
            basCodesVO.setCodenameE(basCodes.getCodenameE());
            basCodesVO.setEditwho(basCodes.getEditwho());
            if(basCodes.getEdittime()!=null){
                basCodesVO.setEdittime(basCodes.getEdittime());
            }
            basCodesVOList.add(basCodesVO);
        }
//		int total = basGtnMybatisDao.queryByCount(criteria);
        datagrid.setTotal((long)basCodesVOList.size() );
        datagrid.setRows(basCodesVOList);
        return datagrid;
    }
//历史档案管理
    public Json getHistoryFileInfo(BasCodesQuery query){
        BasCodes basCodes = basCodesMybatisDao.queryByIdAndCodenameC(query);
        if(basCodes == null){
            return Json.error("历史文档不存在！");
        }
        return Json.success("",basCodes);
    }
//历史档案管理
    public Json editDocAsnCertificate(BasCodesForm basCodesForm) {
        Json json = new Json();
        //DocAsnCertificate docAsnCertificate = docAsnCertificateMybatisDao.findById(docAsnCertificateForm.getSku());
        //BeanUtils.copyProperties(docAsnCertificateForm, docAsnCertificate);
        basCodesForm.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
        basCodesMybatisDao.updateCodenameCBySelective(basCodesForm);
        json.setSuccess(true);
        return json;
    }

    /**
     * 根据codeid查询code
     * @param codeid
     * @return
     */
    public List<EasyuiCombobox> getBy(String codeid){
        List<EasyuiCombobox> baseCodesVOList = new ArrayList<>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        Map<String,Object> map = new HashMap<>();
        map.put("codeid",codeid);
        mybatisCriteria.setCondition(map);
        mybatisCriteria.setOrderByClause("show_sequence");
        List<BasCodes> list =  basCodesMybatisDao.queryByList(mybatisCriteria);
        if(list!=null && list.size()>0){
            EasyuiCombobox easyuiCombobox = new EasyuiCombobox();
            easyuiCombobox.setId("");
            easyuiCombobox.setValue("");
            //easyuiCombobox.setSelected(true);
            baseCodesVOList.add(easyuiCombobox);
            for(BasCodes b : list){
                easyuiCombobox = new EasyuiCombobox();
                easyuiCombobox.setId(b.getCode());
                easyuiCombobox.setValue(b.getCodenameC());
                baseCodesVOList.add(easyuiCombobox);
            }

        }
        return baseCodesVOList;
    }

    /**
     * 根据codeid查询code
     * @param codeid
     * @return
     */
    public List<EasyuiCombobox> getByIsUse(String codeid){
        List<EasyuiCombobox> baseCodesVOList = new ArrayList<>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        Map<String,Object> map = new HashMap<>();
        map.put("codeid",codeid);
        map.put("select","00");
        mybatisCriteria.setCondition(map);
        mybatisCriteria.setOrderByClause("show_sequence");
        List<BasCodes> list =  basCodesMybatisDao.queryByList(mybatisCriteria);
        if(list!=null && list.size()>0){
            EasyuiCombobox easyuiCombobox = new EasyuiCombobox();
            easyuiCombobox.setId("");
            easyuiCombobox.setValue("");
            //easyuiCombobox.setSelected(true);
            baseCodesVOList.add(easyuiCombobox);
            for(BasCodes b : list){
                easyuiCombobox = new EasyuiCombobox();
                easyuiCombobox.setId(b.getCode());
                easyuiCombobox.setValue(b.getCodenameC());
                baseCodesVOList.add(easyuiCombobox);
            }

        }
        return baseCodesVOList;
    }

    /**
     * 收货单位首营申请  通过收货单位查询货主
     * @param enterpriseId
     * @return
     */
    public List<EasyuiCombobox> getClientByRecevingId(String enterpriseId){
        List<EasyuiCombobox> baseCodesVOList = new ArrayList<>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        Map<String,Object> map = new HashMap<>();
//        map.put("codeid",codeid);
        map.put("isUse","1");
        map.put("enterpriseId",enterpriseId);
        map.put("firstState","40");
        mybatisCriteria.setCondition(map);
//        mybatisCriteria.setOrderByClause("show_sequence");
        List<GspReceiving> list =  gspReceivingMybatisDao.queryByList(mybatisCriteria);
        if(list!=null && list.size()>0){
            EasyuiCombobox easyuiCombobox = new EasyuiCombobox();
            easyuiCombobox.setId("");
            easyuiCombobox.setValue("");
            //easyuiCombobox.setSelected(true);
            System.out.println();
            baseCodesVOList.add(easyuiCombobox);
            for(GspReceiving b : list){
                easyuiCombobox = new EasyuiCombobox();
                easyuiCombobox.setId(b.getReceivingId());
                easyuiCombobox.setValue(b.getClientId());
                baseCodesVOList.add(easyuiCombobox);
            }

        }
        return baseCodesVOList;
    }

    /**
     * 企业过期

     * @param
     * @return
     */
    public void remind() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat();
        int num = 0;
        boolean flag = true;


        List<String> enterpriseIdList = new ArrayList<>() ;
        MybatisCriteria criteriaEnt = new MybatisCriteria();
        List<String> enterpriseNoList =gspEnterpriseInfoMybatisDao.queryEnterpriseNo();
        for(String no:enterpriseNoList){
            //所有企业
            GspEnterpriseInfo g =gspEnterpriseInfoMybatisDao.queryByEnterpriseNo(no);
            enterpriseIdList.add(g.getEnterpriseId());
        }
        long num11=0 ;
        List<String> enterpriseIdList11 = new ArrayList<>() ;
        String enterpriseIdList11Str ="";
        long num12=0 ;
        List<String> enterpriseIdList12 = new ArrayList<>() ;
        String enterpriseIdList12Str ="";
        long num13=0 ;
        List<String> enterpriseIdList13 = new ArrayList<>() ;
        String enterpriseIdList13Str ="";
        long num14=0 ;
        List<String> enterpriseIdList14 = new ArrayList<>() ;
        String enterpriseIdList14Str ="";
        long num15=0 ;
        List<String> enterpriseIdList15 = new ArrayList<>() ;
        String enterpriseIdList15Str ="";
        long num16=0 ;
        List<String> enterpriseIdList16 = new ArrayList<>() ;
        String enterpriseIdList16Str ="";
        long num17=0 ;
        List<String> enterpriseIdList17 = new ArrayList<>() ;
        String enterpriseIdList17Str ="";
        long num18=0 ;
        String enterpriseIdList18Str ="";
        List<String> enterpriseIdList18 = new ArrayList<>() ;
        long num19=0 ;
        String enterpriseIdList19Str ="";
        List<String> enterpriseIdList19 = new ArrayList<>() ;
        long num20=0 ;
        String enterpriseIdList20Str ="";
        List<String> enterpriseIdList20 = new ArrayList<>() ;
        long num21=0 ;
        String enterpriseIdList21Str ="";
        List<String> enterpriseIdList21 = new ArrayList<>() ;
        long num22=0 ;
        String enterpriseIdList22Str ="";
        List<String> enterpriseIdList22 = new ArrayList<>() ;
        long num23=0 ;
        String enterpriseIdList23Str ="";
        List<String> enterpriseIdList23 = new ArrayList<>() ;
        long num24=0 ;
        String enterpriseIdList24Str ="";
        List<String> enterpriseIdList24 = new ArrayList<>() ;
        long num25=0 ;
        String enterpriseIdList25Str ="";
        List<String> enterpriseIdList25 = new ArrayList<>() ;
        long num26=0 ;
        String enterpriseIdList26Str ="";
        List<String> enterpriseIdList26 = new ArrayList<>() ;
        long num27=0 ;
        String enterpriseIdList27Str ="";
        List<String> enterpriseIdList27= new ArrayList<>() ;
        long num28=0 ;
        String enterpriseIdList28Str ="";
        List<String> enterpriseIdList28 = new ArrayList<>() ;
        long num29=0 ;
        String enterpriseIdList29Str ="";
        List<String> enterpriseIdList29= new ArrayList<>() ;
        long num30=0 ;
        String enterpriseIdList30Str ="";
        List<String> enterpriseIdList30 = new ArrayList<>() ;
        long num31=0 ;
        String enterpriseIdList31Str ="";
        List<String> enterpriseIdList31 = new ArrayList<>() ;
        long num32=0 ;
        String enterpriseIdList32Str ="";
        List<String> enterpriseIdList32 = new ArrayList<>() ;
        long num33=0 ;
        String enterpriseIdList33Str ="";
        List<String> enterpriseIdList33 = new ArrayList<>() ;
        long num34=0 ;
        String enterpriseIdList34Str ="";
        List<String> enterpriseIdList34 = new ArrayList<>() ;
        long num35=0 ;
        String enterpriseIdList35Str ="";
        List<String> enterpriseIdList35 = new ArrayList<>() ;
        long num36=0 ;
        String enterpriseIdList36Str ="";
        List<String> enterpriseIdList36 = new ArrayList<>() ;
        java.util.Date now = new java.util.Date();



        //库内货品   过效期

        List<InvLotAtt> InvLotAttList =  invLotAttMybatisDao.queryAll();
        if(InvLotAttList!=null){
            for(InvLotAtt invLotAtt:InvLotAttList){
                if(invLotAtt!=null){
                    if(invLotAtt.getLotatt02()!=null){
                        java.util.Date endDate =sdf2.parse(invLotAtt.getLotatt02());

                        if(endDate.getTime()-now.getTime()>0){
                            long diff =endDate.getTime()-now.getTime();
                            long days = diff / (1000 * 60 * 60 * 24);
                            if(days<180){
                                //近效期30天
//                                enterpriseIdList11.add(enterpriseId);
                                num11++;
                                enterpriseIdList11Str =  JSON.toJSONString(enterpriseIdList11);
                            }
                            if(days<90){
//                                enterpriseIdList11.add(enterpriseId);
                                num12++;
                                enterpriseIdList11Str =  JSON.toJSONString(enterpriseIdList12);
                            }
                            if(days<30){
//                                enterpriseIdList11.add(enterpriseId);
                                num13++;
                                enterpriseIdList11Str =  JSON.toJSONString(enterpriseIdList13);
                            }
                        }else{
                            //已过期
                            num14++;
//                            enterpriseIdList14.add(enterpriseId);
                            enterpriseIdList14Str =  JSON.toJSONString(enterpriseIdList14);
                        }
                    }
                }
            }
        }
        BasCodes b= new BasCodes();
        b.setCodeid("remind");
        b.setUdf1(enterpriseIdList11Str);
        b.setShowSequence(num11);
        b.setCode("11");
        basCodesMybatisDao.updateBySelective(b);
        b.setUdf1(enterpriseIdList12Str);
        b.setShowSequence(num12);
        b.setCode("12");
        basCodesMybatisDao.updateBySelective(b);
        b.setUdf1(enterpriseIdList13Str);
        b.setShowSequence(num13);
        b.setCode("13");
        basCodesMybatisDao.updateBySelective(b);
        b.setUdf1(enterpriseIdList14Str);
        b.setShowSequence(num14);
        b.setCode("14");
        basCodesMybatisDao.updateBySelective(b);

        //营业
        for(String enterpriseId:enterpriseIdList){
            GspBusinessLicense gspBusinessLicense = gspBusinessLicenseMybatisDao.selectCompareByEnterpriseId(enterpriseId);
            if(gspBusinessLicense!=null){
                if(gspBusinessLicense.getBusinessEndDate()!=null){
                    if(gspBusinessLicense.getBusinessEndDate().getTime()-now.getTime()>0){

                        long diff =gspBusinessLicense.getBusinessEndDate().getTime()-now.getTime();
                        long days = diff / (1000 * 60 * 60 * 24);
                        if(days<30){
                            //近效期30天
                            enterpriseIdList15.add(enterpriseId);
                            num15++;
                            enterpriseIdList15Str =  JSON.toJSONString(enterpriseIdList15);
                        }
                    }else{
                        //已过期
                        enterpriseIdList16.add(enterpriseId);
                        enterpriseIdList16Str =  JSON.toJSONString(enterpriseIdList16);
                        num16++;
                    }
                }
            }
        }
//        BasCodes b= new BasCodes();
//        b.setCodeid("remind");
        b.setUdf1(enterpriseIdList15Str);
        b.setShowSequence(num15);
        b.setCode("15");
        basCodesMybatisDao.updateBySelective(b);
        b.setShowSequence(num16);
        b.setCode("16");
        b.setUdf1(enterpriseIdList16Str);
        basCodesMybatisDao.updateBySelective(b);

        //经营
        for(String enterpriseId:enterpriseIdList){
            GspOperateLicense gspOperateLicense = gspOperateLicenseMybatisDao.selectByEnterprisId(enterpriseId,"operate");
            if(gspOperateLicense!=null){
                if(gspOperateLicense.getLicenseExpiryDate()!=null){
                    if(gspOperateLicense.getLicenseExpiryDate().getTime()-now.getTime()>0){
                        long diff =gspOperateLicense.getLicenseExpiryDate().getTime()-now.getTime();
                        long days = diff / (1000 * 60 * 60 * 24);
                        if(days<30){
                            //近效期30天
                            enterpriseIdList17.add(enterpriseId);
                            num17++;
                            enterpriseIdList17Str =  JSON.toJSONString(enterpriseIdList17);
                        }
                    }else{
                        //已过期
                        enterpriseIdList18.add(enterpriseId);
                        enterpriseIdList18Str =  JSON.toJSONString(enterpriseIdList18);
                        num18++;
                    }
                }
            }
        }
        b.setCode("17");
        b.setShowSequence(num17);
        b.setUdf1(enterpriseIdList17Str);
        basCodesMybatisDao.updateBySelective(b);
        b.setCode("18");
        b.setShowSequence(num18);
        b.setUdf1(enterpriseIdList18Str);
        basCodesMybatisDao.updateBySelective(b);
        //生产
        for(String enterpriseId:enterpriseIdList){
            GspOperateLicense gspProdLicense = gspOperateLicenseMybatisDao.selectByEnterprisId(enterpriseId,"prod");
            if(gspProdLicense!=null){
                if(gspProdLicense.getLicenseExpiryDate()!=null){
                    if(gspProdLicense.getLicenseExpiryDate().getTime()-now.getTime()>0){
                        long diff =gspProdLicense.getLicenseExpiryDate().getTime()-now.getTime();
                        long days = diff / (1000 * 60 * 60 * 24);
                        if(days<30){
                            //近效期30天
                            enterpriseIdList19.add(enterpriseId);
                            num19++;
                            enterpriseIdList19Str =  JSON.toJSONString(enterpriseIdList19);
                        }
                    }else{
                        //已过期
                        enterpriseIdList20.add(enterpriseId);
                        enterpriseIdList20Str =  JSON.toJSONString(enterpriseIdList20);
                        num20++;
                    }
                }
            }
        }
        b.setCode("19");
        b.setShowSequence(num19);
        b.setUdf1(enterpriseIdList19Str);
        basCodesMybatisDao.updateBySelective(b);
        b.setCode("20");
        b.setShowSequence(num20);
        b.setUdf1(enterpriseIdList20Str);
        basCodesMybatisDao.updateBySelective(b);

        //医疗
        for(String enterpriseId:enterpriseIdList){
            GspMedicalRecord gspMedicalRecord = gspMedicalRecordMybatisDao.selectCompareByEnterprisId(enterpriseId);
            if(gspMedicalRecord!=null){
                if(gspMedicalRecord.getLicenseExpiryDateEnd()!=null){
                    if(gspMedicalRecord.getLicenseExpiryDateEnd().getTime()-now.getTime()>0){
                        long diff =gspMedicalRecord.getLicenseExpiryDateEnd().getTime()-now.getTime();
                        long days = diff / (1000 * 60 * 60 * 24);
                        if(days<30){
                            //近效期30天
                            enterpriseIdList21.add(enterpriseId);
                            num21++;
                            enterpriseIdList21Str =  JSON.toJSONString(enterpriseIdList21);
                        }
                    }else{
                        //已过期
                        enterpriseIdList22.add(enterpriseId);
                        enterpriseIdList22Str =  JSON.toJSONString(enterpriseIdList22);
                        num22++;
                    }
                }
            }
        }
        b.setCode("21");
        b.setShowSequence(num21);
        b.setUdf1(enterpriseIdList21Str);
        basCodesMybatisDao.updateBySelective(b);
        b.setCode("22");
        b.setShowSequence(num22);
        b.setUdf1(enterpriseIdList22Str);
        basCodesMybatisDao.updateBySelective(b);


        //合同  //授权
        List<GspSupplier> supplierAndclient =gspSupplierMybatisDao.querySupplierAndClient();
        for(GspSupplier customer:supplierAndclient){
            if(customer!=null){
                if(customer.getClientEndDate()!=null){
                    if(customer.getClientEndDate().getTime()-now.getTime()>0){
                        long diff =customer.getClientEndDate().getTime()-now.getTime();
                        long days = diff / (1000 * 60 * 60 * 24);
                        if(days<30){
                            //近效期30天
                            enterpriseIdList23.add(customer.getSupplierId());
                            num23++;
                            enterpriseIdList23Str =  JSON.toJSONString(enterpriseIdList23);
                        }
                    }else{
                        //已过期
                        enterpriseIdList24.add(customer.getSupplierId());
                        enterpriseIdList24Str =  JSON.toJSONString(enterpriseIdList24);
                        num24++;
                    }
                }

                if(customer.getEmpowerEnddate()!=null){
                    if(customer.getEmpowerEnddate().getTime()-now.getTime()>0){
                        long diff =customer.getEmpowerEnddate().getTime()-now.getTime();
                        long days = diff / (1000 * 60 * 60 * 24);
                        if(days<30){
                            //近效期30天
                            enterpriseIdList25.add(customer.getSupplierId());
                            num25++;
                            enterpriseIdList25Str =  JSON.toJSONString(enterpriseIdList25);
                        }
                    }else{
                        //已过期
                        enterpriseIdList26.add(customer.getSupplierId());
                        enterpriseIdList26Str =  JSON.toJSONString(enterpriseIdList26);
                        num26++;
                    }
                }
            }
        }
        //合同
        b.setCode("23");
        b.setShowSequence(num23);
        b.setUdf1(enterpriseIdList23Str);
        basCodesMybatisDao.updateBySelective(b);
        b.setCode("24");
        b.setShowSequence(num24);
        b.setUdf1(enterpriseIdList24Str);
        basCodesMybatisDao.updateBySelective(b);
        //授权
        b.setCode("25");
        b.setShowSequence(num25);
        b.setUdf1(enterpriseIdList25Str);
        basCodesMybatisDao.updateBySelective(b);
        b.setCode("26");
        b.setShowSequence(num26);
        b.setUdf1(enterpriseIdList26Str);
        basCodesMybatisDao.updateBySelective(b);

        //快递
        MybatisCriteria criteria = new MybatisCriteria();

        List<BasCarrierLicense> c = basCarrierLicenseMybatisDao.queryByList(criteria);
        for(BasCarrierLicense basCarrierLicense:c){

//            GspBusinessLicense gspBusinessLicense = gspBusinessLicenseMybatisDao.selectCompareByEnterpriseId(basCarrierLicense.getEnterpriseId());
            //道路经营
            if(basCarrierLicense.getRoadNumberTerm()!=null){
                java.util.Date roadNumberEnddate =sdf2.parse(basCarrierLicense.getRoadNumberTerm());
                if(roadNumberEnddate.getTime()-now.getTime()>0){
                    long diff =roadNumberEnddate.getTime()-now.getTime();
                    long days = diff / (1000 * 60 * 60 * 24);
                    if(days<30){
                        //近效期30天
                        enterpriseIdList27.add(basCarrierLicense.getEnterpriseId());
                        num27++;
                        enterpriseIdList27Str =  JSON.toJSONString(enterpriseIdList27);
                    }
                }else{
                    //已过期
                    enterpriseIdList28.add(basCarrierLicense.getEnterpriseId());
                    enterpriseIdList28Str =  JSON.toJSONString(enterpriseIdList28);
                    num28++;
                }
            }
            //快递经营
            if(basCarrierLicense.getCarrierEndDate()!=null){
                if(basCarrierLicense.getCarrierEndDate().getTime()-now.getTime()>0){
                    long diff =basCarrierLicense.getCarrierEndDate().getTime()-now.getTime();
                    long days = diff / (1000 * 60 * 60 * 24);
                    if(days<30){
                        //近效期30天
                        enterpriseIdList29.add(basCarrierLicense.getEnterpriseId());
                        num29++;
                        enterpriseIdList29Str =  JSON.toJSONString(enterpriseIdList29);
                    }
                }else{
                    //已过期
                    enterpriseIdList30.add(basCarrierLicense.getEnterpriseId());
                    enterpriseIdList30Str =  JSON.toJSONString(enterpriseIdList30);
                    num30++;
                }
            }
            //合同
            if(basCarrierLicense.getClientEndDate()!=null){
                if(basCarrierLicense.getClientEndDate().getTime()-now.getTime()>0){
                    long diff =basCarrierLicense.getClientEndDate().getTime()-now.getTime();
                    long days = diff / (1000 * 60 * 60 * 24);
                    if(days<30){
                        //近效期30天
                        enterpriseIdList31.add(basCarrierLicense.getEnterpriseId());
                        num31++;
                        enterpriseIdList31Str =  JSON.toJSONString(enterpriseIdList31);
                    }
                }else{
                    //已过期
                    enterpriseIdList32.add(basCarrierLicense.getEnterpriseId());
                    enterpriseIdList32Str =  JSON.toJSONString(enterpriseIdList32);
                    num32++;
                }
            }
        }
        //道路经营
        b.setCode("27");
        b.setShowSequence(num27);
        b.setUdf1(enterpriseIdList27Str);
        basCodesMybatisDao.updateBySelective(b);
        b.setCode("28");
        b.setShowSequence(num28);
        b.setUdf1(enterpriseIdList28Str);
        basCodesMybatisDao.updateBySelective(b);
        //快递经营
        b.setCode("29");
        b.setShowSequence(num29);
        b.setUdf1(enterpriseIdList29Str);
        basCodesMybatisDao.updateBySelective(b);
        b.setCode("30");
        b.setShowSequence(num30);
        b.setUdf1(enterpriseIdList30Str);
        basCodesMybatisDao.updateBySelective(b);
        //合同
        b.setCode("31");
        b.setShowSequence(num31);
        b.setUdf1(enterpriseIdList31Str);
        basCodesMybatisDao.updateBySelective(b);
        b.setCode("32");
        b.setShowSequence(num32);
        b.setUdf1(enterpriseIdList32Str);
        basCodesMybatisDao.updateBySelective(b);






        //库内货品   养护提醒
        List<InvLotLocId> list = gspOperateDateTimeService.getSkuDisDay(DateUtil.format(new java.util.Date(),"yyyy-MM-dd"));
        b.setCode("33");
        b.setShowSequence((long)list.size());
        b.setUdf1("");
        basCodesMybatisDao.updateBySelective(b);






        //计量设备
        List<QcMeteringDevice> qcMeteringDeviceList = qcMeteringDeviceMybatisDao.queryByList(criteria);
        for(QcMeteringDevice qcMeteringDevice:qcMeteringDeviceList){
            if(qcMeteringDevice!=null){
                if(qcMeteringDevice.getCalTerm()!=null){
                    java.util.Date qcMeteringDeviceEnddate =sdf2.parse(qcMeteringDevice.getCalTerm());
                    if(qcMeteringDeviceEnddate.getTime()-now.getTime()>0){
                        long diff =qcMeteringDeviceEnddate.getTime()-now.getTime();
                        long days = diff / (1000 * 60 * 60 * 24);
                        if(days<30){
                            //近效期30天
                            enterpriseIdList34.add(qcMeteringDevice.getCalId());
                            num34++;
                            enterpriseIdList34Str =  JSON.toJSONString(enterpriseIdList34);
                        }
                    }else{
                        //已过期
                        enterpriseIdList35.add(qcMeteringDevice.getCalId());
                        enterpriseIdList35Str =  JSON.toJSONString(enterpriseIdList35);
                        num35++;
                    }
                }
            }
        }
        //计量设备
        b.setCode("34");
        b.setShowSequence(num34);
        b.setUdf1(enterpriseIdList34Str);
        basCodesMybatisDao.updateBySelective(b);
        b.setCode("35");
        b.setShowSequence(num35);
        b.setUdf1(enterpriseIdList35Str);
        basCodesMybatisDao.updateBySelective(b);

        //未备案产品

        List<BasSkuLeak> basSkuLeak = basSkuLeakMybatisDao.queryByList(criteria);
        num36 = basSkuLeak.size();
        b.setCode("36");
        b.setShowSequence(num36);
        b.setUdf1("All");
        basCodesMybatisDao.updateBySelective(b);



    }




    /**
     * 验证PDA请求版本
     */
    public Json verifyRequestValidation(String version) {

        Json json = new Json();
        Map<String, Object> resultMap = new HashMap<>();
        if (StringUtil.isEmpty(version)) {

            PdaResult result = new PdaResult(PdaResult.CODE_FAILURE, "当前版本过旧，请根据首页右上角个人中心中的更新流程进行更新");
            resultMap.put(Constant.RESULT, result);
            json.setSuccess(false);
            json.setObj(resultMap);
            return json;
        }

        BasCodesQuery basCodesQuery = new BasCodesQuery();
        basCodesQuery.setCodeid(Constant.CODE_CATALOG_PDA_VERSION);
        basCodesQuery.setCode(Constant.CODE_PDA_VERSION);
        BasCodes basCodes = basCodesMybatisDao.queryById(basCodesQuery);
        if (null == basCodes || StringUtil.isEmpty(basCodes.getUdf1())) {

            PdaResult result = new PdaResult(PdaResult.CODE_FAILURE, "服务器配置出错，请联系管理员");
            resultMap.put(Constant.RESULT, result);
            json.setSuccess(false);
            json.setObj(resultMap);
            return json;
        }

        if (!version.equals(basCodes.getUdf1())) {
            PdaResult result = new PdaResult(PdaResult.CODE_FAILURE, "当前版本过旧，请根据首页右上角个人中心中的更新流程进行更新");
            resultMap.put(Constant.RESULT, result);
            json.setSuccess(false);
            json.setObj(resultMap);
            return json;
        }

        return Json.success("");
    }



}