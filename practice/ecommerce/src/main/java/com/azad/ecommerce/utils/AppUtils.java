package com.azad.ecommerce.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
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

    public Logger getLogger() {
        return LOG;
    }

    public int getPage(String reqParamPageValue) {
        int intPageValue = convertStringToInt(reqParamPageValue);
        return intPageValue != -1 ? intPageValue : defaultPage;
    }

    public int getLimit(String reqParamLimitValue) {
        int intLimitValue = convertStringToInt(reqParamLimitValue);
        return intLimitValue != -1 ? intLimitValue : defaultLimit;
    }

    private boolean isNumeric(String strValue) {
        return strValue != null && strValue.matches("[0-9.]+");
    }

    public Map<String, String> getDefaultReqParamMap() {
        Map<String, String> defaultParamMap = new HashMap<>();
        defaultParamMap.put("page", String.valueOf(defaultPage));
        defaultParamMap.put("limit", String.valueOf(defaultLimit));
        defaultParamMap.put("sort", "");
        defaultParamMap.put("order", defaultOrder);
        return defaultParamMap;
    }

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

    private int convertStringToInt(String strValue) {
        try {
            if (isNumeric(strValue)) {
                return Integer.parseInt(strValue);
            }
            throw new RuntimeException(strValue + " is not numeric");
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
}
