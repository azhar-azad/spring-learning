package com.azad.moneymanagerapi.rest.assemblers;

import com.azad.moneymanagerapi.commons.PagingAndSorting;
import com.azad.moneymanagerapi.models.requests.TransactionRequest;
import com.azad.moneymanagerapi.models.responses.TransactionResponse;
import com.azad.moneymanagerapi.repositories.TransactionRepository;
import com.azad.moneymanagerapi.rest.controllers.TransactionRestController;
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
public class TransactionModelAssembler implements GenericApiResponseModelAssembler<TransactionResponse> {

    @Autowired
    private TransactionRepository repository;

    @Override
    public EntityModel<TransactionResponse> toModel(TransactionResponse response) {
        EntityModel<TransactionResponse> responseEntityModel = EntityModel.of(response);

        responseEntityModel.add(linkTo(methodOn(TransactionRestController.class)
                .getEntity(response.getId()))
                .withSelfRel());
        responseEntityModel.add(linkTo(methodOn(TransactionRestController.class)
                .updateEntity(response.getId(), new TransactionRequest()))
                .withRel("edit"));
        responseEntityModel.add(linkTo(methodOn(TransactionRestController.class)
                .deleteEntity(response.getId()))
                .withRel("remove"));
        responseEntityModel.add(linkTo(methodOn(TransactionRestController.class)
                .getAllEntities(defaultPage, defaultLimit, "", defaultOrder))
                .withRel(IanaLinkRelations.COLLECTION));

        return responseEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<TransactionResponse>> toCollectionModel(Iterable<? extends TransactionResponse> responses) {
        List<EntityModel<TransactionResponse>> responseEntityModels = new ArrayList<>();

        responses.forEach(response -> responseEntityModels.add(toModel(response)));

        return CollectionModel.of(responseEntityModels);
    }

    @Override
    public CollectionModel<EntityModel<TransactionResponse>> getCollectionModel(Iterable<? extends TransactionResponse> responses, PagingAndSorting ps) {
        CollectionModel<EntityModel<TransactionResponse>> responseCollectionModel = toCollectionModel(responses);

        responseCollectionModel.add(linkTo(methodOn(TransactionRestController.class)
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
    public void addNextLink(CollectionModel<EntityModel<TransactionResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(TransactionRestController.class)
                .getAllEntities(ps.getPage() + 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.NEXT));
    }

    @Override
    public void addPrevLink(CollectionModel<EntityModel<TransactionResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(TransactionRestController.class)
                .getAllEntities(ps.getPage() - 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.PREV));
    }
}
