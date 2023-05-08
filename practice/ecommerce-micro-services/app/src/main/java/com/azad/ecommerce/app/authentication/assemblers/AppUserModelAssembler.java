package com.azad.ecommerce.app.authentication.assemblers;

import com.azad.ecommerce.app.authentication.controllers.AuthRestController;
import com.azad.ecommerce.app.authentication.models.responses.AppUserResponse;
import com.azad.ecommerce.app.commons.PagingAndSorting;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AppUserModelAssembler implements GenericApiModelAssembler<AppUserResponse> {
    @Override
    public EntityModel<AppUserResponse> toModel(AppUserResponse response) {
        EntityModel<AppUserResponse> entityModel = EntityModel.of(response);
        entityModel.add(linkTo(methodOn(AuthRestController.class).getUserInfo()).withSelfRel());
        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<AppUserResponse>> toCollectionModel(Iterable<? extends AppUserResponse> responses) {
        return null;
    }

    @Override
    public CollectionModel<EntityModel<AppUserResponse>> getCollectionModel(Iterable<? extends AppUserResponse> responses, PagingAndSorting ps) {
        return null;
    }

    @Override
    public void addNextLink(CollectionModel<EntityModel<AppUserResponse>> collectionModel, PagingAndSorting ps) {

    }

    @Override
    public void addPrevLink(CollectionModel<EntityModel<AppUserResponse>> collectionModel, PagingAndSorting ps) {

    }
}
