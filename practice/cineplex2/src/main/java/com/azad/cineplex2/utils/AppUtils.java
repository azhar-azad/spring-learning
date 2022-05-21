package com.azad.cineplex2.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Sort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppUtils {

	public static void printControllerMethodInfo(String httpMethod, String requestPath, String controllerMethodName) {
		log.info("");
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.info(httpMethod + " " + requestPath + " :::: " + controllerMethodName);
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	public static Sort getSortBy(String sort, String order) {
		
		String[] sortItems = sort.split(",");
		String[] filteredSortItems = new String[sortItems.length];
		
		for (int i = 0; i < sortItems.length; i++) {
			filteredSortItems[i] = sortItems[i].toLowerCase().trim();
		}
		
		Sort sortBy = Sort.by(filteredSortItems);
		
		if (order.equalsIgnoreCase("desc")) {
			sortBy = sortBy.descending();
		} else {
			sortBy = sortBy.ascending();
		}
		
		return sortBy;
	}
}
