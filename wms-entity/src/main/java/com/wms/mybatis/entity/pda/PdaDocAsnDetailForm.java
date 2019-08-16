package com.wms.mybatis.entity.pda;

import com.wms.entity.BaseLotatt;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PdaDocAsnDetailForm extends BaseLotatt {

    private String result = "";//procedure执行结果

    private String otherCode;//pda
    private String warehouseid; //pda传参
    private String GTIN;//pda
    private String asnno;
    private long asnlineno;//行号
    private String receivedqty;//收货件数

    private String receivinglocation;
    private String receivingtime;
    private String customerid;
    private String sku;
    private BigDecimal totalcubic;
    private BigDecimal totalgrossweight;
    private BigDecimal totalprice;
    private String editwho;
}
