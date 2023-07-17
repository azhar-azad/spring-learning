package com.azad.hosteldiningapi.api.assembers;

import com.azad.hosteldiningapi.api.resources.ItemRestResource;
import com.azad.hosteldiningapi.common.PagingAndSorting;
import com.azad.hosteldiningapi.common.generics.GenericApiResponseModelAssembler;
import com.azad.hosteldiningapi.models.item.ItemRequest;
import com.azad.hosteldiningapi.models.item.ItemResponse;
import com.azad.hosteldiningapi.repositories.ItemRepository;
import jakarta.validation.constraints.NotNull;
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
public class ItemResponseModelAssembler implements GenericApiResponseModelAssembler<ItemResponse> {

    @Autowired
    private ItemRepository repository;

    @Override
    public @NotNull EntityModel<ItemResponse> toModel(@NotNull ItemResponse response) {
        EntityModel<ItemResponse> responseEntityModel = EntityModel.of(response);

        if (response.getError() != null) {
            return responseEntityModel;
        }

        responseEntityModel.add(linkTo(methodOn(ItemRestResource.class)
                .getEntity(response.getId()))
                .withSelfRel());
        responseEntityModel.add(linkTo(methodOn(ItemRestResource.class)
                .updateEntity(response.getId(), new ItemRequest()))
                .withRel("edit"));
        responseEntityModel.add(linkTo(methodOn(ItemRestResource.class)
                .deleteEntity(response.getId()))
                .withRel("remove"));
        responseEntityModel.add(linkTo(methodOn(ItemRestResource.class)
                .getAllEntities(defaultPage, defaultLimit, "", defaultOrder))
                .withRel(IanaLinkRelations.COLLECTION));

        return responseEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<ItemResponse>> toCollectionModel(Iterable<? extends ItemResponse> responses) {
        List<EntityModel<ItemResponse>> responseEntityModels = new ArrayList<>();

        responses.forEach(response -> responseEntityModels.add(toModel(response)));

        return CollectionModel.of(responseEntityModels);
    }

    @Override
    public CollectionModel<EntityModel<ItemResponse>> getCollectionModel(Iterable<? extends ItemResponse> responses, PagingAndSorting ps) {
        CollectionModel<EntityModel<ItemResponse>> responseCollectionModel = toCollectionModel(responses);

        responseCollectionModel.add(linkTo(methodOn(ItemRestResource.class)
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
    public void addNextLink(CollectionModel<EntityModel<ItemResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(ItemRestResource.class)
                .getAllEntities(ps.getPage() + 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.NEXT));
    }

    @Override
    public void addPrevLink(CollectionModel<EntityModel<ItemResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(ItemRestResource.class)
                .getAllEntities(ps.getPage() - 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.PREV));
    }
}
