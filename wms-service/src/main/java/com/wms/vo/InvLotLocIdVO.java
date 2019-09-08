package com.wms.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/9/8
 */
@Getter
@Setter
public class InvLotLocIdVO implements Serializable {
    private String sku;
    private String locationid;
    private String customerid;
    private String qty;
}