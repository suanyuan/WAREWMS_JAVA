package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class GspBusinessLicense  implements Serializable {

	private String businessId;

	private String enterpriseId;

	private String licenseNumber;

	private String socialCreditCode;

	private String licenseName;

	private String licenseType;

	private String residence;

	private String juridicalPerson;

	private String registeredCapital;

	private java.util.Date establishmentDate;

	private java.util.Date businessStartDate;

	private java.util.Date businessEndDate;

	private String isLong;

	private String businessScope;

	private java.util.Date issueDate;

	private String registrationAuthority;

	private String attachmentUrl;

	private String createId;

	private Date createDate;

	private String editId;

	private Date editDate;

	private String isUse;

}
