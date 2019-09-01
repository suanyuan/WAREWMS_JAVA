package com.wms.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;


@Data
@Entity
public class GspOperteLicenseTime implements Serializable {

    //主键id
    private String papersId;
    //开始日期
    private java.util.Date goTime;
    //结束日期
    private java.util.Date endTime;
    //类型
    private String lincenseType;
    //天数
    private String remainDay;



}
