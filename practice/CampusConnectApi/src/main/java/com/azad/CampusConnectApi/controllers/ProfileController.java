package com.azad.CampusConnectApi.controllers;

import com.azad.CampusConnectApi.exceptions.InvalidPathVariableException;
import com.azad.CampusConnectApi.exceptions.RequestBodyEmptyException;
import com.azad.CampusConnectApi.models.dtos.ProfileDto;
import com.azad.CampusConnectApi.models.requests.ProfileRequest;
import com.azad.CampusConnectApi.models.responses.ProfileResponse;
import com.azad.CampusConnectApi.services.ProfileService;
import com.azad.CampusConnectApi.utils.AppUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/appUsers/{appUserId}/profiles")
public class ProfileController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    private ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<ProfileResponse> createProfile(@Valid @PathVariable Long appUserId, @Valid @RequestBody ProfileRequest request) {

        appUtils.printControllerMethodInfo("POST", "/api/v1/appUsers/"+ appUserId +"/profiles", "createProfile");

        if (appUserId < 1) {
            throw new InvalidPathVariableException("Invalid resource id value for the entity AppUser");
        }

        if (request == null) {
            throw new RequestBodyEmptyException("Request body is empty for entity Profile");
        }

        ProfileDto dto = modelMapper.map(request, ProfileDto.class);
        dto.setAppUserId(appUserId);

        ProfileDto savedProfileDto = profileService.create(dto);

        return new ResponseEntity<>(modelMapper.map(savedProfileDto, ProfileResponse.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile(@Valid @PathVariable Long appUserId) {

        appUtils.printControllerMethodInfo("GET", "/api/v1/appUsers/"+ appUserId +"/profiles/", "getProfile");

        if (appUserId < 1) {
            throw new InvalidPathVariableException("Invalid resource id value for the entity AppUser");
        }

        ProfileDto dto = profileService.getProfileByAppUserId(appUserId);

        return new ResponseEntity<>(modelMapper.map(dto, ProfileResponse.class), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ProfileResponse> updateProfile(@Valid @PathVariable Long appUserId, @RequestBody ProfileRequest updatedRequest) {

        appUtils.printControllerMethodInfo("PUT", "/api/v1/appUsers/"+ appUserId +"/profiles/", "updateProfile");

        if (appUserId < 1) {
            throw new InvalidPathVariableException("Invalid resource id value for the entity AppUser");
        }

        if (updatedRequest == null) {
            throw new RequestBodyEmptyException("Request body is empty for entity Profile");
        }

        ProfileDto dto = modelMapper.map(updatedRequest, ProfileDto.class);
        dto.setAppUserId(appUserId);

        ProfileDto updatedProfileDto = profileService.updateProfileByAppUserId(appUserId, dto);

        return new ResponseEntity<>(modelMapper.map(updatedProfileDto, ProfileResponse.class), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProfile(@Valid @PathVariable Long appUserId) {

        appUtils.printControllerMethodInfo("DELETE", "/api/v1/"+ appUserId +"/profiles/", "deleteProfile");

        if (appUserId < 1) {
            throw new InvalidPathVariableException("Invalid resource id value for the entity AppUser");
        }

        profileService.deleteProfileByAppUserId(appUserId);

        return ResponseEntity.noContent().build();
    }
}
