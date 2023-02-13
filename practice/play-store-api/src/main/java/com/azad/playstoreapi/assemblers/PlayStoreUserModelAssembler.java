package com.azad.playstoreapi.assemblers;

import com.azad.playstoreapi.controllers.AuthController;
import com.azad.playstoreapi.models.responses.PlayStoreUserResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlayStoreUserModelAssembler implements RepresentationModelAssembler<PlayStoreUserResponse, EntityModel<PlayStoreUserResponse>> {

    @Override
    public EntityModel<PlayStoreUserResponse> toModel(PlayStoreUserResponse entity) {

        EntityModel<PlayStoreUserResponse> entityModel = EntityModel.of(entity);

        entityModel.add(linkTo(methodOn(AuthController.class).getUserInfo()).withSelfRel());

        return entityModel;
    }
}
