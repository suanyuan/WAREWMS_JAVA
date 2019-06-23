package com.wms.vo.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RequestOrderInfo")
@XmlType(propOrder = { "ldlNo","ldlType","dispatchName","dispatchTel","driver","driverTel","vehicalNo","loadingTime","orderList" })
public class StowageDetailVo {
	@XmlElement(name = "ldlNo")
	private String ldlNo;
	@XmlElement(name = "ldlType")
	private String ldlType;
	@XmlElement(name = "dispatchName")
	private String dispatchName;
	@XmlElement(name = "dispatchTel")
	private String dispatchTel;
	@XmlElement(name = "driver")
	private String driver;
	@XmlElement(name = "driverTel")
	private String driverTel;
	@XmlElement(name = "vehicalNo")
	private String vehicalNo;
	@XmlElement(name = "loadingTime")
	private String loadingTime;
	@XmlElementWrapper(name = "orders")
	@XmlElement(name = "order")
	private List<OrderVO> orderList;
	public String getLdlNo() {
		return ldlNo;
	}
	public void setLdlNo(String ldlNo) {
		this.ldlNo = ldlNo;
	}
	public String getLdlType() {
		return ldlType;
	}
	public void setLdlType(String ldlType) {
		this.ldlType = ldlType;
	}
	public String getDispatchName() {
		return dispatchName;
	}
	public void setDispatchName(String dispatchName) {
		this.dispatchName = dispatchName;
	}
	public String getDispatchTel() {
		return dispatchTel;
	}
	public void setDispatchTel(String dispatchTel) {
		this.dispatchTel = dispatchTel;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getDriverTel() {
		return driverTel;
	}
	public void setDriverTel(String driverTel) {
		this.driverTel = driverTel;
	}
	public String getVehicalNo() {
		return vehicalNo;
	}
	public void setVehicalNo(String vehicalNo) {
		this.vehicalNo = vehicalNo;
	}
	public String getLoadingTime() {
		return loadingTime;
	}
	public void setLoadingTime(String loadingTime) {
		this.loadingTime = loadingTime;
	}
	public List<OrderVO> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<OrderVO> orderList) {
		this.orderList = orderList;
	}
	
	@Override
	public String toString() {
		return "StowageDetailVo [ldlNo=" + ldlNo + ", dispatchName=" + dispatchName + ", dispatchTel=" + dispatchTel
				+ ", driver=" + driver + ", driverTel=" + driverTel + ", vehicalNo=" + vehicalNo + ", loadingTime="
				+ loadingTime + ", orderList=" + orderList + "]";
	}
}
