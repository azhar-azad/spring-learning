package com.azad.grocery.api.assembler;

import com.azad.grocery.api.AuthRestResource;
import com.azad.grocery.common.PagingAndSorting;
import com.azad.grocery.models.AppUserResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AppUserResponseAssembler implements GenericApiResponseModelAssembler<AppUserResponse> {
    @Override
    public @NotNull EntityModel<AppUserResponse> toModel(@NotNull AppUserResponse response) {
        EntityModel<AppUserResponse> responseEntityModel = EntityModel.of(response);
        responseEntityModel.add(linkTo(methodOn(AuthRestResource.class).getUser()).withSelfRel());
        return responseEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<AppUserResponse>> toCollectionModel(@NotNull Iterable<? extends AppUserResponse> responses) {
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
