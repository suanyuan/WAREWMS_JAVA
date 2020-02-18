package com.wms.query;

import com.wms.mybatis.entity.SfcCustomer;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class OrderHeaderForNormalQuery implements IQuery {

    private String orderno;
    private String customerid;
    private String soreference1;
    private String soreference2;
    private java.util.Date orderStartTime;
    private java.util.Date orderEndTime;
    private String orderCode;
    private String ordertype;
    private String orderStatus;
    private java.util.Date currentTime;
    private String warehouseId;
    private Set<SfcCustomer> customerSet;
    private Set<SfcCustomer> customers;//多货主查询
    //省
    private String cProvince;
    //市
    private String cCity;
    //区
    private String cAddress2;
    //收货地址
    private String cAddress1;
    //收货人
    private String cContact;
    //收货电话
    private String cTel1;
    //订单状态
    private String orderStatusName;
    //订单类型
    private String orderTypeName;

    private String sostatus;
    private String sostatusTo;
    //释放状态
    private String releasestatus;
    //显示完成-取消订单
    private String sostatusCheck;
    //产品线
    private String productLineId;
    //订单发运时间    起始时间
    private String edittime;
    //订单发运时间    结束时间
    private String edittimeTo;
    //订单创建时间
    private String ordertime;
    //订单创建时间
    private String ordertimeTo;
    //导出类型
    private String outtype;
    private String token;
    private String notes;//备注
    private String editwho;//编辑人
    private String cAddress4;//快递单号
    private String carriercontact;//运输公司
    private String edisendflag;//回传标识
    private String consigneeid;//收货单位 公司抬头
}