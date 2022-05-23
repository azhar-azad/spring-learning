package com.azad.cineplex2.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
	
	public static int getAge(Date birthDate) {
		
		if (birthDate != null) {
			return getAge(birthDate, new Date());
		}
		
		return 0;
	}
	
	public static int getAge(Date birthDate, Date deathDate) {
		
		if (birthDate != null && deathDate != null) {
			LocalDate localBirthDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			LocalDate localDeathDate = deathDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			return Period.between(localBirthDate, localDeathDate).getYears();
		}
		
		return 0;
	}
	
	
	
	
}
