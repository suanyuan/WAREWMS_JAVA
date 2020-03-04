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

	private Date carrierDate;//发证日期
	private String carrierDateDc;//发证日期导出

	private Date carrierEndDate;//有效期限
	private String carrierEndDateDc;//有效期限导出

	private String clientTerm;

	private String carrierAuthorityPermit;

	private String carrierBusinessScope;

	private String contractNo;

	private String contractUrl;
	private String roadNumberUrl;
	private String licenseUrl;

	private String clientContent;

	private Date clientStartDate;

	private Date clientEndDate;



	private String createId;

	private java.util.Date createDate;
	private String createDateDc;

	private String editId;

	private java.util.Date editDate;
	private String editDateDc;

	private String activeFlag;
	private String enterpriseName;

}
