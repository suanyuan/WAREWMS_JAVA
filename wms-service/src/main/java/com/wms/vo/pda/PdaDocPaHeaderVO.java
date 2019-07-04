package com.wms.vo.pda;

public class PdaDocPaHeaderVO {

    /**
     * 上架任务单号
     */
    private String pano;

    /**
     * 预入库单号
     */
    private String asnno;

    /**
     * 客户id
     */
    private String customerid;

    /**
     * 上架状态
     */
    private String pastatus;

    /**
     * 上架类型
     */
    private String patype;

    /**
     * 仓库id
     */
    private String warehouseid;

    public String getPano() {
        return pano;
    }

    public void setPano(String pano) {
        this.pano = pano;
    }

    public String getAsnno() {
        return asnno;
    }

    public void setAsnno(String asnno) {
        this.asnno = asnno;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getPastatus() {
        return pastatus;
    }

    public void setPastatus(String pastatus) {
        this.pastatus = pastatus;
    }

    public String getPatype() {
        return patype;
    }

    public void setPatype(String patype) {
        this.patype = patype;
    }

    public String getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }
}
