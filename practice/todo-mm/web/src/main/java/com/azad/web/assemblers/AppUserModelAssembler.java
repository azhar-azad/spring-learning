package com.azad.web.assemblers;

import com.azad.data.models.responses.AppUserResponse;
import com.azad.web.controllers.AuthController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AppUserModelAssembler implements RepresentationModelAssembler<AppUserResponse, EntityModel<AppUserResponse>> {
    @Override
    public EntityModel<AppUserResponse> toModel(AppUserResponse entity) {

        EntityModel<AppUserResponse> entityModel = EntityModel.of(entity);

        entityModel.add(linkTo(methodOn(AuthController.class).getUserInfo()).withSelfRel());

        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<AppUserResponse>> toCollectionModel(Iterable<? extends AppUserResponse> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
