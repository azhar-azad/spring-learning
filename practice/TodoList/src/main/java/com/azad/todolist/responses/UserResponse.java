package com.azad.todolist.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {

	private Long id;
	private String email;
	private String fullname;
	private String username;
}
