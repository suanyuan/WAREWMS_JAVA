package com.wms.vo.pda;

import com.wms.entity.BasPackage;
import com.wms.entity.BasSku;

public class PdaDocAsnDetailVO {

    /**
     * 预期到货通知编号 ≈ 收货任务单号
     */
    private String asnno;

    /**
     * 产品代码
     */
    private String sku;

    /**
     * 生产批号
     */
    private String lotatt01;

    /**
     * 序列号
     */
    private String lotatt02;

    private BasSku basSku;

    private BasPackage basPackage;

    public String getAsnno() {
        return asnno;
    }

    public void setAsnno(String asnno) {
        this.asnno = asnno;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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

    public BasSku getBasSku() {
        return basSku;
    }

    public void setBasSku(BasSku basSku) {
        this.basSku = basSku;
    }

    public BasPackage getBasPackage() {
        return basPackage;
    }

    public void setBasPackage(BasPackage basPackage) {
        this.basPackage = basPackage;
    }
}
