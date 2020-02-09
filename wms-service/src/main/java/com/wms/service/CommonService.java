package com.wms.service;

import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.query.*;
import com.wms.query.pda.*;
import com.wms.utils.StringUtil;
import com.wms.vo.Json;
import com.wms.vo.form.pda.ScanResultForm;
import com.wms.vo.pda.CommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/7/8
 */
@Service("commonService")
public class CommonService extends BaseService{

    @Autowired
    private CommonMybatisDao commonMybatisDao;
    @Autowired
    private BasSerialNumMybatisDao basSerialNumMybatisDao;
    @Autowired
    private BasSkuMybatisDao basSkuMybatisDao;
    @Autowired
    private ProductLineMybatisDao productLineMybatisDao;
    @Autowired
    private DocPaDetailsMybatisDao docPaDetailsMybatisDao;
    @Autowired
    private DocQcDetailsMybatisDao docQcDetailsMybatisDao;
    @Autowired
    private DocAsnHeaderMybatisDao docAsnHeaderMybatisDao;
    @Autowired
    private DocSerialNumRecordMybatisDao docSerialNumRecordMybatisDao;
    @Autowired
    private ActAllocationDetailsMybatisDao actAllocationDetailsMybatisDao;
    @Autowired
    private InvLotAttMybatisDao invLotAttMybatisDao;
    @Autowired
    private DocMtDetailsMybatisDao docMtDetailsMybatisDao;

    public String generateSeq(String seqType,String warehouseid){
        Map<String,Object> map = new HashMap<>();
        map.put("warehouseid",warehouseid);
        map.put("no", seqType);
        map.put("resultNo","");
        map.put("resultCode","");
        commonMybatisDao.getIdSequence(map);
        return map.get("resultNo").toString();
    }

    /**
     * 扫描条码适配判断
     * 1，扫描GS1条码，无操作；
     * 2，扫描序列号条码，匹配之前导入的bas_serial_num数据表，自动赋上生产批号（一般到序列号记录的都是批号入，序列号出的） -> otherCode传参；
     * 3，扫描产品代码条码，做完了上验证再检查SKU是否存在 -> otherCode传参；
     * 4，扫描自赋码条码，todo 待测
     * @param form 详见form内的字段描述
     * @return ~
     */
    public CommonVO adaptScanResult4SKU(ScanResultForm form) {

        CommonVO commonVO = new CommonVO();
        //默认值
        commonVO.setBatchNum(form.getLotatt04());
        commonVO.setSerialNum(form.getLotatt05());
        commonVO.setSerialManagement(false);

        BasSerialNum basSerialNum = null;
        if (StringUtil.isNotEmpty(form.getLotatt05()) ||
                StringUtil.isNotEmpty(form.getOtherCode())) {

            List<BasSerialNum> basSerialNumList = basSerialNumMybatisDao.queryValidatedId(StringUtil.isNotEmpty(form.getOtherCode()) ? form.getOtherCode() : form.getLotatt05());
            if (basSerialNumList != null && basSerialNumList.size() > 0) {

                basSerialNum = basSerialNumList.get(0);
                //序列号扫码数据缺失 效期、生产批号（注：序列号不需要传，效期不参与查询）
                //如果扫了序列号管理中的序列号，自动匹配出批号进行SKU查询
                form.setLotatt04(basSerialNum.getBatchNum());
                commonVO.setBatchNum(basSerialNum.getBatchNum());//返回批号，为之后的业务逻辑
            }
        }

        //获取BasSku
        PdaBasSkuQuery pdaBasSkuQuery = new PdaBasSkuQuery(
                form.getGTIN(),
                form.getCustomerid(),
                StringUtil.fixNull(form.getOtherCode()).contains(ScanResultForm.ALTERNATE_SKU_ID) ? form.getOtherCode() : "",//如果包含自赋码的规则字符串，则赋上自赋码
                form.getLotatt04(),
                basSerialNum != null ? "" : form.getLotatt05());//如果是序列号存在于序列号导入模块，则说明产品是批号入，出库记录序列号的.
        BasSku basSku = basSkuMybatisDao.query4ScanInBasGtnLotatt(pdaBasSkuQuery);
        if (null == basSku) basSku = basSkuMybatisDao.query4ScanInBasGtn(pdaBasSkuQuery);
        if (null == basSku) basSku = basSkuMybatisDao.query4ScanInBasSku(pdaBasSkuQuery);

        //如果获取不到，可能属于适配情况3（基本上）
        if (basSku == null) {

            BasSkuQuery basSkuQuery = new BasSkuQuery(form.getCustomerid(), form.getOtherCode());
            basSku = basSkuMybatisDao.queryById(basSkuQuery);
        }

        //如果还没有，就说明扫码错误
        if (basSku == null) {

            commonVO.setSuccess(false);
            commonVO.setMessage("查无此产品档案数据！");
            return commonVO;
        }

        commonVO.setBasSku(basSku);

        //产品线(BasSku.skuGroup1) - 序列号管理flag
        ProductLineQuery productLineQuery = new ProductLineQuery(basSku.getSkuGroup1());
        ProductLine productLine = productLineMybatisDao.queryById(productLineQuery);
        if (productLine != null && productLine.getSerialFlag() == 1) {

            commonVO.setSerialManagement(true);
            commonVO.setSerialNum("");
        }

        commonVO.setSuccess(true);

        return commonVO;
    }

