package com.azad.hosteldiningapi.api.assembers;

import com.azad.hosteldiningapi.api.resources.AuthRestResource;
import com.azad.hosteldiningapi.common.PagingAndSorting;
import com.azad.hosteldiningapi.common.generics.GenericApiResponseModelAssembler;
import com.azad.hosteldiningapi.models.auth.MemberResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MemberResponseModelAssembler implements GenericApiResponseModelAssembler<MemberResponse> {

    @Override
    public @NotNull EntityModel<MemberResponse> toModel(@NotNull MemberResponse response) {
        EntityModel<MemberResponse> responseEntityModel = EntityModel.of(response);
        if (response.getError() != null) {
            return responseEntityModel;
        }
        responseEntityModel.add(linkTo(methodOn(AuthRestResource.class).getMember()).withSelfRel());
        return responseEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<MemberResponse>> toCollectionModel(Iterable<? extends MemberResponse> responses) {
        return null;
    }

    @Override
    public CollectionModel<EntityModel<MemberResponse>> getCollectionModel(Iterable<? extends MemberResponse> responses, PagingAndSorting ps) {
        return null;
    }

    @Override
    public void addNextLink(CollectionModel<EntityModel<MemberResponse>> collectionModel, PagingAndSorting ps) {

    }

    @Override
    public void addPrevLink(CollectionModel<EntityModel<MemberResponse>> collectionModel, PagingAndSorting ps) {

    }
}
