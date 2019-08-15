/*
 * ************************************************************
 * FileName：GS1Code128DataUtil.java  Module：app  Project：Ware_WMS_Android
 * CurrentModifyDate：2019年05月24日 02:36:35
 * LastModifyDate：2019年05月24日 02:36:35
 * Editor：Gizmo
 * Copyright (c) 2019
 * ************************************************************
 * Description:
 * ************************************************************
 */

package com.wms.utils;

import java.util.HashMap;
import java.util.Map;

public class GS1Code128DataUtil {

    private final static String GTIN_CODE       = "01";//GTIN码
    private final static String LOT_NUM_CODE    = "10";//批号
    private final static String EXP_DATE_CODE   = "17";//效期
    private final static String SERIAL_NUM_CODE = "21";//序列码
    private final static String COUNT_CODE      = "30";//包装个数

    /** HoneyWell FNC1 */
    char honeywell_fnc1 = '\u001D';

    /**
     * 存储GS1规则数据
     */
    private final Map<String, String> data = new HashMap<>();

    private String otherCode;//其他类型的条码（不符合GS1规则的）

    private static final Map<String, AII> aiinfo = new HashMap<>();

    static class AII {
        final int minLength;
        final int maxLength;

        public AII(String id, int minLength, int maxLength) {
            this.minLength = minLength;
            this.maxLength = maxLength;
        }
    }

    private static void ai(String id, int minLength, int maxLength) {
        aiinfo.put(id, new AII(id, minLength, maxLength));
    }

    private static void ai(String id, int length) {
        aiinfo.put(id, new AII(id, length, length));
    }

    static {
        ai(GTIN_CODE, 14);
        ai(LOT_NUM_CODE, 1, 20);
        ai(EXP_DATE_CODE,6);
        ai(SERIAL_NUM_CODE,1,20);
        ai(COUNT_CODE, 1, 8);
        //条码规则参考： http://en.wikipedia.org/wiki/GS1-128
    }

    /**
     * 解码GS1-128码
     */
    public GS1Code128DataUtil(String s) {
        StringBuilder ai = new StringBuilder();
        int index = 0;
        while (index < s.length()) {
            ai.append(s.charAt(index++));
            AII info = aiinfo.get(ai.toString());
            if (info != null) {
                StringBuilder value = new StringBuilder();
                for (int i = 0; i < info.maxLength && index < s.length(); i++) {
                    char c = s.charAt(index++);
                    if (c == honeywell_fnc1) {
                        break;
                    }
                    value.append(c);
                }
                if (value.length() < info.minLength) {
                    throw new IllegalArgumentException("Short field for AI \"" + ai + "\": \"" + value + "\".");
                }
                data.put(ai.toString(), value.toString());
                ai.setLength(0);
            }
        }
        if (ai.length() > 0) {
            otherCode = ai.toString();
//            throw new IllegalArgumentException("Unknown AI \"" + ai + "\".");
        }
    }

    public String getGTIN() {
        return data.get(GTIN_CODE);
    }

    public String getLotNum() {
        return data.get(LOT_NUM_CODE);
    }

    public String getExpDate() {
        return data.get(EXP_DATE_CODE);
    }

    public String getSerialNum() {
        return data.get(SERIAL_NUM_CODE);
    }

    public String getCount() {
        return data.get(COUNT_CODE);
    }

    public String getOtherCode() {return otherCode;}
    public void setOtherCode(String otherCode) {
        this.otherCode = otherCode;
    }
}
