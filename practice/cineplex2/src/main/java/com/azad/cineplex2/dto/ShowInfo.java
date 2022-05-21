package com.azad.cineplex2.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowInfo {

	private Long id;
	private String name;
	private Date showTime;
}
