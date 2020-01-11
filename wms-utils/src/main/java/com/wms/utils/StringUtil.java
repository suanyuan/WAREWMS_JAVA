package com.wms.utils;

import com.wms.utils.vo.MailVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static boolean isEmpty(String input) {
        return (input == null || input.equals(""));
    }

    public static boolean isNotEmpty(String input) {
        return !isEmpty(input);
    }

    public static boolean checkPassword(String password) {
        Pattern Password_Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(.{6,16})$");
        Matcher matcher = Password_Pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * 解决传参null的问题
     *
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
     *
     * @param str1 ~
     * @param str2 ~
     * @return ~
     */
    public static boolean compare(String str1, String str2) {

        return StringUtil.fixNull(str1).equals(StringUtil.fixNull(str2));
    }

    /**
     * 判断时间格式是否满足 yyyy-MM-dd
     */
    public static boolean isyMdDate(String str) {

        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(fixNull(str));
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    public static boolean isNotyMdDate(String str) {

        return !isyMdDate(str);
    }

    public static void main(String[] args) {

        MailVO mailVO = new MailVO();
        mailVO.setTo("JSML");
        List<String> customerids = new ArrayList<>();
        customerids.add("jsml");
        customerids.add("JSJY");
        boolean isExist = customerids.contains(mailVO.getTo());
        System.out.println(isExist);

//        String date = "";
//        System.out.println(isyMdDate(date));
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("123", "12");
//        System.out.println("12"+ null);

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
