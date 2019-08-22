package com.wms.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
public class DocMtDetails {

  private String mtno;
  private long mtlineno;
  private String linestatus;
  private String customerid;
  private String sku;
  private String inventoryage;     //库龄
  private String locationid;       //库位
  private String lotnum;           //批号
  private double mtqtyExpected;    //预期养护件数
  private double mtqtyEachExpected;//预期养护数量
  private double mtqtyCompleted;
  private double mtqtyEachCompleted;
  private String uom;                  //单位
  private long checkFlag;              //检查内容（外观、包装等）
  private String conclusion;          //养护结论
  private Date conversedate; //养护日期
  private String conversewho;         //养护人
  private long storageFlag;           //贮存条件 1||0
  private long flowFlag;              //作业流程 1||0
  private long signFlag;              //标志清晰 1||0
  private long fenceFlag;             //防护措施 1||0
  private long sanitationFlag;        //卫生环境 1||0
  private String remark;              //备注
  private Date addtime;
  private String addwho;
  private Date edittime;
  private String editwho;


}
