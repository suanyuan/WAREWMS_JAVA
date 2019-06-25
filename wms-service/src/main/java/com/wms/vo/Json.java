package com.wms.vo;

import java.io.Serializable;

/**
 * JSON模型
 * @author OwenHuang
 * @Date 2013/5/29
 */
public class Json implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private boolean success = false;// 是否成功
	private String msg = "";// 提示信息
	private Object obj = null;// 其他信息

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public static Json success(String msg){
		Json json = new Json();
		json.setMsg(msg);
		json.setSuccess(true);
		return json;
	}

	public static Json success(String msg,Object data){
		Json json = new Json();
		json.setMsg(msg);
		json.setObj(data);
		json.setSuccess(true);
		return json;
	}

	public static Json error(String msg){
		Json json = new Json();
		json.setMsg(msg);
		json.setSuccess(false);
		return json;
	}

}
