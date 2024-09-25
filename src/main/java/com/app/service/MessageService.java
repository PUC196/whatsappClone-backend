package com.app.service;

import java.util.List;

import com.app.exception.ChatException;
import com.app.exception.MessageException;
import com.app.exception.UserException;
import com.app.modal.Message;
import com.app.modal.User;
import com.app.request.SendMessageReques;

public interface MessageService {
	
	public Message sendMessage(SendMessageReques req) throws UserException, ChatException;
	
	public List<Message> getChatsMessages (Integer chatId, User reqUser) throws ChatException, UserException;
	
	public Message findMessageById(Integer messageId) throws MessageException;
	
	public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException;
}
