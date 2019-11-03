package com.wms.vo.form;


import java.util.Date;

public class OrderHeaderForNormalForm {





	private String orderno;
	private String customerid;
	private String customerShortName;
	private String ordertime;
	private String requiredDeliveryTime;
	private String orderCode;
	private String orderStatus;
	private String orderStatusName;
	private String ordertype;
	private String orderTypeName;
	private String consigneename;
	private String consigneeid;
	private String cProvince;
	private String cCity;
	private String cAddress2;
	private String cAddress4;
	private String cAddress3;
	private String province;
	private String city;
	private String district;
	private String cAddress1;
	private String cTel1;
	private String zipCode;
	private String carrierid;
	private String carrierName;
	private java.util.Date lastshipmenttime;
	private String notes;
	private String warehouseId;
	private java.util.Date addtime;
	private String addwho;
	private java.util.Date edittime;
	private String editwho;
	private Integer boxQty;
	private String soreference1;
	private String soreference2;
	private String docOrderHeaderId;
	private String sostatus;
	private String releasestatus;
	private String orderFlag;
	//收货人
	private String cContact;
	/**
	 * 自定义1
	 */
	private String userdefine1;
	/**
	 * 自定义2
	 */
	private String userdefine2;

	private String returnSfOrder;

	private String carrieraddress1;//是否回写签回单号标记：1：是

	//快递投诉
	private String courierComplaint;
	private String courierComplaintU;


	private String door; //温度记录表
	private String route;//发运方式
	private String stop; //结算方式

	public String getCarrieraddress1() {
		return carrieraddress1;
	}

	public String getcAddress4() {
		return cAddress4;
	}

	public void setcAddress4(String cAddress4) {
		this.cAddress4 = cAddress4;
	}

	public String getcAddress3() {
		return cAddress3;
	}

	public void setcAddress3(String cAddress3) {
		this.cAddress3 = cAddress3;
	}

	public void setCarrieraddress1(String carrieraddress1) {
		this.carrieraddress1 = carrieraddress1;
	}

	public String getDoor() {
		return door;
	}

	public void setDoor(String door) {
		this.door = door;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public String getReturnSfOrder() {
		return returnSfOrder;
	}

	public void setReturnSfOrder(String returnSfOrder) {
		this.returnSfOrder = returnSfOrder;
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

	public String getCustomerShortName() {
		return customerShortName;
	}

	public void setCustomerShortName(String customerShortName) {
		this.customerShortName = customerShortName;
	}

	public String getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}

	public String getRequiredDeliveryTime() {
		return requiredDeliveryTime;
	}

	public void setRequiredDeliveryTime(String requiredDeliveryTime) {
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

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public String getConsigneename() {
		return consigneename;
	}

	public void setConsigneename(String consigneename) {
		this.consigneename = consigneename;
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

	public String getcAddress1() {
		return cAddress1;
	}

	public void setcAddress1(String cAddress1) {
		this.cAddress1 = cAddress1;
	}

	public String getcTel1() {
		return cTel1;
	}

	public void setcTel1(String cTel1) {
		this.cTel1 = cTel1;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCarrierid() {
		return carrierid;
	}

	public void setCarrierid(String carrierid) {
		this.carrierid = carrierid;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public Date getLastshipmenttime() {
		return lastshipmenttime;
	}

	public void setLastshipmenttime(Date lastshipmenttime) {
		this.lastshipmenttime = lastshipmenttime;
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

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	public Date getEdittime() {
		return edittime;
	}

	public void setEdittime(Date edittime) {
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

	public String getDocOrderHeaderId() {
		return docOrderHeaderId;
	}

	public void setDocOrderHeaderId(String docOrderHeaderId) {
		this.docOrderHeaderId = docOrderHeaderId;
	}

	public String getSostatus() {
		return sostatus;
	}

	public void setSostatus(String sostatus) {
		this.sostatus = sostatus;
	}

	public String getReleasestatus() {
		return releasestatus;
	}

	public void setReleasestatus(String releasestatus) {
		this.releasestatus = releasestatus;
	}

	public String getConsigneeid() {
		return consigneeid;
	}

	public void setConsigneeid(String consigneeid) {
		this.consigneeid = consigneeid;
	}

	public String getUserdefine1() {
		return userdefine1;
	}

	public void setUserdefine1(String userdefine1) {
		this.userdefine1 = userdefine1;
	}

	public String getUserdefine2() {
		return userdefine2;
	}

	public void setUserdefine2(String userdefine2) {
		this.userdefine2 = userdefine2;
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

	public String getcContact() { return cContact; }

	public void setcContact(String cContact) { this.cContact = cContact; }

	public String getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	public String getCourierComplaint() {
		return courierComplaint;
	}

	public void setCourierComplaint(String courierComplaint) {
		this.courierComplaint = courierComplaint;
	}

	public String getCourierComplaintU() {
		return courierComplaintU;
	}

	public void setCourierComplaintU(String courierComplaintU) {
		this.courierComplaintU = courierComplaintU;
	}
}