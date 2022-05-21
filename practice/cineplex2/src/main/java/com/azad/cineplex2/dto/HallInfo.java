package com.azad.cineplex2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallInfo {

	private Long id;
	private String name;
	private String serialNo;
	private int capacity;
	private int floor;
}
