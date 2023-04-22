package com.azad.moneymanagerapi.rest.assemblers;

import com.azad.moneymanagerapi.commons.PagingAndSorting;
import com.azad.moneymanagerapi.models.requests.AccountGroupRequest;
import com.azad.moneymanagerapi.models.requests.AccountRequest;
import com.azad.moneymanagerapi.models.responses.AccountResponse;
import com.azad.moneymanagerapi.repositories.AccountRepository;
import com.azad.moneymanagerapi.rest.controllers.AccountGroupRestController;
import com.azad.moneymanagerapi.rest.controllers.AccountRestController;
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
public class AccountModelAssembler implements GenericApiResponseModelAssembler<AccountResponse> {

    @Autowired
    private AccountRepository repository;

    @Override
    public EntityModel<AccountResponse> toModel(AccountResponse response) {
        EntityModel<AccountResponse> responseEntityModel = EntityModel.of(response);

        responseEntityModel.add(linkTo(methodOn(AccountRestController.class)
                .getEntity(response.getId()))
                .withSelfRel());
        responseEntityModel.add(linkTo(methodOn(AccountRestController.class)
                .updateEntity(response.getId(), new AccountRequest()))
                .withRel("edit"));
        responseEntityModel.add(linkTo(methodOn(AccountRestController.class)
                .deleteEntity(response.getId()))
                .withRel("remove"));
        responseEntityModel.add(linkTo(methodOn(AccountRestController.class)
                .getAllEntities(defaultPage, defaultLimit, "", defaultOrder))
                .withRel(IanaLinkRelations.COLLECTION));

        return responseEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<AccountResponse>> toCollectionModel(Iterable<? extends AccountResponse> responses) {
        List<EntityModel<AccountResponse>> responseEntityModels = new ArrayList<>();

        responses.forEach(response -> responseEntityModels.add(toModel(response)));

        return CollectionModel.of(responseEntityModels);
    }

    @Override
    public CollectionModel<EntityModel<AccountResponse>> getCollectionModel(Iterable<? extends AccountResponse> responses, PagingAndSorting ps) {
        CollectionModel<EntityModel<AccountResponse>> responseCollectionModel = toCollectionModel(responses);

        responseCollectionModel.add(linkTo(methodOn(AccountRestController.class)
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
    public void addNextLink(CollectionModel<EntityModel<AccountResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(AccountRestController.class)
                .getAllEntities(ps.getPage() + 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.NEXT));
    }

    @Override
    public void addPrevLink(CollectionModel<EntityModel<AccountResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(AccountRestController.class)
                .getAllEntities(ps.getPage() - 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.PREV));
    }
}
