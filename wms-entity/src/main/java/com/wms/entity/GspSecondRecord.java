package com.wms.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class GspSecondRecord  implements Serializable {

	private String recordId;

	private String recordNo;

	private String enterpriseId;

	private String headName;

	private String operateMode;

	private String operatePlace;

	private String businessScope;

	private String residence;

	private String recordUrl;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date approveDate;

	private String registrationAuthority;

	private String createId;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date createDate;

	private String editId;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date editDate;

	private String isUse;

	private String warehouseAddress;

	private String enterpriseName;

	private String  juridicalPerson;
}
