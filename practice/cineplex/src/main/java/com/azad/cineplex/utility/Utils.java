package com.azad.cineplex.utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

	public static void printControllerMethodInfo(String httpMethod, String requestPath, String controllerMethodName) {
		log.info("");
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.info(httpMethod + " " + requestPath + " :::: " + controllerMethodName);
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	public static boolean sortItemsAreValid(String className, List<String> sortItems) {
		
		try {
			List<Field> fields = Arrays.asList(Class.forName(className).getFields());
			
			List<String> fieldNames = new ArrayList<>();
			fields.forEach(field -> fieldNames.add(field.getName()));
			
			for (String sortItem: sortItems) {
				if (!fieldNames.contains(sortItem)) {
					return false;
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
