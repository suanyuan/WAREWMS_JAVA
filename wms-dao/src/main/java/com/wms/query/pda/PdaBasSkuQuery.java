package com.wms.query.pda;

import com.wms.entity.BaseLotatt;
import lombok.Data;

/**
 * 通过扫码结果获取bas_sku
 */
@Data
public class PdaBasSkuQuery extends BaseLotatt {

    private String GTIN;//Global Trade Item Number

    private String customerid;

    private String alternate_sku1;//自编码

    private String alternate_sku2;

    private String alternate_sku3;

    private String alternate_sku4;

    private String alternate_sku5;
}
