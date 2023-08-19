package com.azad.moviepedia.common;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface ApiRestController<REQ, RES> {

    ResponseEntity<EntityModel<RES>> createEntity(REQ request);
    ResponseEntity<CollectionModel<EntityModel<RES>>> getAllEntity(int page, int limit, String sort, String order);
    ResponseEntity<EntityModel<RES>> getEntity(Long id);
    ResponseEntity<EntityModel<RES>> updateEntity(Long id, REQ updatedRequest);
    ResponseEntity<?> deleteEntity(Long id);
}
