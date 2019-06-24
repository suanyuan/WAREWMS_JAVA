package com.wms.query;

import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

import java.util.Date;

@Data
public class GspEnterpriseInfoQuery implements IQuery {

	private String enterpriseNo;
	private String shorthandName;
	private String enterpriseName;
	private String enterpriseType;
	private String createDate;
	private String isUse;

}