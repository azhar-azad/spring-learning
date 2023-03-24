package com.azad.playstoreapi.assemblers;

import com.azad.playstoreapi.controllers.PublisherController;
import com.azad.playstoreapi.models.responses.PublisherResponse;
import com.azad.playstoreapi.repos.PublisherRepository;
import com.azad.playstoreapi.utils.PagingAndSorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//@Component
public class PublisherModelAssembler implements RepresentationModelAssembler<PublisherResponse, EntityModel<PublisherResponse>> {

//    @Value("${default_page_number")
    private int defaultPage = 1;

//    @Value("${default_result_limit")
    private int defaultLimit = 25;

//    @Value("${default_sort_order")
    private String defaultOrder = "asc";

    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public EntityModel<PublisherResponse> toModel(PublisherResponse entity) {

        EntityModel<PublisherResponse> publisherResponseEntityModel = EntityModel.of(entity);

        publisherResponseEntityModel.add(linkTo(methodOn(
                PublisherController.class)
                .getPublisher(entity.getId()))
                .withSelfRel());
        publisherResponseEntityModel.add(linkTo(methodOn(
                PublisherController.class)
                .getAllPublishers(defaultPage, defaultLimit, "", defaultOrder))
                .withRel(IanaLinkRelations.COLLECTION));

        return null;
    }

    @Override
    public CollectionModel<EntityModel<PublisherResponse>> toCollectionModel(Iterable<? extends PublisherResponse> entities) {

        List<EntityModel<PublisherResponse>> publisherResponseEntityModels = new ArrayList<>();

        for (PublisherResponse entity: entities)
            publisherResponseEntityModels.add(toModel(entity));

        return CollectionModel.of(publisherResponseEntityModels);
    }

    public CollectionModel<EntityModel<PublisherResponse>> getCollectionModel(Iterable<? extends PublisherResponse> entities, PagingAndSorting ps) {

        if (entities == null) {
            System.out.println("======== entities are null");
        }

        CollectionModel<EntityModel<PublisherResponse>> collectionModel = toCollectionModel(entities);

        collectionModel.add(linkTo(methodOn(
                PublisherController.class)
                .getAllPublishers(ps.getPage(), ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withSelfRel());

        long totalRecords = publisherRepository.count();

        if (ps.getPage() > 1)
            addPrevLink(collectionModel, ps);
        if (totalRecords > (long) ps.getPage() * ps.getLimit())
            addNextLink(collectionModel, ps);

        return collectionModel;
    }

    private void addNextLink(CollectionModel<EntityModel<PublisherResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(
                PublisherController.class)
                .getAllPublishers(ps.getPage() + 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.NEXT));
    }

    private void addPrevLink(CollectionModel<EntityModel<PublisherResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(
                PublisherController.class)
                .getAllPublishers(ps.getPage() - 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.PREV));
    }
}
