package com.wms.result;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/8/22
 */
@Getter
@Setter
public class AsnDetailResult implements Serializable {
    private String docNo;
    private String docLineNo;
    private String fmSku;
    private String lotatt12;
    private String lotatt06;
    private String descC;
    private String lotatt04;
    private String lotatt05;
    private String lotatt07;
    private String lotatt01;
    private String lotatt02;
    private String fmqty;
    private String fmqty_each;
    private String fmLocation;
    private String addTime;
    private String addWho;

    private Double fmqtySum;
    private Double fmqtyEachSum;
}