    /**
     * 通过扫描的结果来匹配出批号序列号，再去各个模块的明细列表中匹配
     * 收货 待定
     * 上架 doc_pa_details
     * 验收 doc_qc_details
     * 拣货 doc_pk_details
     * 复核 act_allocation_details
     * 养护 doc_mt_details
     */
    CommonVO adjustScanResult(ScanResultForm form) {

        CommonVO commonVO = new CommonVO();
        //默认值
        commonVO.setBatchNum(form.getLotatt04());
        commonVO.setSerialNum(form.getLotatt05());
        commonVO.setSerialManagement(false);
        commonVO.setSuccess(false);

        /*
         * 情况1 扫描的是带序列号的GS1条码 / 单独的序列号条码
         * 此情况匹配上则直接返回匹配出的批号，或者 在返回时去除扫描的序列号内容
         */
        if (StringUtil.isNotEmpty(form.getLotatt05()) ||
                StringUtil.isNotEmpty(form.getOtherCode())) {

            List<BasSerialNum> basSerialNumList = basSerialNumMybatisDao.queryValidatedId(StringUtil.isNotEmpty(form.getOtherCode()) ? form.getOtherCode() : form.getLotatt05());
            if (basSerialNumList != null && basSerialNumList.size() > 0) {

                BasSerialNum basSerialNum = basSerialNumList.get(0);
                commonVO.setBatchNum(basSerialNum.getBatchNum());
                commonVO.setSerialNum(basSerialNum.getSerialNum());
                //如果匹配到序列号，则视为此产品是入库序列号管理的产品线产品。
                commonVO.setSerialManagement(true);
                return commonVO;
            }
        }

        /*
         * 情况2 扫描的是仅包含GTIN的GS1条码 / 自赋码（ZFM）/ 产品代码
         * 去bas_gtn中匹配，匹配到之后直接返回BasSku，但在匹配任务明细时，
         * 如果匹配到多条，则提醒用户需要扫描带批号或者序列号的GS1条码，如果是一条那么直接返回任务明细
         */
        PdaBasSkuQuery pdaBasSkuQuery = new PdaBasSkuQuery(
                form.getGTIN(),
                form.getCustomerid(),
                StringUtil.fixNull(form.getOtherCode()).contains(ScanResultForm.ALTERNATE_SKU_ID) ? form.getOtherCode() : "",//如果包含自赋码的规则字符串，则赋上自赋码
                form.getLotatt04(),
                form.getLotatt05());
        //GTIN -> bas_gtn
        BasSku basSku = basSkuMybatisDao.query4ScanInBasGtn(pdaBasSkuQuery);

        //自赋码 -> bas_sku
        if (null == basSku) basSku = basSkuMybatisDao.query4ScanInBasSku(pdaBasSkuQuery);

        //如果获取不到，可能属于扫描的产品代码
        if (null == basSku) {

            BasSkuQuery basSkuQuery = new BasSkuQuery(form.getCustomerid(), form.getOtherCode());
            basSku = basSkuMybatisDao.queryById(basSkuQuery);
        }

        if (null != basSku) {

            //获取到bassku就是成功返回的，其他情况就是在各明细表中进行匹配得到产品档案
            commonVO.setSuccess(true);
            commonVO.setBasSku(basSku);

            //产品线(BasSku.skuGroup1) - 序列号管理flag
            ProductLineQuery productLineQuery = new ProductLineQuery(basSku.getSkuGroup1());
            ProductLine productLine = productLineMybatisDao.queryById(productLineQuery);
            commonVO.setSerialManagement(productLine != null && productLine.getSerialFlag() == 1);
        }

        return commonVO;
    }

