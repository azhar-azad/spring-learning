package com.azad.cineplex2.dto.responses;

import java.util.List;

import com.azad.cineplex2.dto.ShowInfo;

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
	private List<ShowInfo> shows;
}
