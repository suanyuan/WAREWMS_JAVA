package com.wms.vo.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "productInfo")
public class QWmsInstockOrderDetailVO {
	@XmlElement
	private String skuCode;
	@XmlElement
	private Integer itemCount;

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	@Override
	public String toString() {
		return "QWmsInstockOrderDetailVO [skuCode=" + skuCode + ", itemCount=" + itemCount + "]";
	}
}
