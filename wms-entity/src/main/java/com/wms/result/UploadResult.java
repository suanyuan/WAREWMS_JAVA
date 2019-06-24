package com.wms.result;

import java.io.Serializable;

/**
 * 上传文件结果对象
 * @author 黄志豪
 */
public class UploadResult implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean success = true;
	
	private String error;
	
	private String comment;
	
	private Object data;
	
	
	
	public UploadResult() {
		super();
	}

	public UploadResult(String comment, Object data) {
		super();
		this.comment = comment;
		this.data = data;
	}
	
	public UploadResult(String error) {
		super();
		this.success = false;
		this.error = error;
	}
	
	public UploadResult(String error, String comment, Object data) {
		super();
		this.success = false;
		this.error = error;
		this.comment = comment;
		this.data = data;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
