package com.azad.moviepedia.common;

import com.azad.moviepedia.controllers.WriterRestController;
import com.azad.moviepedia.models.dtos.responses.WriterResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AppUtils {

    @Value("${default_page_number}")
    private int defaultPage;

    @Value("${default_result_limit}")
    private int defaultLimit;

    @Value("${default_sort_order}")
    private String defaultOrder;

    public int getPage(String reqParamPageValue) {
        int intPageValue = convertStringToInt(reqParamPageValue);
        return intPageValue != -1 ? intPageValue : defaultPage;
    }

    public int getLimit(String reqParamLimitValue) {
        int intLimitValue = convertStringToInt(reqParamLimitValue);
        return intLimitValue != -1 ? intLimitValue : defaultLimit;
    }

    public Map<String, String> getDefaultReqParamMap() {
        Map<String, String> defaultParamMap = new HashMap<>();
        defaultParamMap.put("page", String.valueOf(defaultPage));
        defaultParamMap.put("limit", String.valueOf(defaultLimit));
        defaultParamMap.put("sort", "");
        defaultParamMap.put("order", defaultOrder);
        return defaultParamMap;
    }

    public String calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date cannot be null");
        }
        Period age = Period.between(birthDate, LocalDate.now());
        return age.getYears() + " years " + (age.getMonths() == 0 ? "" : age.getMonths() + " months");
    }

    public boolean isUserAuthorized(String userRole, String... authorizedRoles) {
        for (String authorizedRole: authorizedRoles) {
            if (userRole.equalsIgnoreCase(authorizedRole))
                return true;
        }
        return false;
    }

    public PagingAndSorting getPagingAndSorting(Map<String, String> reqParam) throws RuntimeException {
        int page = Math.max(getPage(reqParam.get("page")), 0);
        int limit = getLimit(reqParam.get("limit"));
        String sort = reqParam.get("sort");
        String order = reqParam.get("order");

        return new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order);
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

    private int convertStringToInt(String strValue) {
        try {
            return Integer.parseInt(strValue);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }
}
