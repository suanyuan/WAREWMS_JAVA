package com.wms.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * @author OwenHuang Exception工具類
 */
public class ExceptionUtil {

	/**
	 * 返回錯誤訊息字串
	 * 
	 * @param ex
	 *            Exception
	 * @return 錯誤訊息字串
	 */
	public static String getExceptionMessage(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	public static String getRootCauseMessage(Exception e) {
		return ExceptionUtils.getRootCauseMessage(e);
	}
}
