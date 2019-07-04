package com.wms.query.pda;

import com.wms.entity.BaseLotatt;

/**
 * 通过扫码结果获取bas_sku
 */
public class PdaBasSkuQuery extends BaseLotatt {

    private String GTIN;//Global Trade Item Number

    private String alternate_sku1;//自编码

    private String alternate_sku2;

    private String alternate_sku3;

    private String alternate_sku4;

    private String alternate_sku5;

    public String getGTIN() {
        return GTIN;
    }

    public void setGTIN(String GTIN) {
        this.GTIN = GTIN;
    }

    public String getAlternate_sku1() {
        return alternate_sku1;
    }

    public void setAlternate_sku1(String alternate_sku1) {
        this.alternate_sku1 = alternate_sku1;
    }

    public String getAlternate_sku2() {
        return alternate_sku2;
    }

    public void setAlternate_sku2(String alternate_sku2) {
        this.alternate_sku2 = alternate_sku2;
    }

    public String getAlternate_sku3() {
        return alternate_sku3;
    }

    public void setAlternate_sku3(String alternate_sku3) {
        this.alternate_sku3 = alternate_sku3;
    }

    public String getAlternate_sku4() {
        return alternate_sku4;
    }

    public void setAlternate_sku4(String alternate_sku4) {
        this.alternate_sku4 = alternate_sku4;
    }

    public String getAlternate_sku5() {
        return alternate_sku5;
    }

    public void setAlternate_sku5(String alternate_sku5) {
        this.alternate_sku5 = alternate_sku5;
    }
}
