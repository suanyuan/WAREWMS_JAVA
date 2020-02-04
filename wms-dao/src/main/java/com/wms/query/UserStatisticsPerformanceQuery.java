package com.wms.query;

public class UserStatisticsPerformanceQuery implements IQuery {

	private String userId;
	private String perfDate;
	private String perfPick;
	private String perfRecheck;
	private String perfPa;
	private String perfQc;
	private String perfOrder;
	private String userdefine1;
	private String userdefine2;
	private String userdefine3;
	private String userdefine4;
	private String userdefine5;
	private String addtimeBegin;//附加查询起始到结束时间
	private String addtimeEnd;

	public String getAddtimeBegin() {
		return addtimeBegin;
	}

	public void setAddtimeBegin(String addtimeBegin) {
		this.addtimeBegin = addtimeBegin;
	}

	public String getAddtimeEnd() {
		return addtimeEnd;
	}

	public void setAddtimeEnd(String addtimeEnd) {
		this.addtimeEnd = addtimeEnd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPerfDate() {
		return perfDate;
	}

	public void setPerfDate(String perfDate) {
		this.perfDate = perfDate;
	}

	public String getPerfPick() {
		return perfPick;
	}

	public void setPerfPick(String perfPick) {
		this.perfPick = perfPick;
	}

	public String getPerfRecheck() {
		return perfRecheck;
	}

	public void setPerfRecheck(String perfRecheck) {
		this.perfRecheck = perfRecheck;
	}

	public String getPerfPa() {
		return perfPa;
	}

	public void setPerfPa(String perfPa) {
		this.perfPa = perfPa;
	}

	public String getPerfQc() {
		return perfQc;
	}

	public void setPerfQc(String perfQc) {
		this.perfQc = perfQc;
	}

	public String getPerfOrder() {
		return perfOrder;
	}

	public void setPerfOrder(String perfOrder) {
		this.perfOrder = perfOrder;
	}

	public String getUserdefine1() {
		return userdefine1;
	}

	public void setUserdefine1(String userdefine1) {
		this.userdefine1 = userdefine1;
	}

	public String getUserdefine2() {
		return userdefine2;
	}

	public void setUserdefine2(String userdefine2) {
		this.userdefine2 = userdefine2;
	}

	public String getUserdefine3() {
		return userdefine3;
	}

	public void setUserdefine3(String userdefine3) {
		this.userdefine3 = userdefine3;
	}

	public String getUserdefine4() {
		return userdefine4;
	}

	public void setUserdefine4(String userdefine4) {
		this.userdefine4 = userdefine4;
	}

	public String getUserdefine5() {
		return userdefine5;
	}

	public void setUserdefine5(String userdefine5) {
		this.userdefine5 = userdefine5;
	}

}