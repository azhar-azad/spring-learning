package com.azad.ecommerce.assemblers;

import com.azad.ecommerce.controllers.AuthController;
import com.azad.ecommerce.models.responses.UserResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserResponse, EntityModel<UserResponse>> {

    @Override
    public EntityModel<UserResponse> toModel(UserResponse entity) {

        EntityModel<UserResponse> entityModel = EntityModel.of(entity);
        entityModel.add(linkTo(methodOn(
                AuthController.class).getLoggedInUser())
                .withSelfRel());

        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<UserResponse>> toCollectionModel(Iterable<? extends UserResponse> entities) {

        List<EntityModel<UserResponse>> entityModels = new ArrayList<>();

        for (UserResponse entity: entities)
            entityModels.add(toModel(entity));

        return CollectionModel.of(entityModels);
    }
}
