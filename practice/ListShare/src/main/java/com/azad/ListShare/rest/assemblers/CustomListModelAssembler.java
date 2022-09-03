package com.azad.ListShare.rest.assemblers;

import com.azad.ListShare.models.responses.CustomListResponse;
import com.azad.ListShare.repos.CustomListRepository;
import com.azad.ListShare.rest.controllers.CustomListRestController;
import com.azad.ListShare.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomListModelAssembler implements RepresentationModelAssembler<CustomListResponse, EntityModel<CustomListResponse>> {

    @Value("${default_page_number}")
    private int defaultPage;

    @Value("${default_result_limit}")
    private int defaultLimit;

    @Value("${default_sort_order}")
    private String defaultOrder;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private CustomListRepository customListRepository;

    @Override
    public EntityModel<CustomListResponse> toModel(CustomListResponse entity) {

        Map<String, String> defaultRequestParams = new HashMap<>();
        defaultRequestParams.put("page", String.valueOf(defaultPage));
        defaultRequestParams.put("limit", String.valueOf(defaultLimit));
        defaultRequestParams.put("sort", "");
        defaultRequestParams.put("order", String.valueOf(defaultOrder));

        EntityModel<CustomListResponse> entityModel = EntityModel.of(entity);

        entityModel.add(linkTo(methodOn(
                CustomListRestController.class).getList(entity.getId()))
                .withSelfRel());
        entityModel.add(linkTo(methodOn(
                CustomListRestController.class).getAllLists(defaultRequestParams))
                .withRel(IanaLinkRelations.COLLECTION));

        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<CustomListResponse>> toCollectionModel(Iterable<? extends CustomListResponse> entities) {

        List<EntityModel<CustomListResponse>> entityModels = new ArrayList<>();

        for (CustomListResponse entity: entities)
            entityModels.add(toModel(entity));

        return CollectionModel.of(entityModels);
    }

    public CollectionModel<EntityModel<CustomListResponse>> getCollectionModel(Iterable<? extends CustomListResponse> entities,
                                                                               Map<String, String> requestParams) {

        CollectionModel<EntityModel<CustomListResponse>> collectionModel = toCollectionModel(entities);

        collectionModel.add(linkTo(methodOn(
                CustomListRestController.class).getAllLists(requestParams))
                .withSelfRel());

        long totalRecords = customListRepository.count();

        if (appUtils.getIntParamValue(requestParams, "page") > 1)
            addPrevLink(collectionModel, requestParams);
        if (totalRecords > ((long) appUtils.getIntParamValue(requestParams, "page") * appUtils.getIntParamValue(requestParams, "limit")))
            addNextLink(collectionModel, requestParams);

        return collectionModel;
    }

    private void addNextLink(CollectionModel<EntityModel<CustomListResponse>> collectionModel, Map<String, String> requestParams) {
        int value = appUtils.getIntParamValue(requestParams, "page");
        requestParams.put("page", String.valueOf(value + 1));

        collectionModel.add(linkTo(methodOn(
                CustomListRestController.class).getAllLists(requestParams))
                .withRel(IanaLinkRelations.NEXT));
    }

    private void addPrevLink(CollectionModel<EntityModel<CustomListResponse>> collectionModel, Map<String, String> requestParams) {
        int value = appUtils.getIntParamValue(requestParams, "page");
        requestParams.put("page", String.valueOf(value - 1));

        collectionModel.add(linkTo(methodOn(
                CustomListRestController.class).getAllLists(requestParams))
                .withRel(IanaLinkRelations.PREV));
    }
}
