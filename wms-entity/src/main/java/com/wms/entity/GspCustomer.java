package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class GspCustomer implements Serializable {

	private String clientId;

	private String clientNo;

	private String clientName;

	private String enterpriseId;

	private String remark;

	private String firstState;

	private String isCheck;

	private String isCooperation;

	private String operateType;

	private String contractNo;

	private String contractUrl;

	private String clientContent;

	private String clientStartDate;

	private String clientEndDate;

	private String clientTerm;

	private String isChineseLabel;

	private String createId;

	private java.util.Date createDate;

	private String editId;

	private java.util.Date editDate;

	private String isUse;

}
