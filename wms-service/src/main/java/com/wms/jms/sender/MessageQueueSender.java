package com.wms.jms.sender;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import com.wms.entity.User;

/**
 * JMS queue sender
 */
public class MessageQueueSender {
	
	@Autowired
	private JmsTemplate jmsTemplate;

	public void sendString(String msg) {
		jmsTemplate.convertAndSend(msg);
	}
	
	public void sendMap(String msg) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", msg);
		jmsTemplate.convertAndSend(map);
	}
	
	public void sendObj(User adminUser) {
		jmsTemplate.convertAndSend(adminUser);
	}
	
}
