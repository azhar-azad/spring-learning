package com.azad.web.assemblers;

import com.azad.common.PagingAndSorting;
import com.azad.data.models.responses.TagResponse;
import com.azad.data.repos.TagRepository;
import com.azad.web.controllers.TagRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelAssembler implements GenericApiResponseModelAssembler<TagResponse> {

    @Autowired
    private TagRepository repository;

    public EntityModel<TagResponse> toModel(TagResponse response) {
        EntityModel<TagResponse> responseEntityModel = EntityModel.of(response);

        responseEntityModel.add(linkTo(methodOn(TagRestController.class)
                .getEntity(response.getId()))
                .withSelfRel());

        responseEntityModel.add(linkTo(methodOn(TagRestController.class)
                .getAllEntities(defaultPage, defaultLimit, "", defaultOrder))
                .withRel(IanaLinkRelations.COLLECTION));

        return responseEntityModel;
    }

    public CollectionModel<EntityModel<TagResponse>> toCollectionModel(Iterable<? extends TagResponse> responses) {
        List<EntityModel<TagResponse>> responseEntityModels = new ArrayList<>();

        responses.forEach(response -> responseEntityModels.add(toModel(response)));

        return CollectionModel.of(responseEntityModels);
    }

    public CollectionModel<EntityModel<TagResponse>> getCollectionModel(Iterable<? extends TagResponse> responses, PagingAndSorting ps) {
        CollectionModel<EntityModel<TagResponse>> responseCollectionModel = toCollectionModel(responses);

        responseCollectionModel.add(linkTo(methodOn(TagRestController.class)
                .getAllEntities(ps.getPage(), ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withSelfRel());

        long totalRecords = repository.count();

        if (ps.getPage() > 1)
            addPrevLink(responseCollectionModel, ps);
        if (totalRecords > (long) ps.getPage() * ps.getLimit())
            addNextLink(responseCollectionModel, ps);

        return responseCollectionModel;
    }

    public void addNextLink(CollectionModel<EntityModel<TagResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(TagRestController.class)
                .getAllEntities(ps.getPage() + 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.NEXT));
    }

    public void addPrevLink(CollectionModel<EntityModel<TagResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(TagRestController.class)
                .getAllEntities(ps.getPage() - 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.PREV));
    }
}
