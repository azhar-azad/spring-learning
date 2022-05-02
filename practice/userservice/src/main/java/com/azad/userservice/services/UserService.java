package com.azad.userservice.services;

import java.util.List;

import com.azad.userservice.entities.Role;
import com.azad.userservice.entities.User;

public interface UserService {

	User createUser(User user);
	Role createRole(Role role);
	void addRoleToUser(String username, String roleName);
	User getUserByUsername(String username);
	List<User> getUsers();
}
