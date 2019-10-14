package com.wms.vo.form.pda;

import lombok.Data;

@Data
public class ScanResultForm {

    /**
     * 嘉和诚康 自赋码 规则
     * 开头有ZFM，长度不限，但是不建议很长，扫码效率会降低
     * 建议ZFM之后，8 ~ 12 位
     */
    public static final String ALTERNATE_SKU_ID = "ZFM";

    /**
     * 客户代码
     */
    private String customerid;

    private String GTIN;

    /**
     * 生产批号
     */
    private String lotatt04;

    /**
     * 序列号
     */
    private String lotatt05;

    /**
     * 序列号 || 产品代码 || 自赋码
     * 注：条码格式不是GS1格式的，系统无法解析的。
     */
    private String otherCode;
}
