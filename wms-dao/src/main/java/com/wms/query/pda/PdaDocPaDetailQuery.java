package com.wms.query.pda;

import com.wms.entity.BaseLotatt;
import lombok.Data;

@Data
public class PdaDocPaDetailQuery extends BaseLotatt {

    private String warehouseid; //pda传参

    private String pano;//上架任务单号

    private String customerid;

    private String GTIN;//Global Trade Item Num

    private String sku;//传参不需要传，但是在拿到pda扫码传参后需要获取sku并赋值，查询到的sku进行精确查询

    private String otherCode;

    private String version;
}
