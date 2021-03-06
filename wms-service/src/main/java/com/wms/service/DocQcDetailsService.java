package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.CleanInventory;
import com.wms.mybatis.entity.IdSequence;
import com.wms.mybatis.entity.pda.PdaDocQcDetailForm;
import com.wms.mybatis.entity.pda.PdaDocQcStatusForm;
import com.wms.mybatis.entity.pda.PdaGspProductRegister;
import com.wms.query.*;
import com.wms.query.pda.PdaBasSkuQuery;
import com.wms.query.pda.PdaDocQcDetailQuery;
import com.wms.result.PdaResult;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.StringUtil;
import com.wms.vo.DocQcDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocQcDetailsForm;
import com.wms.vo.form.pda.ScanResultForm;
import com.wms.vo.pda.CommonVO;
import com.wms.vo.pda.PdaDocQcDetailVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("docQcDetailsService")
public class DocQcDetailsService extends BaseService {

    @Autowired
    private DocQcDetailsMybatisDao docQcDetailsDao;

    @Autowired
    private BasSkuMybatisDao basSkuMybatisDao;

    @Autowired
    private BasPackageMybatisDao basPackageMybatisDao;

    @Autowired
    private InvLotAttMybatisDao invLotAttMybatisDao;

    @Autowired
    private GspProductRegisterMybatisDao productRegisterMybatisDao;

    @Autowired
    private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;

    @Autowired
    private InvLotLocIdMybatisDao invLotLocIdMybatisDao;

    @Autowired
    private InvLotMybatisDao invLotMybatisDao;

    @Autowired
    private DocPaHeaderMybatisDao docPaHeaderMybatisDao;

    @Autowired
    private DocPaDetailsMybatisDao docPaDetailsMybatisDao;

    @Autowired
    private DocQcHeaderMybatisDao docQcHeaderMybatisDao;

    @Autowired
    private GspVerifyService gspVerifyService;

    @Autowired
    private CommonService commonService;


    /**
     * 显示细单 分页 pano
     *
     * @param pager
     * @param query
     * @return
     */
    public EasyuiDatagrid<DocQcDetailsVO> getPagedDatagrid(EasyuiDatagridPager pager, DocQcDetailsQuery query) {
        EasyuiDatagrid<DocQcDetailsVO> datagrid = new EasyuiDatagrid<>();
        List<DocQcDetailsVO> docQcDetailsVOList = new ArrayList<>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        mybatisCriteria.setOrderByClause("d.sku desc, d.userdefine3 desc, d.userdefine4 desc");
        if (query.getQcno() == null || query.getQcno() == "") {
            datagrid.setRows(docQcDetailsVOList);
            datagrid.setTotal((long) 0);
            return datagrid;
        }
        List<DocQcDetails> docQcDetailsList = docQcDetailsDao.queryByListPano(mybatisCriteria);
         DocQcDetails docQcDetailsSum = docQcDetailsDao.queryByListPanoSum(mybatisCriteria);
        DocQcDetailsVO docQcDetailsVO = null;
        for (DocQcDetails docQcDetails : docQcDetailsList) {
            docQcDetailsVO = new DocQcDetailsVO();
            //计算件数
            docQcDetails.setQcqtyExpected(docQcDetails.getQcqtyExpected() - docQcDetails.getQcqtyCompleted());
            //计算数量
            docQcDetails.setQcqtyExpectedEach(docQcDetails.getQcqtyExpected() * docQcDetails.getQty1());
            docQcDetails.setQcqtyCompletedEach(docQcDetails.getQcqtyCompleted() * docQcDetails.getQty1());
            BeanUtils.copyProperties(docQcDetails, docQcDetailsVO);
            //合计
            docQcDetailsVO.setQcqtyExpectedSum(docQcDetailsSum.getQcqtyExpected());
            docQcDetailsVO.setQcqtyExpectedEachSum(docQcDetailsSum.getQcqtyExpected());
            docQcDetailsVO.setQcqtyCompletedSum(docQcDetailsSum.getQcqtyCompleted());
            docQcDetailsVO.setQcqtyCompletedEachSum(docQcDetailsSum.getQcqtyCompletedEach());
            docQcDetailsVOList.add(docQcDetailsVO);
        }
        datagrid.setTotal((long) docQcDetailsDao.queryByCountPano(mybatisCriteria));
        datagrid.setRows(docQcDetailsVOList);
        return datagrid;
    }

