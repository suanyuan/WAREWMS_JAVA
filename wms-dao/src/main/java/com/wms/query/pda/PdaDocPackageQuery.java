package com.wms.query.pda;

import lombok.Data;

@Data
public class PdaDocPackageQuery extends PdaBasSkuQuery {

    private String orderno;

    private String warehouseid;

    private String customerid;

    private String otherCode;

    private String cartonNum;

    private String version;
}
