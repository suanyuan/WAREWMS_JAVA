package com.wms.vo.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "skus")
public class QWmsStocksVO {
	@XmlElement(name = "sku")
	private List<QWmsStockVO> skus;

	public List<QWmsStockVO> getSkus() {
		return skus;
	}

	public void setSkus(List<QWmsStockVO> skus) {
		this.skus = skus;
	}
}