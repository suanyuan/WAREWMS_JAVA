package com.wms.service;

import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.query.DocAsnDetailQuery;
import com.wms.query.InvLotLocIdQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.StringUtil;
import com.wms.vo.InvLotLocIdVO;
import com.wms.vo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocAsnUtilService {

    @Autowired
    private DocAsnCertificateMybatisDao docAsnCertificateMybatisDao;
    @Autowired
    private DocAsnDetailsMybatisDao docAsnDetailsMybatisDao;
    @Autowired
    private BasSkuService basSkuService;
    @Autowired
    private DocAsnHeaderMybatisDao docAsnHeaderMybatisDao;
    @Autowired
    private DocSerialNumRecordMybatisDao docSerialNumRecordMybatisDao;
    @Autowired
    private ProductLineMybatisDao productLineMybatisDao;
    @Autowired
    private InvLotMybatisDao invLotMybatisDao;
    @Autowired
    private InvLotLocIdMybatisDao invLotLocIdMybatisDao;
    @Autowired
    private ActTransactionLogMybatisDao actTransactionLogMybatisDao;

    /**
     * 在关闭订单之前进行判断
     * 1，是否导入质量合格证
     * 2，判断订单是否部分收货
     * 3，销售退货需要记录序列号的是否导入
     */
    public Json checkCloseAsn(String asnnos) {

        Json json = new Json();
        StringBuilder message = new StringBuilder();
        if (StringUtil.isNotEmpty(asnnos)) {

            String[] asnnoList = asnnos.split(",");
            List<String> partReceivedAsns = new ArrayList<>();//部分收货入库的预入库单号
            for (String asnno : asnnoList) {

                //是否导入质量合格证
                json = checkAsnCertification(asnno);
                if (!json.isSuccess()) return json;

                DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(asnno);

                //销退是否导入序列号
                json = checkSerialRecords4RTAsn(docAsnHeader);
                if (!json.isSuccess()) return json;

                List<DocAsnDetail> unfinishedDetailList = docAsnDetailsMybatisDao.queryPartReceivedAsn(asnno);
                if (unfinishedDetailList.size() > 0 && docAsnHeader.getAsnstatus().equals("70")) {//表示有部分收货入库的预期到货通知明细,前提是完全验收的单子
                    partReceivedAsns.add(asnno);
                }
            }
            if (partReceivedAsns.size() > 1 || (partReceivedAsns.size() == 1 && asnnoList.length > 1)) {//asnnos中有多个部分收货的需要单条操作

                message.append("请逐条关闭部分收货入库的通知单;").append(partReceivedAsns.toString());
                json.setSuccess(false);
            } else if (partReceivedAsns.size() == 1) {

                String partAsnno = partReceivedAsns.get(0);
                message.append("[").append(partAsnno).append("] 此单部分收货入库，");
                json.setSuccess(true);
            } else {

                json.setSuccess(true);
            }
            json.setMsg(message.toString());
        } else {

            json.setSuccess(false);
            json.setMsg("关单失败！(无预入库单号传入)");
        }
        return json;
    }

    /**
     * 检查入库单号下面是否有没有导入的质量合格证
     */
    private Json checkAsnCertification(String asnno) {

        if (StringUtil.isEmpty(asnno)) return Json.error("未传入入库单号");

        Json json = new Json();
        json.setSuccess(true);
        List<DocAsnDetail> docAsnDetailList = docAsnDetailsMybatisDao.queryByAsnNo(asnno);
        StringBuilder message = new StringBuilder();
        for (DocAsnDetail docAsnDetail : docAsnDetailList) {

            BasSku basSku = basSkuService.getSkuInfo(docAsnDetail.getCustomerid(), docAsnDetail.getSku());
            if (null == basSku) {//没有产品信息，一般不可能

                json.setSuccess(false);
                message.append(" ").
                        append("[单号]:").append(docAsnDetail.getAsnno()).
                        append(" [明细行号]:").append(docAsnDetail.getAsnlineno()).
                        append(" [产品代码]:").append(docAsnDetail.getSku()).
                        append(" 查无产品档案数据");
            } else if (StringUtil.fixNull(basSku.getSkuGroup8()).equals("1")) {//产品有质量合格证

                if (StringUtil.isEmpty(docAsnDetail.getLotatt04())) {//质量合格证是有对应的生产批号的

                    json.setSuccess(false);
                    message.append(" ").
                            append("[单号]:").append(docAsnDetail.getAsnno()).
                            append(" [明细行号]:").append(docAsnDetail.getAsnlineno()).
                            append(" [产品代码]:").append(docAsnDetail.getSku()).
                            append(" 数据错误，此产品有质量合格证，但入库数据未包含生产批号，请联系管理员");
                } else {

                    DocAsnCertificate docAsnCertificate = docAsnCertificateMybatisDao.queryBylotatt04(docAsnDetail.getCustomerid(), docAsnDetail.getSku(), docAsnDetail.getLotatt04());
                    if (docAsnCertificate == null) {

                        json.setSuccess(false);
                        message.append(" ").
                                append("[单号]:").append(docAsnDetail.getAsnno()).
                                append(" [明细行号]:").append(docAsnDetail.getAsnlineno()).
                                append(" [产品代码]:").append(docAsnDetail.getSku()).
                                append(" 生产批号(").append(docAsnDetail.getLotatt04()).
                                append(")，未导入质量合格证;");
                    }
                }
            }
        }

        json.setMsg(message.toString());
        return json;
    }

    /**
     * 检查销退需要记录序列号的是否导入序列号
     */
    private Json checkSerialRecords4RTAsn(DocAsnHeader docAsnHeader) {

        if (null == docAsnHeader) return Json.error("查无入库单头档数据!");
        if (!docAsnHeader.getAsntype().equals(Constant.CODE_ASN_TYP_RT)) return Json.success("");

        StringBuilder resultMsg = new StringBuilder();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        DocAsnDetailQuery asnDetailQuery = new DocAsnDetailQuery();

        asnDetailQuery.setAsnno(docAsnHeader.getAsnno());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(asnDetailQuery));
        List<DocAsnDetail> docAsnDetailList = docAsnDetailsMybatisDao.queryByList(mybatisCriteria);
        for (DocAsnDetail docAsnDetail : docAsnDetailList) {

            ProductLine productLine = productLineMybatisDao.queryByDocAsn(docAsnDetail.getCustomerid(), docAsnDetail.getSku());
            if (null == productLine || productLine.getSerialFlag() == 0) continue;

            DocSerialNumRecord recordQuery = new DocSerialNumRecord();
            recordQuery.setOrderNo(docAsnDetail.getAsnno());
            recordQuery.setBatchNum(docAsnDetail.getLotatt04());
            mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(recordQuery));
            int batchCount = docSerialNumRecordMybatisDao.queryByCount(mybatisCriteria);
            if (batchCount < docAsnDetail.getReceivedqty().intValue()) {

                resultMsg.append("入库单号[").append(docAsnHeader.getAsnno()).append("]，");
                resultMsg.append("产品[").append(docAsnDetail.getSku()).append("]，批号[").append(docAsnDetail.getLotatt04()).append("]，需记录序列号。").append(" ");
                resultMsg.append("实际导入条数:").append(batchCount).append("；").append(" ");
                resultMsg.append("预期导入条数:").append(docAsnDetail.getReceivedqty().intValue()).append("；").append(" ");
            }
        }

        if (resultMsg.length() > 0) return Json.error(resultMsg.toString());
        return Json.success("");
    }

    /**
     * todo 关单之后删除此入库单还在收货暂存库上的库存，部分收货的场景
     */
//    public Json clearStageInventory(String asnno) {
//
//        List<InvLotLocId> invLotLocIdList = invLotLocIdMybatisDao.queryStageInventory(asnno);
//        for (InvLotLocId invLotLocId : invLotLocIdList) {
//
//            invLotLocIdMybatisDao.delete(invLotLocId);
//        }
//    }
}
