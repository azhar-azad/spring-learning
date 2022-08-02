package com.azad.jsonplaceholderclone.assemblers;

import com.azad.jsonplaceholderclone.controllers.AlbumController;
import com.azad.jsonplaceholderclone.controllers.MemberController;
import com.azad.jsonplaceholderclone.models.responses.AlbumResponse;
import com.azad.jsonplaceholderclone.models.responses.MemberResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlbumModelAssembler implements RepresentationModelAssembler<AlbumResponse, EntityModel<AlbumResponse>> {
    @Override
    public EntityModel<AlbumResponse> toModel(AlbumResponse entity) {
        EntityModel<AlbumResponse> albumResponseEntityModel = EntityModel.of(entity);

        albumResponseEntityModel.add(linkTo(methodOn(AlbumController.class).getAlbum(entity.getId())).withSelfRel());
        albumResponseEntityModel.add(linkTo(methodOn(AlbumController.class).getAllAlbums(1, 25, "", "asc")).withRel(IanaLinkRelations.COLLECTION));

        return albumResponseEntityModel;
    }
}
