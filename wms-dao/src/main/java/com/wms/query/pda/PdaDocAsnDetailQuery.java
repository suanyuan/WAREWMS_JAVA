package com.wms.query.pda;

import com.wms.entity.BaseLotatt;

public class PdaDocAsnDetailQuery extends BaseLotatt {

    private String warehouseid; //pda传参

    private String asnno;

    private String GTIN;//Global Trade Item Num

    public String getGTIN() {
        return GTIN;
    }

    public void setGTIN(String GTIN) {
        this.GTIN = GTIN;
    }

    public String getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }

    public String getAsnno() {
        return asnno;
    }

    public void setAsnno(String asnno) {
        this.asnno = asnno;
    }
}
