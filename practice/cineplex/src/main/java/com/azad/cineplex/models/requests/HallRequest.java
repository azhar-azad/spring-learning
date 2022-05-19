package com.azad.cineplex.models.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallRequest {

	@NotNull
	private String name;
	
	@NotNull
	@Min(1)
	@Max(99)
	private String serialNo;
	
	@NotNull
	private int capacity;
	
	private int floor;
}
