package com.azad.onlinecourse.common.generics;

import com.azad.onlinecourse.common.PagingAndSorting;
import jakarta.validation.constraints.NotNull;
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

    @Override
    @NotNull
    EntityModel<T> toModel(@NotNull T response);

    @Override
    CollectionModel<EntityModel<T>> toCollectionModel(Iterable<? extends T> responses);

    CollectionModel<EntityModel<T>> getCollectionModel(Iterable<? extends T> responses, PagingAndSorting ps);

    void addNextLink(CollectionModel<EntityModel<T>> collectionModel, PagingAndSorting ps);

    void addPrevLink(CollectionModel<EntityModel<T>> collectionModel, PagingAndSorting ps);
}
