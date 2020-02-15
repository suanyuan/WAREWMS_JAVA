package com.wms.query;

import com.wms.mybatis.entity.SfcCustomer;

import java.util.Date;
import java.util.Set;


public class OrderHeaderForNormalQuery implements IQuery {

    private String orderno;
    private String customerid;
    private String soreference1;
    private String soreference2;
    private java.util.Date orderStartTime;
    private java.util.Date orderEndTime;
    private String orderCode;
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
    private String sostatusTo;
    //释放状态
    private String releasestatus;
    //显示完成-取消订单
    private String sostatusCheck;
    //产品线
    private String psName;
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


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getEditwho() {
        return editwho;
    }

    public void setEditwho(String editwho) {
        this.editwho = editwho;
    }

    public Set<SfcCustomer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<SfcCustomer> customers) {
        this.customers = customers;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public String getOrdertimeTo() {
        return ordertimeTo;
    }

    public void setOrdertimeTo(String ordertimeTo) {
        this.ordertimeTo = ordertimeTo;
    }

    public String getConsigneeid() {
        return consigneeid;
    }

    public void setConsigneeid(String consigneeid) {
        this.consigneeid = consigneeid;
    }

    public String getEdisendflag() {
        return edisendflag;
    }

    public void setEdisendflag(String edisendflag) {
        this.edisendflag = edisendflag;
    }

    public String getCarriercontact() {
        return carriercontact;
    }

    public void setCarriercontact(String carriercontact) {
        this.carriercontact = carriercontact;
    }

    public String getcAddress4() {
        return cAddress4;
    }

    public void setcAddress4(String cAddress4) {
        this.cAddress4 = cAddress4;
    }

    public String getOuttype() {
        return outtype;
    }

    public void setOuttype(String outtype) {
        this.outtype = outtype;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public OrderHeaderForNormalQuery() {
    }

    public String getEdittime() {
        return edittime;
    }

    public void setEdittime(String edittime) {
        this.edittime = edittime;
    }

    public String getEdittimeTo() {
        return edittimeTo;
    }

    public void setEdittimeTo(String edittimeTo) {
        this.edittimeTo = edittimeTo;
    }
    public String getPsName() {
        return psName;
    }

    public void setPsName(String psName) {
        this.psName = psName;
    }

    public String getSostatusCheck() {
        return sostatusCheck;
    }

    public void setSostatusCheck(String sostatusCheck) {
        this.sostatusCheck = sostatusCheck;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getSoreference1() {
        return soreference1;
    }

    public void setSoreference1(String soreference1) {
        this.soreference1 = soreference1;
    }

    public String getSoreference2() {
        return soreference2;
    }

    public void setSoreference2(String soreference2) {
        this.soreference2 = soreference2;
    }

    public String getcContact() {
        return cContact;
    }

    public void setcContact(String cContact) {
        this.cContact = cContact;
    }

    public String getcTel1() {
        return cTel1;
    }

    public void setcTel1(String cTel1) {
        this.cTel1 = cTel1;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }


    public Date getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(Date orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public Date getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(Date orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Set<SfcCustomer> getCustomerSet() {
        return customerSet;
    }

    public void setCustomerSet(Set<SfcCustomer> customerSet) {
        this.customerSet = customerSet;
    }

    public String getcProvince() {
        return cProvince;
    }

    public void setcProvince(String cProvince) {
        this.cProvince = cProvince;
    }

    public String getcCity() {
        return cCity;
    }

    public void setcCity(String cCity) {
        this.cCity = cCity;
    }

    public String getcAddress2() {
        return cAddress2;
    }

    public void setcAddress2(String cAddress2) {
        this.cAddress2 = cAddress2;
    }

    public String getcAddress1() {
        return cAddress1;
    }

    public void setcAddress1(String cAddress1) {
        this.cAddress1 = cAddress1;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public String getReleasestatus() {
        return releasestatus;
    }

    public void setReleasestatus(String releasestatus) {
        this.releasestatus = releasestatus;
    }

    public String getSostatusTo() {
        return sostatusTo;
    }

    public void setSostatusTo(String sostatusTo) {
        this.sostatusTo = sostatusTo;
    }
}