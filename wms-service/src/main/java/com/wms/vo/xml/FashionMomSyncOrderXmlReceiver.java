package com.wms.vo.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "receiver")
@XmlType(propOrder = { "name", "postcode","phone","mobile","prov","city","district","address","buyer_nick"})
public class FashionMomSyncOrderXmlReceiver {
	@XmlElement
	private String name;
	@XmlElement
	private String postcode;
	@XmlElement
	private String phone;
	@XmlElement
	private String mobile;
	@XmlElement
	private String prov;
	@XmlElement
	private String city;
	@XmlElement
	private String district;
	@XmlElement
	private String address;
	@XmlElement
	private String buyer_nick;
	
	public String getBuyer_nick() {
		return buyer_nick;
	}
	public void setBuyer_nick(String buyer_nick) {
		this.buyer_nick = buyer_nick;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProv() {
		return prov;
	}
	public void setProv(String prov) {
		this.prov = prov;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "FashionMomSyncOrderXmlReceiver [name=" + name + ", postcode=" + postcode + ", phone=" + phone
				+ ", mobile=" + mobile + ", prov=" + prov + ", city=" + city + ", district=" + district + ", address="
				+ address + "]";
	}
}
