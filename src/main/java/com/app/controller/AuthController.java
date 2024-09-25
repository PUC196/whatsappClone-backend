package com.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.app.config.TokenProvider;
import com.app.exception.UserException;
import com.app.modal.User;
import com.app.repository.UserRepository;
import com.app.request.LoginRequest;
import com.app.response.AuthResponse;
import com.app.service.CustomUserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private TokenProvider tokenProvider;
	private CustomUserService customUserService;
	
	public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,TokenProvider tokenProvider, CustomUserService customUserService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
		this.customUserService = customUserService;
	}
	
//	@PostMapping("/signup")
	@PostMapping(value = "/signup", produces = "application/json")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException{
		String full_name = user.getFull_name();
		String email = user.getEmail();
		String password = user.getPassword();
		
		User isUser = userRepository.findByEmail(email);
		if(isUser!=null) {
			throw new UserException("Email is used with another account "+email);
		}
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setFull_name(full_name);
		createdUser.setPassword(passwordEncoder.encode(password));
		
		userRepository.save(createdUser);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = tokenProvider.generateToken(authentication);
	
		
		AuthResponse res = new AuthResponse(jwt, true);
		res.setIsAuth(true);
		return new ResponseEntity<AuthResponse>(res, HttpStatus.ACCEPTED);
		
	}
	
//	@PostMapping("/signup")
//	@PostMapping(value = "/signup", produces = "application/json")
//	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
//	    String full_name = user.getFull_name();
//	    String email = user.getEmail();
//	    String password = user.getPassword();
//	    
//	    // Check if the user already exists
//	    User existingUser = userRepository.findByEmail(email);
//	    if (existingUser != null) {
//	        throw new UserException("Email is used with another account: " + email);
//	    }
//	    
//	    // Create and save the new user
//	    User newUser = new User();
//	    newUser.setEmail(email);
//	    newUser.setFull_name(full_name);
//	    newUser.setPassword(passwordEncoder.encode(password));  // Encrypt the password
//	    userRepository.save(newUser);
//	    
//	    // Return a response indicating success
//	    AuthResponse response = new AuthResponse("User registered successfully", true);
//	    return new ResponseEntity<>(response, HttpStatus.CREATED);  // Use HttpStatus.CREATED for successful creation
//	}

	
//	@PostMapping("/login")
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req){
		String email = req.getEmail();
		String password = req.getPassword(); 
		
		Authentication authentication = authenticate(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		String jwt = tokenProvider.generateToken(authentication);
		
		AuthResponse res = new AuthResponse(jwt, true);
		return new ResponseEntity<AuthResponse>(res, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/validateToken")
	public Authentication authenticate(String Username, String password) {
		UserDetails userDetails = customUserService.loadUserByUsername(Username);
		
		if(userDetails==null) {
			throw new BadCredentialsException("invalid username");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("invalid password or username");
		}
		
		return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
	}
	
}
