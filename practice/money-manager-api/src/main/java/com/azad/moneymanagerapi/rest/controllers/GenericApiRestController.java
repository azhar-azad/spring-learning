package com.azad.moneymanagerapi.rest.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

/***
 * A generic rest controller interface to resolve CRUD API requests.
 * Implementing classes must follow the principle of HATEOAS.
 *
 * @param <Q> Entity Request
 * @param <S> Entity Response
 */
public interface GenericApiRestController<Q, S> {

    ResponseEntity<EntityModel<S>> createEntity(Q request);
    ResponseEntity<CollectionModel<EntityModel<S>>> getAllEntities(int page, int limit, String sort, String order);
    ResponseEntity<EntityModel<S>> getEntity(Long id);
    ResponseEntity<EntityModel<S>> updateEntity(Long id, Q updatedRequest);
    ResponseEntity<?> deleteEntity(Long id);
}
