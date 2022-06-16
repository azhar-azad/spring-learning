package com.azad.musiclibrary.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
	
	public int getAge(LocalDate birthDate) {
		
		if (birthDate != null) {
			return getAge(birthDate, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}
		
		return 0;
	}
	
	public int getAge(LocalDate birthDate, LocalDate deathDate) {
		
		if (birthDate != null && deathDate != null) {
			
			return Period.between(birthDate, deathDate).getYears();
		}
		
		return 0;
	}
	
	
	
	
}
