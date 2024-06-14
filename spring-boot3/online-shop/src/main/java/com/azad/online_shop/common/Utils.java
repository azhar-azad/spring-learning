package com.azad.online_shop.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

public class Utils {

    private static Logger LOG = LoggerFactory.getLogger(Utils.class);

    public static void logInfo(Class _class, String log) {
        LOG = LoggerFactory.getLogger(_class);
        LOG.info(">>>> {} <<<<", log);
    }

    public static void logDebug(Class _class, String log) {
        LOG = LoggerFactory.getLogger(_class);
        LOG.debug(">>>> {} <<<<", log);
    }

    public static void logError(Class _class, String log) {
        LOG = LoggerFactory.getLogger(_class);
        LOG.error(">>>> {} <<<<", log);
    }

    public static String generateProductName(String name, String brand, String size) {
        return getSpacesReplacedWith_(brand.toUpperCase() + " " + name.toUpperCase() + " " + size.toUpperCase());
    }

    public static Pageable getPageable(PagingAndSorting ps) {

        if (ps.sort().isEmpty())
            return PageRequest.of(ps.page(), ps.limit());
        else
            return PageRequest.of(ps.page(), ps.limit(), getSort(ps.sort(), ps.order()));
    }

    private static Sort getSort(String sort, String order) {

        Sort sortBy = Sort.by(Arrays.stream(sort.split(","))
                .map(String::trim).toArray(String[]::new));

        if (order.equalsIgnoreCase("desc"))
            sortBy = sortBy.descending();
        else
            sortBy = sortBy.ascending();

        return sortBy;
    }

    private static String getSpacesReplacedWith_(String str) {
        return str.replaceAll("\\s+", "_");
    }
}
