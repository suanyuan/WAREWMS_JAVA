package com.wms.vo.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 共用的回传信息物件
 *	<Response>
 * 		<success>true/false</success>
 * 		<code>000</code>
 * 		<desc>说明</desc>
 * 	</Response>
 * @author Owen
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Response")
@XmlType(propOrder = {"success","code","desc"})
public class ResponseVO {
	@XmlElement
	private Boolean success;
	@XmlElement
	private String code;
	@XmlElement
	private String desc;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "ResponseXmlVo [success=" + success + ", code=" + code + ", desc=" + desc + "]";
	}
}
