package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class FirstReviewLog  implements Serializable {

	private String reviewId;

	private String reviewTypeId;

	private String applyContent;

	private String applyState;

	private String checkIdQc;

	private java.util.Date checkDateQc;

	private String checkRemarkQc;

	private String checkIdHead;

	private java.util.Date checkDateHead;

	private String checkRemarkHead;

	private String createId;

	private java.util.Date createDate;

	private String editId;

	private java.util.Date editDate;

}