    /**
     * 统一处理扫描获取产品档案时错误的提示
     */
    Json fixBasSku(String customerid, String sku) {

        Map<String, Object> skuMap = new HashMap<>();
        skuMap.put("customerid", customerid);
        skuMap.put("sku", sku);
        BasSku basSku = basSkuMybatisDao.queryById(skuMap);
        if (null == basSku || null == basSku.getSku()) {
            return Json.error("查无此产品档案数据");
        } else if (basSku.getFirstop().equals(Constant.CODE_CATALOG_FIRSTSTATE_USELESS)) {
            return Json.error("此产品已报废，请与质量部进行沟通");
        } else if (basSku.getActiveFlag().equals("0")) {
            return Json.error("此产品未激活，请与质量部进行沟通");
        } else {

            Json json = new Json();
            json.setSuccess(true);
            json.setObj(basSku);
            return json;
        }
    }

    /**
     * 判断上架扫码数据是否正确，并且匹配上架任务明细
     */
    Json matchPaDetail(PdaDocPaDetailQuery query, CommonVO commonVO) {

        Json json = new Json();

        //1，获取对应的上架明细列表
        List<DocPaDetails> docPaDetailsList = docPaDetailsMybatisDao.queryDocPaDetail(query);

        //2，如果查询不到对应的上架明细，返回失败
        if (docPaDetailsList.size() == 0) return Json.error("查无此产品的上架明细数据！");

        /*
         * 3，判断产品是否需要记录序列号
         * 记录的前提条件：1，产品线中记录序列号为"是"；2，入库单类型为销退
         */
//        DocPaDetails firstDetails = docPaDetailsList.get(0);
//        DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(firstDetails.getAsnno());
//        //条件2，销退
//        if (docAsnHeader.getAsntype().equals(Constant.CODE_ASN_TYP_RT)) {
//
//            if (null == commonVO.getBasSku()) {
//
//                //如果adjustScanResult未查出产品档案数据
//                Map<String, Object> skuMap = new HashMap<>();
//                skuMap.put("customerid", firstDetails.getCustomerid());
//                skuMap.put("sku", firstDetails.getSku());
//                BasSku basSku = basSkuMybatisDao.queryById(skuMap);
//                //产品线(BasSku.skuGroup1) - 序列号管理flag
//                ProductLineQuery productLineQuery = new ProductLineQuery(basSku.getSkuGroup1());
//                ProductLine productLine = productLineMybatisDao.queryById(productLineQuery);
//                commonVO.setSerialManagement(productLine != null && productLine.getSerialFlag() == 1);
//            }
//
//            if (commonVO.isSerialManagement()) {
//
//                if (StringUtil.isEmpty(query.getLotatt05()) || StringUtil.isEmpty(query.getOtherCode())) return Json.error("销退入库请扫描带序列号的条码!");
//
//                //校验是否重复扫描同一序列号
//                MybatisCriteria mybatisCriteria = new MybatisCriteria();
//                DocSerialNumRecord docSerialNumRecord = new DocSerialNumRecord(docAsnHeader.getAsnno(), StringUtil.isNotEmpty(query.getOtherCode()) ? query.getOtherCode() : query.getLotatt05());
//                mybatisCriteria.setCondition(docSerialNumRecord);
//                List<DocSerialNumRecord> docSerialNumRecordList = docSerialNumRecordMybatisDao.queryByList(mybatisCriteria);
//                if (docSerialNumRecordList.size() > 0) return Json.error("此产品序列号已记录入库");
//            }
//        }

        //4，如果有批号||序列号，直接跳过
        if (StringUtil.isNotEmpty(query.getLotatt04()) || StringUtil.isNotEmpty(query.getLotatt05())) {

            json.setObj(docPaDetailsList);
            json.setSuccess(true);
            json.setMsg("可继续操作");
            return json;
        }

        for (DocPaDetails docPaDetails : docPaDetailsList) {

            //5，如果SKU对应的明细中存在批号或者序列号，提示上架不允许扫描SKU进行
            if (StringUtil.isNotEmpty(docPaDetails.getUserdefine3()) || StringUtil.isNotEmpty(docPaDetails.getUserdefine4())) {

                json.setSuccess(false);
                json.setMsg("请扫描产品GS1条码进行上架操作！");
                return json;
            }
        }

        json.setSuccess(true);
        json.setMsg("可继续操作");
        json.setObj(docPaDetailsList);
        return json;
    }

