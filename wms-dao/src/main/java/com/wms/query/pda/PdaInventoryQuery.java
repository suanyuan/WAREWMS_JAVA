package com.wms.query.pda;

import lombok.Data;

@Data
public class PdaInventoryQuery {

    private String locationid;

    private String lotatt04;

    private String lotatt05;

    private String GTIN;

    private String otherCode;
}
