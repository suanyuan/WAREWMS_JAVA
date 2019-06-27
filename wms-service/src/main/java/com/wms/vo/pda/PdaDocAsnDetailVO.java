package com.wms.vo.pda;

public class PdaDocAsnDetailVO {

    /**
     * 预期到货通知编号 ≈ 收货任务单号
     */
    private String asnno;

    /**
     * 客户单号
     */
    private String clientno;

    /**
     * 产品代码
     */
    private String sku;

    /**
     * 产品名称-中文
     */
    private String skudescrc;

    /**
     * 规格/型号 匹配自产品(BasSku.java)
     */
    private String model;

    /**
     * 生产批号
     */
    private String lotatt01;

    /**
     * 序列号
     */
    private String lotatt02;

    public String getAsnno() {
        return asnno;
    }

    public void setAsnno(String asnno) {
        this.asnno = asnno;
    }

    public String getClientno() {
        return clientno;
    }

    public void setClientno(String clientno) {
        this.clientno = clientno;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkudescrc() {
        return skudescrc;
    }

    public void setSkudescrc(String skudescrc) {
        this.skudescrc = skudescrc;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLotatt01() {
        return lotatt01;
    }

    public void setLotatt01(String lotatt01) {
        this.lotatt01 = lotatt01;
    }

    public String getLotatt02() {
        return lotatt02;
    }

    public void setLotatt02(String lotatt02) {
        this.lotatt02 = lotatt02;
    }
}
