package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

import java.util.Date;

public class BasCustomerForm {
	private String receivingId;
	private String token;
	private String enterpriseId;
	private String clientId;
	private String supplierId;
	private String isCheck;
	private String isCooperation;
	private String createId;
	private String createDate;
	private String editId;
	private String editDate;
	private String isUse;
	private String isReturn;
	private String deliveryAddress;
	private String sellerName;
	private String newreceivingId;

	private String receivingAddressId;
	private String billclassinv;          //供应商对应货主
	private String defaultreplenishrule;  //收货单位对应货主

	private java.lang.String customerid;
	private java.lang.String customerType;
	private java.lang.String activeFlag;
	private java.lang.String address1;
	private java.lang.String address2;
	private java.lang.String address3;
	private java.lang.String address4;
	private String addtime;
	private java.lang.String addwho;
	private java.lang.String city;
	private java.lang.String contact1;
	private java.lang.String contact1Email;
	private java.lang.String contact1Fax;
	private java.lang.String contact1Tel1;
	private java.lang.String contact1Tel2;
	private java.lang.String contact1Title;
	private java.lang.String contact2;
	private java.lang.String contact2Email;
	private java.lang.String contact2Fax;
	private java.lang.String contact2Tel1;
	private java.lang.String contact2Tel2;
	private java.lang.String contact2Title;
	private java.lang.String contact3;
	private java.lang.String contact3Email;
	private java.lang.String contact3Fax;
	private java.lang.String contact3Tel1;
	private java.lang.String contact3Tel2;
	private java.lang.String contact3Title;
	private java.lang.String country;
	private java.lang.String currency;


	private java.lang.String defaultallocationrule;
	private java.lang.String defaultpackid;
	private java.lang.String defaultputawayrule;
	private java.lang.String defaultreceivinguom;
	private java.lang.String defaultreportuom;
	private java.lang.String defaultshipmentuom;
	private java.lang.String defaultskulotid;
	private java.lang.String defaultsoftallocationrule;
	private java.lang.String descrC;
	private java.lang.String descrE;
	private java.lang.String easycode;
	private String edittime;
	private java.lang.String editwho;
	private java.math.BigDecimal overrcvpercentage;
	private java.lang.String overreceiving;
	private java.lang.String province;
	private java.lang.String qcrule;
	private java.lang.String rOwner;
	private java.lang.String reservecode;
	private java.lang.String rotationid;
	private java.lang.String routecode;
	private java.lang.String udf1;
	private java.lang.String udf2;
	private java.lang.String udf3;
	private java.lang.String udf4;
	private java.lang.String udf5;
	private java.lang.String zip;
	private java.lang.String customerTypeName;
	private String operateType;
	private String enterpriseNo;
	private String shortHandName;
	private String enterpriseName;

	private String enterpriseType;

	private String contacts;

	private String contactsPhone;

	private String remark;

	private String contractNo;//误

	private String supContractNo;
	private String contractUrl;

	private String clientContent;

	private java.util.Date clientStartDate;

	private java.util.Date clientEndDate;

	private String clientTerm;

	private String isChineseLabel;

	private String bankaccount;

	private String billclass;

	private String notes;

	private String idList;

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public String getBillclassinv() {
		return billclassinv;
	}

	public void setBillclassinv(String billclassinv) {
		this.billclassinv = billclassinv;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}


	public String getIsChineseLabel() {
		return isChineseLabel;
	}

	public void setIsChineseLabel(String isChineseLabel) {
		this.isChineseLabel = isChineseLabel;
	}

	public String getClientTerm() {
		return clientTerm;
	}

	public void setClientTerm(String clientTerm) {
		this.clientTerm = clientTerm;
	}

	public String getSupContractNo() {
		return supContractNo;
	}

