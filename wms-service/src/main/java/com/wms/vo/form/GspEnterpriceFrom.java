package com.wms.vo.form;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/6/25
 */
@Data
public class GspEnterpriceFrom {
    private GspEnterpriseInfoForm gspEnterpriseInfoForm;
    private GspBusinessLicenseForm gspBusinessLicenseForm;
    private GspOperateLicenseForm gspOperateLicenseForm;
    private GspSecondRecordForm gspSecondRecordForm;
    private GspOperateLicenseForm gspProdLicenseForm;
    private GspMedicalRecordForm gspMedicalRecordForm;
    private GspFirstRecordForm gspFirstRecordForm;
}