package com.wms.utils;

public class StringUtil {

    public static boolean isEmpty(String input) {
        return (input == null || input.equals(""));
    }

    public static boolean isNotEmpty(String input) {
        return !isEmpty(input);
    }
}
