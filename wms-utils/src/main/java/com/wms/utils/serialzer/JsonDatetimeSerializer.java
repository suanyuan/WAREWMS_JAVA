package com.wms.utils.serialzer;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;

import com.wms.utils.DateUtil;

/**
 * Json 工具類
 * 
 * 將Json物件中之Date類型轉成字串，用於前端頁面顯示
 * @Date 2012/6/11
 * @author OwenHuang
 */
@Component
public class JsonDatetimeSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		jsonGenerator.writeString(DateUtil.format(date));
	}
}
