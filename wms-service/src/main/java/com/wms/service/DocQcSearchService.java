package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.SfcRole;
import com.wms.query.BasSkuQuery;
import com.wms.query.DocQcDetailsQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.StringUtil;
import com.wms.vo.DocQcDetailsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("docQcSearchService")
public class DocQcSearchService extends BaseService {

    @Autowired
    private DocQcDetailsMybatisDao docQcDetailsDao;
    @Autowired
    private DocQcDetailsMybatisDao docQcDetailsMybatisDao;
    @Autowired
    private BasSkuMybatisDao basSkuMybatisDao;
    @Autowired
    private BasPackageMybatisDao basPackageMybatisDao;
    @Autowired
    private BasCodesMybatisDao basCodesMybatisDao;
    @Autowired
    private BasCustomerMybatisDao basCustomerMybatisDao;
    @Autowired
    private DocAsnHeaderMybatisDao docAsnHeaderMybatisDao;
    @Autowired
    private SfcRoleMybatisDao sfcRoleMybatisDao;
    /**
     * 显示细单 分页 pano
     * @param pager
     * @param query
     * @return
     */
	public EasyuiDatagrid<DocQcDetailsVO> getPagedDatagrid(EasyuiDatagridPager pager, DocQcDetailsQuery query) {
        EasyuiDatagrid<DocQcDetailsVO> datagrid = new EasyuiDatagrid<>();
        List<DocQcDetailsVO> docQcDetailsVOList = new ArrayList<>();

        //登录用户角色是货主就只显示该货主的数据
        List<SfcRole> sfcUsersList =sfcRoleMybatisDao.queryRoleByID(SfcUserLoginUtil.getLoginUser().getId());
        for (SfcRole sfcRole:sfcUsersList){
            if(sfcRole.getRoleName().equals("货主")){
                query.setCustomerid(SfcUserLoginUtil.getLoginUser().getId());
            }
        }

        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<DocQcDetails> docQcDetailsList = docQcDetailsDao.queryByListSearch(mybatisCriteria);
        DocQcDetailsVO docQcDetailsVO = null;
        for (DocQcDetails docQcDetails : docQcDetailsList) {
            docQcDetailsVO = new DocQcDetailsVO();
            //计算件数
            docQcDetails.setQcqtyExpected(docQcDetails.getQcqtyExpected()-docQcDetails.getQcqtyCompleted());
            //计算数量
            docQcDetails.setQcqtyExpectedEach(docQcDetails.getQcqtyExpected()*docQcDetails.getQty1());
            docQcDetails.setQcqtyCompletedEach(docQcDetails.getQcqtyCompleted()*docQcDetails.getQty1());
            BeanUtils.copyProperties(docQcDetails, docQcDetailsVO);
            docQcDetailsVOList.add(docQcDetailsVO);
        }
        datagrid.setTotal((long) docQcDetailsDao.queryByCountPano(mybatisCriteria));
        datagrid.setRows(docQcDetailsVOList);
        return datagrid;
	}

    /**
     * 显示细单 点击单号放大镜选择
     * @param pager
     * @param query
     * @return
     */
    public EasyuiDatagrid<DocQcDetailsVO> showDatagridByQcNo(EasyuiDatagridPager pager, DocQcDetailsQuery query) {
        EasyuiDatagrid<DocQcDetailsVO> datagrid = new EasyuiDatagrid<>();
        List<DocQcDetailsVO> docQcDetailsVOList = new ArrayList<>();

        //登录用户角色是货主就只显示该货主的数据
        List<SfcRole> sfcUsersList =sfcRoleMybatisDao.queryRoleByID(SfcUserLoginUtil.getLoginUser().getId());
        for (SfcRole sfcRole:sfcUsersList){
            if(sfcRole.getRoleName().equals("货主")){
                query.setCustomerid(SfcUserLoginUtil.getLoginUser().getId());
            }
        }

        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<DocQcDetails> docQcDetailsList = docQcDetailsDao.queryByListSearchByQcNo(mybatisCriteria);
        DocQcDetailsVO docQcDetailsVO = null;
        for (DocQcDetails docQcDetails : docQcDetailsList) {
            docQcDetailsVO = new DocQcDetailsVO();
            BeanUtils.copyProperties(docQcDetails, docQcDetailsVO);
            docQcDetailsVOList.add(docQcDetailsVO);
        }
        datagrid.setTotal((long) docQcDetailsDao.queryByCountPanoByQcNo(mybatisCriteria));
        datagrid.setRows(docQcDetailsVOList);
        return datagrid;
    }




