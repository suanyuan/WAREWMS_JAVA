package com.wms.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/7/1
 */
@Data
public class FirstBusinessProductApplyPageVO implements Serializable {
    private String productApplyId;
    private String applyId;
    private String specsId;
    private String isCheck;
    private String isOperate;
    private String isCold;
    private String createId;
    private java.util.Date createDate;
    private String editId;
    private java.util.Date editDate;
    private String isUse;
    private String productCode;
    private String productName;
    private String specsName;
    private String productModel;

    private String supplierName;
    private String customerid;
    private String productRegisterNo;
    private String productRegisterId;

}