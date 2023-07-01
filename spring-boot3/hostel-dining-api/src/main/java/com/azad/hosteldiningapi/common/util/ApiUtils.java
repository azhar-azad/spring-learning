package com.azad.hosteldiningapi.common.util;

import com.azad.hosteldiningapi.common.PagingAndSorting;
import com.azad.hosteldiningapi.common.exceptions.ApiError;
import com.azad.hosteldiningapi.common.exceptions.ResourceNotFoundException;
import com.azad.hosteldiningapi.common.exceptions.UnauthorizedAccessException;
import com.azad.hosteldiningapi.common.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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

    public ApiError getApiErrorForUnauthorizedAccess(UnauthorizedAccessException ex) {
        return new ApiError(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(),
                "Logged in user doesn't have access or is not the owner of this resource. " +
                        "Valid accesses are " + ex.getValidAccess());
    }

    public ApiError getApiErrorForResourceNotFound(ResourceNotFoundException ex) {
        return new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(),
                ex.getResource() + " not found with identifier: " + ex.getResourceIdentifier());
    }

    public ApiError getApiErrorForUserNotFound(UserNotFoundException ex) {
        return new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
                "User not found with the identifier passed");
    }

//    private String hashSHA256(String entityName, String input) {
//        MessageDigest digest; // create SHA-256 hash object
//        try {
//            digest = MessageDigest.getInstance("SHA-256");
//        } catch (NoSuchAlgorithmException e) {
//            LOG.info("Exception on hashSha256 method");
//            throw new RuntimeException(e);
//        }
//        byte[] hash = digest.digest(input.getBytes()); // generate hash as a byte array
//
//        // take first 4 bytes (32 bits) of the hash and convert to hexadecimal format
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < 4; i++) {
//            String hex = Integer.toHexString(hash[i] & 0xFF);
//            if (hex.length() == 1) {
//                sb.append('0');
//            }
//            sb.append(hex);
//        }
//
//        return entityName + "_" + sb;
//    }

//    private String getSecureRandomString() {
//        final String ALPHA_NUMERIC_SYMBOLS =
//                "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
//                        "abcdefghijklmnopqrstuvwxyz" +
//                        "0123456789!@#$%^&*()_+-=[]{}|;':\",./<>?";
//
//        SecureRandom random = new SecureRandom();
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < 32; i++) {
//            int index = random.nextInt(ALPHA_NUMERIC_SYMBOLS.length());
//            char randomChar = ALPHA_NUMERIC_SYMBOLS.charAt(index);
//            sb.append(randomChar);
//        }
//
//        return sb.toString();
//    }

    private boolean isNumeric(String strValue) {
        return strValue != null && strValue.matches("[0-9.]+");
    }

    private int convertStringToInt(String strValue) {
        try {
            if (strValue == null)
                return -1;
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
