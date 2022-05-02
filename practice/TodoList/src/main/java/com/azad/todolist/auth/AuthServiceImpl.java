package com.azad.todolist.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azad.todolist.entities.User;
import com.azad.todolist.exceptions.UnauthorizedLoginAttemptException;
import com.azad.todolist.exceptions.UserAlreadyRegisteredException;
import com.azad.todolist.exceptions.UserNotRegisteredException;
import com.azad.todolist.repositories.UserRepository;
import com.azad.todolist.utils.Utils;

@Service
public class AuthServiceImpl implements AuthService {

	private UserRepository userRepository;

	@Autowired
	public AuthServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User registerUser(User user, String password) {
		
		User fetchedUser = userRepository.findByUsername(user.getUsername());
		
		if (fetchedUser != null) {
			throw new UserAlreadyRegisteredException("User already registered. Please login.");
		}
		
		user.setEncryptedPassword(Utils.getEncryptedPassword(password));
		
		return userRepository.save(user);
	}

	@Override
	public User loginUser(User user, String password) {
		
		User fetchedUser = userRepository.findByUsername(user.getUsername());
		
		if (fetchedUser == null) {
			throw new UserNotRegisteredException("User is not registered. Please register.");
		}
		
		if (!fetchedUser.getEncryptedPassword().equals(Utils.getEncryptedPassword(password))) {
			throw new UnauthorizedLoginAttemptException("Username or Password is not correct.");
		}
		
		return fetchedUser;
	}
	
	
}