    /**
     * 打印验收记录
     *
     * @return
     */
    public List<DocQcHeader> printQcSearch( String qcno, String linestatus,String lotatt10,String descrc,String customerid,
                                            String shippershortname,String sku,String lotatt12,String lotatt08,String lotatt15,String lotatt03Start,
                                            String lotatt03End,String lotatt14) {

        //验收记录
        List<DocQcHeader> docQcHeaderList = new ArrayList<>();
        BasSkuQuery skuQuery = new BasSkuQuery();
        DocQcHeader docQcHeader = new DocQcHeader();
        Double paQtySum = 0.00;//到货数量
        Double qcQtySum = 0.00;
        Double qcQtyComSum = 0.00;
        //ASN编号
        String asnNo;

        docQcHeader.setDetls(new ArrayList<DocQcDetails>());
        DocQcDetailsQuery docQcDetailsQuery = new DocQcDetailsQuery();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        docQcDetailsQuery.setQcno(qcno);
        docQcDetailsQuery.setLinestatus(linestatus);
        docQcDetailsQuery.setLotatt10(lotatt10);
        docQcDetailsQuery.setDescrc(descrc);
        docQcDetailsQuery.setCustomerid(customerid);
        docQcDetailsQuery.setShippershortname(shippershortname);
        docQcDetailsQuery.setSku(sku);
        docQcDetailsQuery.setLotatt12(lotatt12);
        docQcDetailsQuery.setLotatt08(lotatt08);
        docQcDetailsQuery.setLotatt15(lotatt15);
        docQcDetailsQuery.setLotatt03Start(lotatt03Start);
        docQcDetailsQuery.setLotatt03End(lotatt03End);
        docQcDetailsQuery.setLotatt14(lotatt14);
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(docQcDetailsQuery));
        List<DocQcDetails> docQcDetailsList = docQcDetailsMybatisDao.queryByListPano(mybatisCriteria);//获取需要打印的数据
        if(docQcDetailsList.size()>0) {
            for (DocQcDetails docQcDetails1 : docQcDetailsList) { //规格  数量 不合格数量
                //供应商
//                BasCustomer basCustomer = basCustomerMybatisDao.queryByIdType(docQcDetails1.getLotatt08(), Constant.CODE_CUS_TYP_VE);
//                docQcDetails1.setLotatt08(basCustomer == null ? "N/A" : basCustomer.getDescrC());

                //到货数量
                skuQuery.setCustomerid(docQcDetails1.getCustomerid());
                skuQuery.setSku(docQcDetails1.getSku());
                BasSku basSku = basSkuMybatisDao.queryById(skuQuery);//得到sku的packid
                BasPackage basPackage = basPackageMybatisDao.queryById(basSku.getPackid());
                //规格
                docQcDetails1.setDescrc(basSku.getDescrC());
                //单位
                BasCodes basCodes = basCodesMybatisDao.query4UOM(basSku.getDefaultreceivinguom());
                docQcDetails1.setQcUnit(basCodes == null ? "N/A" : basCodes.getCodenameC());

                docQcDetails1.setQcqtyExpected(basPackage.getQty1().doubleValue() * docQcDetails1.getQcqtyExpected());
                docQcDetails1.setQcqtyCompleted(basPackage.getQty1().doubleValue() * docQcDetails1.getQcqtyCompleted());

                if (docQcDetails1.getUserdefine5().equals("DCL")) {
                    docQcDetails1.setQcqtyCompleted(docQcDetails1.getQcqtyExpected() - docQcDetails1.getQcqtyCompleted());//不合格数量
                    if(docQcDetails1.getQcqtyCompleted() == 0){
                        docQcDetails1.setQcqtyCompleted(0d);
                    }
                    qcQtyComSum += docQcDetails1.getQcqtyExpected();
                }
                if (docQcDetails1.getUserdefine5().equals("HG")) {
                    docQcDetails1.setQcqtyExpected(docQcDetails1.getQcqtyCompleted() - docQcDetails1.getQcqtyExpected());//合格数量
                    if(docQcDetails1.getQcqtyExpected() == 0){
                        docQcDetails1.setQcqtyExpected(0d);
                    }
                    qcQtySum += docQcDetails1.getQcqtyCompleted();

                }
                if (docQcDetails1.getUserdefine5().equals("DJ")) {
                    docQcDetails1.setQcqtyCompleted(0d);
                    docQcDetails1.setQcqtyExpected(0d);
                    //qcQtyComSum += docQcDetails1.setQcqtyCompleted();
                }
               /* //质量状态为不合格、 合格 、 待检 、 分别插入不同的数值
                for (EasyuiCombobox easyuiComboboxQc : easyuiComboboxListZl) {//循环多次
                    //验收结论显示中文
                    if (docQcDetails1.getUserdefine5().equals(easyuiComboboxQc.getId())) {

                    }
                }*/
                docQcDetails1.setNotes("");
                switch (docQcDetails1.getUserdefine5()) {
                    case "DJ":
                        docQcDetails1.setUserdefine5("待检");
                        break;
                    case "HG":
                        docQcDetails1.setUserdefine5("合格");
                        break;
                    case "DCL":
                        docQcDetails1.setUserdefine5("待处理");
                        break;
                }
                docQcDetails1.setPaqtyExpected(basPackage.getQty1().doubleValue() * (docQcDetails1.getPaqtyExpected()));//到货件数（这里是拆开的后面需要合计下）
                //合计数量
                paQtySum += docQcDetails1.getPaqtyExpected();
                docQcDetails1.setPaqtyExpectedSum(paQtySum);
                docQcDetails1.setQcqtyCompletedSum(qcQtySum);
                qcQtyComSum +=docQcDetails1.getQcqtyExpected();
                docQcDetails1.setQcqtyExpectedSum(qcQtyComSum);
                docQcDetails1.setEditwho(StringUtil.fixNull(docQcDetails1.getEditwho()));
                docQcHeader.getDetls().add(docQcDetails1);
            }
            List<String> stringList = new ArrayList<>();
            //如果lotatt14存在不同的就全部为空
            for (DocQcDetails docqcDetailsLotatt14 : docQcHeader.getDetls()) {//这里判断不行 先去list中去重如果size大于1就正面不是同一个ASN编号
                stringList.add(docqcDetailsLotatt14.getLotatt14());

            }
            //同一个ASN编号的 赋值到头档
            if (removeDuplicate(stringList).size() == 1) {
                for (DocQcDetails docqcDetails : docQcHeader.getDetls()) {//这里判断不行 先去list中去重如果size大于1就正面不是同一个ASN编号
                    //这里还有查询。
                    DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(docqcDetails.getLotatt14());//根据ASN编号查询预期入库头档
                    BasCustomer owCustomer = basCustomerMybatisDao.queryByIdType(docqcDetails.getCustomerid(), Constant.CODE_CUS_TYP_OW);
                    docQcHeader.setCustomerid(owCustomer.getDescrC());
                    //入库日期
                    docQcHeader.setLotatt03(docqcDetails.getLotatt03());
                    //入库单号
                    docQcHeader.setLotatt14(docqcDetails.getQcno());
                    //冷链随货温度
                    docQcHeader.setUserdefine1Temp(docAsnHeader.getUserdefine1());
                }
            } else {
                //货主
                docQcHeader.setCustomerid("");
                //供应商
                docQcHeader.setDescrC("");
                //入库日期
                docQcHeader.setLotatt03("");
                //入库单号
                docQcHeader.setLotatt14("");
                //冷链随货温度
                docQcHeader.setUserdefine1Temp("");
            }
            docQcHeaderList.add(docQcHeader);
        }

