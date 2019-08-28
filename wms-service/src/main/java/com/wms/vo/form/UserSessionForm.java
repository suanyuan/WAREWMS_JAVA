package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class UserSessionForm {

	private Long userSessionId;
	private String userId;
	private String userSession;
	private java.util.Date addtime;
	private Long state;

	public Long getUserSessionId() {
		return userSessionId;
	}

	public void setUserSessionId(Long userSessionId) {
		this.userSessionId = userSessionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserSession() {
		return userSession;
	}

	public void setUserSession(String userSession) {
		this.userSession = userSession;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getAddtime() {
		return addtime;
	}

	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

}