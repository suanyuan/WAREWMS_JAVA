package com.wms.vo.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RequestOrderList")
@XmlType(propOrder = { "stowageDetailList" })
public class StowagePushToWmsVO {
	@XmlElement(name = "RequestOrderInfo")
	private List<StowageDetailVo> stowageDetailList;

	public List<StowageDetailVo> getStowageDetailList() {
		return stowageDetailList;
	}

	public void setStowageDetailList(List<StowageDetailVo> stowageDetailList) {
		this.stowageDetailList = stowageDetailList;
	}

	@Override
	public String toString() {
		return "StowagePushToWmsVo [stowageDetailList=" + stowageDetailList + "]";
	}
}
