package com.azad.web.assemblers;

import com.azad.common.PagingAndSorting;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public interface GenericApiResponseModelAssembler<T> extends RepresentationModelAssembler<T, EntityModel<T>> {

    EntityModel<T> toModel(T entity);
    CollectionModel<EntityModel<T>> toCollectionModel(Iterable<? extends T> entities);
    CollectionModel<EntityModel<T>> getCollectionModel(Iterable<? extends T> entities, PagingAndSorting ps);
    void addNextLink(CollectionModel<EntityModel<T>> collectionModel, PagingAndSorting ps);
    void addPrevLink(CollectionModel<EntityModel<T>> collectionModel, PagingAndSorting ps);
}
