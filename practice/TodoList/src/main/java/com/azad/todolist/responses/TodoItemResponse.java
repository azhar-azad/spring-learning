package com.azad.todolist.responses;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoItemResponse {

	private Long id;
	private String name;
	private Date doBefore;
	
	private String message;
}
