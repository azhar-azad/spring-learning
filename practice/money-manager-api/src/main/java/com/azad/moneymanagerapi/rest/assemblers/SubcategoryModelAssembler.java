package com.azad.moneymanagerapi.rest.assemblers;

import com.azad.moneymanagerapi.commons.AppUtils;
import com.azad.moneymanagerapi.commons.PagingAndSorting;
import com.azad.moneymanagerapi.models.entities.SubcategoryEntity;
import com.azad.moneymanagerapi.models.requests.SubcategoryRequest;
import com.azad.moneymanagerapi.models.responses.SubcategoryResponse;
import com.azad.moneymanagerapi.repositories.SubcategoryRepository;
import com.azad.moneymanagerapi.rest.controllers.CategoryRestController;
import com.azad.moneymanagerapi.rest.controllers.SubcategoryRestController;
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
public class SubcategoryModelAssembler implements GenericApiResponseModelAssembler<SubcategoryResponse> {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private SubcategoryRepository repository;

    @Override
    public EntityModel<SubcategoryResponse> toModel(SubcategoryResponse response) {
        EntityModel<SubcategoryResponse> responseEntityModel = EntityModel.of(response);

        responseEntityModel.add(linkTo(methodOn(SubcategoryRestController.class)
                .getEntity(response.getId()))
                .withSelfRel());
        responseEntityModel.add(linkTo(methodOn(SubcategoryRestController.class)
                .updateEntity(response.getId(), new SubcategoryRequest()))
                .withRel("edit"));
        responseEntityModel.add(linkTo(methodOn(SubcategoryRestController.class)
                .deleteEntity(response.getId()))
                .withRel("remove"));
        responseEntityModel.add(linkTo(methodOn(SubcategoryRestController.class)
                .getAllEntities(defaultPage, defaultLimit, "", defaultOrder))
                .withRel(IanaLinkRelations.COLLECTION));

        return responseEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<SubcategoryResponse>> toCollectionModel(Iterable<? extends SubcategoryResponse> responses) {
        List<EntityModel<SubcategoryResponse>> responseEntityModels = new ArrayList<>();

        responses.forEach(response -> responseEntityModels.add(toModel(response)));

        return CollectionModel.of(responseEntityModels);
    }

    @Override
    public CollectionModel<EntityModel<SubcategoryResponse>> getCollectionModel(Iterable<? extends SubcategoryResponse> responses, PagingAndSorting ps) {

        CollectionModel<EntityModel<SubcategoryResponse>> responseCollectionModel = toCollectionModel(responses);

        responseCollectionModel.add(linkTo(methodOn(SubcategoryRestController.class)
                .getAllEntities(ps.getPage(), ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withSelfRel());

        long totalRecords = repository.count();

        if (ps.getPage() > 1)
            addPrevLink(responseCollectionModel, ps);
        if (totalRecords > (long) ps.getPage() * ps.getLimit())
            addNextLink(responseCollectionModel, ps);

        return responseCollectionModel;
    }

    public CollectionModel<EntityModel<SubcategoryResponse>> getCollectionModelForCategory(
            Long categoryId, Iterable<? extends SubcategoryResponse> responses, PagingAndSorting ps) {

        CollectionModel<EntityModel<SubcategoryResponse>> responseCollectionModel = toCollectionModel(responses);

        responseCollectionModel.add(linkTo(methodOn(CategoryRestController.class)
                .getAllSubcategoriesByCategory(categoryId, ps.getPage(), ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withSelfRel());
        responseCollectionModel.add(linkTo(methodOn(SubcategoryRestController.class)
                .getAllEntities(ps.getPage(), ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel("all"));

        long totalRecords = repository.count();

        if (ps.getPage() > 1)
            addPrevLink(responseCollectionModel, ps);
        if (totalRecords > (long) ps.getPage() * ps.getLimit())
            addNextLink(responseCollectionModel, ps);

        return responseCollectionModel;
    }

    @Override
    public void addNextLink(CollectionModel<EntityModel<SubcategoryResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(SubcategoryRestController.class)
                .getAllEntities(ps.getPage() + 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.NEXT));
    }

    @Override
    public void addPrevLink(CollectionModel<EntityModel<SubcategoryResponse>> collectionModel, PagingAndSorting ps) {
        collectionModel.add(linkTo(methodOn(SubcategoryRestController.class)
                .getAllEntities(ps.getPage() - 1, ps.getLimit(), ps.getSort(), ps.getOrder()))
                .withRel(IanaLinkRelations.PREV));
    }
}
