package com.wms.query.pda;

import com.wms.entity.BaseLotatt;

public class PdaDocPaDetailQuery extends BaseLotatt {

    private String warehouseid; //pda传参

    private String pano;//上架任务单号

    private String GTIN;//Global Trade Item Num

    public String getPano() {
        return pano;
    }

    public void setPano(String pano) {
        this.pano = pano;
    }

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
}
