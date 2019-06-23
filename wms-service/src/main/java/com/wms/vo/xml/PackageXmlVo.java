package com.wms.vo.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "package")
@XmlType(propOrder = { "packageCode", "volume", "weight", "packageSkuList" })
public class PackageXmlVo {
	@XmlElement
	private String packageCode;
	@XmlElement
	private Double volume;
	@XmlElement
	private Double weight;
	@XmlElementWrapper(name = "packageSkuList")
	@XmlElement(name = "packageSku")
	private List<PackageSkuXmlVo> packageSkuList;

	public String getPackageCode() {
		return packageCode;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public List<PackageSkuXmlVo> getPackageSkuList() {
		return packageSkuList;
	}

	public void setPackageSkuList(List<PackageSkuXmlVo> packageSkuList) {
		this.packageSkuList = packageSkuList;
	}

	@Override
	public String toString() {
		return "PackageXmlVo [packageCode=" + packageCode + ", volume=" + volume + ", weight=" + weight + "]";
	}
}
