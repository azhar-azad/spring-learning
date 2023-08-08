package com.azad.taskapiwithauth.commons.utils;

import com.azad.taskapiwithauth.commons.PagingAndSorting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ApiUtils {

    @Value("${default_page_number}")
    private int defaultPage;

    @Value("${default_result_limit}")
    private int defaultLimit;

    @Value("${default_sort_order}")
    private String defaultOrder;

    private Logger logger = LoggerFactory.getLogger(ApiUtils.class);

    public void printRequestInfo(Class _class, String url, String method, String hasAccess) {
        logger = LoggerFactory.getLogger(_class);
        logger.info(">>> URL: " + url + " <<<");
        logger.info(">>> Method: " + method.toUpperCase() + " <<<");
        logger.info(">>> Access: " + hasAccess + " <<<\n");
    }

    public void logInfo(Class _class, String log) {
        logger = LoggerFactory.getLogger(_class);
        logger.info(">>>> {} <<<<", log);
    }

    public void logDebug(Class _class, String log) {
        logger = LoggerFactory.getLogger(_class);
        logger.debug(">>>> {} <<<<", log);
    }

    public void logError(Class _class, String log) {
        logger = LoggerFactory.getLogger(_class);
        logger.error(">>>> {} <<<<", log);
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
