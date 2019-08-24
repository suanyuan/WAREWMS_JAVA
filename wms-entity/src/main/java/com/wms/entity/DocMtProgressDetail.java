package com.wms.entity;

import lombok.Data;

@Data
public class DocMtProgressDetail {

    private String mtno;//养护单号

    private String goodsModel;//规格型号 descr_e

    private String unmtPiece;//待养护件数

    private String mtedPiece;//已养护件数

    private String serialNum;//序列号

    private String batchNum;//生产批号

    private String locationid;//库位

    private String lotnum;//批此
}
