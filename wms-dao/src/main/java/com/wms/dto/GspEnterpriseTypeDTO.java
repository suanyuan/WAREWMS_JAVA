package com.wms.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/7/14
 */
@Data
public class GspEnterpriseTypeDTO implements Serializable {
    private String enterpriseId;
    private String enterPriseType;
    private String firstState;
}