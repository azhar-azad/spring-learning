package com.azad.basicecommerce.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

@Component
public class ApiUtils {

    @Value("${default_page_number}")
    private int defaultPage;

    @Value("${default_result_limit}")
    private int defaultLimit;

    @Value("${default_sort_order}")
    private String defaultOrder;

    @Autowired
    private RestTemplate restTemplate;

    private final Logger LOG = LoggerFactory.getLogger(ApiUtils.class);

    public Logger getLogger() {
        return LOG;
    }

    public Pageable getPageable(PagingAndSorting ps) {

        if (ps.getSort().isEmpty())
            return PageRequest.of(ps.getPage(), ps.getLimit());
        else
            return PageRequest.of(ps.getPage(), ps.getLimit(), getSortAndOrder(ps.getSort(), ps.getOrder()));
    }

    public Sort getSortAndOrder(String sort, String order) {

        Sort sortBy = Sort.by(Arrays.stream(sort.split(","))
                .map(String::trim).toArray(String[]::new));

        if (order.equalsIgnoreCase("desc")) {
            sortBy = sortBy.descending();
        } else {
            sortBy = sortBy.ascending();
        }

        return sortBy;
    }

    public String getHash(String data) {
        return hashSHA256(data + getSecureRandomString());
    }

    public int convertStringToInt(String strValue) {
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

    private boolean isNumeric(String strValue) {
        return strValue != null && strValue.matches("[0-9.]+");
    }

    private String hashSHA256(String input) {
        MessageDigest digest; // create SHA-256 hash object
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            getLogger().info("Exception on hashSha256 method");
            throw new RuntimeException(e);
        }
        byte[] hash = digest.digest(input.getBytes()); // generate hash as a byte array

        // take first 4 bytes (32 bits) of the hash and convert to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String hex = Integer.toHexString(hash[i] & 0xFF);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }

        return sb.toString();
    }

    private long hash64(final byte[] k) {

        final long FNV_64_INIT = 0xcbf29ce484222325L;
        final long FNV_64_PRIME = 0x100000001b3L;

        long rv = FNV_64_INIT;
        final int len = k.length;
        for (byte b : k) {
            rv ^= b;
            rv *= FNV_64_PRIME;
        }
        return rv;
    }

    private String getSecureRandomString() {
        final String ALPHA_NUMERIC_SYMBOLS =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                        "abcdefghijklmnopqrstuvwxyz" +
                        "0123456789!@#$%^&*()_+-=[]{}|;':\",./<>?";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(ALPHA_NUMERIC_SYMBOLS.length());
            char randomChar = ALPHA_NUMERIC_SYMBOLS.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    private String getUserUid() {
        String getUserInfoUrl = "http://localhost:8081/api/v1/auth/me";
        String responseString = callRestApiAndGetString(getUserInfoUrl);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(responseString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String userUid = jsonNode.get("userUid").asText();
        if (userUid == null || userUid.equalsIgnoreCase(""))
            throw new RuntimeException("userUid not found. Maybe the request is not from a logged in user");

        return userUid;
    }

    private String callRestApiAndGetString(String apiUrl) {
        return restTemplate.getForObject(apiUrl, String.class);
    }
}
