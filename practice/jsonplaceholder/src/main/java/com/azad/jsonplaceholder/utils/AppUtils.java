package com.azad.jsonplaceholder.utils;

import com.azad.jsonplaceholder.models.responses.CommentResponse;
import com.azad.jsonplaceholder.rest.controllers.GenericRestController;
import com.azad.jsonplaceholder.rest.controllers.PostRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
}
