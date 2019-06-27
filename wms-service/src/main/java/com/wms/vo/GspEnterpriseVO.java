package com.wms.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/6/26
 */
@Data
public class GspEnterpriseVO implements Serializable {
    private GspEnterpriseInfoVO gspEnterpriseInfoVO;
    private GspBusinessLicenseVO gspBusinessLicenseVO;
    private GspOperateLicenseVO gspOperateLicenseVO;
    private GspSecondRecordVO gspSecondRecordVO;
}