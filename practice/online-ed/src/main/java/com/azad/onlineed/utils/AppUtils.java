package com.azad.onlineed.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AppUtils {

    private final Logger LOG = LoggerFactory.getLogger(AppUtils.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

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

//    public ApiResponse getApiResponse(Boolean isSuccess, String message, Map<String, List<?>> data) {
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setSuccess(isSuccess);
//        apiResponse.setMessage(message);
//        apiResponse.setData(data);
//        return apiResponse;
//    }

    public Sort getSortBy(String sort, String order) {

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

    public String getUserId(String email) {

        int mark = email.indexOf('@');
        String emailId = email.substring(0, mark);

        String encodedEmailId = passwordEncoder.encode(emailId);

        String userId = "";

        if (encodedEmailId.length() >= 8)
            userId += encodedEmailId.substring(encodedEmailId.length() - 8) + getRandomString();

        else userId += encodedEmailId + getRandomString();

        userId = userId.replace('/', '_');
        userId = userId.replace('.', '_');

        return userId;
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
