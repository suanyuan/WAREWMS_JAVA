package com.wms.vo.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *		<Detail>
 *			<SkuCode>TEST002</SkuCode>
 *			<ReceivedTime>2015-01-01 11:20:00</ReceivedTime>
 *			<ExpectedQty>10</ExpectedQty>
 *			<ReceivedQty>10</ReceivedQty>
 *			<Lotatt01></Lotatt01>
 *			<Lotatt02></Lotatt02>
 *			<Lotatt03>2015-01-01</Lotatt03>
 *			<Lotatt04></Lotatt04>
 *			<Lotatt05></Lotatt05>
 *			<Lotatt06>HG</Lotatt06>
 *			<Lotatt07></Lotatt07>
 *			<Lotatt08></Lotatt08>
 *			<Lotatt09></Lotatt09>
 *			<Lotatt10></Lotatt10>
 *			<Lotatt11></Lotatt11>
 *			<Lotatt12></Lotatt12>
 *		</Detail>
 * @author Owen
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Detail")
@XmlType(propOrder = {"skuCode","receivedTime","expectedQty","receivedQty","lotatt01","lotatt02","lotatt03","lotatt04","lotatt05","lotatt06","lotatt07","lotatt08","lotatt09","lotatt10","lotatt11","lotatt12"})
public class DetailXmlVo {
	@XmlElement(name = "SkuCode")
	private String skuCode;
	@XmlElement(name = "ReceivedTime")
	private String receivedTime;
	@XmlElement(name = "ExpectedQty")
	private Integer expectedQty;
	@XmlElement(name = "ReceivedQty")
	private Integer receivedQty;
	@XmlElement(name = "Lotatt01")
	private String lotatt01;
	@XmlElement(name = "Lotatt02")
	private String lotatt02;
	@XmlElement(name = "Lotatt03")
	private String lotatt03;
	@XmlElement(name = "Lotatt04")
	private String lotatt04;
	@XmlElement(name = "Lotatt05")
	private String lotatt05;
	@XmlElement(name = "Lotatt06")
	private String lotatt06;
	@XmlElement(name = "Lotatt07")
	private String lotatt07;
	@XmlElement(name = "Lotatt08")
	private String lotatt08;
	@XmlElement(name = "Lotatt09")
	private String lotatt09;
	@XmlElement(name = "Lotatt10")
	private String lotatt10;
	@XmlElement(name = "Lotatt11")
	private String lotatt11;
	@XmlElement(name = "Lotatt12")
	private String lotatt12;

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Integer getExpectedQty() {
		return expectedQty;
	}

	public void setExpectedQty(Integer expectedQty) {
		this.expectedQty = expectedQty;
	}

	public Integer getReceivedQty() {
		return receivedQty;
	}

	public void setReceivedQty(Integer receivedQty) {
		this.receivedQty = receivedQty;
	}

	public String getLotatt01() {
		return lotatt01;
	}

	public void setLotatt01(String lotatt01) {
		this.lotatt01 = lotatt01;
	}

	public String getLotatt02() {
		return lotatt02;
	}

	public void setLotatt02(String lotatt02) {
		this.lotatt02 = lotatt02;
	}

	public String getReceivedTime() {
		return receivedTime;
	}

	public void setReceivedTime(String receivedTime) {
		this.receivedTime = receivedTime;
	}

	public String getLotatt03() {
		return lotatt03;
	}

	public void setLotatt03(String lotatt03) {
		this.lotatt03 = lotatt03;
	}

	public String getLotatt04() {
		return lotatt04;
	}

	public void setLotatt04(String lotatt04) {
		this.lotatt04 = lotatt04;
	}

	public String getLotatt05() {
		return lotatt05;
	}

	public void setLotatt05(String lotatt05) {
		this.lotatt05 = lotatt05;
	}

	public String getLotatt06() {
		return lotatt06;
	}

	public void setLotatt06(String lotatt06) {
		this.lotatt06 = lotatt06;
	}

	public String getLotatt07() {
		return lotatt07;
	}

	public void setLotatt07(String lotatt07) {
		this.lotatt07 = lotatt07;
	}

	public String getLotatt08() {
		return lotatt08;
	}

	public void setLotatt08(String lotatt08) {
		this.lotatt08 = lotatt08;
	}

	public String getLotatt09() {
		return lotatt09;
	}

	public void setLotatt09(String lotatt09) {
		this.lotatt09 = lotatt09;
	}

	public String getLotatt10() {
		return lotatt10;
	}

	public void setLotatt10(String lotatt10) {
		this.lotatt10 = lotatt10;
	}

	public String getLotatt11() {
		return lotatt11;
	}

	public void setLotatt11(String lotatt11) {
		this.lotatt11 = lotatt11;
	}

	public String getLotatt12() {
		return lotatt12;
	}

	public void setLotatt12(String lotatt12) {
		this.lotatt12 = lotatt12;
	}

	@Override
	public String toString() {
		return "DetailXmlVo [skuCode=" + skuCode + ", receivedTime=" + receivedTime + ", expectedQty=" + expectedQty
				+ ", receivedQty=" + receivedQty + ", lotatt01=" + lotatt01 + ", lotatt02=" + lotatt02 + ", lotatt03="
				+ lotatt03 + ", lotatt04=" + lotatt04 + ", lotatt05=" + lotatt05 + ", lotatt06=" + lotatt06
				+ ", lotatt07=" + lotatt07 + ", lotatt08=" + lotatt08 + ", lotatt09=" + lotatt09 + ", lotatt10="
				+ lotatt10 + ", lotatt11=" + lotatt11 + ", lotatt12=" + lotatt12 + "]";
	}
	
}
