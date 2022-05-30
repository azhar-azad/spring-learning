package com.azad.bankapi.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AppUtils {

	public void printControllerMethodInfo(String httpMethod, String requestPath, String controllerMethodName) {
		log.info("");
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.info(httpMethod + " " + requestPath + " :::: " + controllerMethodName);
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	public Sort getSortBy(String sort, String order) {
		
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
	
	public int getBankUserAge(LocalDate birthDate) {
		
		if (birthDate != null) {
			return Period.between(birthDate, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).getYears();
		}
		
		return 0;
	}
}