        return docQcHeaderList;
    }

    public   static   List  removeDuplicate(List list)  {
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
                if  (list.get(j).equals(list.get(i)))  {
                    list.remove(j);
                }
            }
        }
        return list;
    }

//	public Json addDocQcDetails(DocQcDetailsForm docQcDetailsForm) throws Exception {
//		Json json = new Json();
//		DocQcDetails docQcDetails = new DocQcDetails();
//		BeanUtils.copyProperties(docQcDetailsForm, docQcDetails);
//		docQcDetailsDao.add(docQcDetails);
//		json.setSuccess(true);
//		return json;
//	}
//
//	public Json editDocQcDetails(DocQcDetailsForm docQcDetailsForm) {
//		Json json = new Json();
//		DocQcDetails docQcDetails = docQcDetailsDao.queryById(docQcDetailsForm);
//		BeanUtils.copyProperties(docQcDetailsForm, docQcDetails);
//		docQcDetailsDao.update(docQcDetails);
//		json.setSuccess(true);
//		return json;
//	}
//
//    //TODO WARNING!! 此处不可用个，删除条件欠缺 应该qcno + qclineno
////	public Json deleteDocQcDetails(String id) {
////		Json json = new Json();
////		DocQcDetails docQcDetails = docQcDetailsDao.queryById(id);
////		if(docQcDetails != null){
////			docQcDetailsDao.delete(docQcDetails);
////		}
////		json.setSuccess(true);
////		return json;
////	}
//
////	public List<EasyuiCombobox> getDocQcDetailsCombobox() {
////		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
////		EasyuiCombobox combobox = null;
////		List<DocQcDetails> docQcDetailsList = docQcDetailsDao.findAll();
////		if(docQcDetailsList != null && docQcDetailsList.size() > 0){
////			for(DocQcDetails docQcDetails : docQcDetailsList){
////				combobox = new EasyuiCombobox();
////				combobox.setId(String.valueOf(docQcDetails.getId()));
////				combobox.setValue(docQcDetails.getDocQcDetailsName());
////				comboboxList.add(combobox);
////			}
////		}
////		return comboboxList;
////	}
//
//    /**
//     * 获取验收详情
//     * @param query ~
//     * @return ~
//     */
//    public Map<String, Object> queryDocQcDetail(PdaDocQcDetailQuery query) {
//
//        Map<String, Object> map = new HashMap<>();
//        PdaDocQcDetailVO pdaDocQcDetailVO = new PdaDocQcDetailVO();
//
//        /*
//        111，处理BasSku获取问题
//        并且返回准确的批号、序列号匹配条件
//         */
//        ScanResultForm scanResultForm = new ScanResultForm();
//        //customerid, GTIN, lotatt04, lotatt05, otherCode
//        BeanUtils.copyProperties(query, scanResultForm);
//        CommonVO commonVO = commonService.adaptScanResult4SKU(scanResultForm);
//
//        if (!commonVO.isSuccess()) {
//
//            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, commonVO.getMessage()));
//            return map;
//        }
//
//        //获取BasSku，设置
//        BasSku basSku = commonVO.getBasSku();
//        query.setLotatt04(commonVO.getBatchNum());
//        query.setLotatt05(commonVO.getSerialNum());
//        query.setSku(basSku.getSku());
//        pdaDocQcDetailVO.setBasSku(basSku);
//
//        /*
//        222，判断获取上架扫码数据是否齐全
//         */
//        Json scanJson = commonService.judgeQcScanResult(query, commonVO);
//        if (!scanJson.isSuccess()) {
//
//            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, scanJson.getMsg()));
//            return map;
//        }
//
//        /*
//        333，验收明细
//         */
//        DocQcDetails docQcDetails = (DocQcDetails) scanJson.getObj();
//        BeanUtils.copyProperties(docQcDetails, pdaDocQcDetailVO);
//
//        /*
//        444，获取包装规格
//         */
//        BasPackageQuery packageQuery = new BasPackageQuery();
//        packageQuery.setPackid(basSku.getPackid());
//        BasPackage basPackage = basPackageMybatisDao.queryById(packageQuery);
//        if (basPackage == null) {
//            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "查无产品的包装规格"));
//            return map;
//        }
//        pdaDocQcDetailVO.setBasPackage(basPackage);
//
//        /*
//        555，批次
//         */
//        InvLotAtt lotAtt = invLotAttMybatisDao.queryById(docQcDetails.getLotnum());
//        if (lotAtt == null || lotAtt.getLotnum() == null) {
//            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "查无批次信息"));
//            return map;
//        }
//        pdaDocQcDetailVO.setInvLotAtt(lotAtt);
//
//        /*
//        666，此批号产品 已验件数 & 未验件数
//         */
//        DocQcDetails qtyQcDetails = docQcDetailsDao.queryAcceptQty(query);
//        pdaDocQcDetailVO.setAcceptedQty(qtyQcDetails.getQcqtyCompleted().intValue());
//        pdaDocQcDetailVO.setUnacceptedQty((int) (qtyQcDetails.getQcqtyExpected() - qtyQcDetails.getQcqtyCompleted()));
//
//        /*
//        777,当前批次-产品注册证对应的 生产厂家
//         */
//        PdaGspProductRegister productRegister = productRegisterMybatisDao.queryByNo(lotAtt.getLotatt06());
//        if ((productRegister == null || productRegister.getEnterpriseInfo() == null ) &&
//        StringUtil.isEmpty(basSku.getReservedfield14())) {
//            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "查无生产厂家信息"));
//            return map;
//        }
//        pdaDocQcDetailVO.setEnterpriseName(
//                (productRegister == null || productRegister.getEnterpriseInfo() == null)
//                        ?
//                        basSku.getReservedfield14()
//                        :
//                        productRegister.getEnterpriseInfo().getEnterpriseName()
//        );
//
//        /*
//        888，最新+历史注册证(+生产企业详情)
//         */
//        if (productRegister != null && StringUtil.isNotEmpty(productRegister.getVersion())) {
//
//            MybatisCriteria mybatisCriteria = new MybatisCriteria();
//            GspProductRegisterQuery historyQuery = new GspProductRegisterQuery();
//            historyQuery.setVersion(productRegister.getVersion());
//            mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(historyQuery));
//            List<PdaGspProductRegister> gspProductRegisterList = productRegisterMybatisDao.queryByList(mybatisCriteria);
//            pdaDocQcDetailVO.setProductRegisterList(gspProductRegisterList.size() == 0 ? new ArrayList<PdaGspProductRegister>() : gspProductRegisterList);
//        }
//
//        map.put(Constant.DATA, pdaDocQcDetailVO);
//        map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG));
//
//        return map;
//    }
//
//    /**
//     * 查询任务明细列表
//     * @param qcno ~
//     * @return ~
//     */
//    public List<PdaDocQcDetailVO> queryDocQcList(String qcno, int pageNum) {
//
//        List<PdaDocQcDetailVO> docQcDetailVOList = new ArrayList<>();
//        PdaDocQcDetailVO pdaDocQcDetailVO;
//
//        List<DocQcDetails> docQcDetailsList = docQcDetailsDao.queryDocQcList(qcno, (pageNum - 1) * Constant.pageSize, Constant.pageSize);
//        for (DocQcDetails docQcDetails : docQcDetailsList) {
//
//            pdaDocQcDetailVO = new PdaDocQcDetailVO();
//            BeanUtils.copyProperties(docQcDetails, pdaDocQcDetailVO);
//
//            //批次属性
//            InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(docQcDetails.getLotnum());
//            String jsonStr1 = JSON.toJSONString(invLotAtt, SerializerFeature.DisableCircularReferenceDetect);
//            pdaDocQcDetailVO.setInvLotAtt(JSONObject.parseObject(jsonStr1, InvLotAtt.class));
//
//            //产品档案
//            BasSkuQuery basSkuQuery = new BasSkuQuery(docQcDetails.getCustomerid(), docQcDetails.getSku());
//            BasSku basSku = basSkuMybatisDao.queryById(basSkuQuery);
//            pdaDocQcDetailVO.setBasSku(JSONObject.parseObject(JSON.toJSONString(basSku, SerializerFeature.DisableCircularReferenceDetect), BasSku.class));
//
//            docQcDetailVOList.add(pdaDocQcDetailVO);
//        }
//
//        return docQcDetailVOList;
//    }
//
//    /**
//     * 更新已验收的验收说明
//     * @param query  ~
//     * @return ~
//     */
//    public PdaResult editQcDesc(DocQcDetailsQuery query) {
//
//        query.setEditwho(query.getEditwho());
//        int result = docQcDetailsDao.updateQcDesc(query);
//        if (result == 0) {
//            return new PdaResult(PdaResult.CODE_FAILURE, "操作失败, 任务单不存在");
//        }
//        return new PdaResult(PdaResult.CODE_SUCCESS, "操作成功");
//    }
//
//    /**
//     * 验收提交
//     * @param form ~
//     * @return ~
//     */
//    public PdaResult submitDocQc(PdaDocQcDetailForm form) {
//
//        /**
//         * 日期校验
//         */
//        if (StringUtil.isEmpty(form.getLotatt01())) {
//            return new PdaResult(PdaResult.CODE_FAILURE, "请选择生产日期");
//        }else if (StringUtil.isEmpty(form.getLotatt02())) {
//            return new PdaResult(PdaResult.CODE_FAILURE, "请选择失效日期");
//        }
//
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date prdDate = format.parse(form.getLotatt01());
//            Date expiryDate = format.parse(form.getLotatt02());
//            if (prdDate.getTime() >= expiryDate.getTime()) {
//                return new PdaResult(PdaResult.CODE_FAILURE, "失效日期不可小于生产日期");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        /**
//         * 批量修改日期校验
//         * 目前批量修改日期可操作，但是同批号、未上架的产品，日期就还是原来的
//         * 所以这边做个限制，批量操作如果上架任务中有此批号的未上架产品，不允许批量验收
//         */
//        DocQcDetails docQcDetails = new DocQcDetails();
//        if (form.getAllqcflag() == 1) {
//
//            docQcDetails = docQcDetailsDao.queryById(form);
//            InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(docQcDetails);
//            int paPiece = docPaDetailsMybatisDao.queryUndoneNum4BatchNum(form.getQcno(), form.getLotatt04());
//            if ((StringUtil.fixNull(invLotAtt.getLotatt01()).equals(form.getLotatt01()) || StringUtil.fixNull(invLotAtt.getLotatt02()).equals(form.getLotatt02())) &&
//                    paPiece > 0) {
//
//                return new PdaResult(PdaResult.CODE_FAILURE, "当前生产批号下还有未上架的产品，不可进行批量修改日期操作");
//            }
//        }
//
//        /**
//         * 验证
//         */
//
//        form.setLanguage("CN");
////        form.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
//        form.setReturncode("");
//
//        try {
//
//            docQcDetailsDao.submitDocQc(form);
//        }catch (Exception e) {
//            e.printStackTrace();
//
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//
//            return new PdaResult(PdaResult.CODE_FAILURE, PdaResult.PDA_FAILURE_IDENTIFIER + "验收系统错误");
//        }
//        if (form.getReturncode().equals(Constant.PROCEDURE_OK)) {//判断是否验收成功
//
//            if (form.getAllqcflag() == 1) {
//
//                //处理批量验收合格操作
//                return configAllQc(form, docQcDetails);
//            }else {
//
//                return new PdaResult(PdaResult.CODE_SUCCESS, "验收成功");
//            }
//        } else {
//
//            return new PdaResult(PdaResult.CODE_FAILURE, form.getReturncode());
//        }
//    }
//
//    /**
//     * 处理批量验收操作 procedure 拆出来的
//     * @param form ~
//     * @return ~
//     */
//    private PdaResult configAllQc(PdaDocQcDetailForm form, DocQcDetails currentQcDetail) {
//
//        try {
//
//            /*
//            获取同生产批号的验收任务明细记录，userdefine5=DJ,相同的QCNO、sku、userdefine3、userdefine5=DJ
//            */
//            DocQcDetailsQuery qcDetailsQuery = new DocQcDetailsQuery();
//            MybatisCriteria mybatisCriteria = new MybatisCriteria();
//            qcDetailsQuery.setQcno(currentQcDetail.getQcno());
//            qcDetailsQuery.setSku(currentQcDetail.getSku());
//            qcDetailsQuery.setUserdefine3(currentQcDetail.getUserdefine3());
//            qcDetailsQuery.setUserdefine5("DJ");
//
//            mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(qcDetailsQuery));
//            List<DocQcDetails> docQcDetailsList = docQcDetailsDao.queryByList(mybatisCriteria);
//
//            /*
//            查询出当前验收任务单下的同生产批号的待检的验收明细，并且批量设置为合格
//            出了循环再直接在上架任务明细中修改此生产批号的默认的上架质量状态。
//            */
//            for (DocQcDetails qcDetails : docQcDetailsList) {
//
//                /* ********************************inv_lot_att******************************** */
//
//                /*
//                获取待检的验收明细中的批次属性
//                */
//                InvLotAtt lotatt_history = invLotAttMybatisDao.queryById(qcDetails.getLotnum());
//                InvLotAttQuery lotAttQuery = new InvLotAttQuery(lotatt_history);
//                lotAttQuery.setLotatt01(form.getLotatt01());
//                lotAttQuery.setLotatt02(form.getLotatt02());
//                lotAttQuery.setLotatt04(form.getLotatt04());
//                lotAttQuery.setLotatt06(form.getLotatt06());
//                lotAttQuery.setLotatt10("HG");
//                lotAttQuery.setLotatt11(form.getLotatt11());
//                lotAttQuery.setLotatt15(form.getLotatt15());
//                mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(lotAttQuery));
//                List<InvLotAtt> invLotAttList = invLotAttMybatisDao.queryByList(mybatisCriteria);
//                /*
//                判断是否存在此批次
//                不存在 ->  插入新批次
//                存在 -> 查出来做流转
//                */
//                InvLotAtt lotatt_hg = new InvLotAtt();
//                if (invLotAttList == null || invLotAttList.size() < 1) {
//
//                    IdSequence idSequence = new IdSequence(form.getWarehouseid(), "CN", IdSequence.SEQUENCE_TYPE_LOT_NUM);
//                    invLotAttMybatisDao.getIdSequence(idSequence);
//
//                    BeanUtils.copyProperties(lotatt_history, lotatt_hg);
//                    lotatt_hg.setLotnum(idSequence.getResultNo());
//                    lotatt_hg.setLotatt01(form.getLotatt01());
//                    lotatt_hg.setLotatt02(form.getLotatt02());
//                    lotatt_hg.setLotatt04(form.getLotatt04());
//                    lotatt_hg.setLotatt06(form.getLotatt06());
//                    lotatt_hg.setLotatt10("HG");
//                    lotatt_hg.setLotatt11(form.getLotatt11());
//                    lotatt_hg.setLotatt15(form.getLotatt15());
//                    lotatt_hg.setAddwho(form.getEditwho());
//                    lotatt_hg.setAddtime(new java.sql.Date((new Date()).getTime()));
//                    invLotAttMybatisDao.add(lotatt_hg);
//                }else {
//
//                    lotatt_hg = invLotAttList.get(0);//18个批次属性匹配完查询只有一个
//                }
//
//
//                /* ********************************inv_lot_loc_id******************************** */
//
//                /*
//                根据qcdetails+历史批次属性，获取库位批次库存详情
//                * */
//                InvLotLocIdQuery invLotLocIdQuery = new InvLotLocIdQuery();
//                invLotLocIdQuery.setLocationid(qcDetails.getUserdefine1());
//                invLotLocIdQuery.setCustomerid(qcDetails.getCustomerid());
//                invLotLocIdQuery.setSku(qcDetails.getSku());
//                invLotLocIdQuery.setLotnum(lotatt_history.getLotnum());
//                mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(invLotLocIdQuery));
//                List<InvLotLocId> invLotLocIdList = invLotLocIdMybatisDao.queryByList(mybatisCriteria);
//                if (invLotLocIdList == null || invLotLocIdList.size() == 0) {
//
//                    throw new Exception("111");
//                }
//                InvLotLocId invLotLocId_history = invLotLocIdList.get(0);//历史存在数据
//                InvLotLocId invLotLocId_hg = new InvLotLocId();
//                BeanUtils.copyProperties(invLotLocId_history, invLotLocId_hg);
//                invLotLocId_hg.setLotnum(lotatt_hg.getLotnum());//更换新批次号
//                InvLotLocId invLotLocId_tmp = invLotLocIdMybatisDao.queryById(invLotLocId_hg);
//                if (invLotLocId_tmp == null || invLotLocId_tmp.getLotnum() == null) {
//
//                    //无 插入
//                    invLotLocIdMybatisDao.add(invLotLocId_hg);//插入合格批次的库位库存
//                }else {
//
//                    //有 更新
//                    invLotLocId_tmp.setQty(invLotLocId_tmp.getQty() + qcDetails.getQcqtyExpected());
//                    invLotLocId_tmp.setEditwho(form.getEditwho());
//                    invLotLocIdMybatisDao.updateQty(invLotLocId_tmp);
//                }
//
//                //根据历史批次+验收库位 更新历史批次库存件数-本明细中的预期验收数
//                invLotLocId_history.setQty(invLotLocId_history.getQty() - qcDetails.getQcqtyExpected());
//                invLotLocId_history.setEditwho(form.getEditwho());
//                invLotLocIdMybatisDao.updateQty(invLotLocId_history);
//
//
//                /* ********************************inv_lot******************************** */
//
//                /*
//                根据qcdetails+历史批次属性，获取批次库存详情
//                * */
//                InvLotQuery invLotQuery = new InvLotQuery();
//                invLotQuery.setLotnum(lotatt_history.getLotnum());
//                invLotQuery.setCustomerid(qcDetails.getCustomerid());
//                invLotQuery.setSku(qcDetails.getSku());
//                mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(invLotQuery));
//                List<InvLot> invLotList_history = invLotMybatisDao.queryByList(mybatisCriteria);
//                if (invLotList_history == null || invLotList_history.size() == 0) {
//
//                    throw new Exception("222");
//                }
//                InvLot invLot_history = invLotList_history.get(0);
//                InvLot invLot_hg = new InvLot();
//                BeanUtils.copyProperties(invLot_history, invLot_hg);
//                invLot_history.setQty(invLot_history.getQty() - qcDetails.getQcqtyExpected());
//                invLot_history.setEditwho(form.getEditwho());
//                invLotMybatisDao.update(invLot_history);//库存里面减去此明细中的预期验收数
//
//                //库存里面插入或更新加上预期验收数
//                invLotQuery.setLotnum(lotatt_hg.getLotnum());
//                mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(invLotQuery));
//                List<InvLot> invLotList_hg = invLotMybatisDao.queryByList(mybatisCriteria);
//                invLot_hg.setLotnum(lotatt_hg.getLotnum());//修改为真正的合格批次号
//                invLot_hg.setAddtime(new java.sql.Date((new Date()).getTime()));
//                invLot_hg.setAddwho(form.getEditwho());
//                if (invLotList_hg == null || invLotList_hg.size() == 0) {
//
//                    //插入
//                    invLot_hg.setQty(qcDetails.getQcqtyExpected());
//                    invLotMybatisDao.add(invLot_hg);
//                }else {
//
//                    InvLot invLot_exist = invLotList_hg.get(0);
//
//                    //加上件数
//                    invLot_hg.setQty(invLot_exist.getQty() + qcDetails.getQcqtyExpected());
//                    invLotMybatisDao.updateQty(invLot_hg);
//                }
//
//
//                /* ********************************doc_qc_details******************************** */
//
//                /*
//                根据qcdetails,更新批次号为合格的批次号，验收完成数为预期验收数，验收状态为合格
//
//                -->❌思路有点问题：
//                --> 应该先查询出上面普通合格的验收明细，把数量加上去，然后把这条detail删掉
//                * */
//
//                //如果更新后的验收明细和普通验收的相同，需要合并,但也不是一味地合并，如果上架过程中待检批次发生了变化，就得单独拿出来做一条记录，
//                //不然在查看验收明细的时候，会查出来多条
//                //  1，如果currentQcDetail.lotnum 等于 lotatt_history.lotnum && 库位相同等，此明细和普通验收的明细匹配，删除本条明细，吧数量
//                //  加到普通验收的数量上去
//
//                //  2，如果lotnum不相等，说明上架过程中的DJ批次改了生产日期，直接更新当前的lotnum, qcqtycompleted, qcdescr, linestatus
//                //  ,userdefine5等
//
//                if (currentQcDetail.getLotnum().equals(lotatt_history.getLotnum()) &&
//                currentQcDetail.getCustomerid().equals(qcDetails.getCustomerid()) &&
//                currentQcDetail.getSku().equals(qcDetails.getSku()) &&
////                currentQcDetail.getPalineno().equals(qcDetails.getPalineno()) &&
//                currentQcDetail.getUserdefine1().equals(qcDetails.getUserdefine1())) {//其实这个适用于在批量验收的时候，验收数量没有满足预期验收数，需要把普通验收后的两条记录合并成一条
//
//                    // 获取普通验收通过的验收明细
//                    PdaDocQcDetailQuery pdaDocQcDetailQuery = new PdaDocQcDetailQuery();
//                    pdaDocQcDetailQuery.setQcno(qcDetails.getQcno());
//                    pdaDocQcDetailQuery.setCustomerid(qcDetails.getCustomerid());
//                    pdaDocQcDetailQuery.setSku(qcDetails.getSku());
//                    pdaDocQcDetailQuery.setLotatt01(form.getLotatt01());
//                    pdaDocQcDetailQuery.setLocationid(qcDetails.getUserdefine1());
//                    pdaDocQcDetailQuery.setLotatt04(qcDetails.getUserdefine3());
//                    pdaDocQcDetailQuery.setLotatt05(qcDetails.getUserdefine4());
//                    pdaDocQcDetailQuery.setLotatt10("HG");
//                    pdaDocQcDetailQuery.setLotnum(lotatt_hg.getLotnum());
//                    DocQcDetails normalDocQcDetails = docQcDetailsDao.queryDocQcDetail(pdaDocQcDetailQuery);
//                    if (normalDocQcDetails == null) {
//                        throw new Exception();
//                    }
//                    normalDocQcDetails.setPaqtyExpected(normalDocQcDetails.getPaqtyExpected() + qcDetails.getPaqtyExpected());
//                    normalDocQcDetails.setQcqtyExpected(normalDocQcDetails.getQcqtyExpected() + qcDetails.getQcqtyExpected());
//                    normalDocQcDetails.setQcqtyCompleted(normalDocQcDetails.getQcqtyCompleted() + qcDetails.getQcqtyExpected());
//                    normalDocQcDetails.setEditwho(form.getEditwho());
//                    docQcDetailsDao.updateQcCompletedQty(normalDocQcDetails);
//
//                    //删除qcdetails
//                    docQcDetailsDao.delete(qcDetails);
//                }else {
//
//                    qcDetails.setLotnum(lotatt_hg.getLotnum());
//                    qcDetails.setQcqtyCompleted(qcDetails.getQcqtyExpected());
//                    qcDetails.setQcdescr(form.getQcdescr());
//                    qcDetails.setLinestatus("40");
//                    qcDetails.setUserdefine5("HG");
//                    qcDetails.setEditwho(form.getEditwho());
//                    docQcDetailsDao.updateQcDetail(qcDetails);
//                }
//            }
//
//            /* ********************************doc_pa_details******************************** */
//            /*
//                因为上架明细中的质量只有DJ 和 HG，所以需要匹配pano、sku、userdefine3、userdefine5（DJ）
//            */
//            DocPaHeader docPaHeader = docPaHeaderMybatisDao.queryByQcno(currentQcDetail.getQcno());
//            DocPaDetails docPaDetails = new DocPaDetails();
//            docPaDetails.setPano(docPaHeader.getPano());
//            docPaDetails.setSku(currentQcDetail.getSku());
//            docPaDetails.setUserdefine3(currentQcDetail.getUserdefine3());
//            docPaDetails.setUserdefine5("DJ");
//            docPaDetails.setEditwho(form.getEditwho());
//            docPaDetailsMybatisDao.updateBatchQc(docPaDetails);
//
//            /* ********************************doc_qc_header******************************** */
//
//            /*
//                验收单状态变更
//                如果验收完全，会更新相关联的预入库单状态
//            */
//            PdaDocQcDetailForm statusForm = new PdaDocQcDetailForm();
//            statusForm.setWarehouseid(form.getWarehouseid());
//            statusForm.setQcno(currentQcDetail.getQcno());
//            docQcHeaderMybatisDao.updateTaskStatus(statusForm);
//
//            /* ********************************清除0库存******************************** */
//            CleanInventory cleanInventory = new CleanInventory(form.getWarehouseid(), "CN", form.getEditwho());
//            docQcDetailsDao.cleanInventory(cleanInventory);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//
//            if (StringUtils.isEmpty(e.getMessage())) {
//
//                return new PdaResult(PdaResult.CODE_FAILURE, PdaResult.PDA_FAILURE_IDENTIFIER + "当前数量验收成功，批量合格失败");
//            }else if (e.getMessage().equals("111")) {
//
//                return new PdaResult(PdaResult.CODE_FAILURE, PdaResult.PDA_FAILURE_IDENTIFIER + "当前数量验收成功，批量合格失败(库位批次库存错误)");
//            }else if (e.getMessage().equals("222")) {
//
//                return new PdaResult(PdaResult.CODE_FAILURE, PdaResult.PDA_FAILURE_IDENTIFIER + "当前数量验收成功，批量合格失败(批次库存错误)");
//            }
//            return new PdaResult(PdaResult.CODE_FAILURE, PdaResult.PDA_FAILURE_IDENTIFIER + "当前数量验收成功，批量合格失败(系统错误:" + e.getMessage() + ")");
//        }
//        return new PdaResult(PdaResult.CODE_SUCCESS, "批量验收成功");
//    }
//
//    public int queryMaxLineNo(String qcno){
//        return docQcDetailsDao.queryMaxLineNo(qcno);
//    }
//
//    /**
//     * 批量验收
//     */
//    public Json submitDocQcList(String forms){
//        Json json=new Json();
//        StringBuffer result=new StringBuffer();
////        json转集合
//        List<PdaDocQcDetailForm> list=JSON.parseArray(forms,PdaDocQcDetailForm.class);
//        Boolean con=true;
//        for (PdaDocQcDetailForm detailForm : list) {
//            InitPdaDocQcDetailForm(detailForm);//完善form值
//            detailForm.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
//            detailForm.setUserid(SfcUserLoginUtil.getLoginUser().getId());
//            PdaResult pdaResult = submitDocQc(detailForm);//调用验收作业方法 单个验收
//            if(pdaResult.getErrorCode()==400){
//                result.append("验收单号:"+detailForm.getQcno()+",行号:"+detailForm.getQclineno()).append(","+pdaResult.getMsg()).append("<br/>");
//                con=false;
//            }
//        }
//         if(con){
//             json.setSuccess(true);
//             json.setMsg("验收成功!");
//         }else{
//             json.setSuccess(false);
//             json.setMsg("部分验收未成功!<br/>"+result.toString());
//         }
//         return json;
//    }
//    /**
//     * 查批属 赋给form
//     */
//    public void InitPdaDocQcDetailForm(PdaDocQcDetailForm form){
//
//        InvLotAtt invLotAtt= invLotAttMybatisDao.queryById(form.getLotnum());
//        form.setLotatt01(invLotAtt.getLotatt01());
//        form.setLotatt02(invLotAtt.getLotatt02());
//        form.setLotatt04(invLotAtt.getLotatt04());
//        form.setLotatt06(invLotAtt.getLotatt06());
//        form.setLotatt11(invLotAtt.getLotatt11());
//        form.setLotatt15(invLotAtt.getLotatt15());
//
//    }
}