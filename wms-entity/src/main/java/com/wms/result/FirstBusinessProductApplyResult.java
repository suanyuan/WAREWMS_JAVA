package com.wms.result;

import com.wms.entity.FirstBusinessProductApply;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/7/1
 */
@Data
public class FirstBusinessProductApplyResult extends FirstBusinessProductApply {
    private String productCode;
    private String productName;
    private String specsName;
    private String productModel;
    private String productRegisterNo;
    private String productRegisterId;

//    private String supplierName;
}