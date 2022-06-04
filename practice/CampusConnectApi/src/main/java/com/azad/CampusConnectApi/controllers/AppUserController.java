package com.azad.CampusConnectApi.controllers;

import com.azad.CampusConnectApi.exceptions.InvalidPathVariableException;
import com.azad.CampusConnectApi.exceptions.RequestBodyEmptyException;
import com.azad.CampusConnectApi.models.dtos.AppUserDto;
import com.azad.CampusConnectApi.models.requests.AppUserRequest;
import com.azad.CampusConnectApi.models.responses.AppUserResponse;
import com.azad.CampusConnectApi.services.AppUserService;
import com.azad.CampusConnectApi.utils.AppUtils;
import com.azad.CampusConnectApi.utils.PagingAndSortingObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/appUsers")
public class AppUserController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    private AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping
    public ResponseEntity<AppUserResponse> createAppUser(@Valid @RequestBody AppUserRequest request) {

        appUtils.printControllerMethodInfo("POST", "/api/v1/appUsers", "createAppUser");

        if (request == null) {
            throw new RequestBodyEmptyException("Request body is empty for entity AppUser");
        }

        AppUserDto dto = appUserService.create(modelMapper.map(request, AppUserDto.class));

        return new ResponseEntity<>(modelMapper.map(dto, AppUserResponse.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AppUserResponse>> getAllAppUsers(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        appUtils.printControllerMethodInfo("GET", "/api/v1/appUsers", "getAllAppUsers");

        if (page > 0) page--;

        List<AppUserDto> dtos = appUserService.getAll(new PagingAndSortingObject(page, limit, sort, order));

        if (dtos == null || dtos.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<AppUserResponse> responses = new ArrayList<>();
        dtos.forEach(dto -> responses.add(modelMapper.map(dto, AppUserResponse.class)));

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AppUserResponse> getAppUserById(@Valid @PathVariable Long id) {

        appUtils.printControllerMethodInfo("GET", "/api/v1/appUsers/"+ id, "getAppUserById");

        if (id < 1) {
            throw new InvalidPathVariableException("Invalid resource id value for the entity AppUser.");
        }

        AppUserDto dto = appUserService.getById(id);
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(modelMapper.map(dto, AppUserResponse.class), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AppUserResponse> updateAppUserById(@Valid @PathVariable Long id, @RequestBody AppUserRequest updatedRequest) {

        appUtils.printControllerMethodInfo("PUT", "/api/v1/appUsers/"+ id, "updateAppUserById");

        if (id < 1) {
            throw new InvalidPathVariableException("Invalid resource id value for the entity AppUser.");
        }

        if (updatedRequest == null) {
            throw new RequestBodyEmptyException("Request body is empty for entity AppUser");
        }

        return new ResponseEntity<>(
                modelMapper.map(
                        appUserService.updateById(id, modelMapper.map(updatedRequest, AppUserDto.class)),
                        AppUserResponse.class),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteAppUserById(@Valid @PathVariable Long id) {

        appUtils.printControllerMethodInfo("DELETE", "/api/v1/appUsers/"+ id, "deleteAppUserById");

        if (id < 1) {
            throw new InvalidPathVariableException("Invalid resource id value for the entity AppUser.");
        }

        appUserService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
