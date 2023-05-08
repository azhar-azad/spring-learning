package com.azad.ecommerce.inventoryservice.assemblers;

import com.azad.ecommerce.inventoryservice.apis.StoreRestController;
import com.azad.ecommerce.inventoryservice.commons.PagingAndSorting;
import com.azad.ecommerce.inventoryservice.models.requests.StoreRequest;
import com.azad.ecommerce.inventoryservice.models.responses.StoreResponse;
import com.azad.ecommerce.inventoryservice.repositories.StoreRepository;
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
public class StoreResponseModelAssembler implements GenericApiResponseModelAssembler<StoreResponse> {
    @Autowired
    private StoreRepository repository;

    @Override
    public EntityModel<StoreResponse> toModel(StoreResponse response) {
        EntityModel<StoreResponse> responseEntityModel = EntityModel.of(response);

        responseEntityModel.add(linkTo(methodOn(StoreRestController.class)
                .getEntity(response.getId()))
                .withSelfRel());
        responseEntityModel.add(linkTo(methodOn(StoreRestController.class)
                .updateEntity(response.getId(), new StoreRequest()))
                .withRel("edit"));
        responseEntityModel.add(linkTo(methodOn(StoreRestController.class)
                .deleteEntity(response.getId()))
                .withRel("remove"));
        responseEntityModel.add(linkTo(methodOn(StoreRestController.class)
                .getAllEntities(defaultPage, defaultLimit, "", defaultOrder))
                .withRel(IanaLinkRelations.COLLECTION));

        return responseEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<StoreResponse>> toCollectionModel(Iterable<? extends StoreResponse> responses) {
        List<EntityModel<StoreResponse>> responseEntityModels = new ArrayList<>();

        responses.forEach(response -> responseEntityModels.add(toModel(response)));

        return CollectionModel.of(responseEntityModels);
    }

    @Override
    public CollectionModel<EntityModel<StoreResponse>> getCollectionModel(Iterable<? extends StoreResponse> responses, PagingAndSorting ps) {
        CollectionModel<EntityModel<StoreResponse>> responseCollectionModel = toCollectionModel(responses);

        responseCollectionModel.add(linkTo(methodOn(StoreRestController.class)
                .getAllEntities(ps.getPage(), ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withSelfRel());

        long totalRecords = repository.count();

        if (ps.getPage() > 1)
            addPrevLink(responseCollectionModel, ps);
        if (totalRecords > (long) ps.getPage() * ps.getLimit())
            addNextLink(responseCollectionModel, ps);

        return responseCollectionModel;
    }

    @Override
    public void addNextLink(CollectionModel<EntityModel<StoreResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(StoreRestController.class)
                .getAllEntities(ps.getPage() + 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.NEXT));
    }

    @Override
    public void addPrevLink(CollectionModel<EntityModel<StoreResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(StoreRestController.class)
                .getAllEntities(ps.getPage() - 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.PREV));
    }
}
