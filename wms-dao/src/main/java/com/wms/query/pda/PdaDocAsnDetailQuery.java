package com.wms.query.pda;

import com.wms.entity.BaseLotatt;
import lombok.Data;

@Data
public class PdaDocAsnDetailQuery extends BaseLotatt {

    private String warehouseid; //pda传参

    private String asnno;

    private String GTIN;//Global Trade Item Num

    private String customerid;

    private String sku;

    private String otherCode;
}
