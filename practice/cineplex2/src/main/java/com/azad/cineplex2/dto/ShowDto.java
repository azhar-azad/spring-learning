package com.azad.cineplex2.dto;

import java.util.Date;

import com.azad.cineplex2.entities.Hall;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowDto {

	private Long id;
	private String name;
	private Date showTime;
	private Long hallId;
	private String hallName;
	private Hall hall;
}
