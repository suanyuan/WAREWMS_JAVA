package com.wms.service;

import com.wms.entity.BasSerialNum;
import com.wms.entity.BasSku;
import com.wms.entity.DocSerialNumRecord;
import com.wms.entity.InvLotLocId;
import com.wms.entity.order.OrderDetailsForNormal;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.mybatis.dao.*;
import com.wms.query.OrderHeaderForNormalQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.StringUtil;
import com.wms.vo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocOrderUtilService {

    @Autowired
    private BasSkuService basSkuService;
    @Autowired
    private BasSerialNumService basSerialNumService;

    @Autowired
    private ActAllocationDetailsMybatisDao actAllocationDetailsMybatisDao;
    @Autowired
    private InvLotLocIdMybatisDao invLotLocIdMybatisDao;
    @Autowired
    private DocAsnDoublecMybatisDao docAsnDoublecMybatisDao;
    @Autowired
    private BasSerialNumMybatisDao basSerialNumMybatisDao;
    @Autowired
    private OrderHeaderForNormalMybatisDao orderHeaderForNormalMybatisDao;
    @Autowired
    private OrderDetailsForNormalMybatisDao orderDetailsForNormalMybatisDao;
    @Autowired
    private DocSerialNumRecordMybatisDao docSerialNumRecordMybatisDao;


    /**
     * 如果是定向订单，检查发货凭证号关联的入库序列号是否已导入
     * @param orderno 出库单号
     * @return 若成功则返回头档数据
     */
    Json validateAllocation(String orderno) {

        Json json = new Json();
        OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
        orderHeaderForNormalQuery.setOrderno(orderno);
        OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
        json.setSuccess(true);
        json.setObj(orderHeaderForNormal);
        if (null != orderHeaderForNormal && orderHeaderForNormal.getOrdertype().equals("DX")) {

            if (StringUtil.isEmpty(orderHeaderForNormal.getSoreference2()) ||
            !orderHeaderForNormal.getSoreference2().contains("ASN")) {

                return Json.error(orderno + ":未匹配到关联的定向入库单据！不可进行分配操作");
            }

            //如果count == 0说明没有需要记录序列号的产品
            int count = orderDetailsForNormalMybatisDao.findSerialNumRecordRequired(orderno);
            if (count == 0) {

                json.setMsg("无需记录序列号的产品，可直接分配");
                return json;
            }

            json = basSerialNumService.countSerialNum4Match(orderno);
            if (!json.isSuccess()) return json;

            //Success
            json.setMsg("分配验证成功");
            json.setObj(orderHeaderForNormal);
            return json;
        }
        return json;
    }

    /**
     * 验证单据是否可以操作
     * 1，需要记录序列号出库的产品没有记录的不能PC操作
     * @param type 0-分配 1-复核 2-发运
     */
    Json validateSerialNumRecords(String orderno, int type) {

        int count = orderDetailsForNormalMybatisDao.findSerialNumRecordRequired(orderno);
        if (count > 0) {

            if (type == 0) return Json.error("此单中是有记录序列号的产品");//暂时还没用到
            if (type == 1) return Json.error("此出库单中包含了需要扫码记录序列号的产品，不可进行一键复核拣货");

            MybatisCriteria mybatisCriteria = new MybatisCriteria();
            DocSerialNumRecord recordQuery = new DocSerialNumRecord();
            recordQuery.setOrderNo(orderno);
            mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(recordQuery));
            List<DocSerialNumRecord> docSerialNumRecordList = docSerialNumRecordMybatisDao.queryByList(mybatisCriteria);
            if (docSerialNumRecordList.size() == 0) return Json.error("此出库单复核未记录序列号，不可发运");
            return Json.success("");
        }else {

            return Json.success("");
        }
    }

    /**
     * 记录序列号出库记录 for bas_serial_num
     *
     * @param orderno 发运的订单号
     */
    void recordSerialNumOutStorage(String orderno) {

        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        DocSerialNumRecord recordQuery = new DocSerialNumRecord();
        recordQuery.setOrderNo(orderno);
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(recordQuery));
        List<DocSerialNumRecord> docSerialNumRecordList = docSerialNumRecordMybatisDao.queryByList(mybatisCriteria);
        if (docSerialNumRecordList.size() > 0) {

            for (DocSerialNumRecord docSerialNumRecord : docSerialNumRecordList) {

                //记录出库时间和出库单号
                BasSerialNum basSerialNum = basSerialNumMybatisDao.queryExistBySerialNum(docSerialNumRecord.getSerialNum());
                if (null == basSerialNum) continue;

                basSerialNum.setUserdefine3(orderno);
                basSerialNum.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
                basSerialNumMybatisDao.recordSerialNumOut(basSerialNum);
            }
        }
    }

    /**
     * 双证出库之后，需要删除导入的双证
     */
    void removeAsnDoublecOutStorage(String orderno) {

        docAsnDoublecMybatisDao.removeDoublecByContext2(orderno);
    }

    /* ********************* J **********************
     * ********************* U **********************
     * ********************* D **********************
     * ********************* G **********************
     * ********************* E **********************
     * 判断是否是冷链出库，如果是的话，只能通过PDA/打包台进行转有的冷链出库操作，不可再PC上进行复核
     */
    Json fixLLPackage(String orderno) {

        List<OrderDetailsForNormal> docOrderDetailsList = orderDetailsForNormalMybatisDao.queryByOrderNo(orderno);
        if (docOrderDetailsList.size() == 0) return Json.success("");

        BasSku basSku = basSkuService.getSkuInfo(docOrderDetailsList.get(0).getCustomerid(), docOrderDetailsList.get(0).getSku());
        if (basSku == null || StringUtil.isEmpty(basSku.getReservedfield06())) return Json.success("");

        if (basSku.getReservedfield06().equals("LL") || basSku.getReservedfield06().equals("LC"))
            return Json.error("冷链产品出库不可PC一键操作，请使用PDA/打包台，扫码出库");

        return Json.success("");
    }

    /**
     * 遍历子单查看产品是否需要质量合格证或者双证，
     * 如果没有匹配或者导入的，则不可进行分配操作
     *
     * @param orderno 出库单号
     * @return ~
     */
    Json fixCertificateFlag(String orderno) {

        Json json = new Json();
        json.setSuccess(true);

        StringBuilder message = new StringBuilder();
        List<OrderDetailsForNormal> docOrderDetailList = orderDetailsForNormalMybatisDao.queryByOrderNo(orderno);
        for (OrderDetailsForNormal docOrderDetail : docOrderDetailList) {

            BasSku basSku = docOrderDetail.getBasSku() == null ? new BasSku() : docOrderDetail.getBasSku();
            if (StringUtil.fixNull(basSku.getSkuGroup7()).equals("1") && StringUtil.isNotEmpty(docOrderDetail.getLotatt05())) {

                InvLotLocId invLotLocId = invLotLocIdMybatisDao.queryByLotatt05(docOrderDetail.getLotatt05(), docOrderDetail.getCustomerid());
                if (invLotLocId == null) {

                    json.setSuccess(false);
                    message.append(" ").
                            append("序列号:").
                            append(docOrderDetail.getLotatt05()).
                            append("，无库存;");
                } else if (!invLotLocId.isDoublecflag() && !docOrderDetail.getCustomerid().equals("JSJY")) {//嘉事嘉意出库不校验双证是否匹配

                    json.setSuccess(false);
                    message.append(" ").
                            append("序列号:").
                            append(docOrderDetail.getLotatt05()).
                            append("，未匹配双证;");
                }
            }
            /*else if (StringUtil.fixNull(basSku.getSkuGroup8()).equals("1") && StringUtil.isNotEmpty(docOrderDetail.getLotatt04())) {

                DocAsnCertificate docAsnCertificate = docAsnCertificateMybatisDao.queryBylotatt04(docOrderDetail.getCustomerid(), docOrderDetail.getSku(), docOrderDetail.getLotatt04());
                if (docAsnCertificate == null) {

                    json.setSuccess(false);
                    message.append(" ").
                            append("生产批号:").
                            append(docOrderDetail.getLotatt04()).
                            append("，未导入质量合格证;");
                }
            }*/
        }

        json.setMsg(message.toString());
        return json;
    }

    String getYesNo(String obj) {
        if (obj == null || obj.equals("0")) {
            return "否";
        } else {
            return "是";
        }
    }

    static String doubleTrans(double d) {
        if (Math.round(d) - d == 0) {
            return String.valueOf((long) d);
        }
        return String.valueOf(d);
    }
}
