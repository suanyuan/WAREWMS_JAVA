package com.wms.vo.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author Owen
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ordereExportedRequest")
@XmlType(propOrder = { "orderCode", "orderStatus", "totalVolume", "totalWeight", "totalNum", "pickupName", "packageName", "packageList"})
public class OrderExportedRequestXmlVo {
	@XmlElement
	private String orderCode;
	@XmlElement
	private Integer orderStatus;
	@XmlElement
	private Double totalVolume;
	@XmlElement
	private Double totalWeight;
	@XmlElement
	private Integer totalNum;
	@XmlElement
	private String pickupName;
	@XmlElement
	private String packageName;
	@XmlElementWrapper(name = "packageList")
	@XmlElement(name = "package")
	private List<PackageXmlVo> packageList;
	
	public List<PackageXmlVo> getPackageList() {
		return packageList;
	}

	public void setPackageList(List<PackageXmlVo> packageList) {
		this.packageList = packageList;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Double getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(Double totalVolume) {
		this.totalVolume = totalVolume;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getPickupName() {
		return pickupName;
	}

	public void setPickupName(String pickupName) {
		this.pickupName = pickupName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	@Override
	public String toString() {
		return "OrderExportedRequestXmlVo [orderCode=" + orderCode + ", orderStatus=" + orderStatus + ", totalVolume="
				+ totalVolume + ", totalWeight=" + totalWeight + ", totalNum=" + totalNum + ", pickupName=" + pickupName
				+ ", packageName=" + packageName + ", packageList=" + packageList + "]";
	}

}
