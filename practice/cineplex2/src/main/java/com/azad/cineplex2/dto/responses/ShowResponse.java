package com.azad.cineplex2.dto.responses;

import java.util.Date;

import com.azad.cineplex2.dto.HallInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowResponse {

	private Long id;
	private String name;
	private Date showTime;
	private String hallName;
	private HallInfo hall;
}
