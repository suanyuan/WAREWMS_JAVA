package com.wms.mybatis.entity;

import lombok.Data;

@Data
public class IdSequence {

    /**
     * 批次号
     */
    public final static String SEQUENCE_TYPE_LOT_NUM = "LOTNumber";

    /**
     * 事务id
     */
    public final static String SEQUENCE_TYPE_TRANSACTION = "TRANSACTIONID";

    /**
     * 验收单号
     */
    public final static String SEQUENCE_TYPE_QC = "QCNO";

    /**
     * 上架单号
     */
    public final static String SEQUENCE_TYPE_PA = "PANO";

    private String warehouseid;

    private String language;

    /**
     * 获取id的类型
     */
    private String type;

    /**
     * 返回的id
     */
    private String resultNo;

    /**
     * 返回的状态码
     */
    private String resultCode;

    public IdSequence(String warehouseid, String language, String type) {
        this.warehouseid = warehouseid;
        this.language = language;
        this.type = type;
    }
}
