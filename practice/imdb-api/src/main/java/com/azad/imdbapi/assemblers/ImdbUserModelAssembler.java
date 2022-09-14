package com.azad.imdbapi.assemblers;

import com.azad.imdbapi.controllers.AuthController;
import com.azad.imdbapi.responses.ImdbUserResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ImdbUserModelAssembler implements RepresentationModelAssembler<ImdbUserResponse, EntityModel<ImdbUserResponse>> {

    @Override
    public EntityModel<ImdbUserResponse> toModel(ImdbUserResponse entity) {

        EntityModel<ImdbUserResponse> entityModel = EntityModel.of(entity);
        entityModel.add(linkTo(methodOn(AuthController.class).getLoggedInUser()).withSelfRel());

        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<ImdbUserResponse>> toCollectionModel(Iterable<? extends ImdbUserResponse> entities) {

        List<EntityModel<ImdbUserResponse>> entityModels = new ArrayList<>();

        for (ImdbUserResponse entity: entities)
            entityModels.add(toModel(entity));

        return CollectionModel.of(entityModels);
    }
}
