package com.wms.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
public class BasGtn implements Serializable {

  private static final long serialVersionUID = 1L;

  private String sku;
  private String gtncode;


  @Transient
  private int hashCode = Integer.MIN_VALUE;

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }


  public String getGtncode() {
    return gtncode;
  }

  public void setGtncode(String gtncode) {
    this.gtncode = gtncode;
  }

}