    /**
     * 判断获取的上架扫码数据是否齐全
     * - 如果入库有批号 || 序列号，扫描了SKU需要提示扫描带批号 || 序列号的条码
     * @param query pano, sku
     * @return 主要判断如果是批号或者序列号维护的产品，出入库扫描不可扫描SKU
     */
    Json judgePaScanResult(PdaDocPaDetailQuery query, CommonVO commonVO) {

        Json json = new Json();

        //1，获取对应的上架明细列表
        List<DocPaDetails> docPaDetailsList = docPaDetailsMybatisDao.queryDocPaDetail(query);

        //2，如果查询不到对应的上架明细，返回失败
        if (docPaDetailsList.size() == 0) {

            json.setSuccess(false);
            json.setMsg("查无此产品的上架明细数据！");
            return json;
        }

        //3，如果有批号||序列号，直接跳过
        if (StringUtil.isNotEmpty(commonVO.getBatchNum()) || StringUtil.isNotEmpty(commonVO.getSerialNum())) {

            json.setObj(docPaDetailsList);
            json.setSuccess(true);
            json.setMsg("可继续操作  ");
            return json;
        }

        for (DocPaDetails docPaDetails : docPaDetailsList) {

            //4，如果SKU对应的明细中存在批号或者序列号，提示上架不允许扫描SKU进行
            if (StringUtil.isNotEmpty(docPaDetails.getUserdefine3()) || StringUtil.isNotEmpty(docPaDetails.getUserdefine4())) {

                json.setSuccess(false);
                json.setMsg("请扫描产品GS1条码进行上架操作！");
                return json;
            }
        }

        json.setSuccess(true);
        json.setMsg("可继续操作");
        json.setObj(docPaDetailsList);
        return json;
    }

    /**
     * 判断获取的验收扫码数据是否齐全
     * - 如果入库有批号 || 序列号，扫描了SKU需要提示扫描带批号 || 序列号的条码
     * @param query qcno, customerid, sku, lotatt01, lotatt02, locationid, lotatt04, lotatt05, lotatt10, lotnum
     * @return 主要判断如果是批号或者序列号维护的产品，出入库扫描不可扫描SKU
     */
    Json matchQcDetail(PdaDocQcDetailQuery query) {

        Json json = new Json();

        //1，查询验收明细
        List<DocQcDetails> docQcDetails = docQcDetailsMybatisDao.queryDocQcDetailList(query);
        if (docQcDetails.size() == 0) {

            json.setSuccess(false);
            json.setMsg("查无此产品的验收明细数据");
            return json;
        }

        //2，如果commonVo中有批号/序列号，将查询到的验收明细返回
        if (StringUtil.isNotEmpty(query.getLotatt04()) || StringUtil.isNotEmpty(query.getLotatt05())) {

            json.setObj(docQcDetails.get(0));
            json.setSuccess(true);
            json.setMsg("可继续操作");
            return json;
        }

        //3，如果SKU对应的明细中存在批号或者序列号，提示上架不允许扫描SKU进行
        for (DocQcDetails docQcDetail : docQcDetails) {

            if (StringUtil.isNotEmpty(docQcDetail.getUserdefine3()) || StringUtil.isNotEmpty(docQcDetail.getUserdefine4())) {

                json.setSuccess(false);
                json.setMsg("请扫描产品GS1条码进行验收操作！");
                return json;
            }
        }

        json.setSuccess(true);
        json.setMsg("可继续操作");
        json.setObj(docQcDetails.get(0));
        return json;
    }

