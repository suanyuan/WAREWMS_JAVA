package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class GspOperateDetail  implements Serializable {

	private String licenseId;

	private String operateId;

	private String createId;

	private java.util.Date createDate;

	private String editId;

	private java.util.Date editDate;

	private String isUse;

	private String licenseType;

	private String enterpriseId;
}
