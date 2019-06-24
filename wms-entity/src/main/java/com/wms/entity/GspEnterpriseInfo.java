package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class GspEnterpriseInfo  implements Serializable {

	private Long enterpriseId;

	private String enterpriseNo;

	private String shorthandName;

	private String enterpriseName;

	private String enterpriseType;

	private String contacts;

	private String contactsPhone;

	private String remark;

	private Long createId;

	private java.util.Date createDate;

	private Long editId;

	private java.util.Date editDate;

	private String isUse;

}
