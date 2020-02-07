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

    private String editwho;

    private String sku;

    /**
     * add by Gizmo 2019-10-22
     * 扫描的GS1条码，用于JSJY、BDL回传记录GS1条码
     */
    private String gs1Code;

    private String description;

    private String conclusion;
}
