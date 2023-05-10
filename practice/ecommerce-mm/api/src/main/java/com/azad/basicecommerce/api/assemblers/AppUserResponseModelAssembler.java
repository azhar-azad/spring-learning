package com.azad.basicecommerce.api.assemblers;

import com.azad.basicecommerce.api.resources.AuthRestResource;
import com.azad.basicecommerce.auth.models.AppUserResponse;
import com.azad.basicecommerce.common.GenericApiResponseModelAssembler;
import com.azad.basicecommerce.common.PagingAndSorting;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AppUserResponseModelAssembler implements GenericApiResponseModelAssembler<AppUserResponse> {
    @Override
    public EntityModel<AppUserResponse> toModel(AppUserResponse response) {
        EntityModel<AppUserResponse> responseEntityModel = EntityModel.of(response);
        responseEntityModel.add(linkTo(methodOn(AuthRestResource.class).getUser()).withSelfRel());
        return responseEntityModel;
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
