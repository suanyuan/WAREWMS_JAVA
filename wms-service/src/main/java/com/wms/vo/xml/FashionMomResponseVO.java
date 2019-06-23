package com.wms.vo.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RequestOrderList")
@XmlType(propOrder = { "RequestOrderInfo" })
public class FashionMomResponseVO {
	@XmlElement
	private List<FashionMomOrderVO> RequestOrderInfo;

	public List<FashionMomOrderVO> getRequestOrderInfo() {
		return RequestOrderInfo;
	}

	public void setRequestOrderInfo(List<FashionMomOrderVO> requestOrderInfo) {
		RequestOrderInfo = requestOrderInfo;
	}

	@Override
	public String toString() {
		return "FashionMomSyncOrderXmlResponseVo [RequestOrderInfo=" + RequestOrderInfo + "]";
	}
}
