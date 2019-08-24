package com.wms.utils;

public class StringUtil {

    public static boolean isEmpty(String input) {
        return (input == null || input.equals(""));
    }

    public static boolean isNotEmpty(String input) {
        return !isEmpty(input);
    }

    /**
     * 解决传参null的问题
     * @param param 参数
     * @return ~
     */
    public static String fixNull(String param) {

        if (isEmpty(param)) {
            return "";
        }
        return param;
    }
}
