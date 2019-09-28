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
	private String customerTypeName;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		GspEnterpriseInfo person = (GspEnterpriseInfo) o;
//		if(!(enterpriseId != null ? enterpriseId.equals(person.enterpriseId) : person.enterpriseId == null))return  false;
		if(!(enterpriseNo != null ? enterpriseNo.equals(person.enterpriseNo) : person.enterpriseNo == null))return  false;
		if(!(shorthandName != null ? shorthandName.equals(person.shorthandName) : person.shorthandName == null))return  false;
		if(!(enterpriseName != null ? enterpriseName.equals(person.enterpriseName) : person.enterpriseName == null))return  false;
		if(!(enterpriseType != null ? enterpriseType.equals(person.enterpriseType) : person.enterpriseType == null))return  false;
		if(!(contacts != null ? contacts.equals(person.contacts) : person.contacts == null))return  false;
		if(!(contactsPhone != null ? contactsPhone.equals(person.contactsPhone) : person.contactsPhone == null))return  false;
		return remark != null ? remark.equals(person.remark) : person.remark == null;
//		if(!(contacts != null ? contacts.equals(person.contacts) : person.contacts == null))return  false;
	}


	@Override
	public String toString() {
		return
//				"enterpriseId='" + enterpriseId + '\'' +
				"企业代码='" + enterpriseNo + '\'' +
				"企业简称='" + shorthandName + '\'' +
				"企业名称='" + enterpriseName + '\'' +
				"企业类型='" + enterpriseType + '\'' +
				"企业联系人='" + contacts + '\'' +
				"企业联系人电话='" + contactsPhone + '\''
				;
	}
}
