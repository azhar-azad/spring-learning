package com.azad.cineplex.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallResponse {

	private Long id;
	private String name;
	private String serialNo;
	private int capacity;
	private int floor;
}
