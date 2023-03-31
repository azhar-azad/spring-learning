package com.azad.web.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface GenericApiRestController<REQ, RES> {

    ResponseEntity<EntityModel<RES>> createEntity(REQ request);
    ResponseEntity<CollectionModel<EntityModel<RES>>> getAllEntities(int page, int limit, String sort, String order);
    ResponseEntity<EntityModel<RES>> getEntity(Long id);
    ResponseEntity<EntityModel<RES>> updateEntity(Long id, REQ updatedRequest);
    ResponseEntity<?> deleteEntity(Long id);
}
