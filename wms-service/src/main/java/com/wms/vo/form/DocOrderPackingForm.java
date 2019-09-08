package com.wms.vo.form;

import lombok.Data;

@Data
public class DocOrderPackingForm {

    public final static String DESCRIPTION_HG = "未见异常，检查合格";

    public final static String CONCLUSION_HG = "合格";

	private String orderno;

	private int packlineno;

    private String traceid;//PDA cartonNum

    private String cartonno;

    private double grossweight;

    private double cube;

    private int packingflag;

    private String addwho;

    //结束装箱
    private String cartontype;//箱型


    //装箱明细
    private String customerid;

    private String sku;

    private int qty;

    private String allocationdetailsid;

    private String lotnum;

    private String lotatt11;

    private String description;

    private String conclusion;

    private String editwho;

    private String skudesce;//包装规格

    //强生产品线序列号记录
    // ex. lotatt05,lotatt05,lotatt05
    // 以英文逗号分割
    private String serialNums;
}