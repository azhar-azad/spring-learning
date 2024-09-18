package com.azad.tennis_score_api.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class AppUtils {

    @Value("${default_page_number}")
    private int defaultPageNumber;

    @Value("${default_result_limit}")
    private int defaultResultLimit;

    @Value("${default_sort_order}")
    private String defaultSortOrder;

    public int getPage(String reqParamPageValue) {
        int intPageValue = convertStringToInt(reqParamPageValue);
        return intPageValue != -1 ? intPageValue : defaultPageNumber;
    }

    public int getLimit(String reqParamLimitValue) {
        int intLimitValue = convertStringToInt(reqParamLimitValue);
        return intLimitValue != -1 ? intLimitValue : defaultResultLimit;
    }

    public Map<String, String> getDefaultReqParamMap() {
        Map<String, String> defaultParamMap = new HashMap<>();
        defaultParamMap.put("page", String.valueOf(defaultPageNumber));
        defaultParamMap.put("limit", String.valueOf(defaultResultLimit));
        defaultParamMap.put("sort", "");
        defaultParamMap.put("order", defaultSortOrder);
        return defaultParamMap;
    }

    public PagingAndSorting getPagingAndSorting(Map<String, String> reqParamMap) {
        int page = Math.max(getPage(reqParamMap.get("page")), 0);
        int limit = getLimit(reqParamMap.get("limit"));
        String sort = reqParamMap.get("sort");
        String order = reqParamMap.get("order");

        return new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order);
    }

    public Pageable getPageable(PagingAndSorting ps) {
        if (ps.getSort().isEmpty())
            return PageRequest.of(ps.getPage(), ps.getLimit());
        else
            return PageRequest.of(ps.getPage(), ps.getLimit(),
                    getSortAndOrder(ps.getSort(), ps.getOrder()));
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

    private int convertStringToInt(String strValue) {
        try {
            return Integer.parseInt(strValue);
        } catch (NumberFormatException ex) {
            Log.error(AppUtils.class, ex.getMessage());
        }
        return -1;
    }
}
