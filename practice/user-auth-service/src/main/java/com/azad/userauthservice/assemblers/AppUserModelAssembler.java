package com.azad.userauthservice.assemblers;

import com.azad.userauthservice.controllers.AuthController;
import com.azad.userauthservice.models.responses.AppUserResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AppUserModelAssembler implements RepresentationModelAssembler<AppUserResponse, EntityModel<AppUserResponse>> {

    @Override
    public EntityModel<AppUserResponse> toModel(AppUserResponse entity) {

        EntityModel<AppUserResponse> entityModel = EntityModel.of(entity);
        entityModel.add(linkTo(methodOn(AuthController.class).getLoggedInUser()).withSelfRel());

        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<AppUserResponse>> toCollectionModel(Iterable<? extends AppUserResponse> entities) {

        List<EntityModel<AppUserResponse>> entityModels = new ArrayList<>();

        for (AppUserResponse appUserResponse: entities)
            entityModels.add(toModel(appUserResponse));

        return CollectionModel.of(entityModels);
    }
}
