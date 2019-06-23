package com.wms.vo.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MerchantInfos")
@XmlType(propOrder = { "merchantList" })
public class MerchantToWmsVo {
	@XmlElementWrapper(name = "MerchantInfos")
	@XmlElement(name = "MerchantInfo")
	List<MerchantXmlVo> merchantList;

	public List<MerchantXmlVo> getMerchantList() {
		return merchantList;
	}

	public void setMerchantList(List<MerchantXmlVo> merchantList) {
		this.merchantList = merchantList;
	}

	@Override
	public String toString() {
		return "MerchantToWmsVo [merchantList=" + merchantList + "]";
	}

}
