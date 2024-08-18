package com.azad.moviepedia.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

public class LogUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(LogUtils.class);

    public static void logInfo(Class _class, String log) {
        LOGGER = LoggerFactory.getLogger(_class);
        LOGGER.info(">>>> {} <<<<", log);
    }

    public static void logDebug(Class _class, String log) {
        LOGGER = LoggerFactory.getLogger(_class);
        LOGGER.debug(">>>> {} <<<<", log);
    }

    public static void logError(Class _class, String log) {
        LOGGER = LoggerFactory.getLogger(_class);
        LOGGER.error(">>>> {} <<<<", log);
    }

    public static void printRequestInfo(Class _class, String url, HttpMethod method, String hasAccess) {
        LOGGER = LoggerFactory.getLogger(_class);
        LOGGER.info(">>> URL: " + url + " <<<");
        LOGGER.info(">>> Method: " + method.name() + " <<<");
        LOGGER.info(">>> Access: " + hasAccess + " <<<\n");
    }

}
