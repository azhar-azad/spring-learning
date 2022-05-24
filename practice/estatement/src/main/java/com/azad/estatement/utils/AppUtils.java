package com.azad.estatement.utils;

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
			filteredSortItems[i] = sortItems[i].trim();
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