    public Json addDocQcDetails(DocQcDetailsForm docQcDetailsForm) throws Exception {
        Json json = new Json();
        DocQcDetails docQcDetails = new DocQcDetails();
        BeanUtils.copyProperties(docQcDetailsForm, docQcDetails);
        docQcDetailsDao.add(docQcDetails);
        json.setSuccess(true);
        return json;
    }

    public Json editDocQcDetails(DocQcDetailsForm docQcDetailsForm) {
        Json json = new Json();
        DocQcDetails docQcDetails = docQcDetailsDao.queryById(docQcDetailsForm);
        BeanUtils.copyProperties(docQcDetailsForm, docQcDetails);
        docQcDetailsDao.update(docQcDetails);
        json.setSuccess(true);
        return json;
    }

    /**
     * 获取验收详情
     *
     * @param query ~
     * @return ~
     */
    public Map<String, Object> queryDocQcDetail(PdaDocQcDetailQuery query) {

        Map<String, Object> map = new HashMap<>();
        PdaDocQcDetailVO pdaDocQcDetailVO = new PdaDocQcDetailVO();

        /*
        111，处理BasSku获取问题,并且返回准确的批号、序列号匹配条件
         */
        ScanResultForm scanResultForm = new ScanResultForm();
        //customerid, GTIN, lotatt04, lotatt05, otherCode
        BeanUtils.copyProperties(query, scanResultForm);
        CommonVO commonVO = commonService.adjustScanResult(scanResultForm);

        if (commonVO.isSuccess()) query.setSku(commonVO.getBasSku().getSku());
        query.setLotatt04(commonVO.getBatchNum());
        query.setLotatt05(commonVO.isSerialManagement() ? "" : commonVO.getSerialNum());

        /*
        222，判断获取验收扫码数据是否齐全
         */
        Json scanJson = commonService.matchQcDetail(query);
        if (!scanJson.isSuccess()) {

            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, scanJson.getMsg()));
            return map;
        }

        /*
        333，验收明细
         */
        DocQcDetails docQcDetails = (DocQcDetails) scanJson.getObj();
        BeanUtils.copyProperties(docQcDetails, pdaDocQcDetailVO);


        Json skuJson = commonService.fixBasSku(docQcDetails.getCustomerid(), docQcDetails.getSku());
        if (!skuJson.isSuccess()) {

            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, skuJson.getMsg()));
            return map;
        }
        BasSku basSku = (BasSku) skuJson.getObj();
        pdaDocQcDetailVO.setBasSku(basSku);
        query.setSku(basSku.getSku());

        /*
        444，获取包装规格
         */
        BasPackageQuery packageQuery = new BasPackageQuery();
        packageQuery.setPackid(basSku.getPackid());
        BasPackage basPackage = basPackageMybatisDao.queryById(packageQuery);
        if (basPackage == null) {
            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "查无产品的包装规格"));
            return map;
        }
        pdaDocQcDetailVO.setBasPackage(basPackage);

        /*
        555，批次
         */
        InvLotAtt lotAtt = invLotAttMybatisDao.queryById(docQcDetails.getLotnum());
        if (lotAtt == null || lotAtt.getLotnum() == null) {
            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "查无批次信息"));
            return map;
        }
        pdaDocQcDetailVO.setInvLotAtt(lotAtt);

        /*
        666，查看是否需要提示同批号不同批属的提示
         */
        pdaDocQcDetailVO.setAlertflag(0);
        PdaDocQcDetailQuery similarQuery = new PdaDocQcDetailQuery();
        BeanUtils.copyProperties(query, similarQuery);
        BeanUtils.copyProperties(lotAtt, similarQuery);
        List<DocQcDetails> similarQcDetails = docQcDetailsDao.querySimilarDetails(similarQuery);
        if (similarQcDetails.size() > 0) {

            pdaDocQcDetailVO.setAlertflag(1);
        }

        /*
        777，此批号产品 已验件数 & 未验件数
         */
        DocQcDetails qtyQcDetails = docQcDetailsDao.queryAcceptQty(query);
        pdaDocQcDetailVO.setAcceptedQty(qtyQcDetails.getQcqtyCompleted().intValue());
        pdaDocQcDetailVO.setUnacceptedQty((int) (qtyQcDetails.getQcqtyExpected() - qtyQcDetails.getQcqtyCompleted()));

        /*
        888,当前批次-产品注册证对应的 生产厂家
         */
        List<GspProductRegister> productRegisterList = productRegisterMybatisDao.queryByNoAndOrderBy(
                lotAtt.getLotatt06(),
                GspProductRegister.ORDERBY_EXPIRY_DATE_DESC);

        if (productRegisterList.size() == 0 &&
                StringUtil.isEmpty(basSku.getReservedfield14())) {
            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "查无生产企业信息"));
            return map;
        } else if (productRegisterList.size() > 0) {

            GspProductRegister productRegister = productRegisterList.get(0);
            GspEnterpriseInfo enterpriseInfo = gspEnterpriseInfoMybatisDao.queryById(productRegister.getEnterpriseId());

            pdaDocQcDetailVO.setEnterpriseName(
                    (enterpriseInfo == null)
                            ?
                            basSku.getReservedfield14()
                            :
                            enterpriseInfo.getEnterpriseName()
            );
        } else {

            pdaDocQcDetailVO.setEnterpriseName(basSku.getReservedfield14());
        }

        /*
        999，最新+历史注册证(+生产企业详情)
         */
        PdaGspProductRegister pdaGspProductRegister;
        List<PdaGspProductRegister> returnRgisterList = new ArrayList<>();
        List<String> numberList = new ArrayList<>();
        List<GspProductRegister> skuProductRegisterList = productRegisterMybatisDao.queryBysku(
                basSku.getSku(),
                GspProductRegister.ORDERBY_EXPIRY_DATE_DESC_SKU);
        for (GspProductRegister gspProductRegister : skuProductRegisterList) {

            if (StringUtil.isEmpty(gspProductRegister.getProductRegisterNo())) continue;

            if (!numberList.contains(gspProductRegister.getProductRegisterNo())) {

                pdaGspProductRegister = new PdaGspProductRegister();
                BeanUtils.copyProperties(gspProductRegister, pdaGspProductRegister);
                GspEnterpriseInfo enterpriseInfo = gspEnterpriseInfoMybatisDao.queryById(gspProductRegister.getEnterpriseId());
                pdaGspProductRegister.setEnterpriseInfo(enterpriseInfo);

                returnRgisterList.add(JSONObject.parseObject(JSON.toJSONString(pdaGspProductRegister, SerializerFeature.DisableCircularReferenceDetect), PdaGspProductRegister.class));
                numberList.add(gspProductRegister.getProductRegisterNo());
            }
        }
        pdaDocQcDetailVO.setProductRegisterList(returnRgisterList);

        map.put(Constant.DATA, pdaDocQcDetailVO);
        map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG));

        return map;
    }

    /**
     * 查询任务明细列表
     *
     * @param qcno ~
     * @return ~
     */
    public List<PdaDocQcDetailVO> queryDocQcList(String qcno, int pageNum) {

        List<PdaDocQcDetailVO> docQcDetailVOList = new ArrayList<>();
        PdaDocQcDetailVO pdaDocQcDetailVO;

        List<DocQcDetails> docQcDetailsList = docQcDetailsDao.queryDocQcList(qcno, (pageNum - 1) * Constant.pageSize, Constant.pageSize);
        for (DocQcDetails docQcDetails : docQcDetailsList) {

            pdaDocQcDetailVO = new PdaDocQcDetailVO();
            BeanUtils.copyProperties(docQcDetails, pdaDocQcDetailVO);

            //批次属性
            InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(docQcDetails.getLotnum());
            String jsonStr1 = JSON.toJSONString(invLotAtt, SerializerFeature.DisableCircularReferenceDetect);
            pdaDocQcDetailVO.setInvLotAtt(JSONObject.parseObject(jsonStr1, InvLotAtt.class));

            //产品档案
            BasSkuQuery basSkuQuery = new BasSkuQuery(docQcDetails.getCustomerid(), docQcDetails.getSku());
            BasSku basSku = basSkuMybatisDao.queryById(basSkuQuery);
            pdaDocQcDetailVO.setBasSku(JSONObject.parseObject(JSON.toJSONString(basSku, SerializerFeature.DisableCircularReferenceDetect), BasSku.class));

            docQcDetailVOList.add(pdaDocQcDetailVO);
        }

        return docQcDetailVOList;
    }

    /**
     * 更新已验收的验收说明
     *
     * @param query ~
     * @return ~
     */
    public PdaResult editQcDesc(DocQcDetailsQuery query) {

        query.setEditwho(query.getEditwho());
        int result = docQcDetailsDao.updateQcDesc(query);
        if (result == 0) {
            return new PdaResult(PdaResult.CODE_FAILURE, "操作失败, 任务单不存在");
        }
        return new PdaResult(PdaResult.CODE_SUCCESS, "操作成功");
    }

    /**
     * 验收提交
     *
     * @param form ~
     * @return ~
     */
    public PdaResult submitDocQc(PdaDocQcDetailForm form) {

        //日期校验
        Json json = gspVerifyService.verifyQcDateValidation(form.getLotatt01(), form.getLotatt02(), form.getLotatt06());
        if (!json.isSuccess()) return new PdaResult(PdaResult.CODE_FAILURE, json.getMsg());

        form.setLanguage("CN");
        form.setReturncode("");
        docQcDetailsDao.submitDocQc(form);

        if (form.getReturncode().equals(Constant.PROCEDURE_OK)) {//判断是否验收成功

            if (form.getAllqcflag() == 1) {

                //处理批量验收合格操作
                DocQcDetails docQcDetailsSub = docQcDetailsDao.queryById(form);//更新批次号，存在批属修改的情况
                return configAllQc(form, docQcDetailsSub);
            } else {

                return new PdaResult(PdaResult.CODE_SUCCESS, "验收成功");
            }
        } else {

            return new PdaResult(PdaResult.CODE_FAILURE, form.getReturncode());
        }
    }

    /**
     * 处理批量验收操作 procedure 拆出来的
     *
     * @param form ~
     * @return ~
     */
    private PdaResult configAllQc(PdaDocQcDetailForm form, DocQcDetails currentQcDetail) {

        try {

            /*
            获取同生产批号的验收任务明细记录，userdefine5=DJ,相同的QCNO、sku、userdefine3、userdefine5=DJ
            */
            DocQcDetailsQuery qcDetailsQuery = new DocQcDetailsQuery();
            MybatisCriteria mybatisCriteria = new MybatisCriteria();
            qcDetailsQuery.setQcno(currentQcDetail.getQcno());
            qcDetailsQuery.setSku(currentQcDetail.getSku());
            qcDetailsQuery.setUserdefine3(currentQcDetail.getUserdefine3());
            qcDetailsQuery.setUserdefine5("DJ");

            mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(qcDetailsQuery));
            List<DocQcDetails> docQcDetailsList = docQcDetailsDao.queryByList(mybatisCriteria);

            /*
            本次普通验收成功的批次号，获取asnno
             */
            InvLotAtt asnLotAtt = invLotAttMybatisDao.queryById(currentQcDetail.getLotnum());

            /*
            查询出当前验收任务单下的同生产批号的待检的验收明细，并且批量设置为合格
            出了循环再直接在上架任务明细中修改此生产批号的默认的上架质量状态。
            */
            for (DocQcDetails qcDetails : docQcDetailsList) {

                /* ********************************inv_lot_att******************************** */

                /*
                获取待检的验收明细中的批次属性
                */
                InvLotAtt lotatt_history = invLotAttMybatisDao.queryById(qcDetails.getLotnum());
                if (!StringUtil.compare(lotatt_history.getLotatt14(), asnLotAtt.getLotatt14())) {
                    continue;
                }

                InvLotAttQuery lotAttQuery = new InvLotAttQuery(lotatt_history);
                lotAttQuery.setLotatt01(form.getLotatt01());
                lotAttQuery.setLotatt02(form.getLotatt02());
                lotAttQuery.setLotatt04(form.getLotatt04());
                lotAttQuery.setLotatt06(form.getLotatt06());
                lotAttQuery.setLotatt10("HG");
                lotAttQuery.setLotatt11(form.getLotatt11());
                lotAttQuery.setLotatt12(form.getLotatt12());
                lotAttQuery.setLotatt15(form.getLotatt15());
                mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(lotAttQuery));
                List<InvLotAtt> invLotAttList = invLotAttMybatisDao.queryByList(mybatisCriteria);
                /*
                判断是否存在此批次
                不存在 ->  插入新批次
                存在 -> 查出来做流转
                */
                InvLotAtt lotatt_hg = new InvLotAtt();
                if (invLotAttList == null || invLotAttList.size() < 1) {

                    IdSequence idSequence = new IdSequence(form.getWarehouseid(), "CN", IdSequence.SEQUENCE_TYPE_LOT_NUM);
                    invLotAttMybatisDao.getIdSequence(idSequence);

                    BeanUtils.copyProperties(lotatt_history, lotatt_hg);
                    lotatt_hg.setLotnum(idSequence.getResultNo());
                    lotatt_hg.setLotatt01(form.getLotatt01());
                    lotatt_hg.setLotatt02(form.getLotatt02());
                    lotatt_hg.setLotatt04(form.getLotatt04());
                    lotatt_hg.setLotatt06(form.getLotatt06());
                    lotatt_hg.setLotatt10("HG");
                    lotatt_hg.setLotatt11(form.getLotatt11());
                    lotatt_hg.setLotatt12(form.getLotatt12());
                    lotatt_hg.setLotatt15(form.getLotatt15());
                    lotatt_hg.setAddwho(form.getEditwho());
                    lotatt_hg.setAddtime(new java.sql.Date((new Date()).getTime()));
                    invLotAttMybatisDao.add(lotatt_hg);
                } else {

                    lotatt_hg = invLotAttList.get(0);//18个批次属性匹配完查询只有一个
                }


                /* ********************************inv_lot_loc_id******************************** */

                /*
                根据qcdetails+历史批次属性，获取库位批次库存详情
                * */
                InvLotLocIdQuery invLotLocIdQuery = new InvLotLocIdQuery();
                invLotLocIdQuery.setLocationid(qcDetails.getUserdefine1());
                invLotLocIdQuery.setCustomerid(qcDetails.getCustomerid());
                invLotLocIdQuery.setSku(qcDetails.getSku());
                invLotLocIdQuery.setLotnum(lotatt_history.getLotnum());
                mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(invLotLocIdQuery));
                List<InvLotLocId> invLotLocIdList = invLotLocIdMybatisDao.queryByList(mybatisCriteria);
                if (invLotLocIdList == null || invLotLocIdList.size() == 0) {

                    throw new Exception("111");
                }
                InvLotLocId invLotLocId_history = invLotLocIdList.get(0);//历史存在数据
                InvLotLocId invLotLocId_hg = new InvLotLocId();
                BeanUtils.copyProperties(invLotLocId_history, invLotLocId_hg);
                invLotLocId_hg.setLotnum(lotatt_hg.getLotnum());//更换新批次号
                InvLotLocId invLotLocId_tmp = invLotLocIdMybatisDao.queryById(invLotLocId_hg);
                if (invLotLocId_tmp == null || invLotLocId_tmp.getLotnum() == null) {

                    //无 插入
                    invLotLocIdMybatisDao.add(invLotLocId_hg);//插入合格批次的库位库存
                } else {

                    //有 更新
                    invLotLocId_tmp.setQty(invLotLocId_tmp.getQty() + qcDetails.getQcqtyExpected());
                    invLotLocId_tmp.setEditwho(form.getEditwho());
                    invLotLocIdMybatisDao.updateQty(invLotLocId_tmp);
                }

                //根据历史批次+验收库位 更新历史批次库存件数-本明细中的预期验收数
                invLotLocId_history.setQty(invLotLocId_history.getQty() - qcDetails.getQcqtyExpected());
                invLotLocId_history.setEditwho(form.getEditwho());
                invLotLocIdMybatisDao.updateQty(invLotLocId_history);


                /* ********************************inv_lot******************************** */

                /*
                根据qcdetails+历史批次属性，获取批次库存详情
                * */
                InvLotQuery invLotQuery = new InvLotQuery();
                invLotQuery.setLotnum(lotatt_history.getLotnum());
                invLotQuery.setCustomerid(qcDetails.getCustomerid());
                invLotQuery.setSku(qcDetails.getSku());
                mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(invLotQuery));
                List<InvLot> invLotList_history = invLotMybatisDao.queryByList(mybatisCriteria);
                if (invLotList_history == null || invLotList_history.size() == 0) {

                    throw new Exception("222");
                }
                InvLot invLot_history = invLotList_history.get(0);
                InvLot invLot_hg = new InvLot();
                BeanUtils.copyProperties(invLot_history, invLot_hg);
                invLot_history.setQty(invLot_history.getQty() - qcDetails.getQcqtyExpected());
                invLot_history.setEditwho(form.getEditwho());
                invLotMybatisDao.update(invLot_history);//库存里面减去此明细中的预期验收数

                //库存里面插入或更新加上预期验收数
                invLotQuery.setLotnum(lotatt_hg.getLotnum());
                mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(invLotQuery));
                List<InvLot> invLotList_hg = invLotMybatisDao.queryByList(mybatisCriteria);
                invLot_hg.setLotnum(lotatt_hg.getLotnum());//修改为真正的合格批次号
                invLot_hg.setAddtime(new java.sql.Date((new Date()).getTime()));
                invLot_hg.setAddwho(form.getEditwho());
                if (invLotList_hg == null || invLotList_hg.size() == 0) {

                    //插入
                    invLot_hg.setQty(qcDetails.getQcqtyExpected());
                    invLotMybatisDao.add(invLot_hg);
                } else {

                    InvLot invLot_exist = invLotList_hg.get(0);

                    //加上件数
                    invLot_hg.setQty(invLot_exist.getQty() + qcDetails.getQcqtyExpected());
                    invLotMybatisDao.updateQty(invLot_hg);
                }


                /* ********************************doc_qc_details******************************** */

                /*
                根据qcdetails,更新批次号为合格的批次号，验收完成数为预期验收数，验收状态为合格

                -->❌思路有点问题：
                --> 应该先查询出上面普通合格的验收明细，把数量加上去，然后把这条detail删掉
                * */

                //如果更新后的验收明细和普通验收的相同，需要合并,但也不是一味地合并，如果上架过程中待检批次发生了变化，就得单独拿出来做一条记录，
                //不然在查看验收明细的时候，会查出来多条
                //  1，如果currentQcDetail.lotnum 等于 lotatt_history.lotnum && 库位相同等，此明细和普通验收的明细匹配，删除本条明细，吧数量
                //  加到普通验收的数量上去

                //  2，如果lotnum不相等，说明上架过程中的DJ批次改了生产日期，直接更新当前的lotnum, qcqtycompleted, qcdescr, linestatus
                //  ,userdefine5等

                if (currentQcDetail.getLotnum().equals(lotatt_history.getLotnum()) &&
                        currentQcDetail.getCustomerid().equals(qcDetails.getCustomerid()) &&
                        currentQcDetail.getSku().equals(qcDetails.getSku()) &&
                        currentQcDetail.getPalineno().equals(qcDetails.getPalineno()) &&
                        currentQcDetail.getUserdefine1().equals(qcDetails.getUserdefine1())) {//其实这个适用于在批量验收的时候，验收数量没有满足预期验收数，需要把普通验收后的两条记录合并成一条

                    // 获取普通验收通过的验收明细
                    PdaDocQcDetailQuery pdaDocQcDetailQuery = new PdaDocQcDetailQuery();
                    pdaDocQcDetailQuery.setQcno(qcDetails.getQcno());
                    pdaDocQcDetailQuery.setCustomerid(qcDetails.getCustomerid());
                    pdaDocQcDetailQuery.setSku(qcDetails.getSku());
                    pdaDocQcDetailQuery.setLotatt01(form.getLotatt01());
                    pdaDocQcDetailQuery.setPalineno(qcDetails.getPalineno());
                    pdaDocQcDetailQuery.setLocationid(qcDetails.getUserdefine1());
                    pdaDocQcDetailQuery.setLotatt04(qcDetails.getUserdefine3());
                    pdaDocQcDetailQuery.setLotatt05(qcDetails.getUserdefine4());
                    pdaDocQcDetailQuery.setLotatt10("HG");
                    pdaDocQcDetailQuery.setLotnum(lotatt_hg.getLotnum());
                    DocQcDetails normalDocQcDetails = docQcDetailsDao.queryDocQcDetail(pdaDocQcDetailQuery);
                    if (normalDocQcDetails == null) {
                        throw new Exception();
                    }
                    normalDocQcDetails.setPaqtyExpected(normalDocQcDetails.getPaqtyExpected() + qcDetails.getPaqtyExpected());
                    normalDocQcDetails.setQcqtyExpected(normalDocQcDetails.getQcqtyExpected() + qcDetails.getQcqtyExpected());
                    normalDocQcDetails.setQcqtyCompleted(normalDocQcDetails.getQcqtyCompleted() + qcDetails.getQcqtyExpected());
                    normalDocQcDetails.setEditwho(form.getEditwho());
                    docQcDetailsDao.updateQcCompletedQty(normalDocQcDetails);

                    //删除qcdetails
                    docQcDetailsDao.delete(qcDetails);
                } else {

                    qcDetails.setLotnum(lotatt_hg.getLotnum());
                    qcDetails.setQcqtyCompleted(qcDetails.getQcqtyExpected());
                    qcDetails.setQcdescr(form.getQcdescr());
                    qcDetails.setLinestatus("40");
                    qcDetails.setUserdefine5("HG");
                    qcDetails.setEditwho(form.getEditwho());
                    docQcDetailsDao.updateQcDetail(qcDetails);
                }
            }

            /* ********************************doc_pa_details******************************** */
            /*
                因为上架明细中的质量只有DJ 和 HG，所以需要匹配pano、sku、userdefine3、userdefine5（DJ）
            */
            InvLotAtt currentLotatt = invLotAttMybatisDao.queryById(currentQcDetail.getLotnum());
            DocPaHeader docPaHeader = docPaHeaderMybatisDao.queryByQcno(currentQcDetail.getQcno());
            List<String> lotnumList = docPaDetailsMybatisDao.queryMatchLotnum(docPaHeader.getPano(), currentLotatt.getLotatt14());
            DocPaDetails docPaDetails = new DocPaDetails();
            docPaDetails.setPano(docPaHeader.getPano());
            docPaDetails.setSku(currentQcDetail.getSku());
            docPaDetails.setUserdefine3(currentQcDetail.getUserdefine3());
            docPaDetails.setUserdefine5("DJ");
