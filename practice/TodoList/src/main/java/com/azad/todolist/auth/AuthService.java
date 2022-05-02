package com.azad.todolist.auth;

import com.azad.todolist.entities.User;

public interface AuthService {

	User registerUser(User user, String password);

	User loginUser(User user, String password);

}
