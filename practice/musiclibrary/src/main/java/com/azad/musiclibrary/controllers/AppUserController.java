package com.azad.musiclibrary.controllers;

import com.azad.musiclibrary.models.dtos.AppUserDto;
import com.azad.musiclibrary.exceptions.InvalidPathVariableException;
import com.azad.musiclibrary.exceptions.RequestBodyEmptyException;
import com.azad.musiclibrary.models.requests.AppUserRequest;
import com.azad.musiclibrary.models.responses.AppUserResponse;
import com.azad.musiclibrary.services.AppUserService;
import com.azad.musiclibrary.utils.AppUtils;
import com.azad.musiclibrary.utils.PagingAndSortingObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users")
public class AppUserController {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ModelMapper modelMapper;

    private AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping
    public ResponseEntity<AppUserResponse> createAppUser(@Valid @RequestBody AppUserRequest appUserRequest) {

        appUtils.printControllerMethodInfo("POST", "/api/v1/users", "createAppUser");

        if (appUserRequest == null) {
            throw new RequestBodyEmptyException("Request body is empty for entity AppUser");
        }

        AppUserDto appUserDto = appUserService.create(modelMapper.map(appUserRequest, AppUserDto.class));

        return new ResponseEntity<>(modelMapper.map(appUserDto, AppUserResponse.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AppUserResponse>> getAllAppUsers(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        appUtils.printControllerMethodInfo("GET", "/api/v1/users", "getAllAppUsers");

        if (page > 0) page--;

        List<AppUserDto> appUserDtos = appUserService.getAll(new PagingAndSortingObject(page, limit, sort, order));

        if (appUserDtos == null || appUserDtos.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<AppUserResponse> appUserResponses = new ArrayList<>();
        appUserDtos.forEach(dto -> appUserResponses.add(modelMapper.map(dto, AppUserResponse.class)));

        return new ResponseEntity<>(appUserResponses, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AppUserResponse> getAppUserById(@Valid @PathVariable Long id) {

        appUtils.printControllerMethodInfo("GET", "/api/v1/users/"+id, "getAppUserById");

        if (id < 1) {
            throw new InvalidPathVariableException("Invalid resource id value for the entity AppUser");
        }

        AppUserDto appUserDto = appUserService.getById(id);
        if (appUserDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(modelMapper.map(appUserDto, AppUserResponse.class), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AppUserResponse> updateAppUserById(@Valid @PathVariable Long id, @RequestBody AppUserRequest updatedRequest) {

        appUtils.printControllerMethodInfo("PUT", "/api/v1/users/"+id, "updateAppUserById");

        if (id < 1) {
            throw new InvalidPathVariableException("Invalid resource id value for the entity AppUser");
        }

        if (updatedRequest == null) {
            throw new RequestBodyEmptyException("Request body is empty for entity AppUser");
        }

        AppUserDto updatedAppUserDto = appUserService.updateById(id, modelMapper.map(updatedRequest, AppUserDto.class));

        return new ResponseEntity<>(modelMapper.map(updatedAppUserDto, AppUserResponse.class), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteAppUserById(@Valid @PathVariable Long id) {

        appUtils.printControllerMethodInfo("PUT", "/api/v1/users/"+id, "deleteAppUserById");

        if (id < 1) {
            throw new InvalidPathVariableException("Invalid resource id value for the entity AppUser");
        }

        appUserService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
