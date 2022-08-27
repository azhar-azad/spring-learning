package com.azad.ListShare.rest.assemblers;

import com.azad.ListShare.models.responses.MemberResponse;
import com.azad.ListShare.rest.controllers.AuthController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

        EntityModel<MemberResponse> entityModel = EntityModel.of(entity);

        entityModel.add(linkTo(methodOn(
                AuthController.class).getLoggedInUser())
                .withSelfRel());

        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<MemberResponse>> toCollectionModel(Iterable<? extends MemberResponse> entities) {
        List<EntityModel<MemberResponse>> entityModels = new ArrayList<>();

        for (MemberResponse entity: entities)
            entityModels.add(toModel(entity));

        return CollectionModel.of(entityModels);
    }
}
