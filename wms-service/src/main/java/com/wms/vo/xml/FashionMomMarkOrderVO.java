package com.wms.vo.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request_order_mark")
@XmlType(propOrder = { "item" })
public class FashionMomMarkOrderVO {
	@XmlElement
	private FashionMomMarkItemVO item;

	public FashionMomMarkItemVO getItem() {
		return item;
	}

	public void setItem(FashionMomMarkItemVO item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "FashionMomMarkOrderXmlVo [item=" + item + "]";
	}
	
}
