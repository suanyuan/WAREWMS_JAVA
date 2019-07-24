package com.wms.vo.form;

import com.wms.entity.BasCarrierLicense;
import com.wms.entity.GspBusinessLicense;
import com.wms.entity.GspCustomer;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

import java.util.Date;

public class BasCarrierLicenseForm {

    private GspBusinessLicenseForm gspBusinessLicenseForm;
    private String carrierLicenseId;
	private String enterpriseId;
	private String roadNumber;
	private String enterpriseName;
	private String roadNumberTerm;
	private String roadAuthorityPermit;
	private String roadBusinessScope;
	private String carrierNo;
	private Date carrierDate;
	private Date carrierEndDate;
	private String clientTerm;
	private String carrierAuthorityPermit;
	private String carrierBusinessScope;
	private String createId;
	private String createDate;
	private String editId;
	private String editDate;
	private String activeFlag;
	private String file;
	private String roadNumberlicenseUrl;
	private String licenseUrl;

    private String roadNumberUrl;

    private String businessId;



    private String licenseNumber;

    private String socialCreditCode;

    private String licenseName;

    private String licenseType;

    private String residence;

    private String juridicalPerson;

    private String registeredCapital;

    private String establishmentDate;

    private String businessStartDate;

    private String businessEndDate;

    private String isLong;

    private String businessScope;

    private String issueDate;

    private String registrationAuthority;

    private String attachmentUrl;

    private String contractNo;

    private String contractUrl;

    private String clientContent;

    private Date clientStartDate;

    private Date clientEndDate;

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }

    public String getClientContent() {
        return clientContent;
    }

    public void setClientContent(String clientContent) {
        this.clientContent = clientContent;
    }

    public Date getClientStartDate() {
        return clientStartDate;
    }

    public void setClientStartDate(Date clientStartDate) {
        this.clientStartDate = clientStartDate;
    }

    public Date getClientEndDate() {
        return clientEndDate;
    }

    public void setClientEndDate(Date clientEndDate) {
        this.clientEndDate = clientEndDate;
    }



    public GspBusinessLicenseForm getGspBusinessLicenseForm() {
        return gspBusinessLicenseForm;
    }

    public void setGspBusinessLicenseForm(GspBusinessLicenseForm gspBusinessLicenseForm) {
        this.gspBusinessLicenseForm = gspBusinessLicenseForm;
    }

    public String getRoadNumberUrl() {
        return roadNumberUrl;
    }

    public void setRoadNumberUrl(String roadNumberUrl) {
        this.roadNumberUrl = roadNumberUrl;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getRoadNumberlicenseUrl() {
        return roadNumberlicenseUrl;
    }

    public void setRoadNumberlicenseUrl(String roadNumberlicenseUrl) {
        this.roadNumberlicenseUrl = roadNumberlicenseUrl;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getJuridicalPerson() {
        return juridicalPerson;
    }

    public void setJuridicalPerson(String juridicalPerson) {
        this.juridicalPerson = juridicalPerson;
    }

    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getEstablishmentDate() {
        return establishmentDate;
    }

    public void setEstablishmentDate(String establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public String getBusinessStartDate() {
        return businessStartDate;
    }

    public void setBusinessStartDate(String businessStartDate) {
        this.businessStartDate = businessStartDate;
    }

    public String getBusinessEndDate() {
        return businessEndDate;
    }

    public void setBusinessEndDate(String businessEndDate) {
        this.businessEndDate = businessEndDate;
    }

    public String getIsLong() {
        return isLong;
    }

    public void setIsLong(String isLong) {
        this.isLong = isLong;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getRegistrationAuthority() {
        return registrationAuthority;
    }

    public void setRegistrationAuthority(String registrationAuthority) {
        this.registrationAuthority = registrationAuthority;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    private String isUse;

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getCarrierLicenseId() {
		return carrierLicenseId;
	}

	public void setCarrierLicenseId(String carrierLicenseId) {
		this.carrierLicenseId = carrierLicenseId;
	}

	public String getRoadNumber() {
		return roadNumber;
	}

	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}

	public String getRoadNumberTerm() {
		return roadNumberTerm;
	}

	public void setRoadNumberTerm(String roadNumberTerm) {
		this.roadNumberTerm = roadNumberTerm;
	}

	public String getRoadAuthorityPermit() {
		return roadAuthorityPermit;
	}

	public void setRoadAuthorityPermit(String roadAuthorityPermit) {
		this.roadAuthorityPermit = roadAuthorityPermit;
	}

	public String getRoadBusinessScope() {
		return roadBusinessScope;
	}

	public void setRoadBusinessScope(String roadBusinessScope) {
		this.roadBusinessScope = roadBusinessScope;
	}

	public String getCarrierNo() {
		return carrierNo;
	}

	public void setCarrierNo(String carrierNo) {
		this.carrierNo = carrierNo;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public Date getCarrierDate() {
		return carrierDate;
	}

	public void setCarrierDate(Date carrierDate) {
		this.carrierDate = carrierDate;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public Date getCarrierEndDate() {
		return carrierEndDate;
	}

	public void setCarrierEndDate(Date carrierEndDate) {
		this.carrierEndDate = carrierEndDate;
	}

	public String getClientTerm() {
		return clientTerm;
	}

	public void setClientTerm(String clientTerm) {
		this.clientTerm = clientTerm;
	}

	public String getCarrierAuthorityPermit() {
		return carrierAuthorityPermit;
	}

	public void setCarrierAuthorityPermit(String carrierAuthorityPermit) {
		this.carrierAuthorityPermit = carrierAuthorityPermit;
	}

	public String getCarrierBusinessScope() {
		return carrierBusinessScope;
	}

	public void setCarrierBusinessScope(String carrierBusinessScope) {
		this.carrierBusinessScope = carrierBusinessScope;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getEditId() {
		return editId;
	}

	public void setEditId(String editId) {
		this.editId = editId;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public String getEditDate() {
		return editDate;
	}

	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

}