package com.wms.vo.form.pda;

import lombok.Data;

/**
 * PDA库存移动提交表单
 */
@Data
public class PdaInventoryMoveForm {

    private String warehouseid;

    private String customerid;

    private String sku;

    private String lotnum;

    private String fmlocationid;

    private String fmqty;

    private String tolocationid;

    private String toqty;

    private String reasoncode;

    private String reasontext;

    private String editwho;

    private String version;
}
