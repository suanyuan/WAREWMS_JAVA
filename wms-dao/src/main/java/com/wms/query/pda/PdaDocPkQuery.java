package com.wms.query.pda;

import lombok.Data;

@Data
public class PdaDocPkQuery extends PdaBasSkuQuery {

    private String orderno;

    private String warehouseid;

    private String customerid;

    private String otherCode;

    private String version;

    private String editwho;

    private String sku;

    private String locationid;

    private int qty;
}