    /**
     * 判断获取的拣货扫码数据是否齐全
     * - 如果入库有批号 || 序列号，扫描了SKU需要提示扫描带批号 || 序列号的条码
     * @param query ~
     * @return 主要判断如果是批号或者序列号维护的产品，出入库扫描不可扫描SKU
     */
    Json matchPkDetails(PdaDocPkQuery query) {

        Json json = new Json();

        //1，查询分配明细
        ActAllocationDetailsQuery actAllocationDetailsQuery = new ActAllocationDetailsQuery(query);
        List<ActAllocationDetails> actAllocationDetailsList = actAllocationDetailsMybatisDao.queryForScan(actAllocationDetailsQuery);
        if (actAllocationDetailsList.size() == 0) return Json.error("查无此产品的分配明细数据");

        //2，如果commonVo中有批号/序列号，将查询到的分配明细返回
        if (StringUtil.isNotEmpty(query.getLotatt04()) || StringUtil.isNotEmpty(query.getLotatt05())) {

            json.setObj(actAllocationDetailsList.get(0));
            json.setSuccess(true);
            return json;
        }

        //3,如果SKU对应的明细中存在批号或者序列号，提示复核不允许扫描SKU进行
        for (ActAllocationDetails actAllocationDetails : actAllocationDetailsList) {

            InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(actAllocationDetails.getLotnum());
            if (StringUtil.isNotEmpty(invLotAtt.getLotatt04()) || StringUtil.isNotEmpty(invLotAtt.getLotatt05())) {

                return Json.error("请扫描产品GS1条码进行复核操作！");
            }
        }

        json.setObj(actAllocationDetailsList.get(0));
        json.setSuccess(true);
        return json;
    }

    /**
     * 判断获取的验收扫码数据是否齐全
     * - 如果入库有批号 || 序列号，扫描了SKU需要提示扫描带批号 || 序列号的条码
     * @param query qcno, customerid, sku, lotatt01, lotatt02, locationid, lotatt04, lotatt05, lotatt10, lotnum
     * @return 主要判断如果是批号或者序列号维护的产品，出入库扫描不可扫描SKU
     */
    @Deprecated
    Json judgeQcScanResult(PdaDocQcDetailQuery query, CommonVO commonVO) {

        Json json = new Json();

        //1，查询验收明细
        List<DocQcDetails> docQcDetails = docQcDetailsMybatisDao.queryDocQcDetailList(query);
        if (docQcDetails.size() == 0) {

            json.setSuccess(false);
            json.setMsg("查无此产品的验收明细数据");
            return json;
        }

        //2，如果commonVo中有批号/序列号，将查询到的验收明细返回
        if (StringUtil.isNotEmpty(commonVO.getBatchNum()) || StringUtil.isNotEmpty(commonVO.getSerialNum())) {

            json.setObj(docQcDetails.get(0));
            json.setSuccess(true);
            json.setMsg("可继续操作");
            return json;
        }

        //3，如果SKU对应的明细中存在批号或者序列号，提示上架不允许扫描SKU进行
        for (DocQcDetails docQcDetail : docQcDetails) {

            if (StringUtil.isNotEmpty(docQcDetail.getUserdefine3()) || StringUtil.isNotEmpty(docQcDetail.getUserdefine4())) {

                json.setSuccess(false);
                json.setMsg("请扫描产品GS1条码进行验收操作！");
                return json;
            }
        }

        json.setSuccess(true);
        json.setMsg("可继续操作");
        json.setObj(docQcDetails.get(0));
        return json;
    }

