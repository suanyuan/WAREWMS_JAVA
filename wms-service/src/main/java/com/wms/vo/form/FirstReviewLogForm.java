package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class FirstReviewLogForm {

	private String reviewId;
	private String reviewTypeId;
	private String applyContent;
	private String applyState;
	private String checkIdQc;
	private java.util.Date checkDateQc;
	private String checkRemarkQc;
	private String checkIdHead;
	private java.util.Date checkDateHead;
	private String checkRemarkHead;
	private String createId;
	private java.util.Date createDate;
	private String editId;
	private java.util.Date editDate;

	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	public String getReviewTypeId() {
		return reviewTypeId;
	}

	public void setReviewTypeId(String reviewTypeId) {
		this.reviewTypeId = reviewTypeId;
	}

	public String getApplyContent() {
		return applyContent;
	}

	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}

	public String getApplyState() {
		return applyState;
	}

	public void setApplyState(String applyState) {
		this.applyState = applyState;
	}

	public String getCheckIdQc() {
		return checkIdQc;
	}

	public void setCheckIdQc(String checkIdQc) {
		this.checkIdQc = checkIdQc;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getCheckDateQc() {
		return checkDateQc;
	}

	public void setCheckDateQc(java.util.Date checkDateQc) {
		this.checkDateQc = checkDateQc;
	}

	public String getCheckRemarkQc() {
		return checkRemarkQc;
	}

	public void setCheckRemarkQc(String checkRemarkQc) {
		this.checkRemarkQc = checkRemarkQc;
	}

	public String getCheckIdHead() {
		return checkIdHead;
	}

	public void setCheckIdHead(String checkIdHead) {
		this.checkIdHead = checkIdHead;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getCheckDateHead() {
		return checkDateHead;
	}

	public void setCheckDateHead(java.util.Date checkDateHead) {
		this.checkDateHead = checkDateHead;
	}

	public String getCheckRemarkHead() {
		return checkRemarkHead;
	}

	public void setCheckRemarkHead(String checkRemarkHead) {
		this.checkRemarkHead = checkRemarkHead;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public String getEditId() {
		return editId;
	}

	public void setEditId(String editId) {
		this.editId = editId;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEditDate() {
		return editDate;
	}

	public void setEditDate(java.util.Date editDate) {
		this.editDate = editDate;
	}

}