package com.wms.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the DOC_ASN_HEADER database table.
 * 
 */
@Entity
public class ImportQMDData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String seq;
	private String calId;

	private String calName;

	private String calNumber;

	private String calTerm;

	private String calCardUrl;

	private String createId;

	private java.util.Date createDate;

	private String editId;

	private java.util.Date editDate;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getCalId() {
		return calId;
	}

	public void setCalId(String calId) {
		this.calId = calId;
	}

	public String getCalName() {
		return calName;
	}

	public void setCalName(String calName) {
		this.calName = calName;
	}

	public String getCalNumber() {
		return calNumber;
	}

	public void setCalNumber(String calNumber) {
		this.calNumber = calNumber;
	}

	public String getCalTerm() {
		return calTerm;
	}

	public void setCalTerm(String calTerm) {
		this.calTerm = calTerm;
	}

	public String getCalCardUrl() {
		return calCardUrl;
	}

	public void setCalCardUrl(String calCardUrl) {
		this.calCardUrl = calCardUrl;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getEditId() {
		return editId;
	}

	public void setEditId(String editId) {
		this.editId = editId;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}
}