    /**
     * 判断获取的复核扫码数据是否齐全
     * - 如果入库有批号 || 序列号，扫描了SKU需要提示扫描带批号 || 序列号的条码
     * @param query ~
     * @return 主要判断如果是批号或者序列号维护的产品，出入库扫描不可扫描SKU
     */
    Json matchPackageDetails(PdaDocPackageQuery query) {

        Json json = new Json();

        //1，查询分配明细
        ActAllocationDetailsQuery actAllocationDetailsQuery = new ActAllocationDetailsQuery();
        actAllocationDetailsQuery.setOrderno(query.getOrderno());
        actAllocationDetailsQuery.setCustomerid(query.getCustomerid());
        actAllocationDetailsQuery.setSku(query.getSku());
        actAllocationDetailsQuery.setLotatt04(query.getLotatt04());
        actAllocationDetailsQuery.setLotatt05(query.getLotatt05());
        actAllocationDetailsQuery.setPackflag("0");
        List<ActAllocationDetails> actAllocationDetailsList = actAllocationDetailsMybatisDao.queryForScan(actAllocationDetailsQuery);
        if (actAllocationDetailsList.size() == 0) {

            json.setSuccess(false);
            json.setMsg("查无此产品的分配明细数据");
            return json;
        }

        //2，如果commonVo中有批号/序列号，将查询到的分配明细返回
        if (StringUtil.isNotEmpty(query.getLotatt04()) || StringUtil.isNotEmpty(query.getLotatt05())) {

            ActAllocationDetails actAllocationDetails = actAllocationDetailsList.get(0);
            json.setObj(actAllocationDetails);
            json.setSuccess(true);
            json.setMsg("可继续操作");
            return json;
        }

        //3,如果SKU对应的明细中存在批号或者序列号，提示复核不允许扫描SKU进行
        for (ActAllocationDetails actAllocationDetails : actAllocationDetailsList) {

            InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(actAllocationDetails.getLotnum());
            if (StringUtil.isNotEmpty(invLotAtt.getLotatt04()) || StringUtil.isNotEmpty(invLotAtt.getLotatt05())) {

                json.setSuccess(false);
                json.setMsg("请扫描产品GS1条码进行复核操作！");
                return json;
            }
        }

        json.setSuccess(true);
        json.setMsg("可继续操作");
        json.setObj(actAllocationDetailsList.get(0));
        return json;
    }

    /**
     * 判断获取的复核扫码数据是否齐全
     * - 如果入库有批号 || 序列号，扫描了SKU需要提示扫描带批号 || 序列号的条码
     * @param query ~
     * @return 主要判断如果是批号或者序列号维护的产品，出入库扫描不可扫描SKU
     */
    @Deprecated
    Json judgePackageScanResult(PdaDocPackageQuery query, CommonVO commonVO) {

        Json json = new Json();

        //1，查询分配明细
        ActAllocationDetailsQuery actAllocationDetailsQuery = new ActAllocationDetailsQuery();
        actAllocationDetailsQuery.setOrderno(query.getOrderno());
        actAllocationDetailsQuery.setCustomerid(query.getCustomerid());
        actAllocationDetailsQuery.setSku(commonVO.getBasSku().getSku());
        actAllocationDetailsQuery.setLotatt04(query.getLotatt04());
        if (!commonVO.isSerialManagement()) actAllocationDetailsQuery.setLotatt05(query.getLotatt05());
        actAllocationDetailsQuery.setPackflag("0");
        List<ActAllocationDetails> actAllocationDetailsList = actAllocationDetailsMybatisDao.queryForScan(actAllocationDetailsQuery);
        if (actAllocationDetailsList.size() == 0) {

            json.setSuccess(false);
            json.setMsg("查无此产品的分配明细数据");
            return json;
        }

        //2，如果commonVo中有批号/序列号，将查询到的分配明细返回
        if (StringUtil.isNotEmpty(commonVO.getBatchNum()) || StringUtil.isNotEmpty(commonVO.getSerialNum())) {

            ActAllocationDetails actAllocationDetails = actAllocationDetailsList.get(0);
            json.setObj(actAllocationDetails);
            json.setSuccess(true);
            json.setMsg("可继续操作");
            return json;
        }

        //3,如果SKU对应的明细中存在批号或者序列号，提示复核不允许扫描SKU进行
        for (ActAllocationDetails actAllocationDetails : actAllocationDetailsList) {

            InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(actAllocationDetails.getLotnum());
            if (StringUtil.isNotEmpty(invLotAtt.getLotatt04()) || StringUtil.isNotEmpty(invLotAtt.getLotatt05())) {

                json.setSuccess(false);
                json.setMsg("请扫描产品GS1条码进行复核操作！");
                return json;
            }
        }

        json.setSuccess(true);
        json.setMsg("可继续操作");
        json.setObj(actAllocationDetailsList.get(0));
        return json;
    }

