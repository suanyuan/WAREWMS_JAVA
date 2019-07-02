package com.wms.vo.pda;

import com.wms.entity.BasPackage;
import com.wms.entity.BasSku;
import com.wms.entity.BaseLotatt;

public class PdaDocAsnDetailVO extends BaseLotatt {

    /**
     * 预期到货通知编号 ≈ 收货任务单号
     */
    private String asnno;

    /**
     * 行号
     */
    private int asnlineno;

    /**
     * 产品代码
     */
    private String sku;

    private BasSku basSku;

    private BasPackage basPackage;

    public int getAsnlineno() {
        return asnlineno;
    }

    public void setAsnlineno(int asnlineno) {
        this.asnlineno = asnlineno;
    }

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
