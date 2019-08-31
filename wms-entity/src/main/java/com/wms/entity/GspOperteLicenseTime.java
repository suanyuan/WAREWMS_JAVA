package com.wms.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;


@Data
@Entity
public class GspOperteLicenseTime implements Serializable {

    //主键id
    public String papersId;
    //开始日期
    public java.util.Date goTime;
    //结束日期
    public java.util.Date endTime;
    //类型
    public String lincenseType;
    //天数
    public String remainDay;



}
