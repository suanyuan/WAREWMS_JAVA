package com.wms.vo.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *	<ASNs>
 *		<ASNNo>0000000001</ASNNo>
 *		<CustmorOrderNo>CG00000001</CustmorOrderNo>
 *		<ExpectedArriveTime>2015-01-01 10:00:00</ExpectedArriveTime>
 *		<Details>
 *			<Detail>
 *				<SkuCode>TEST001</SkuCode>
 *				<ReceivedTime>2015-01-01 11:00:00</ReceivedTime>
 *				<ExpectedQty>20</ExpectedQty>
 *				<ReceivedQty>20</ReceivedQty>
 *				<Lotatt01></Lotatt01>
 *				<Lotatt02></Lotatt02>
 *				<Lotatt03>2015-01-01</Lotatt03>
 *				<Lotatt04></Lotatt04>
 *				<Lotatt05></Lotatt05>
 *				<Lotatt06>HG</Lotatt06>
 *				<Lotatt07></Lotatt07>
 *				<Lotatt08></Lotatt08>
 *				<Lotatt09></Lotatt09>
 *				<Lotatt10></Lotatt10>
 *				<Lotatt11></Lotatt11>
 *				<Lotatt12></Lotatt12>
 *			</Detail>
 *			<Detail>
 *				<SkuCode>TEST002</SkuCode>
 *				<ReceivedTime>2015-01-01 11:20:00</ReceivedTime>
 *				<ExpectedQty>10</ExpectedQty>
 *				<ReceivedQty>10</ReceivedQty>
 *				<Lotatt01></Lotatt01>
 *				<Lotatt02></Lotatt02>
 *				<Lotatt03>2015-01-01</Lotatt03>
 *				<Lotatt04></Lotatt04>
 *				<Lotatt05></Lotatt05>
 *				<Lotatt06>HG</Lotatt06>
 *				<Lotatt07></Lotatt07>
 *				<Lotatt08></Lotatt08>
 *				<Lotatt09></Lotatt09>
 *				<Lotatt10></Lotatt10>
 *				<Lotatt11></Lotatt11>
 *				<Lotatt12></Lotatt12>
 *			</Detail>
 *		</Details>
 *	</ASNs>
 *
 * @author Owen
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ASNs")
@XmlType(propOrder = {"asnno","custmorOrderNo","expectedArriveTime","detailList"})
public class AsnXmlVo {
	@XmlElement(name = "ASNNo")
	private String asnno;
	@XmlElement(name = "CustmorOrderNo")
	private String custmorOrderNo;
	@XmlElement(name = "ExpectedArriveTime")
	private String expectedArriveTime;
	@XmlElementWrapper(name = "Details")
	@XmlElement(name = "Detail")
	private List<DetailXmlVo> detailList;

	public String getAsnno() {
		return asnno;
	}

	public void setAsnno(String asnno) {
		this.asnno = asnno;
	}

	public String getCustmorOrderNo() {
		return custmorOrderNo;
	}

	public void setCustmorOrderNo(String custmorOrderNo) {
		this.custmorOrderNo = custmorOrderNo;
	}

	public String getExpectedArriveTime() {
		return expectedArriveTime;
	}

	public void setExpectedArriveTime(String expectedArriveTime) {
		this.expectedArriveTime = expectedArriveTime;
	}

	public List<DetailXmlVo> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<DetailXmlVo> detailList) {
		this.detailList = detailList;
	}

	@Override
	public String toString() {
		return "AsnXmlVo [asnno=" + asnno + ", custmorOrderNo=" + custmorOrderNo + ", expectedArriveTime="
				+ expectedArriveTime + ", detailList=" + detailList + "]";
	}
}
