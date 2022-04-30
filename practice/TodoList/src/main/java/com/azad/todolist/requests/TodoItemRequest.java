package com.azad.todolist.requests;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoItemRequest {

	@NotBlank
	private String name;
	private Date doBefore;
}
