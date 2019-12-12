package com.wms.query;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DocQsmProcedureQuery {

    private String warehouseid;
    private String customerid;
    private String sku;
    private String lotnum;
    private String fmlocationid;
    private String fmqcstatus;
    private String tolocationid;
    private String toqcstatus;
    private BigDecimal locqty;
    private BigDecimal qty;
    private String userid;
    private String result;
}
