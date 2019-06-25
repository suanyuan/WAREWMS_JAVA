package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class GspOperateLicense  implements Serializable {

	private Long operateId;

	private Long enterpriseId;

	private String licenseNo;

	private String operateMode;

	private String headName;

	private String bussinessScope;

	private String warehouseAddress;

	private java.util.Date licenseExpiryDate;

	private String licenseUrl;

	private java.util.Date approveDate;

	private String registrationAuthority;

	private String operateType;

	private Long createId;

	private java.util.Date createDate;

	private Long editId;

	private java.util.Date editDate;

	private String isUse;

}
