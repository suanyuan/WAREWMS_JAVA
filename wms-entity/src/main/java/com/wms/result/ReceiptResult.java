package com.wms.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/8/9
 */
@Data
public class ReceiptResult implements Serializable {
    private String orderno;
    private Date edittime;
    private String carrierName;
    private String consigneename;
    private String cContact;
    private String userdefine1;
    private String address;
    private String cTel1;
    private String userdefine2;
    private String soreference1;
    private String notes;
    private String customerid;
    private String sku;
    private String lotatt12;
    private String reservedfield02;
    private String lotatt06;
    private String descrE;
    private String lotatt04;
    private String lotatt05;
    private String lotatt07;
    private String lotatt02;
    private String qtyEach;
    private String defaultreceivinguom;
    private String descr;
    private String qty;
    private String lotatt11;
    private String lotatt15;
    private String doubleCertificate;
    private String certificate;
    private String descrC;
    private String lotatt01;
    private String consigneeCompany;
}