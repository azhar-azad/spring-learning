package com.azad.web.assemblers;

import com.azad.common.PagingAndSorting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public interface GenericApiResponseModelAssembler<T> extends RepresentationModelAssembler<T, EntityModel<T>> {

    @Value("${default_page_number}")
    int defaultPage = 0;
    @Value("${default_result_limit}")
    int defaultLimit = 25;
    @Value("${default_sort_order}")
    String defaultOrder = "asc";

    EntityModel<T> toModel(T entity);
    CollectionModel<EntityModel<T>> toCollectionModel(Iterable<? extends T> entities);
    CollectionModel<EntityModel<T>> getCollectionModel(Iterable<? extends T> entities, PagingAndSorting ps);
    void addNextLink(CollectionModel<EntityModel<T>> collectionModel, PagingAndSorting ps);
    void addPrevLink(CollectionModel<EntityModel<T>> collectionModel, PagingAndSorting ps);
}
