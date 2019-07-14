package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class GspOperateLicense  implements Serializable {

	private String operateId;

	private String enterpriseId;

	private String licenseNo;

	private String operateMode;

	private String headName;

	private String businessScope;

	private String warehouseAddress;

	private java.util.Date licenseExpiryDate;

	private String licenseUrl;

	private java.util.Date approveDate;

	private String registrationAuthority;

	private String operateType;

	private String createId;

	private java.util.Date createDate;

	private String editId;

	private java.util.Date editDate;

	private String isUse;

}
