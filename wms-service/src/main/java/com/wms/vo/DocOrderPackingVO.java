package com.wms.vo;

public class DocOrderPackingVO {

	private String orderNo;
	private String orderCode;
	private Integer cartonNo;
	private String sku;
	private String skuName;
	private Integer scanQty;
	private Double grossWeight;
	private Double cube;
	private String allocationDetailsId;
	private String traceId;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getCartonNo() {
		return cartonNo;
	}

	public void setCartonNo(Integer cartonNo) {
		this.cartonNo = cartonNo;
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

	public Integer getScanQty() {
		return scanQty;
	}

	public void setScanQty(Integer scanQty) {
		this.scanQty = scanQty;
	}

	public Double getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Double getCube() {
		return cube;
	}

	public void setCube(Double cube) {
		this.cube = cube;
	}

	public String getAllocationDetailsId() {
		return allocationDetailsId;
	}

	public void setAllocationDetailsId(String allocationDetailsId) {
		this.allocationDetailsId = allocationDetailsId;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
}