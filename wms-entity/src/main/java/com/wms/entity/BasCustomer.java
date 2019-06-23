package com.wms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the Bas_Customer database table.
 * 
 */
@Entity
public class BasCustomer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	private int hashCode = Integer.MIN_VALUE;

	@Id
	private String customerid;

	private String customerType;

	private String activeFlag;

	private String address1;

	private String address2;

	private String address3;

	private String address4;

	@Temporal(TemporalType.TIMESTAMP)
	private Date addtime;

	private String addwho;

	private String city;

	private String contact1;

	private String contact1Email;

	private String contact1Fax;

	private String contact1Tel1;

	private String contact1Tel2;

	private String contact1Title;

	private String contact2;

	private String contact2Email;

	private String contact2Fax;

	private String contact2Tel1;

	private String contact2Tel2;

	private String contact2Title;

	private String contact3;

	private String contact3Email;

	private String contact3Fax;

	private String contact3Tel1;

	private String contact3Tel2;

	private String contact3Title;

	private String country;

	private String currency;

	private String defaultallocationrule;

	private String defaultpackid;

	private String defaultputawayrule;

	private String defaultreceivinguom;

	private String defaultreplenishrule;

	private String defaultreportuom;

	private String defaultshipmentuom;

	private String defaultskulotid;

	private String defaultsoftallocationrule;

	private String descrC;

	private String descrE;

	private String easycode;

	@Temporal(TemporalType.TIMESTAMP)
	private Date edittime;

	private String editwho;

	private BigDecimal overrcvpercentage;

	private String overreceiving;

	private String province;

	private String qcrule;

	private String rOwner;

	private String reservecode;

	private String rotationid;

	private String routecode;

	private String udf1;

	private String udf2;

	private String udf3;

	private String udf4;

	private String udf5;

	private String zip;

	private String customerTypeName;

	public BasCustomer() {
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContact1() {
		return contact1;
	}

	public void setContact1(String contact1) {
		this.contact1 = contact1;
	}

	public String getContact1Email() {
		return contact1Email;
	}

	public void setContact1Email(String contact1Email) {
		this.contact1Email = contact1Email;
	}

	public String getContact1Fax() {
		return contact1Fax;
	}

	public void setContact1Fax(String contact1Fax) {
		this.contact1Fax = contact1Fax;
	}

	public String getContact1Tel1() {
		return contact1Tel1;
	}

	public void setContact1Tel1(String contact1Tel1) {
		this.contact1Tel1 = contact1Tel1;
	}

	public String getContact1Tel2() {
		return contact1Tel2;
	}

	public void setContact1Tel2(String contact1Tel2) {
		this.contact1Tel2 = contact1Tel2;
	}

	public String getContact1Title() {
		return contact1Title;
	}

	public void setContact1Title(String contact1Title) {
		this.contact1Title = contact1Title;
	}

	public String getContact2() {
		return contact2;
	}

	public void setContact2(String contact2) {
		this.contact2 = contact2;
	}

	public String getContact2Email() {
		return contact2Email;
	}

	public void setContact2Email(String contact2Email) {
		this.contact2Email = contact2Email;
	}

	public String getContact2Fax() {
		return contact2Fax;
	}

	public void setContact2Fax(String contact2Fax) {
		this.contact2Fax = contact2Fax;
	}

	public String getContact2Tel1() {
		return contact2Tel1;
	}

	public void setContact2Tel1(String contact2Tel1) {
		this.contact2Tel1 = contact2Tel1;
	}

	public String getContact2Tel2() {
		return contact2Tel2;
	}

	public void setContact2Tel2(String contact2Tel2) {
		this.contact2Tel2 = contact2Tel2;
	}

	public String getContact2Title() {
		return contact2Title;
	}

	public void setContact2Title(String contact2Title) {
		this.contact2Title = contact2Title;
	}

	public String getContact3() {
		return contact3;
	}

	public void setContact3(String contact3) {
		this.contact3 = contact3;
	}

	public String getContact3Email() {
		return contact3Email;
	}

	public void setContact3Email(String contact3Email) {
		this.contact3Email = contact3Email;
	}

	public String getContact3Fax() {
		return contact3Fax;
	}

	public void setContact3Fax(String contact3Fax) {
		this.contact3Fax = contact3Fax;
	}

	public String getContact3Tel1() {
		return contact3Tel1;
	}

	public void setContact3Tel1(String contact3Tel1) {
		this.contact3Tel1 = contact3Tel1;
	}

	public String getContact3Tel2() {
		return contact3Tel2;
	}

	public void setContact3Tel2(String contact3Tel2) {
		this.contact3Tel2 = contact3Tel2;
	}

	public String getContact3Title() {
		return contact3Title;
	}

	public void setContact3Title(String contact3Title) {
		this.contact3Title = contact3Title;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDefaultallocationrule() {
		return defaultallocationrule;
	}

	public void setDefaultallocationrule(String defaultallocationrule) {
		this.defaultallocationrule = defaultallocationrule;
	}

	public String getDefaultpackid() {
		return defaultpackid;
	}

	public void setDefaultpackid(String defaultpackid) {
		this.defaultpackid = defaultpackid;
	}

	public String getDefaultputawayrule() {
		return defaultputawayrule;
	}

	public void setDefaultputawayrule(String defaultputawayrule) {
		this.defaultputawayrule = defaultputawayrule;
	}

	public String getDefaultreceivinguom() {
		return defaultreceivinguom;
	}

	public void setDefaultreceivinguom(String defaultreceivinguom) {
		this.defaultreceivinguom = defaultreceivinguom;
	}

	public String getDefaultreplenishrule() {
		return defaultreplenishrule;
	}

	public void setDefaultreplenishrule(String defaultreplenishrule) {
		this.defaultreplenishrule = defaultreplenishrule;
	}

	public String getDefaultreportuom() {
		return defaultreportuom;
	}

	public void setDefaultreportuom(String defaultreportuom) {
		this.defaultreportuom = defaultreportuom;
	}

	public String getDefaultshipmentuom() {
		return defaultshipmentuom;
	}

	public void setDefaultshipmentuom(String defaultshipmentuom) {
		this.defaultshipmentuom = defaultshipmentuom;
	}

	public String getDefaultskulotid() {
		return defaultskulotid;
	}

	public void setDefaultskulotid(String defaultskulotid) {
		this.defaultskulotid = defaultskulotid;
	}

	public String getDefaultsoftallocationrule() {
		return defaultsoftallocationrule;
	}

	public void setDefaultsoftallocationrule(String defaultsoftallocationrule) {
		this.defaultsoftallocationrule = defaultsoftallocationrule;
	}

	public String getDescrC() {
		return descrC;
	}

	public void setDescrC(String descrC) {
		this.descrC = descrC;
	}

	public String getDescrE() {
		return descrE;
	}

	public void setDescrE(String descrE) {
		this.descrE = descrE;
	}

	public String getEasycode() {
		return easycode;
	}

	public void setEasycode(String easycode) {
		this.easycode = easycode;
	}

	public Date getEdittime() {
		return edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public BigDecimal getOverrcvpercentage() {
		return overrcvpercentage;
	}

	public void setOverrcvpercentage(BigDecimal overrcvpercentage) {
		this.overrcvpercentage = overrcvpercentage;
	}

	public String getOverreceiving() {
		return overreceiving;
	}

	public void setOverreceiving(String overreceiving) {
		this.overreceiving = overreceiving;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getQcrule() {
		return qcrule;
	}

	public void setQcrule(String qcrule) {
		this.qcrule = qcrule;
	}

	public String getrOwner() {
		return rOwner;
	}

	public void setrOwner(String rOwner) {
		this.rOwner = rOwner;
	}

	public String getReservecode() {
		return reservecode;
	}

	public void setReservecode(String reservecode) {
		this.reservecode = reservecode;
	}

	public String getRotationid() {
		return rotationid;
	}

	public void setRotationid(String rotationid) {
		this.rotationid = rotationid;
	}

	public String getRoutecode() {
		return routecode;
	}

	public void setRoutecode(String routecode) {
		this.routecode = routecode;
	}

	public String getUdf1() {
		return udf1;
	}

	public void setUdf1(String udf1) {
		this.udf1 = udf1;
	}

	public String getUdf2() {
		return udf2;
	}

	public void setUdf2(String udf2) {
		this.udf2 = udf2;
	}

	public String getUdf3() {
		return udf3;
	}

	public void setUdf3(String udf3) {
		this.udf3 = udf3;
	}

	public String getUdf4() {
		return udf4;
	}

	public void setUdf4(String udf4) {
		this.udf4 = udf4;
	}

	public String getUdf5() {
		return udf5;
	}

	public void setUdf5(String udf5) {
		this.udf5 = udf5;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCustomerTypeName() {
		return customerTypeName;
	}

	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((customerType == null) ? 0 : customerType.hashCode());
		result = prime * result
				+ ((customerid == null) ? 0 : customerid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasCustomer other = (BasCustomer) obj;
		if (customerType == null) {
			if (other.customerType != null)
				return false;
		} else if (!customerType.equals(other.customerType))
			return false;
		if (customerid == null) {
			if (other.customerid != null)
				return false;
		} else if (!customerid.equals(other.customerid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BasCustomer [hashCode=" + hashCode + ", customerid="
				+ customerid + ", customerType=" + customerType
				+ ", activeFlag=" + activeFlag + ", address1=" + address1
				+ ", address2=" + address2 + ", address3=" + address3
				+ ", address4=" + address4 + ", addtime=" + addtime
				+ ", addwho=" + addwho + ", city=" + city + ", contact1="
				+ contact1 + ", contact1Email=" + contact1Email
				+ ", contact1Fax=" + contact1Fax + ", contact1Tel1="
				+ contact1Tel1 + ", contact1Tel2=" + contact1Tel2
				+ ", contact1Title=" + contact1Title + ", contact2=" + contact2
				+ ", contact2Email=" + contact2Email + ", contact2Fax="
				+ contact2Fax + ", contact2Tel1=" + contact2Tel1
				+ ", contact2Tel2=" + contact2Tel2 + ", contact2Title="
				+ contact2Title + ", contact3=" + contact3 + ", contact3Email="
				+ contact3Email + ", contact3Fax=" + contact3Fax
				+ ", contact3Tel1=" + contact3Tel1 + ", contact3Tel2="
				+ contact3Tel2 + ", contact3Title=" + contact3Title
				+ ", country=" + country + ", currency=" + currency
				+ ", defaultallocationrule=" + defaultallocationrule
				+ ", defaultpackid=" + defaultpackid + ", defaultputawayrule="
				+ defaultputawayrule + ", defaultreceivinguom="
				+ defaultreceivinguom + ", defaultreplenishrule="
				+ defaultreplenishrule + ", defaultreportuom="
				+ defaultreportuom + ", defaultshipmentuom="
				+ defaultshipmentuom + ", defaultskulotid=" + defaultskulotid
				+ ", defaultsoftallocationrule=" + defaultsoftallocationrule
				+ ", descrC=" + descrC + ", descrE=" + descrE + ", easycode="
				+ easycode + ", edittime=" + edittime + ", editwho=" + editwho
				+ ", overrcvpercentage=" + overrcvpercentage
				+ ", overreceiving=" + overreceiving + ", province=" + province
				+ ", qcrule=" + qcrule + ", rOwner=" + rOwner
				+ ", reservecode=" + reservecode + ", rotationid=" + rotationid
				+ ", routecode=" + routecode + ", udf1=" + udf1 + ", udf2="
				+ udf2 + ", udf3=" + udf3 + ", udf4=" + udf4 + ", udf5=" + udf5
				+ ", zip=" + zip + ", customerTypeName=" + customerTypeName
				+ "]";
	}
	
}