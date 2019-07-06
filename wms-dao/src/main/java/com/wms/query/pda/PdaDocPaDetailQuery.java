package com.wms.query.pda;

import com.wms.entity.BaseLotatt;

public class PdaDocPaDetailQuery extends BaseLotatt {

    private String warehouseid; //pda传参

    private String pano;//上架任务单号

    private String customerid;

    private String GTIN;//Global Trade Item Num

    private String sku;//传参不需要传，但是在拿到pda扫码传参后需要获取sku并赋值，查询到的sku进行精确查询

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

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
