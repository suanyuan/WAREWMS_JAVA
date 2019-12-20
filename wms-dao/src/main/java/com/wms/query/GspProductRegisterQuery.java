package com.wms.query;

import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

@Data
public class GspProductRegisterQuery implements IQuery {
	private String token;
	private String productRegisterId;
	private String productRegisterNo;
	private String productNameMain;
	private String productRegisterName;
	private String productRegisterAddress;
	private String productProductionAddress;
	private String agentName;
	private String applyScope;
	private String mainPart;
	private String expectUse;
	private String storageConditions;
	private String productExpiryDate;
	private String otherContent;
	private String remark;
	private String approveDate;
	private String productRegisterExpiryDate;
	private String productRegisterVersion;
	private String version;
	private String checkerId;
	private String checkDate;
	private String createId;
	private String createDateBegin;
	private String createDateEnd;
	private String editId;
	private String editDate;
	private String isUse;
	private String attachmentUrl;
	private String classifyId;
	private String classifyCatalog;
	private String enterpriseId;
	private String idList;
}