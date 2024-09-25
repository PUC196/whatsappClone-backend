package com.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.exception.ChatException;
import com.app.exception.MessageException;
import com.app.exception.UserException;
import com.app.modal.Message;
import com.app.modal.User;
import com.app.request.SendMessageReques;
import com.app.response.ApiResponse;
import com.app.service.MessageService;
import com.app.service.UserService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
	
	private MessageService messageService;
	private UserService userService;
	
	public MessageController(MessageService messageService, UserService userService) {
		super();
		this.messageService = messageService;
		this.userService = userService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageReques req, @RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		
		User user = userService.findUserProfile(jwt);
		
		req.setUserId(user.getId());
		Message message = messageService.sendMessage(req);
		
		return new ResponseEntity<Message>(message, HttpStatus.OK);
		
	}
	
	@GetMapping("/chat/{chatId}")
	public ResponseEntity<List<Message>> getChatsMessagesHandler(@PathVariable Integer chatId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		
		User user = userService.findUserProfile(jwt);
		
		List<Message> messages = messageService.getChatsMessages(chatId, user);
		
		return new ResponseEntity<>(messages, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{messageId}")
	public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Integer messageId, @RequestHeader("Authorization") String jwt) throws UserException, MessageException{
		
		User user = userService.findUserProfile(jwt);
		
		messageService.deleteMessage(messageId, user);
		
		ApiResponse res = new ApiResponse("message deleted successfully", false);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
		
	}
}
