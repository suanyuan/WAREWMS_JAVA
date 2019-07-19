package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class BasCarrierLicense  implements Serializable {
	private String enterpriseId;

	private String carrierLicenseId;

	private String roadNumber;

	private String roadNumberTerm;

	private String roadAuthorityPermit;

	private String roadBusinessScope;

	private String carrierNo;

	private java.util.Date carrierDate;

	private Date carrierEndDate;

	private String clientTerm;

	private String carrierAuthorityPermit;

	private String carrierBusinessScope;

	private String contractNo;

	private String contractUrl;

	private String clientContent;

	private Date clientStartDate;

	private Date clientEndDate;



	private String createId;

	private java.util.Date createDate;

	private String editId;

	private java.util.Date editDate;

	private String activeFlag;

}
