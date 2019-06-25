package com.wms.tools;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/6/25
 */
public class FieldUtil {
    public static Map<String,Object> getField(Object o,Class clazz) throws IllegalAccessException {

        Map<String,Object> pojoField = new HashMap<>();
        Field[] fields = clazz.getClass().getDeclaredFields();
        for (int j = 0; j < fields.length; j++)
        {
            fields[j].setAccessible(true);
            fields[j].getName();
            fields[j].get(o);
        }
        return pojoField;
    }

}