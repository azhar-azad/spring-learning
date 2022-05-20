package com.azad.cineplex2.dto.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallRequest {

	@NotNull(message = "Hall name cannot be empty")
	@Size(min = 2, max = 10, message = "Hall name has to be 2 to 10 characters long")
	private String name;
	
	@NotNull(message = "Hall serial no cannot be empty")
	@Size(min = 2, max = 4, message = "Hall serial no has to be 2 to 4 characters long")
	private String serialNo;
	
	@NotNull(message = "Hall capacity cannot be empty")
	@Min(20)
	@Max(500)
	private int capacity;
	
	@NotNull(message = "Hall has to belong on a floor")
	@Min(0)
	private int floor;
}
