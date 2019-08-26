package com.wms.query;

import java.util.Date;
import java.util.Set;

import com.wms.mybatis.entity.SfcCustomer;
import lombok.Data;


public class OrderHeaderForNormalQuery implements IQuery {

    private String orderno;
    private String customerid;
    private String soreference1;
    private String soreference2;
    //收货人
    private String cContact;
    //收货电话
    private String cTel1;
    //订单状态
    private String orderStatusName;
    //订单类型
    private String orderTypeName;
    private java.util.Date orderStartTime;
    private java.util.Date orderEndTime;
    private String orderCode;
    private String orderStatus;
    private java.util.Date currentTime;
    private String warehouseId;
    private Set<SfcCustomer> customerSet;
    //省
    private String cProvince;
    //市
    private String cCity;
    //区
    private String cAddress2;
    //收货地址
    private String cAddress1;

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
}