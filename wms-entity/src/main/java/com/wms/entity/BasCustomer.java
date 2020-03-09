package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class BasCustomer  implements Serializable {
	private String receivingAddressId;

	private String supContractNo;
	private String contractUrl;
	private String clientContent;
	private java.util.Date clientStartDate;
	private java.util.Date clientEndDate;
	private String clientTerm;
	private String enterpriseNo;


	private String customerid;
	private String isChineseLabel;

	private String customerType;
	private String customerTypeName;

	private String descrC;

	private String operateTypeName;
	private String enterpriseId;
	private String descrE;

	private String address1;

	private String address2;

	private String address3;

	private String address4;

	private String city;

	private String province;

	private String zip;

	private String country;

	private String contact1;

	private String operateType;

	private String contact1Tel1;

	private String contact1Tel2;

	private String contact1Fax;

	private String contact1Email;

	private String contact1Title;

	private String contact2;

	private String contact2Tel1;

	private String contact2Tel2;

	private String contact2Fax;

	private String contact2Email;

	private String contact2Title;

	private String contact3;

	private String contact3Tel1;

	private String contact3Tel2;

	private String contact3Fax;

	private String contact3Email;

	private String contact3Title;

	private String billto;

	private String activeFlag;

	private String cartongroup;

	private String defaultsoftallocationrule;

	private String currency;

	private String defaultputawayrule;

	private String docPrefix;

	private String defaultallocationrule;

	private String udf1;

	private String udf2;

	private String udf3;

	private String udf4;

	private String udf5;

	private String defaultskulotid;

	private String asnref1tolot4;

	private String asnref2tolot5;

	private String asnref3tolot6;

	private String asnref4tolot7;

	private String soref1tolot4;

	private String soref2tolot5;

	private String soref3tolot6;

	private String soref4tolot7;

	private String notes;

	private java.util.Date addtime;

	private String addwho;



//	@Override
//	public boolean equals(Object o) {
//		if (this == o) return true;
//		if (o == null || getClass() != o.getClass()) return false;
//
//		BasCustomer person = (BasCustomer) o;
//
//		if (age != person.age) return false;
//		return name != null ? name.equals(person.name) : person.name == null;
//
//	}



	private java.util.Date edittime;

	private String editwho;

	private String defaultreceivinguom;

	private String defaultshipmentuom;

	private String defaultreportuom;

	private String overreceiving;

	private String vat;

	private String bankaccount;

	private String inboundLabel;

	private Double allinrate;

	private String billclass;

	private Double overrcvpercentage;

	private String rOwner;

	private String cubicuom;

	private String weightuom;

	private String asnref5Tolot8;

	private String soref5Tolot8;
//
//	private String billclassinv;
//
//	private String defaultreplenishrule;

	private String billclassinv;          //供应商对应货主
	private String defaultreplenishrule;  //收货单位对应货主

	private String routecode;

	private String easycode;

	private String snAsnCls;

	private String snSoCls;

	private String snAsnSo;

	private String asnSndEml;

	private String soSndEml;

	private String idxLoadSku;

	private String idxLoadSupplier;

	private String idxLoadConsignee;

	private String invchgwithshipment;

	private String carrier;

	private String outboundLabel;

	private String ediCode;

	private String tariffid;

	private String rotationid;

	private String reservecode;

	private String calculatecode;

	private String defaultpackid;

	private Long billingdate;

	private String followup;//国内国外

	private String hEdi01;

	private String hEdi02;

	private String hEdi03;

	private String hEdi04;

	private String hEdi05;

	private String hEdi06;

	private String hEdi07;

	private String hEdi08;

	private String hEdi09;

	private String hEdi10;

	private String hEdi11;

	private String hEdi12;

	private String hEdi13;

	private String hEdi14;

	private String hEdi15;

	private String hEdi16;

	private String hEdi17;

	private String hEdi18;

	private String hEdi19;

	private String hEdi20;

	private String dEdi01;

	private String dEdi02;

	private String orderbysql;

	private String qcrule;

	private String billwithasntype;

	private String billwithsotype;

	private String allinflag;

	private Double allinarea;

	private String allinratetype;

	private String stop;

	private String copypricetolotatt09;

	private String printmedicineqcreport;

	private String bilObdStk;

	private String asnnobycustomer;

	private String ordernobycustomer;

	private String orderlotcontrol;

	private String fullcaselotcontrol;

	private String piecelotcontrol;

	private String asnLnkPo;

	private String inboundserialnoqtycontrol;

	private String outboundserialnoqtycontrol;

	private String validdatefrom;

	private String validdateto;

	private String emedicineqcreport;

	private String skuanalysisfields;






	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Date getEdittime() {
		return edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;
	}
	public String getReceivingAddressId() {
		return receivingAddressId;
	}

	public void setReceivingAddressId(String receivingAddressId) {
		this.receivingAddressId = receivingAddressId;
	}

	public String getCustomerTypeName() {
		return customerTypeName;
	}

	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}

	public String getOperateTypeName() {
		return operateTypeName;
	}

	public void setOperateTypeName(String operateTypeName) {
		this.operateTypeName = operateTypeName;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getContact1() {
		return contact1;
	}

	public void setContact1(String contact1) {
		this.contact1 = contact1;
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

	public String getContact1Fax() {
		return contact1Fax;
	}

	public void setContact1Fax(String contact1Fax) {
		this.contact1Fax = contact1Fax;
	}

	public String getContact1Email() {
		return contact1Email;
	}

	public void setContact1Email(String contact1Email) {
		this.contact1Email = contact1Email;
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

	public String getContact2Fax() {
		return contact2Fax;
	}

	public void setContact2Fax(String contact2Fax) {
		this.contact2Fax = contact2Fax;
	}

	public String getContact2Email() {
		return contact2Email;
	}

	public void setContact2Email(String contact2Email) {
		this.contact2Email = contact2Email;
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

	public String getContact3Fax() {
		return contact3Fax;
	}

	public void setContact3Fax(String contact3Fax) {
		this.contact3Fax = contact3Fax;
	}

	public String getContact3Email() {
		return contact3Email;
	}

	public void setContact3Email(String contact3Email) {
		this.contact3Email = contact3Email;
	}

	public String getContact3Title() {
		return contact3Title;
	}

	public void setContact3Title(String contact3Title) {
		this.contact3Title = contact3Title;
	}

	public String getBillto() {
		return billto;
	}

	public void setBillto(String billto) {
		this.billto = billto;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getCartongroup() {
		return cartongroup;
	}

	public void setCartongroup(String cartongroup) {
		this.cartongroup = cartongroup;
	}

	public String getDefaultsoftallocationrule() {
		return defaultsoftallocationrule;
	}

	public void setDefaultsoftallocationrule(String defaultsoftallocationrule) {
		this.defaultsoftallocationrule = defaultsoftallocationrule;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDefaultputawayrule() {
		return defaultputawayrule;
	}

	public void setDefaultputawayrule(String defaultputawayrule) {
		this.defaultputawayrule = defaultputawayrule;
	}

	public String getDocPrefix() {
		return docPrefix;
	}

	public void setDocPrefix(String docPrefix) {
		this.docPrefix = docPrefix;
	}

	public String getDefaultallocationrule() {
		return defaultallocationrule;
	}

	public void setDefaultallocationrule(String defaultallocationrule) {
		this.defaultallocationrule = defaultallocationrule;
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

	public String getDefaultskulotid() {
		return defaultskulotid;
	}

	public void setDefaultskulotid(String defaultskulotid) {
		this.defaultskulotid = defaultskulotid;
	}

	public String getSupContractNo() {
		return supContractNo;
	}

	public void setSupContractNo(String supContractNo) {
		this.supContractNo = supContractNo;
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

	public String getClientTerm() {
		return clientTerm;
	}

	public void setClientTerm(String clientTerm) {
		this.clientTerm = clientTerm;
	}

	public String getAsnref1tolot4() {
		return asnref1tolot4;
	}

	public void setAsnref1tolot4(String asnref1tolot4) {
		this.asnref1tolot4 = asnref1tolot4;
	}

	public String getAsnref2tolot5() {
		return asnref2tolot5;
	}

	public void setAsnref2tolot5(String asnref2tolot5) {
		this.asnref2tolot5 = asnref2tolot5;
	}

	public String getAsnref3tolot6() {
		return asnref3tolot6;
	}

	public void setAsnref3tolot6(String asnref3tolot6) {
		this.asnref3tolot6 = asnref3tolot6;
	}

	public String getAsnref4tolot7() {
		return asnref4tolot7;
	}

	public void setAsnref4tolot7(String asnref4tolot7) {
		this.asnref4tolot7 = asnref4tolot7;
	}

	public String getSoref1tolot4() {
		return soref1tolot4;
	}

	public void setSoref1tolot4(String soref1tolot4) {
		this.soref1tolot4 = soref1tolot4;
	}

	public String getSoref2tolot5() {
		return soref2tolot5;
	}

	public void setSoref2tolot5(String soref2tolot5) {
		this.soref2tolot5 = soref2tolot5;
	}

	public String getSoref3tolot6() {
		return soref3tolot6;
	}

	public void setSoref3tolot6(String soref3tolot6) {
		this.soref3tolot6 = soref3tolot6;
	}

	public String getSoref4tolot7() {
		return soref4tolot7;
	}

	public void setSoref4tolot7(String soref4tolot7) {
		this.soref4tolot7 = soref4tolot7;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}



	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}




	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public String getDefaultreceivinguom() {
		return defaultreceivinguom;
	}

	public void setDefaultreceivinguom(String defaultreceivinguom) {
		this.defaultreceivinguom = defaultreceivinguom;
	}

	public String getDefaultshipmentuom() {
		return defaultshipmentuom;
	}

	public void setDefaultshipmentuom(String defaultshipmentuom) {
		this.defaultshipmentuom = defaultshipmentuom;
	}

	public String getDefaultreportuom() {
		return defaultreportuom;
	}

	public void setDefaultreportuom(String defaultreportuom) {
		this.defaultreportuom = defaultreportuom;
	}

	public String getOverreceiving() {
		return overreceiving;
	}

	public void setOverreceiving(String overreceiving) {
		this.overreceiving = overreceiving;
	}

	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public String getBankaccount() {
		return bankaccount;
	}

	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}

	public String getInboundLabel() {
		return inboundLabel;
	}

	public void setInboundLabel(String inboundLabel) {
		this.inboundLabel = inboundLabel;
	}

	public Double getAllinrate() {
		return allinrate;
	}

	public void setAllinrate(Double allinrate) {
		this.allinrate = allinrate;
	}

	public String getBillclass() {
		return billclass;
	}

	public void setBillclass(String billclass) {
		this.billclass = billclass;
	}

	public Double getOverrcvpercentage() {
		return overrcvpercentage;
	}

	public void setOverrcvpercentage(Double overrcvpercentage) {
		this.overrcvpercentage = overrcvpercentage;
	}

	public String getrOwner() {
		return rOwner;
	}

	public void setrOwner(String rOwner) {
		this.rOwner = rOwner;
	}

	public String getCubicuom() {
		return cubicuom;
	}

	public void setCubicuom(String cubicuom) {
		this.cubicuom = cubicuom;
	}

	public String getWeightuom() {
		return weightuom;
	}

	public void setWeightuom(String weightuom) {
		this.weightuom = weightuom;
	}

	public String getAsnref5Tolot8() {
		return asnref5Tolot8;
	}

	public void setAsnref5Tolot8(String asnref5Tolot8) {
		this.asnref5Tolot8 = asnref5Tolot8;
	}

	public String getSoref5Tolot8() {
		return soref5Tolot8;
	}

	public void setSoref5Tolot8(String soref5Tolot8) {
		this.soref5Tolot8 = soref5Tolot8;
	}

	public String getBillclassinv() {
		return billclassinv;
	}

	public void setBillclassinv(String billclassinv) {
		this.billclassinv = billclassinv;
	}

	public String getDefaultreplenishrule() {
		return defaultreplenishrule;
	}

	public void setDefaultreplenishrule(String defaultreplenishrule) {
		this.defaultreplenishrule = defaultreplenishrule;
	}

	public String getRoutecode() {
		return routecode;
	}

	public void setRoutecode(String routecode) {
		this.routecode = routecode;
	}

	public String getEasycode() {
		return easycode;
	}

	public void setEasycode(String easycode) {
		this.easycode = easycode;
	}

	public String getSnAsnCls() {
		return snAsnCls;
	}

	public void setSnAsnCls(String snAsnCls) {
		this.snAsnCls = snAsnCls;
	}

	public String getSnSoCls() {
		return snSoCls;
	}

	public void setSnSoCls(String snSoCls) {
		this.snSoCls = snSoCls;
	}

	public String getSnAsnSo() {
		return snAsnSo;
	}

	public void setSnAsnSo(String snAsnSo) {
		this.snAsnSo = snAsnSo;
	}

	public String getAsnSndEml() {
		return asnSndEml;
	}

	public void setAsnSndEml(String asnSndEml) {
		this.asnSndEml = asnSndEml;
	}

	public String getSoSndEml() {
		return soSndEml;
	}

	public void setSoSndEml(String soSndEml) {
		this.soSndEml = soSndEml;
	}

	public String getIdxLoadSku() {
		return idxLoadSku;
	}

	public void setIdxLoadSku(String idxLoadSku) {
		this.idxLoadSku = idxLoadSku;
	}

	public String getIdxLoadSupplier() {
		return idxLoadSupplier;
	}

	public void setIdxLoadSupplier(String idxLoadSupplier) {
		this.idxLoadSupplier = idxLoadSupplier;
	}

	public String getIdxLoadConsignee() {
		return idxLoadConsignee;
	}

	public void setIdxLoadConsignee(String idxLoadConsignee) {
		this.idxLoadConsignee = idxLoadConsignee;
	}

	public String getInvchgwithshipment() {
		return invchgwithshipment;
	}

	public void setInvchgwithshipment(String invchgwithshipment) {
		this.invchgwithshipment = invchgwithshipment;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getOutboundLabel() {
		return outboundLabel;
	}

	public void setOutboundLabel(String outboundLabel) {
		this.outboundLabel = outboundLabel;
	}

	public String getEdiCode() {
		return ediCode;
	}

	public void setEdiCode(String ediCode) {
		this.ediCode = ediCode;
	}

	public String getTariffid() {
		return tariffid;
	}

	public void setTariffid(String tariffid) {
		this.tariffid = tariffid;
	}

	public String getRotationid() {
		return rotationid;
	}

	public void setRotationid(String rotationid) {
		this.rotationid = rotationid;
	}

	public String getReservecode() {
		return reservecode;
	}

	public void setReservecode(String reservecode) {
		this.reservecode = reservecode;
	}

	public String getCalculatecode() {
		return calculatecode;
	}

	public void setCalculatecode(String calculatecode) {
		this.calculatecode = calculatecode;
	}

	public String getDefaultpackid() {
		return defaultpackid;
	}

	public void setDefaultpackid(String defaultpackid) {
		this.defaultpackid = defaultpackid;
	}

	public Long getBillingdate() {
		return billingdate;
	}

	public void setBillingdate(Long billingdate) {
		this.billingdate = billingdate;
	}

	public String getFollowup() {
		return followup;
	}

	public void setFollowup(String followup) {
		this.followup = followup;
	}

	public String gethEdi01() {
		return hEdi01;
	}

	public void sethEdi01(String hEdi01) {
		this.hEdi01 = hEdi01;
	}

	public String gethEdi02() {
		return hEdi02;
	}

	public void sethEdi02(String hEdi02) {
		this.hEdi02 = hEdi02;
	}

	public String gethEdi03() {
		return hEdi03;
	}

	public void sethEdi03(String hEdi03) {
		this.hEdi03 = hEdi03;
	}

	public String gethEdi04() {
		return hEdi04;
	}

	public void sethEdi04(String hEdi04) {
		this.hEdi04 = hEdi04;
	}

	public String gethEdi05() {
		return hEdi05;
	}

	public void sethEdi05(String hEdi05) {
		this.hEdi05 = hEdi05;
	}

	public String gethEdi06() {
		return hEdi06;
	}

	public void sethEdi06(String hEdi06) {
		this.hEdi06 = hEdi06;
	}

	public String gethEdi07() {
		return hEdi07;
	}

	public void sethEdi07(String hEdi07) {
		this.hEdi07 = hEdi07;
	}

	public String gethEdi08() {
		return hEdi08;
	}

	public void sethEdi08(String hEdi08) {
		this.hEdi08 = hEdi08;
	}

	public String gethEdi09() {
		return hEdi09;
	}

	public void sethEdi09(String hEdi09) {
		this.hEdi09 = hEdi09;
	}

	public String gethEdi10() {
		return hEdi10;
	}

	public void sethEdi10(String hEdi10) {
		this.hEdi10 = hEdi10;
	}

	public String gethEdi11() {
		return hEdi11;
	}

	public void sethEdi11(String hEdi11) {
		this.hEdi11 = hEdi11;
	}

	public String gethEdi12() {
		return hEdi12;
	}

	public void sethEdi12(String hEdi12) {
		this.hEdi12 = hEdi12;
	}

	public String gethEdi13() {
		return hEdi13;
	}

	public void sethEdi13(String hEdi13) {
		this.hEdi13 = hEdi13;
	}

	public String gethEdi14() {
		return hEdi14;
	}

	public void sethEdi14(String hEdi14) {
		this.hEdi14 = hEdi14;
	}

	public String gethEdi15() {
		return hEdi15;
	}

	public void sethEdi15(String hEdi15) {
		this.hEdi15 = hEdi15;
	}

	public String gethEdi16() {
		return hEdi16;
	}

	public void sethEdi16(String hEdi16) {
		this.hEdi16 = hEdi16;
	}

	public String gethEdi17() {
		return hEdi17;
	}

	public void sethEdi17(String hEdi17) {
		this.hEdi17 = hEdi17;
	}

	public String gethEdi18() {
		return hEdi18;
	}

	public void sethEdi18(String hEdi18) {
		this.hEdi18 = hEdi18;
	}

	public String gethEdi19() {
		return hEdi19;
	}

	public void sethEdi19(String hEdi19) {
		this.hEdi19 = hEdi19;
	}

	public String gethEdi20() {
		return hEdi20;
	}

	public void sethEdi20(String hEdi20) {
		this.hEdi20 = hEdi20;
	}

	public String getdEdi01() {
		return dEdi01;
	}

	public void setdEdi01(String dEdi01) {
		this.dEdi01 = dEdi01;
	}

	public String getdEdi02() {
		return dEdi02;
	}

	public void setdEdi02(String dEdi02) {
		this.dEdi02 = dEdi02;
	}

	public String getOrderbysql() {
		return orderbysql;
	}

	public void setOrderbysql(String orderbysql) {
		this.orderbysql = orderbysql;
	}

	public String getQcrule() {
		return qcrule;
	}

	public void setQcrule(String qcrule) {
		this.qcrule = qcrule;
	}

	public String getBillwithasntype() {
		return billwithasntype;
	}

	public void setBillwithasntype(String billwithasntype) {
		this.billwithasntype = billwithasntype;
	}

	public String getBillwithsotype() {
		return billwithsotype;
	}

	public void setBillwithsotype(String billwithsotype) {
		this.billwithsotype = billwithsotype;
	}

	public String getAllinflag() {
		return allinflag;
	}

	public void setAllinflag(String allinflag) {
		this.allinflag = allinflag;
	}

	public Double getAllinarea() {
		return allinarea;
	}

	public void setAllinarea(Double allinarea) {
		this.allinarea = allinarea;
	}

	public String getAllinratetype() {
		return allinratetype;
	}

	public void setAllinratetype(String allinratetype) {
		this.allinratetype = allinratetype;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public String getCopypricetolotatt09() {
		return copypricetolotatt09;
	}

	public void setCopypricetolotatt09(String copypricetolotatt09) {
		this.copypricetolotatt09 = copypricetolotatt09;
	}

	public String getPrintmedicineqcreport() {
		return printmedicineqcreport;
	}

	public void setPrintmedicineqcreport(String printmedicineqcreport) {
		this.printmedicineqcreport = printmedicineqcreport;
	}

	public String getBilObdStk() {
		return bilObdStk;
	}

	public void setBilObdStk(String bilObdStk) {
		this.bilObdStk = bilObdStk;
	}

	public String getAsnnobycustomer() {
		return asnnobycustomer;
	}

	public void setAsnnobycustomer(String asnnobycustomer) {
		this.asnnobycustomer = asnnobycustomer;
	}

	public String getOrdernobycustomer() {
		return ordernobycustomer;
	}

	public void setOrdernobycustomer(String ordernobycustomer) {
		this.ordernobycustomer = ordernobycustomer;
	}

	public String getOrderlotcontrol() {
		return orderlotcontrol;
	}

	public void setOrderlotcontrol(String orderlotcontrol) {
		this.orderlotcontrol = orderlotcontrol;
	}

	public String getFullcaselotcontrol() {
		return fullcaselotcontrol;
	}

	public void setFullcaselotcontrol(String fullcaselotcontrol) {
		this.fullcaselotcontrol = fullcaselotcontrol;
	}

	public String getPiecelotcontrol() {
		return piecelotcontrol;
	}

	public void setPiecelotcontrol(String piecelotcontrol) {
		this.piecelotcontrol = piecelotcontrol;
	}

	public String getAsnLnkPo() {
		return asnLnkPo;
	}

	public void setAsnLnkPo(String asnLnkPo) {
		this.asnLnkPo = asnLnkPo;
	}

	public String getInboundserialnoqtycontrol() {
		return inboundserialnoqtycontrol;
	}

	public void setInboundserialnoqtycontrol(String inboundserialnoqtycontrol) {
		this.inboundserialnoqtycontrol = inboundserialnoqtycontrol;
	}

	public String getOutboundserialnoqtycontrol() {
		return outboundserialnoqtycontrol;
	}

	public void setOutboundserialnoqtycontrol(String outboundserialnoqtycontrol) {
		this.outboundserialnoqtycontrol = outboundserialnoqtycontrol;
	}

	public String getIsChineseLabel() {
		return isChineseLabel;
	}

	public void setIsChineseLabel(String isChineseLabel) {
		this.isChineseLabel = isChineseLabel;
	}

	public String getValiddatefrom() {
		return validdatefrom;
	}

	public void setValiddatefrom(String validdatefrom) {
		this.validdatefrom = validdatefrom;
	}

	public String getValiddateto() {
		return validdateto;
	}

	public void setValiddateto(String validdateto) {
		this.validdateto = validdateto;
	}

	public String getEmedicineqcreport() {
		return emedicineqcreport;
	}

	public void setEmedicineqcreport(String emedicineqcreport) {
		this.emedicineqcreport = emedicineqcreport;
	}

	public String getSkuanalysisfields() {
		return skuanalysisfields;
	}

	public void setSkuanalysisfields(String skuanalysisfields) {
		this.skuanalysisfields = skuanalysisfields;
	}
}
