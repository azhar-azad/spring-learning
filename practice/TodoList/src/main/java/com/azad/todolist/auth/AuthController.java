package com.azad.todolist.auth;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azad.todolist.entities.User;
import com.azad.todolist.exceptions.RequestBodyEmptyException;
import com.azad.todolist.requests.UserLoginRequest;
import com.azad.todolist.requests.UserRegisterRequest;
import com.azad.todolist.responses.UserResponse;
import com.azad.todolist.utils.Utils;

@RestController
@RequestMapping(path = "/api/v1/users")
public class AuthController {
	
	private ModelMapper modelMapper = new ModelMapper();

	private AuthService userService;

	@Autowired
	public AuthController(AuthService userService) {
		this.userService = userService;
	}
	
	@GetMapping(path = "/heartbeat")
	public ResponseEntity<String> usersRouteTest() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(path = "/register")
	public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
		
		Utils.printControllerMethodInfo("POST", "/api/v1/users/register", "registerUser");
		
		if (userRegisterRequest == null) {
			throw new RequestBodyEmptyException("Empty body is posted to register a new User");
		}
		
		User registeredUser = userService.registerUser(modelMapper.map(userRegisterRequest, User.class), userRegisterRequest.getPassword());
		
		if (registeredUser == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<UserResponse>(modelMapper.map(registeredUser, UserResponse.class), HttpStatus.CREATED);
	}
	
	@PostMapping(path = "/login")
	public ResponseEntity<UserResponse> loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest) {
		
		Utils.printControllerMethodInfo("POST", "/api/v1/users/login", "loginUser");
		
		if (userLoginRequest == null) {
			throw new RequestBodyEmptyException("Empty body is posted to login an existing User");
		}
		
		User loggedInUser = userService.loginUser(modelMapper.map(userLoginRequest, User.class), userLoginRequest.getPassword());
		
		if (loggedInUser == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<UserResponse>(modelMapper.map(loggedInUser, UserResponse.class), HttpStatus.OK);
	}
	
}
