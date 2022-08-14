package com.azad.jsonplaceholder.rest.assemblers;

import com.azad.jsonplaceholder.models.responses.MemberResponse;
import com.azad.jsonplaceholder.rest.controllers.MemberProfileRestController;
import com.azad.jsonplaceholder.rest.controllers.PostRestController;
import com.azad.jsonplaceholder.rest.controllers.TodoRestController;
import com.azad.jsonplaceholder.security.auth.api.AuthController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MemberModelAssembler implements RepresentationModelAssembler<MemberResponse, EntityModel<MemberResponse>> {

    @Value("${default_page_number}")
    private int defaultPage;

    @Value("${default_result_limit}")
    private int defaultLimit;

    @Value("${default_sort_order}")
    private String defaultOrder;

    @Override
    public EntityModel<MemberResponse> toModel(MemberResponse entity) {

        EntityModel<MemberResponse> memberResponseEntityModel = EntityModel.of(entity);

        memberResponseEntityModel.add(linkTo(methodOn(
                AuthController.class).getLoggedInUser())
                .withSelfRel());
        memberResponseEntityModel.add(linkTo(methodOn(
                MemberProfileRestController.class).getMemberProfile())
                .withRel("profile"));
        memberResponseEntityModel.add(linkTo(methodOn(
                TodoRestController.class).getAllTodos(defaultPage, defaultLimit, "", defaultOrder))
                .withRel("todos"));
        memberResponseEntityModel.add(linkTo(methodOn(
                PostRestController.class).getAllPosts(defaultPage, defaultLimit, "", defaultOrder))
                .withRel("posts"));

        return memberResponseEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<MemberResponse>> toCollectionModel(Iterable<? extends MemberResponse> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
