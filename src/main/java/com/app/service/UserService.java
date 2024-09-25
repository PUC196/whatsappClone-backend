package com.app.service;

import java.util.List;

import com.app.exception.UserException;
import com.app.modal.User;
import com.app.request.UpdateUserRequest;

public interface UserService {
	
	public User findUserById(Integer id) throws UserException;
	
	public User findUserProfile(String jwt) throws UserException;
	
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException;
	
	public List<User> searchUser(String query);

}
