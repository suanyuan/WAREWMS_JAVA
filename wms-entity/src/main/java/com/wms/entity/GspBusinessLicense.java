package com.wms.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date establishmentDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date businessStartDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date businessEndDate;

	private String isLong;

	private String businessScope;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date issueDate;

	private String registrationAuthority;

	private String attachmentUrl;

	private String createId;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createDate;

	private String editId;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date editDate;

	private String isUse;

}
