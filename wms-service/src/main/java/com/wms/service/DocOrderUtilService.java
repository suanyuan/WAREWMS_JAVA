package com.wms.service;

import com.wms.constant.Constant;
import com.wms.entity.DocSerialNumRecord;
import com.wms.entity.order.OrderDetailsForNormal;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.mybatis.dao.DocSerialNumRecordMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.OrderDetailsForNormalMybatisDao;
import com.wms.mybatis.dao.OrderHeaderForNormalMybatisDao;
import com.wms.query.BasSerialNumQuery;
import com.wms.query.OrderHeaderForNormalQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.StringUtil;
import com.wms.vo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("docOrderUtils")
public class DocOrderUtilService {

    @Autowired
    private BasSerialNumService basSerialNumService;

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
    public Json validateAllocation(String orderno) {

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
            if (count == 0) return Json.success("无需记录序列号的产品，可直接分配");

            json = basSerialNumService.countSerialNum4Match(orderno);
            if (!json.isSuccess()) return json;

            //Success
            json.setMsg("分配验证成功");
            return json;
        }
        return json;
    }

    /**
     * 验证单据是否可以操作
     * 1，需要记录序列号出库的产品没有记录的不能PC操作
     * @param type 0-分配 1-复核 2-发运
     */
    public Json validateSerialNumRecords(String orderno, int type) {

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
}
