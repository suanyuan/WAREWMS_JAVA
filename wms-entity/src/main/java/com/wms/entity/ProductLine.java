package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "product_line")
public class ProductLine  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String productLineId;

	private String enterpriseName;

	private String name;

	private String expression;

	private String createId;


  private String createDate;

	private String editId;

	private String editDate;

	private String isUse;

  public String getProductLineId() {
    return productLineId;
  }

  public void setProductLineId(String productLineId) {
    this.productLineId = productLineId;
  }

  public String getEnterpriseName() {
    return enterpriseName;
  }

  public void setEnterpriseName(String enterpriseName) {
    this.enterpriseName = enterpriseName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getExpression() {
    return expression;
  }

  public void setExpression(String expression) {
    this.expression = expression;
  }

  public String getCreateId() {
    return createId;
  }

  public void setCreateId(String createId) {
    this.createId = createId;
  }

  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }

  public String getEditId() {
    return editId;
  }

  public void setEditId(String editId) {
    this.editId = editId;
  }

  public String getEditDate() {
    return editDate;
  }

  public void setEditDate(String editDate) {
    this.editDate = editDate;
  }

  public String getIsUse() {
    return isUse;
  }

  public void setIsUse(String isUse) {
    this.isUse = isUse;
  }
}
