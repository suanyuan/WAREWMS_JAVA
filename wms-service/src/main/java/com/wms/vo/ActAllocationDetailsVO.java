package com.wms.vo;

import com.wms.entity.ActAllocationDetails;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/7/31
 */
@Data
public class ActAllocationDetailsVO extends ActAllocationDetails {
    private String pickName;
    private String statusName;
    private String skuName;
    private Double qtysum;//分配数
    private Double qtyEachsum;//分配件数
    private Double qtypickedEachsum;//拣货数
    private Double qtyshippedEachsum;//发货数

}