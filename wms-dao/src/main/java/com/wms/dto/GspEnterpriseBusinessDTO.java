package com.wms.dto;

import com.wms.entity.GspBusinessLicense;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/7/10
 */
@Data
public class GspEnterpriseBusinessDTO extends GspBusinessLicense implements Serializable {
    private String enterpriseId;

    private String enterpriseNo;

    private String shorthandName;

    private String enterpriseName;

    private String enterpriseType;

    private String contacts;

    private String contactsPhone;

    private String remark;

    private String createId;

    private Date createDate;

    private String editId;

    private Date editDate;

    private String isUse;

    private String state;

    private String userDefine1;

    private String userDefine2;

    private String userDefine3;

    private String userDefine4;

    private Integer outTime;
}