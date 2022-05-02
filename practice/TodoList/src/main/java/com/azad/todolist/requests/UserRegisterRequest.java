package com.azad.todolist.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegisterRequest {

	@NotEmpty
	private String fullname;
	
	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	private String username;
	
	@NotEmpty
	@Min(8)
	private String password;
}
