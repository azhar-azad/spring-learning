package com.azad.tennis_score_api.common;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ApiRestController<DTO> {

    ResponseEntity<EntityModel<DTO>> create(DTO request);
    ResponseEntity<CollectionModel<EntityModel<DTO>>> getAll(Map<String, String> params);
    ResponseEntity<EntityModel<DTO>> get(Long id);
    ResponseEntity<EntityModel<DTO>> update(Long id, DTO updatedRequest);
    ResponseEntity<?> delete(Long id);
}
