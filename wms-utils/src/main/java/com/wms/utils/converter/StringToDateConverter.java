package com.wms.utils.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String, Date> {
    /*从jsp页面把String转换成Date*/
    @Override
    public Date convert(String d) {
        if (d == null || d.isEmpty()) {
            return null;
        }
        //定义数组，格式
        SimpleDateFormat[] sdfs = new SimpleDateFormat[]{
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
                new SimpleDateFormat("yyyy-MM-dd"),
        };
        Date date = null;
        for (SimpleDateFormat sdf : sdfs) {
            try {
                date = sdf.parse(d);
                break;
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                continue;
            }
        }
        return date;
    }

}
