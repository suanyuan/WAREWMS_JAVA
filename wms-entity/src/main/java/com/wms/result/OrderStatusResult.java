package com.wms.result;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/7/27
 */
@Data
public class OrderStatusResult implements Serializable {
    private String orderStatus;
    private String orderStatusName;
    private String orderType;
    private String orderTypeName;
    private String soStatus;
}