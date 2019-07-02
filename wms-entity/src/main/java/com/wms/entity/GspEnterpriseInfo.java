package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class GspEnterpriseInfo  implements Serializable {

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

}
