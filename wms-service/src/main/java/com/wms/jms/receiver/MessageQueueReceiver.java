package com.wms.jms.receiver;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import com.wms.entity.User;

//
//import javax.jms.JMSException;
//import javax.jms.MapMessage;
//import javax.jms.Message;
//import javax.jms.MessageListener;
//import javax.jms.TextMessage;

/**
 * JMS queue receiver
 */
public class MessageQueueReceiver implements MessageListener {
	
	@Override
	public void onMessage(Message message) {
		if (message instanceof MapMessage) {
			MapMessage mapMessage = (MapMessage) message;
			try {
				System.out.println("測試MapMessage："+mapMessage.getString("msg"));
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			try {
				System.out.println("測試TextMessage:" + textMessage.getText());
			} catch (JMSException e) {
				throw new RuntimeException(e);
			}
        } else if (message instanceof ObjectMessage) {
        	ObjectMessage objectMessage = (ObjectMessage) message;
        	try {
				User adminUser = (User) objectMessage.getObject();
				System.out.println("測試ObjectMessage:" + adminUser.getUserName());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
        } else {
            throw new IllegalArgumentException("Message must be of type TextMessage or MapMessage");
        }
	}
}
