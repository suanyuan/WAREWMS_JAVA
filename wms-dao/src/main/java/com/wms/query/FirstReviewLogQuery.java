package com.wms.query;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class FirstReviewLogQuery implements IQuery {

	private String reviewId;
	private String reviewTypeId;
	private String applyContent;
	private String applyState;
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

}