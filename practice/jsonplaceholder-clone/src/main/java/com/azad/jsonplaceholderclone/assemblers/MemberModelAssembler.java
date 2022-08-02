package com.azad.jsonplaceholderclone.assemblers;

import com.azad.jsonplaceholderclone.controllers.MemberController;
import com.azad.jsonplaceholderclone.models.responses.MemberResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MemberModelAssembler implements RepresentationModelAssembler<MemberResponse, EntityModel<MemberResponse>> {

    @Override
    public EntityModel<MemberResponse> toModel(MemberResponse entity) {
        EntityModel<MemberResponse> memberResponseEntityModel = EntityModel.of(entity);

        memberResponseEntityModel.add(linkTo(methodOn(MemberController.class).getMember(entity.getId())).withSelfRel());
        memberResponseEntityModel.add(linkTo(methodOn(MemberController.class).getAllMembers(1, 25, "", "asc")).withRel(IanaLinkRelations.COLLECTION));

        return memberResponseEntityModel;
    }
}
