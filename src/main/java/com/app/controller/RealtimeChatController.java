package com.app.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.app.modal.Message;

public class RealtimeChatController {
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping
	@SendTo("/group/public")
	public Message receiveMessage(@Payload Message message) {
		System.out.println("MESSAGE :"+message);
		simpMessagingTemplate.convertAndSend("/group"+message.getChat().getId().toString(), message);
		
		return message;
	}
	
//	@MessageMapping
//	@SendTo("/single/public")
//	public Message receiveMessagee(@Payload Message message) {
//		System.out.println("MESSAGE :"+message);
//		simpMessagingTemplate.convertAndSend("/single"+message.getChat().getId().toString(), message);
//		
//		return message;
//	}
	
	
}
