package com.security.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.jwt.model.User;
import com.security.jwt.model.UserRequest;
import com.security.jwt.model.UserResponse;
import com.security.jwt.service.UserService;
import com.security.jwt.util.JwtUtil;

@RestController
public class UserController {
	
	@Autowired
	private JwtUtil jwtutil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userServie;
	
	@PostMapping("/user")
	public ResponseEntity<String> saveUser(@RequestBody User user) {
		
		Integer id = userServie.saveUser(user);
		
		String b="User"+id+" save";
		
		return new ResponseEntity<String >(b,HttpStatus.OK);
		
	}
	
	
	// validate user and generate token (login)
	@PostMapping("/userlogin")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest request)
	{
		
		// validate use/password form database
		//13
		authenticationManager
       .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		String token = jwtutil.generateToken(request.getUsername());
		
	return   ResponseEntity.ok(new UserResponse(token,"token generated"));
		
		 
		
		
		
	}
	
	
	

}
