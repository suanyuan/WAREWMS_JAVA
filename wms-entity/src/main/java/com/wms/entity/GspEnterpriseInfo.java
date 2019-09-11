package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class GspEnterpriseInfo implements Serializable {

	private String enterpriseId;

	private String enterpriseNo;

	private String shorthandName;

	private String enterpriseName;

	private String enterpriseType;

	private String contacts;

	private String contactsPhone;

	private String remark;

	private String createId;

	private String createDate;

	private String editId;

	private String editDate;

	private String isUse;

	private String state;

	private String userDefine1;

	private String userDefine2;

	private String userDefine3;

	private String userDefine4;
	//产品许可证 备案号
	private String licenseNo;
	private String recordNo;

	private String prodNo;//生产

	private String plicenseNo;
	private String grecordNo;//生产第一类备案

}