//            docPaDetails.setEditwho(form.getEditwho());
            docPaDetails.setLotnumList(lotnumList);
            docPaDetailsMybatisDao.updateBatchQc(docPaDetails);

            /* ********************************doc_qc_header******************************** */

            /*
                验收单状态变更
                如果验收完全，会更新相关联的预入库单状态
            */
            PdaDocQcDetailForm statusForm = new PdaDocQcDetailForm();
            statusForm.setWarehouseid(form.getWarehouseid());
            statusForm.setQcno(currentQcDetail.getQcno());
            docQcHeaderMybatisDao.updateTaskStatus(statusForm);

            /* ********************************清除0库存******************************** */

        } catch (Exception e) {

            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

//            if (StringUtils.isEmpty(e.getMessage())) {
//
//                return new PdaResult(PdaResult.CODE_FAILURE, PdaResult.PDA_FAILURE_IDENTIFIER + "当前数量验收成功，批量合格失败(系统错误1:" + e.getMessage() + ")");
//            } else if (e.getMessage().equals("111")) {
//
//                return new PdaResult(PdaResult.CODE_FAILURE, PdaResult.PDA_FAILURE_IDENTIFIER + "当前数量验收成功，批量合格失败(库位批次库存错误)");
//            } else if (e.getMessage().equals("222")) {
//
//                return new PdaResult(PdaResult.CODE_FAILURE, PdaResult.PDA_FAILURE_IDENTIFIER + "当前数量验收成功，批量合格失败(批次库存错误)");
//            }
            return new PdaResult(PdaResult.CODE_FAILURE, PdaResult.PDA_FAILURE_IDENTIFIER + "当前数量验收成功，批量合格失败(系统错误:" + e.toString() + ")");
        }
        return new PdaResult(PdaResult.CODE_SUCCESS, "批量验收成功");
    }

    public int queryMaxLineNo(String qcno) {
        return docQcDetailsDao.queryMaxLineNo(qcno);
    }

    /**
     * 批量验收
     */
    public Json submitDocQcList(String forms) {
        Json json = new Json();
        StringBuilder result = new StringBuilder();
//        json转集合
        List<PdaDocQcDetailForm> list = JSON.parseArray(forms, PdaDocQcDetailForm.class);
        boolean con = true;
        for (PdaDocQcDetailForm detailForm : list) {
            if (list.size() > 1) {
                InitPdaDocQcDetailForm(detailForm);//完善form值
            }
            detailForm.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
            detailForm.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
            PdaResult pdaResult = submitDocQc(detailForm);//调用验收作业方法 单个验收
            if (pdaResult.getErrorCode() == 400) {
                result.append("验收单号:").append(detailForm.getQcno()).append(",").append(pdaResult.getMsg()).append("<br/>");
                con = false;
            }
        }
        if (con) {
            json.setSuccess(true);
            json.setMsg("验收成功!");
        } else {
            json.setSuccess(false);
            json.setMsg("部分未验收成功!<br/>" + result.toString());
        }
        return json;
    }

    /**
     * 查批属 赋给form
     */
    public void InitPdaDocQcDetailForm(PdaDocQcDetailForm form) {

        InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(form.getLotnum());
        if (invLotAtt != null) {
            if (invLotAtt.getLotatt01() != null) {
                form.setLotatt01(invLotAtt.getLotatt01());
            }
            if (invLotAtt.getLotatt02() != null) {
                form.setLotatt02(invLotAtt.getLotatt02());
            }
            if (invLotAtt.getLotatt04() != null) {
                form.setLotatt04(invLotAtt.getLotatt04());
            }
            if (invLotAtt.getLotatt06() != null) {
                form.setLotatt06(invLotAtt.getLotatt06());
            }
            if (invLotAtt.getLotatt11() != null) {
                form.setLotatt11(invLotAtt.getLotatt11());
            }
            if (invLotAtt.getLotatt12() != null) {
                form.setLotatt12(invLotAtt.getLotatt12());
            }
            if (invLotAtt.getLotatt15() != null) {
                form.setLotatt15(invLotAtt.getLotatt15());
            }
        }

    }

    /**
     * 根据sku查询所有注册证号
     */

    public List<PdaGspProductRegister> getRgisterListBySku(String sku) {

        PdaGspProductRegister pdaGspProductRegister;
        List<PdaGspProductRegister> returnRgisterList = new ArrayList<>();
        List<String> numberList = new ArrayList<>();
        List<GspProductRegister> skuProductRegisterList = productRegisterMybatisDao.queryBysku(
                sku,
                GspProductRegister.ORDERBY_EXPIRY_DATE_DESC_SKU);
        for (GspProductRegister gspProductRegister : skuProductRegisterList) {

            if (StringUtil.isEmpty(gspProductRegister.getProductRegisterNo())) continue;

            if (!numberList.contains(gspProductRegister.getProductRegisterNo())) {

                pdaGspProductRegister = new PdaGspProductRegister();
                BeanUtils.copyProperties(gspProductRegister, pdaGspProductRegister);
                GspEnterpriseInfo enterpriseInfo = gspEnterpriseInfoMybatisDao.queryById(gspProductRegister.getEnterpriseId());
                pdaGspProductRegister.setEnterpriseInfo(enterpriseInfo);

                returnRgisterList.add(JSONObject.parseObject(JSON.toJSONString(pdaGspProductRegister, SerializerFeature.DisableCircularReferenceDetect), PdaGspProductRegister.class));
                numberList.add(gspProductRegister.getProductRegisterNo());
            }
        }
        return returnRgisterList;
    }
}