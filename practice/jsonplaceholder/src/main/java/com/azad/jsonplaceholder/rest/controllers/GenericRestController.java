package com.azad.jsonplaceholder.rest.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface GenericRestController<T> {

    ResponseEntity<CollectionModel<EntityModel<T>>> getAllEntities(int page, int limit, String sort, String order);

    ResponseEntity<EntityModel<T>> getEntity(Long id);
}
