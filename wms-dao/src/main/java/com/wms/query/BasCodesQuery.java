package com.wms.query;

import com.wms.mybatis.entity.SfcCustomer;

import java.util.Set;

public class BasCodesQuery implements IQuery {

	private String codeid;
	private String code;
	private String codenameC;
	private String codenameE;
	private String showSequence;
	private String udf1;
	private String udf2;
	private String udf3;
	private String addtime;
	private String addwho;
	private String edittime;
	private String editwho;
	private String udfOprChk;
	private java.lang.String warehouseid;
	private Set<SfcCustomer> customerSet;

	private String idList;

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public java.lang.String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(java.lang.String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public Set<SfcCustomer> getCustomerSet() {
		return customerSet;
	}

	public void setCustomerSet(Set<SfcCustomer> customerSet) {
		this.customerSet = customerSet;
	}

	public String getCodeid() {
		return codeid;
	}

	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodenameC() {
		return codenameC;
	}

	public void setCodenameC(String codenameC) {
		this.codenameC = codenameC;
	}

	public String getCodenameE() {
		return codenameE;
	}

	public void setCodenameE(String codenameE) {
		this.codenameE = codenameE;
	}

	public String getShowSequence() {
		return showSequence;
	}

	public void setShowSequence(String showSequence) {
		this.showSequence = showSequence;
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

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	public String getEdittime() {
		return edittime;
	}

	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public String getUdfOprChk() {
		return udfOprChk;
	}

	public void setUdfOprChk(String udfOprChk) {
		this.udfOprChk = udfOprChk;
	}

}