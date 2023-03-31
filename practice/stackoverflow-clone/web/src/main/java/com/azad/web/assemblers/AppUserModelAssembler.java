package com.azad.web.assemblers;

import com.azad.common.PagingAndSorting;
import com.azad.data.models.responses.AppUserResponse;
import com.azad.web.controllers.AuthController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AppUserModelAssembler implements GenericApiResponseModelAssembler<AppUserResponse> {

    public EntityModel<AppUserResponse> toModel(AppUserResponse entity) {
        EntityModel<AppUserResponse> entityModel = EntityModel.of(entity);

        entityModel.add(linkTo(methodOn(AuthController.class).getUserInfo()).withSelfRel());

        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<AppUserResponse>> toCollectionModel(Iterable<? extends AppUserResponse> entities) {
        return null;
    }

    @Override
    public CollectionModel<EntityModel<AppUserResponse>> getCollectionModel(Iterable<? extends AppUserResponse> entities, PagingAndSorting ps) {
        return null;
    }

    @Override
    public void addNextLink(CollectionModel<EntityModel<AppUserResponse>> collectionModel, PagingAndSorting ps) {

    }

    @Override
    public void addPrevLink(CollectionModel<EntityModel<AppUserResponse>> collectionModel, PagingAndSorting ps) {

    }
}
