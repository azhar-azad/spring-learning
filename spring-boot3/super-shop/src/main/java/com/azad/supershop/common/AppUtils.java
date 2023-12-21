package com.azad.supershop.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

public class AppUtils {

    @Value("${default_page_number}")
    private int defaultPage;

    @Value("${default_result_limit}")
    private int defaultLimit;

    @Value("${default_sort_order}")
    private String defaultOrder;

    private static Logger LOGGER = LoggerFactory.getLogger(AppUtils.class);

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

    public boolean isUserAuthorized(String userRole, String... authorizedRoles) {
        for (String authorizedRole: authorizedRoles) {
            if (userRole.equalsIgnoreCase(authorizedRole))
                return true;
        }
        return false;
    }

    public void printRequestInfo(Class _class, String url, String method, String hasAccess) {
        LOGGER = LoggerFactory.getLogger(_class);
        LOGGER.info(">>> URL: " + url + " <<<");
        LOGGER.info(">>> Method: " + method.toUpperCase() + " <<<");
        LOGGER.info(">>> Access: " + hasAccess + " <<<\n");
    }

    public Pageable getPageable(PagingAndSorting ps) {

        if (ps.getSort().isEmpty())
            return PageRequest.of(ps.getPage(), ps.getLimit());
        else
            return PageRequest.of(ps.getPage(), ps.getLimit(), getSortAndOrder(ps.getSort(), ps.getOrder()));
    }

    private Sort getSortAndOrder(String sort, String order) {

        Sort sortBy = Sort.by(Arrays.stream(sort.split(","))
                .map(String::trim).toArray(String[]::new));

        if (order.equalsIgnoreCase("desc"))
            sortBy = sortBy.descending();
        else
            sortBy = sortBy.ascending();

        return sortBy;
    }
}
