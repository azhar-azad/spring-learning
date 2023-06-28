package com.azad.onlinecourse.api.assembler;

import com.azad.onlinecourse.api.resource.AuthRestResource;
import com.azad.onlinecourse.common.generics.GenericApiResponseModelAssembler;
import com.azad.onlinecourse.common.PagingAndSorting;
import com.azad.onlinecourse.models.auth.AppUserResponse;
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
        if (response.getError() != null) {
            return responseEntityModel;
        }
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
