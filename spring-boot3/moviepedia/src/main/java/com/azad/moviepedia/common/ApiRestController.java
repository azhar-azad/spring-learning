package com.azad.moviepedia.common;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ApiRestController<DTO> {

    ResponseEntity<EntityModel<DTO>> createEntity(DTO request);
    ResponseEntity<CollectionModel<EntityModel<DTO>>> getAllEntity(Map<String, String> params);
    ResponseEntity<EntityModel<DTO>> getEntity(Long id);
    ResponseEntity<EntityModel<DTO>> updateEntity(Long id, DTO updatedRequest);
    ResponseEntity<?> deleteEntity(Long id);
}
