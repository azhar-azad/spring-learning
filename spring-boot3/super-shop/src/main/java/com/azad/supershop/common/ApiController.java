package com.azad.supershop.common;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ApiController<REQ, RES> {

    ResponseEntity<RES> createEntity(REQ request);
    ResponseEntity<List<RES>> getAllEntity(int page, int limit, String sort, String order);
    ResponseEntity<RES> getEntity(Long id);
    ResponseEntity<RES> updateEntity(Long id, REQ updatedRequest);
    ResponseEntity<?> deleteEntity(Long id);
}
