package com.wms.entity;

import lombok.Data;

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

	private String bussinessScope;

	private String residence;

	private String recordUrl;

	private java.util.Date approveDate;

	private String registrationAuthority;

	private String createId;

	private java.util.Date createDate;

	private String editId;

	private java.util.Date editDate;

	private String isUse;

}
