package com.azad.ecommerce.productservice.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;

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

    public long getHash(String data) {
        return hash64(data.getBytes());
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
}
