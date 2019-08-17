package com.wms.query.pda;

import com.wms.entity.BaseLotatt;
import lombok.Data;

@Data
public class PdaDocQcDetailQuery extends BaseLotatt {

    private String warehouseid; //pda传参

    private String qcno;//验收任务单号

    private String customerid;

    private String GTIN;//Global Trade Item Num

    private String locationid;//库位

    private String sku;//传参不需要传，但是在拿到pda扫码传参后需要获取sku并赋值，查询到的sku进行精确查询

    private String lotnum;//批量合格操作中，赋值为当前detail对应的合格批次，

    private String otherCode;
}
