package com.azad.ecommerce.controllers;

import com.azad.ecommerce.assemblers.UserProfileModelAssembler;
import com.azad.ecommerce.models.UserProfile;
import com.azad.ecommerce.models.dtos.UserProfileDto;
import com.azad.ecommerce.models.responses.UserProfileResponse;
import com.azad.ecommerce.services.UserProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/profiles")
public class UserProfileRestController {

    @Autowired
    private ModelMapper modelMapper;

    private final UserProfileService service;
    private final UserProfileModelAssembler assembler;

    @Autowired
    public UserProfileRestController(UserProfileService service, UserProfileModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    /**
     * @Path: /api/v1/profiles/me
     * @Method: POST
     * @Desc: Logged-in user can create his/her profile
     * @Access: ADMIN, CUSTOMER, SUPPLIER
     * */
    @PostMapping(path = "/me")
    public ResponseEntity<EntityModel<UserProfileResponse>> createProfile(@Valid @RequestBody UserProfile userProfile) {

        UserProfileDto userProfileDto = modelMapper.map(userProfile, UserProfileDto.class);

        UserProfileDto savedUserProfile = service.create(userProfileDto);

        UserProfileResponse userProfileResponse = modelMapper.map(savedUserProfile, UserProfileResponse.class);
        EntityModel<UserProfileResponse> entityModel = assembler.toModel(userProfileResponse);

        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    /**
     * @Path: /api/v1/profiles/me
     * @Method: GET
     * @Desc: Logged-in user can get his/her profile
     * @Access: ADMIN, CUSTOMER, SUPPLIER
     * */
    @GetMapping(path = "/me")
    public ResponseEntity<EntityModel<UserProfileResponse>> getProfile() {
        return null;
    }

    /**
     * @Path: /api/v1/profiles/me
     * @Method: PUT
     * @Desc: Logged-in user can update his/her profile
     * @Access: ADMIN, CUSTOMER, SUPPLIER
     * */
    @PutMapping(path = "/me")
    public ResponseEntity<EntityModel<UserProfileResponse>> updateProfile(@RequestBody UserProfile updatedUserProfile) {
        return null;
    }

    /**
     * @Path: /api/v1/profiles/me
     * @Method: DELETE
     * @Desc: Logged-in user can delete his/her profile
     * @Access: ADMIN, CUSTOMER, SUPPLIER
     * */
    @DeleteMapping(path = "/me")
    public ResponseEntity<EntityModel<UserProfileResponse>> deleteProfile() {
        return null;
    }

    /**
     * =============================================
     * Only Admin can access the following endpoints
     * =============================================
     * */

    /**
     * @Path: /api/v1/profiles
     * @Method: GET
     * @Desc: Logged-in admin can get all profiles registered in the system
     * @Access: ADMIN
     * */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserProfileResponse>>> getAllProfiles(Map<String, String> reqParams) {
        return null;
    }

    /**
     * @Path: /api/v1/profiles
     * @Method: GET
     * @Desc: Logged-in admin can get any profile by id
     * @Access: ADMIN
     * */
    @GetMapping(path = "/{id}")
    public ResponseEntity<EntityModel<UserProfileResponse>> getProfileById(@Valid @PathVariable("id") Long id) {
        return null;
    }

    /**
     * @Path: /api/v1/profiles
     * @Method: PUT
     * @Desc: Logged-in admin can update any profile by id
     * @Access: ADMIN
     * */
    @PutMapping(path = "/{id}")
    public ResponseEntity<EntityModel<UserProfileResponse>> updateProfile(@Valid @PathVariable("id") Long id, @RequestBody UserProfile updatedUserProfile) {
        return null;
    }

    /**
     * @Path: /api/v1/profiles
     * @Method: DELETE
     * @Desc: Logged-in admin can delete any profile by id
     * @Access: ADMIN
     * */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<EntityModel<UserProfileResponse>> deleteProfile(@Valid @PathVariable("id") Long id) {
        return null;
    }
}
