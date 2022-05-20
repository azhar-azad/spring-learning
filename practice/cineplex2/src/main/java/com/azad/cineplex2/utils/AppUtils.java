package com.azad.cineplex2.utils;

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
}
