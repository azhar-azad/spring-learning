package com.azad.moneymanagerapi.rest.assemblers;

import com.azad.moneymanagerapi.commons.PagingAndSorting;
import com.azad.moneymanagerapi.models.responses.AccountGroupResponse;
import com.azad.moneymanagerapi.repositories.AccountGroupRepository;
import com.azad.moneymanagerapi.rest.controllers.AccountGroupRestController;
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
public class AccountGroupModelAssembler implements GenericApiResponseModelAssembler<AccountGroupResponse> {

    @Autowired
    private AccountGroupRepository repository;

    @Override
    public EntityModel<AccountGroupResponse> toModel(AccountGroupResponse response) {
        EntityModel<AccountGroupResponse> responseEntityModel = EntityModel.of(response);

        responseEntityModel.add(linkTo(methodOn(AccountGroupRestController.class)
                .getEntity(response.getId()))
                .withSelfRel());
        responseEntityModel.add(linkTo(methodOn(AccountGroupRestController.class)
                .getAllEntities(defaultPage, defaultLimit, "", defaultOrder))
                .withRel(IanaLinkRelations.COLLECTION));

        return responseEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<AccountGroupResponse>> toCollectionModel(Iterable<? extends AccountGroupResponse> responses) {
        List<EntityModel<AccountGroupResponse>> responseEntityModels = new ArrayList<>();

        responses.forEach(response -> responseEntityModels.add(toModel(response)));

        return CollectionModel.of(responseEntityModels);
    }

    @Override
    public CollectionModel<EntityModel<AccountGroupResponse>> getCollectionModel(Iterable<? extends AccountGroupResponse> responses, PagingAndSorting ps) {
        CollectionModel<EntityModel<AccountGroupResponse>> responseCollectionModel = toCollectionModel(responses);

        responseCollectionModel.add(linkTo(methodOn(AccountGroupRestController.class)
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
    public void addNextLink(CollectionModel<EntityModel<AccountGroupResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(AccountGroupRestController.class)
                .getAllEntities(ps.getPage() + 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.NEXT));
    }

    @Override
    public void addPrevLink(CollectionModel<EntityModel<AccountGroupResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(AccountGroupRestController.class)
                .getAllEntities(ps.getPage() - 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.PREV));
    }
}
