package com.wms.query.pda;

import lombok.Data;

@Data
public class PdaInventoryQuery {

    private String warehouseid;

    private String locationid;

    private String lotatt04;

    private String lotatt05;

    private String GTIN;

    private String otherCode;


    //Single Inventory 精确查找
    private String customerid;

    private String sku;

    //分页
    private int start;

    private int pageSize;
}
