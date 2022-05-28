package com.azad.estatement.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingAndSorting {

	private int page;
	private int limit;
	private String sort;
	private String order;
}
