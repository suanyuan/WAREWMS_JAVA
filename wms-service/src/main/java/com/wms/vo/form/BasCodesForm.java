package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class BasCodesForm {

	private String codeid;
	private String code;
	private String codenameC;
	private String codenameE;
	private Long showSequence;
	private String udf1;
	private String udf2;
	private String udf3;
	private java.sql.Date addtime;
	private String addwho;
	private java.sql.Date edittime;
	private String editwho;
	private String udfOprChk;
	private String token;

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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

	public Long getShowSequence() {
		return showSequence;
	}

	public void setShowSequence(Long showSequence) {
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

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.sql.Date getAddtime() {
		return addtime;
	}

	public void setAddtime(java.sql.Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.sql.Date getEdittime() {
		return edittime;
	}

	public void setEdittime(java.sql.Date edittime) {
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