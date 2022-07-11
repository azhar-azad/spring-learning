package com.azad.onlineed.admin.controllers;

import com.azad.onlineed.admin.services.AuthorityService;
import com.azad.onlineed.models.Authority;
import com.azad.onlineed.models.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Path;
import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping(path = "/api/admin/authorities")
public class AuthorityController {

    private final AuthorityService authorityService;

    @Autowired
    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createAuthority(@Valid @RequestBody Authority request) {

        Authority authority = authorityService.create(request);

        return new ResponseEntity<>(new ApiResponse(true, "Authority Created",
                Collections.singletonMap("authorities", Collections.singletonList(authority))),
                HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> getAuthority(@Valid @PathVariable Integer id) {

        Authority authority = authorityService.getById(id);

        return new ResponseEntity<>(new ApiResponse(true, "Authority Fetched",
                Collections.singletonMap("authorities", Collections.singletonList(authority))),
                HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> updateAuthority(@Valid @PathVariable Integer id, @RequestBody Authority updatedRequest) {

        Authority authority = authorityService.updateById(id, updatedRequest);

        return new ResponseEntity<>(new ApiResponse(true, "Authority Updated",
                Collections.singletonMap("authorities", Collections.singletonList(authority))),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteAuthority(@Valid @PathVariable Integer id) {

        authorityService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
