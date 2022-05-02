package com.azad.todolist.requests;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginRequest {

	@NotEmpty
	private String username;
	
	@NotEmpty
	private String password;
}
