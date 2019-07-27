package com.wms.vo;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDateSerializer;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class OrderHeaderForNormalVO {

	private Integer seq;
	private String orderNo;
	private String customerId;
	private String customerShortName;
	private java.util.Date orderTime;
	private java.util.Date requiredDeliveryTime;
	private String orderCode;
	private String orderStatus;
	private String orderStatusName;
	private String orderType;
	private String orderTypeName;
	private String consigneeName;
	private String province;
	private String city;
	private String district;
	private String address;
	private String tel;
	private String zipCode;
	private String carrierId;
	private String carrierName;
	private java.util.Date lastShipmentTime;
	private String notes;
	private String warehouseId;
	private java.util.Date addtime;
	private String addwho;
	private java.util.Date edittime;
	private String editwho;
	private Integer boxQty;
	private Double totalCubic;
	private Double totalGrossWeight;
	private List<OrderDetailsForNormalVO> orderDetailsForNormalVOList;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerShortName() {
		return customerShortName;
	}

	public void setCustomerShortName(String customerShortName) {
		this.customerShortName = customerShortName;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(java.util.Date orderTime) {
		this.orderTime = orderTime;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public java.util.Date getRequiredDeliveryTime() {
		return requiredDeliveryTime;
	}

	public void setRequiredDeliveryTime(java.util.Date requiredDeliveryTime) {
		this.requiredDeliveryTime = requiredDeliveryTime;
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

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getLastShipmentTime() {
		return lastShipmentTime;
	}

	public void setLastShipmentTime(java.util.Date lastShipmentTime) {
		this.lastShipmentTime = lastShipmentTime;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getAddtime() {
		return addtime;
	}

	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEdittime() {
		return edittime;
	}

	public void setEdittime(java.util.Date edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public Integer getBoxQty() {
		return boxQty;
	}

	public void setBoxQty(Integer boxQty) {
		this.boxQty = boxQty;
	}

	public Double getTotalCubic() {
		return totalCubic;
	}

	public void setTotalCubic(Double totalCubic) {
		this.totalCubic = totalCubic;
	}

	public Double getTotalGrossWeight() {
		return totalGrossWeight;
	}

	public void setTotalGrossWeight(Double totalGrossWeight) {
		this.totalGrossWeight = totalGrossWeight;
	}

	public List<OrderDetailsForNormalVO> getOrderDetailsForNormalVOList() {
		return orderDetailsForNormalVOList;
	}

	public void setOrderDetailsForNormalVOList(
			List<OrderDetailsForNormalVO> orderDetailsForNormalVOList) {
		this.orderDetailsForNormalVOList = orderDetailsForNormalVOList;
	}
}