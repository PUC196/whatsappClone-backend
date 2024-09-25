package com.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.exception.ChatException;
import com.app.exception.MessageException;
import com.app.exception.UserException;
import com.app.modal.Chat;
import com.app.modal.Message;
import com.app.modal.User;
import com.app.repository.MessageRepository;
import com.app.request.SendMessageReques;

@Service
public class MessageServiceImplementation implements MessageService {
	
	private MessageRepository messageRepository;
	private UserService userService;
	private ChatService chatService;

	public MessageServiceImplementation(MessageRepository messageRepository, UserService userService,
			ChatService chatService) {
		super();
		this.messageRepository = messageRepository;
		this.userService = userService;
		this.chatService = chatService;
	}

	@Override
	public Message sendMessage(SendMessageReques req) throws UserException, ChatException {
		
		User user = userService.findUserById(req.getUserId());
		Chat chat = chatService.findChatById(req.getChatId());
		
		Message message = new Message();
		message.setChat(chat);
		message.setUser(user);
		message.setContent(req.getContent());
		message.setTimestamp(LocalDateTime.now());
		
		return message;
	}

	@Override
	public List<Message> getChatsMessages(Integer chatId, User reqUser) throws ChatException, UserException {
		Chat chat = chatService.findChatById(chatId);
		
		if(!chat.getUsers().contains(reqUser)) {
			throw new UserException("You are not related to this chat "+chat.getId());
		}
		
		List<Message> messages = messageRepository.findByChatId(chat.getId());
		return messages;
	}

	@Override
	public Message findMessageById(Integer messageId) throws MessageException {
		
		Optional<Message> opt = messageRepository.findById(messageId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new MessageException("message not found with id "+messageId);
	}

	@Override
	public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException {
		
		Message message = findMessageById(messageId);
		
		if(message.getUser().getId().equals(reqUser.getId())) {
			messageRepository.deleteById(messageId);
		}
		throw new UserException("you can't delete another user's message "+reqUser.getFull_name());
	}

}
