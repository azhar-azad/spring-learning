package com.azad.tennis_score_api.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    public static void info(Class _class, String log) {
        getLogger(_class).info(getLog(log));
    }

    public static void debug(Class _class, String log) {
        getLogger(_class).debug(getLog(log));
    }

    public static void error(Class _class, String log) {
        getLogger(_class).error(getLog(log));
    }

    public static void reqInfo(Class _class, String url, String method, String hasAccess) {
        getLogger(_class).info(">>> URL: " + url + " <<<");
        getLogger(_class).info(">>> Method: " + method.toUpperCase() + " <<<");
        getLogger(_class).info(">>> Access: " + hasAccess + " <<<\n");
    }

    private static Logger getLogger(Class _class) {
        return LoggerFactory.getLogger(_class);
    }

    private static String getLog(String log) {
        return String.format(">>>> %s <<<<", log);
    }
}
