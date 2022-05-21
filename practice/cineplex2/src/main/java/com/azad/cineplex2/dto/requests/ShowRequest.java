package com.azad.cineplex2.dto.requests;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowRequest {

	@NotNull(message = "Show name cannot be empty")
	@Size(min = 2, max = 20, message = "Show name has to be 2 to 20 characters long")
	private String name;
	
	@NotNull(message = "Show time cannot be empty")
	private Date showTime;
	
	private Long hallId;
	
	private String hallName;
}
