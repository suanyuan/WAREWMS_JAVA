package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDateSerializer;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class OrderDetailsForNormalForm {

	private String orderNo;
	private Integer orderLineNo;
	private String customerId;
	private String customerShortName;
	private String lineStatus;
	private String lineStatusName;
	private String sku;
	private String skuName;
	private String skuEnglishName;
	private Integer qtyPlaned;
	private Integer qtyOrdered;
	private Integer qtyAllocated;
	private Integer qtyShipped;
	private Double totalCubic;
	private Double totalGrossWeight;
	private Double totalPrice;
	private String pickZone;
	private String locationId;
	private String lotnum;
	private java.util.Date lotatt01;
	private java.util.Date lotatt02;
	private java.util.Date lotatt03;
	private String lotatt04;
	private java.util.Date addtime;
	private String addwho;
	private java.util.Date edittime;
	private String editwho;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderLineNo() {
		return orderLineNo;
	}

	public void setOrderLineNo(Integer orderLineNo) {
		this.orderLineNo = orderLineNo;
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

	public String getLineStatus() {
		return lineStatus;
	}

	public void setLineStatus(String lineStatus) {
		this.lineStatus = lineStatus;
	}

	public String getLineStatusName() {
		return lineStatusName;
	}

	public void setLineStatusName(String lineStatusName) {
		this.lineStatusName = lineStatusName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getSkuEnglishName() {
		return skuEnglishName;
	}

	public void setSkuEnglishName(String skuEnglishName) {
		this.skuEnglishName = skuEnglishName;
	}

	public Integer getQtyPlaned() {
		return qtyPlaned;
	}

	public void setQtyPlaned(Integer qtyPlaned) {
		this.qtyPlaned = qtyPlaned;
	}

	public Integer getQtyOrdered() {
		return qtyOrdered;
	}

	public void setQtyOrdered(Integer qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	public Integer getQtyAllocated() {
		return qtyAllocated;
	}

	public void setQtyAllocated(Integer qtyAllocated) {
		this.qtyAllocated = qtyAllocated;
	}

	public Integer getQtyShipped() {
		return qtyShipped;
	}

	public void setQtyShipped(Integer qtyShipped) {
		this.qtyShipped = qtyShipped;
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

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getPickZone() {
		return pickZone;
	}

	public void setPickZone(String pickZone) {
		this.pickZone = pickZone;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getLotnum() {
		return lotnum;
	}

	public void setLotnum(String lotnum) {
		this.lotnum = lotnum;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public java.util.Date getLotatt01() {
		return lotatt01;
	}

	public void setLotatt01(java.util.Date lotatt01) {
		this.lotatt01 = lotatt01;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public java.util.Date getLotatt02() {
		return lotatt02;
	}

	public void setLotatt02(java.util.Date lotatt02) {
		this.lotatt02 = lotatt02;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public java.util.Date getLotatt03() {
		return lotatt03;
	}

	public void setLotatt03(java.util.Date lotatt03) {
		this.lotatt03 = lotatt03;
	}

	public String getLotatt04() {
		return lotatt04;
	}

	public void setLotatt04(String lotatt04) {
		this.lotatt04 = lotatt04;
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
}