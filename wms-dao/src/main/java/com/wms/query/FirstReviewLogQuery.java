package com.wms.query;

import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

@Data
public class FirstReviewLogQuery implements IQuery {
	private String token;
	private String reviewId;
	private String reviewTypeId;
	private String applyContent;
	private String applyState;
	private String applyType; //申请类型

	private String checkIdQc;
	private String checkDateQc;
	private String checkRemarkQc;
	private String checkIdHead;
	private String checkDateHead;
	private String checkRemarkHead;
	private String createId;
	private String createDateBegin;
	private String createDateEnd;
	private String editId;
	private String editDate;
	private String applyNo;

}