	public void setSupContractNo(String supContractNo) {
		this.supContractNo = supContractNo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNewreceivingId() {
		return newreceivingId;
	}

	public void setNewreceivingId(String newreceivingId) {
		this.newreceivingId = newreceivingId;
	}

	public String getReceivingId() {
		return receivingId;
	}

	public void setReceivingId(String receivingId) {
		this.receivingId = receivingId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getIsCooperation() {
		return isCooperation;
	}

	public void setIsCooperation(String isCooperation) {
		this.isCooperation = isCooperation;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getEditId() {
		return editId;
	}

	public void setEditId(String editId) {
		this.editId = editId;
	}

	public String getEditDate() {
		return editDate;
	}

	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public String getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getrOwner() {
		return rOwner;
	}

	public void setrOwner(String rOwner) {
		this.rOwner = rOwner;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getEnterpriseNo() {
		return enterpriseNo;
	}

	public void setEnterpriseNo(String enterpriseNo) {
		this.enterpriseNo = enterpriseNo;
	}

	public String getShortHandName() {
		return shortHandName;
	}

	public void setShortHandName(String shortHandName) {
		this.shortHandName = shortHandName;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getReceivingAddressId() {
		return receivingAddressId;
	}

	public void setReceivingAddressId(String receivingAddressId) {
		this.receivingAddressId = receivingAddressId;
	}

	public String getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsPhone() {
		return contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractUrl() {
		return contractUrl;
	}

	public void setContractUrl(String contractUrl) {
		this.contractUrl = contractUrl;
	}

	public String getClientContent() {
		return clientContent;
	}

	public void setClientContent(String clientContent) {
		this.clientContent = clientContent;
	}

	public Date getClientStartDate() {
		return clientStartDate;
	}

	public void setClientStartDate(Date clientStartDate) {
		this.clientStartDate = clientStartDate;
	}

	public Date getClientEndDate() {
		return clientEndDate;
	}

	public void setClientEndDate(Date clientEndDate) {
		this.clientEndDate = clientEndDate;
	}




	public java.lang.String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(java.lang.String customerid) {
		this.customerid = customerid;
	}

	public java.lang.String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(java.lang.String customerType) {
		this.customerType = customerType;
	}

	public java.lang.String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(java.lang.String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public java.lang.String getAddress1() {
		return address1;
	}

	public void setAddress1(java.lang.String address1) {
		this.address1 = address1;
	}

	public java.lang.String getAddress2() {
		return address2;
	}

	public void setAddress2(java.lang.String address2) {
		this.address2 = address2;
	}

	public java.lang.String getAddress3() {
		return address3;
	}

	public void setAddress3(java.lang.String address3) {
		this.address3 = address3;
	}

	public java.lang.String getAddress4() {
		return address4;
	}

	public void setAddress4(java.lang.String address4) {
		this.address4 = address4;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public java.lang.String getAddwho() {
		return addwho;
	}

	public void setAddwho(java.lang.String addwho) {
		this.addwho = addwho;
	}

	public java.lang.String getCity() {
		return city;
	}

	public void setCity(java.lang.String city) {
		this.city = city;
	}

	public java.lang.String getContact1() {
		return contact1;
	}

	public void setContact1(java.lang.String contact1) {
		this.contact1 = contact1;
	}

	public java.lang.String getContact1Email() {
		return contact1Email;
	}

	public void setContact1Email(java.lang.String contact1Email) {
		this.contact1Email = contact1Email;
	}

	public java.lang.String getContact1Fax() {
		return contact1Fax;
	}

	public void setContact1Fax(java.lang.String contact1Fax) {
		this.contact1Fax = contact1Fax;
	}

	public java.lang.String getContact1Tel1() {
		return contact1Tel1;
	}

	public void setContact1Tel1(java.lang.String contact1Tel1) {
		this.contact1Tel1 = contact1Tel1;
	}

	public java.lang.String getContact1Tel2() {
		return contact1Tel2;
	}

	public void setContact1Tel2(java.lang.String contact1Tel2) {
		this.contact1Tel2 = contact1Tel2;
	}

	public java.lang.String getContact1Title() {
		return contact1Title;
	}

	public void setContact1Title(java.lang.String contact1Title) {
		this.contact1Title = contact1Title;
	}

	public java.lang.String getContact2() {
		return contact2;
	}

	public void setContact2(java.lang.String contact2) {
		this.contact2 = contact2;
	}

	public java.lang.String getContact2Email() {
		return contact2Email;
	}

	public void setContact2Email(java.lang.String contact2Email) {
		this.contact2Email = contact2Email;
	}

	public java.lang.String getContact2Fax() {
		return contact2Fax;
	}

	public void setContact2Fax(java.lang.String contact2Fax) {
		this.contact2Fax = contact2Fax;
	}

	public java.lang.String getContact2Tel1() {
		return contact2Tel1;
	}

	public void setContact2Tel1(java.lang.String contact2Tel1) {
		this.contact2Tel1 = contact2Tel1;
	}

	public java.lang.String getContact2Tel2() {
		return contact2Tel2;
	}

	public void setContact2Tel2(java.lang.String contact2Tel2) {
		this.contact2Tel2 = contact2Tel2;
	}

	public java.lang.String getContact2Title() {
		return contact2Title;
	}

	public void setContact2Title(java.lang.String contact2Title) {
		this.contact2Title = contact2Title;
	}

	public java.lang.String getContact3() {
		return contact3;
	}

	public void setContact3(java.lang.String contact3) {
		this.contact3 = contact3;
	}

	public java.lang.String getContact3Email() {
		return contact3Email;
	}

	public void setContact3Email(java.lang.String contact3Email) {
		this.contact3Email = contact3Email;
	}

	public java.lang.String getContact3Fax() {
		return contact3Fax;
	}

	public void setContact3Fax(java.lang.String contact3Fax) {
		this.contact3Fax = contact3Fax;
	}

	public java.lang.String getContact3Tel1() {
		return contact3Tel1;
	}

	public void setContact3Tel1(java.lang.String contact3Tel1) {
		this.contact3Tel1 = contact3Tel1;
	}

	public java.lang.String getContact3Tel2() {
		return contact3Tel2;
	}

	public void setContact3Tel2(java.lang.String contact3Tel2) {
		this.contact3Tel2 = contact3Tel2;
	}

	public java.lang.String getContact3Title() {
		return contact3Title;
	}

	public void setContact3Title(java.lang.String contact3Title) {
		this.contact3Title = contact3Title;
	}

	public java.lang.String getCountry() {
		return country;
	}

	public void setCountry(java.lang.String country) {
		this.country = country;
	}

	public java.lang.String getCurrency() {
		return currency;
	}

	public void setCurrency(java.lang.String currency) {
		this.currency = currency;
	}

	public java.lang.String getDefaultallocationrule() {
		return defaultallocationrule;
	}

	public void setDefaultallocationrule(java.lang.String defaultallocationrule) {
		this.defaultallocationrule = defaultallocationrule;
	}

	public java.lang.String getDefaultpackid() {
		return defaultpackid;
	}

	public void setDefaultpackid(java.lang.String defaultpackid) {
		this.defaultpackid = defaultpackid;
	}

	public java.lang.String getDefaultputawayrule() {
		return defaultputawayrule;
	}

	public void setDefaultputawayrule(java.lang.String defaultputawayrule) {
		this.defaultputawayrule = defaultputawayrule;
	}

	public java.lang.String getDefaultreceivinguom() {
		return defaultreceivinguom;
	}

	public void setDefaultreceivinguom(java.lang.String defaultreceivinguom) {
		this.defaultreceivinguom = defaultreceivinguom;
	}

	public java.lang.String getDefaultreplenishrule() {
		return defaultreplenishrule;
	}

	public void setDefaultreplenishrule(java.lang.String defaultreplenishrule) {
		this.defaultreplenishrule = defaultreplenishrule;
	}

	public java.lang.String getDefaultreportuom() {
		return defaultreportuom;
	}

	public void setDefaultreportuom(java.lang.String defaultreportuom) {
		this.defaultreportuom = defaultreportuom;
	}

	public java.lang.String getDefaultshipmentuom() {
		return defaultshipmentuom;
	}

	public void setDefaultshipmentuom(java.lang.String defaultshipmentuom) {
		this.defaultshipmentuom = defaultshipmentuom;
	}

	public java.lang.String getDefaultskulotid() {
		return defaultskulotid;
	}

	public void setDefaultskulotid(java.lang.String defaultskulotid) {
		this.defaultskulotid = defaultskulotid;
	}

	public java.lang.String getDefaultsoftallocationrule() {
		return defaultsoftallocationrule;
	}

	public void setDefaultsoftallocationrule(java.lang.String defaultsoftallocationrule) {
		this.defaultsoftallocationrule = defaultsoftallocationrule;
	}

	public java.lang.String getDescrC() {
		return descrC;
	}

	public void setDescrC(java.lang.String descrC) {
		this.descrC = descrC;
	}

	public java.lang.String getDescrE() {
		return descrE;
	}

	public void setDescrE(java.lang.String descrE) {
		this.descrE = descrE;
	}

	public java.lang.String getEasycode() {
		return easycode;
	}

	public void setEasycode(java.lang.String easycode) {
		this.easycode = easycode;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public String getEdittime() {
		return edittime;
	}

	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}

	public java.lang.String getEditwho() {
		return editwho;
	}

	public void setEditwho(java.lang.String editwho) {
		this.editwho = editwho;
	}

	public java.math.BigDecimal getOverrcvpercentage() {
		return overrcvpercentage;
	}

	public void setOverrcvpercentage(java.math.BigDecimal overrcvpercentage) {
		this.overrcvpercentage = overrcvpercentage;
	}

	public java.lang.String getOverreceiving() {
		return overreceiving;
	}

	public void setOverreceiving(java.lang.String overreceiving) {
		this.overreceiving = overreceiving;
	}

	public java.lang.String getProvince() {
		return province;
	}

	public void setProvince(java.lang.String province) {
		this.province = province;
	}

	public java.lang.String getQcrule() {
		return qcrule;
	}

	public void setQcrule(java.lang.String qcrule) {
		this.qcrule = qcrule;
	}

	public java.lang.String getROwner() {
		return rOwner;
	}

	public void setROwner(java.lang.String rOwner) {
		this.rOwner = rOwner;
	}

	public java.lang.String getReservecode() {
		return reservecode;
	}

	public void setReservecode(java.lang.String reservecode) {
		this.reservecode = reservecode;
	}

	public java.lang.String getRotationid() {
		return rotationid;
	}

	public void setRotationid(java.lang.String rotationid) {
		this.rotationid = rotationid;
	}

	public java.lang.String getRoutecode() {
		return routecode;
	}

	public void setRoutecode(java.lang.String routecode) {
		this.routecode = routecode;
	}

	public java.lang.String getUdf1() {
		return udf1;
	}

	public void setUdf1(java.lang.String udf1) {
		this.udf1 = udf1;
	}

	public java.lang.String getUdf2() {
		return udf2;
	}

	public void setUdf2(java.lang.String udf2) {
		this.udf2 = udf2;
	}

	public java.lang.String getUdf3() {
		return udf3;
	}

	public void setUdf3(java.lang.String udf3) {
		this.udf3 = udf3;
	}

	public java.lang.String getUdf4() {
		return udf4;
	}

	public void setUdf4(java.lang.String udf4) {
		this.udf4 = udf4;
	}

	public java.lang.String getUdf5() {
		return udf5;
	}

	public void setUdf5(java.lang.String udf5) {
		this.udf5 = udf5;
	}

	public java.lang.String getZip() {
		return zip;
	}

	public void setZip(java.lang.String zip) {
		this.zip = zip;
	}

	public java.lang.String getCustomerTypeName() {
		return customerTypeName;
	}

	public void setCustomerTypeName(java.lang.String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}

	public String getBankaccount() {
		return bankaccount;
	}

	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}

	public String getBillclass() {
		return billclass;
	}

	public void setBillclass(String billclass) {
		this.billclass = billclass;
	}
}