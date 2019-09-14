package com.wms.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    /**
     * 判断两个字符串是否相同
     * @param str1 ~
     * @param str2 ~
     * @return ~
     */
    public static boolean compare(String str1, String str2) {

        return StringUtil.fixNull(str1).equals(StringUtil.fixNull(str2));
    }

    public static void main(String[] args) {

//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date prdDate = format.parse("2019-09-21");
//            Date expiryDate = format.parse("2019-12-01");
//            if (prdDate.getTime() < expiryDate.getTime()) {
//                System.out.println(expiryDate + "prdDate 在expiryDate前");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