    /**
     * 判断养护扫码条码数据是否齐全
     * @param query 查询明细 mtno, lotatt04, lotatt05, GTIN, locationid, otherCode
     * @return 结果
     */
    Json matchMtDetails(DocMtDetailsQuery query) {

        Json json = new Json();

        //1，查询养护明细
        DocMtDetails docMtDetails = docMtDetailsMybatisDao.queryUnfinishedDetail(query);
        if (docMtDetails == null) {

            json.setSuccess(false);
            json.setMsg("查无此产品的待养护明细数据");
            return json;
        }

        //2，如果commonVo中有批号/序列号，将查询到的养护明细返回
        if (StringUtil.isNotEmpty(query.getLotatt04()) || StringUtil.isNotEmpty(query.getLotatt05())) {

            json.setObj(docMtDetails);
            json.setSuccess(true);
            json.setMsg("可继续操作 ");
            return json;
        }

        //3，如果SKU对应的明细中存在批号或者序列号，提示养护不允许扫描SKU进行
        InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(docMtDetails.getLotnum());
        if (StringUtil.isNotEmpty(invLotAtt.getLotatt04()) || StringUtil.isNotEmpty(invLotAtt.getLotatt05())) {

            json.setSuccess(false);
            json.setMsg("请扫描产品GS1条码进行养护操作！");
            return json;
        }

        json.setSuccess(true);
        json.setMsg("可继续操作");
        json.setObj(docMtDetails);
        return json;
    }

    /**
     * 判断养护扫码条码数据是否齐全
//     * @param invLotAtt 验证通过的养护明细对应的批属
     * @param query 查询明细 mtno, lotatt04, lotatt05, GTIN, locationid, otherCode
     * @return 结果
     */
    Json judgeMtScanResult(DocMtDetailsQuery query, CommonVO commonVO) {

        Json json = new Json();

        //1，查询养护明细
        DocMtDetails docMtDetails = docMtDetailsMybatisDao.queryUnfinishedDetail(query);
        if (docMtDetails == null) {

            json.setSuccess(false);
            json.setMsg("查无此产品的待养护明细数据");
            return json;
        }

        //2，如果commonVo中有批号/序列号，将查询到的养护明细返回
        if (StringUtil.isNotEmpty(commonVO.getBatchNum()) || StringUtil.isNotEmpty(commonVO.getSerialNum())) {

            json.setObj(docMtDetails);
            json.setSuccess(true);
            json.setMsg("可继续操作 ");
            return json;
        }

        //3，如果SKU对应的明细中存在批号或者序列号，提示养护不允许扫描SKU进行
        InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(docMtDetails.getLotnum());
        if (StringUtil.isNotEmpty(invLotAtt.getLotatt04()) || StringUtil.isNotEmpty(invLotAtt.getLotatt05())) {

            json.setSuccess(false);
            json.setMsg("请扫描产品GS1条码进行养护操作！");
            return json;
        }

        json.setSuccess(true);
        json.setMsg("可继续操作");
        json.setObj(docMtDetails);
        return json;
    }
}