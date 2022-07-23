package com.azad.jsonplaceholderclone.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class AppUtils {

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

    public Sort getSortBy(String sort, String order) {

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

    private String getRandomString() {

        int leftLimit= 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 8;

        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <=90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
