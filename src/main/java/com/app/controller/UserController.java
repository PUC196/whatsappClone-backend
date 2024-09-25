package com.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.exception.UserException;
import com.app.modal.User;
import com.app.request.UpdateUserRequest;
import com.app.response.ApiResponse;
import com.app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException{
		
		User user = userService.findUserProfile(token);
		return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
	}
	
//	@GetMapping("/{query}")
//	public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String q){
//		System.out.println(q);
//		List<User> users = userService.searchUser(q);
//		System.out.println("search user Controller");
//		
//		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
//	}
	
	@GetMapping("/search")
	public ResponseEntity<List<User>> searchUserHandler(@RequestParam("name") String query) {
	    System.out.println("Query: " + query);
	    List<User> users = userService.searchUser(query);
	    System.out.println("Found users: " + users);
	    
	    return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest req, @RequestHeader("Authorization") String token) throws UserException{ 
		User user = userService.findUserProfile(token);
		userService.updateUser(user.getId(), req);
		
		ApiResponse res = new ApiResponse("user updated successfully!", true);
		
		return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);
		
	}
}
