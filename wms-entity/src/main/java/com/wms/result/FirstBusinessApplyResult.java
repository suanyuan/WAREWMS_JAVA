package com.wms.result;

import com.wms.entity.FirstBusinessApply;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/7/1
 */
@Data
public class FirstBusinessApplyResult extends FirstBusinessApply {
    private String clientName;
    private String supplierName;
    private String productCode;
    private String productName;
    private String productline;
}