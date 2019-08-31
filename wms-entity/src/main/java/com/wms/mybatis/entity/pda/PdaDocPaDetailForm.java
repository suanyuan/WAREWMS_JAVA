package com.wms.mybatis.entity.pda;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PdaDocPaDetailForm {

    private String pano;//pda传参

    private String palineno;

    private String linestatus;//暂定，没啥用

    private String asnno;

    private long asnlineno;

    private String customerid;//pda传参

    private String sku;

    private BigDecimal asnqtyExpected;//暂定，没啥用

    private BigDecimal putwayqtyExpected;//暂定，没啥用

    private BigDecimal putwayqtyCompleted;//暂定，没啥用

    private String userdefine1;//pda传参 locationid

    private String userdefine2;//pda传参 lotatt02

    private String userdefine3;//pda传参 lotatt04

    private String userdefine4;//pda传参 lotatt05

    private String userdefine5;//pda传参 上架的产品状态 HG || DJ

    private String packid;

    private String userid;

    private String warehouseid;//pda传参

    private BigDecimal paqty;//pda传参

    private String language;

    private String returncode;//Procedure 执行结果

    private String lotatt01;//pda传参 生产日期

    private String editwho;

    //和procedure没关系，单纯为了传参
    private String GTIN;
    private String otherCode;
}
