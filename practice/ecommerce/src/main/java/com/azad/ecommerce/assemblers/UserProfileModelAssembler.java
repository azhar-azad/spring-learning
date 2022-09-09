package com.azad.ecommerce.assemblers;

import com.azad.ecommerce.controllers.UserProfileRestController;
import com.azad.ecommerce.models.responses.UserProfileResponse;
import com.azad.ecommerce.repos.UserProfileRepository;
import com.azad.ecommerce.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserProfileModelAssembler implements RepresentationModelAssembler<UserProfileResponse, EntityModel<UserProfileResponse>> {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private UserProfileRepository repository;

    @Override
    public EntityModel<UserProfileResponse> toModel(UserProfileResponse entity) {

        EntityModel<UserProfileResponse> entityModel = EntityModel.of(entity);

        entityModel.add(linkTo(methodOn(UserProfileRestController.class).getProfile()).withSelfRel());
        entityModel.add(linkTo(methodOn(UserProfileRestController.class)
                .getAllProfiles(appUtils.getDefaultReqParamMap())).withRel(IanaLinkRelations.COLLECTION));

        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<UserProfileResponse>> toCollectionModel(Iterable<? extends UserProfileResponse> entities) {

        List<EntityModel<UserProfileResponse>> entityModels = new ArrayList<>();

        for (UserProfileResponse entity: entities)
            entityModels.add(toModel(entity));

        return CollectionModel.of(entityModels);
    }

    public CollectionModel<EntityModel<UserProfileResponse>> getCollectionModel(
            Iterable<? extends UserProfileResponse> entities, Map<String, String> reqParams) {

        CollectionModel<EntityModel<UserProfileResponse>> collectionModel = toCollectionModel(entities);

        collectionModel.add(linkTo(methodOn(UserProfileRestController.class).getAllProfiles(reqParams)).withSelfRel());

        long totalRecords = repository.count();

        if (appUtils.getPage(reqParams.get("page")) > 1) {
            addPrevLink(collectionModel, reqParams);
        }
        if (totalRecords > ((long) appUtils.getPage(reqParams.get("page")) * appUtils.getLimit(reqParams.get("limit")))) {
            addNextList(collectionModel, reqParams);
        }

        return collectionModel;
    }

    private void addNextList(CollectionModel<EntityModel<UserProfileResponse>> collectionModel, Map<String, String> reqParams) {
        reqParams.put("page", String.valueOf(appUtils.getPage(reqParams.get("page")) + 1));
        collectionModel.add(linkTo(methodOn(UserProfileRestController.class).getAllProfiles(reqParams)).withRel(IanaLinkRelations.NEXT));
    }

    private void addPrevLink(CollectionModel<EntityModel<UserProfileResponse>> collectionModel, Map<String, String> reqParams) {
        reqParams.put("page", String.valueOf(appUtils.getPage(reqParams.get("page")) - 1));
        collectionModel.add(linkTo(methodOn(UserProfileRestController.class).getAllProfiles(reqParams)).withRel(IanaLinkRelations.PREV));
    }
}
