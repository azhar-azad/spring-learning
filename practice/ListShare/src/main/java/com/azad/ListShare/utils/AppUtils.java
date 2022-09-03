package com.azad.ListShare.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AppUtils {

    @Value("${default_page_number}")
    private int defaultPage;

    @Value("${default_result_limit}")
    private int defaultLimit;

    @Value("${default_sort_order}")
    private String defaultOrder;

    private final Logger LOG = LoggerFactory.getLogger(AppUtils.class);

    public void printControllerMethodInfo(String httpMethod, String requestPath, String controllerMethodName, boolean isPublic, String authorizedFor) {
        LOG.info("");
        LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        LOG.info(httpMethod + " " + requestPath + " :::: " + controllerMethodName);
        if (isPublic)
            LOG.info("PUBLIC :: No Authentication");
        else {
            LOG.info("PRIVATE :: Authenticated & Authorized for {}", authorizedFor);
        }
        LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    public Sort getSortAndOrder(String sort, String order) {

        List<String> sortItems = Arrays.stream(sort.split(","))
                .map(String::trim).collect(Collectors.toList());

        Sort sortBy = Sort.by(sortItems.stream().toArray(String[]::new));

        if (order.equalsIgnoreCase("desc")) {
            sortBy = sortBy.descending();
        } else {
            sortBy = sortBy.ascending();
        }

        return sortBy;
    }

    public int getIntParamValue(Map<String, String> requestParams, String key) {

        if (requestParams.containsKey(key)) {
            String paramValue = requestParams.get(key);
            if (paramValue != null && !paramValue.equals("")) {
                try {
                    return Integer.parseInt(paramValue);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        }

        if (key.equalsIgnoreCase("page")) return defaultPage;
        else if (key.equalsIgnoreCase("limit")) return defaultLimit;
        else return -1;
    }

    public String getStringParamValue(Map<String, String> requestParams, String key) {
        if (requestParams.containsKey(key)) {
            String paramValue = requestParams.get(key);
            if (paramValue != null) {
                return paramValue;
            }
        }
        if (key.equalsIgnoreCase("order")) return defaultOrder;
        else return "";
    }
}
