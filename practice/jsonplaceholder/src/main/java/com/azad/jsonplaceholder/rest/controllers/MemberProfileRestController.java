package com.azad.jsonplaceholder.rest.controllers;

import com.azad.jsonplaceholder.models.MemberProfile;
import com.azad.jsonplaceholder.models.dtos.MemberProfileDto;
import com.azad.jsonplaceholder.models.responses.MemberProfileResponse;
import com.azad.jsonplaceholder.rest.assemblers.MemberProfileModelAssembler;
import com.azad.jsonplaceholder.security.auth.api.AuthService;
import com.azad.jsonplaceholder.services.MemberProfileService;
import com.azad.jsonplaceholder.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/profiles")
public class MemberProfileRestController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthService authService;

    private final MemberProfileService memberProfileService;
    private final MemberProfileModelAssembler memberProfileModelAssembler;

    @Autowired
    public MemberProfileRestController(MemberProfileService memberProfileService, MemberProfileModelAssembler memberProfileModelAssembler) {
        this.memberProfileService = memberProfileService;
        this.memberProfileModelAssembler = memberProfileModelAssembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<MemberProfileResponse>> createMemberProfile(@Valid @RequestBody MemberProfile memberProfile) {

        MemberProfileDto memberProfileDto = modelMapper.map(memberProfile, MemberProfileDto.class);

        MemberProfileDto savedMemberProfileDto = memberProfileService.create(memberProfileDto);

        MemberProfileResponse memberProfileResponse = modelMapper.map(savedMemberProfileDto, MemberProfileResponse.class);
        EntityModel<MemberProfileResponse> memberProfileResponseEntityModel = memberProfileModelAssembler.toModel(memberProfileResponse);

        return new ResponseEntity<>(memberProfileResponseEntityModel, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<MemberProfileResponse>>> getAllMemberProfiles(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (!authService.loggedInUserIsAdmin()) {
            throw new RuntimeException("Only admins can access this api");
        }

        if (page < 0) page = 0;

        List<MemberProfileDto> allMemberProfilesFromService = memberProfileService.getAll(
                new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        if (allMemberProfilesFromService == null || allMemberProfilesFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<MemberProfileResponse> memberProfileResponses = allMemberProfilesFromService.stream()
                .map(memberProfileDto -> modelMapper.map(memberProfileDto, MemberProfileResponse.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<MemberProfileResponse>> collectionModel =
                memberProfileModelAssembler.getCollectionModel(memberProfileResponses, new PagingAndSorting(page, limit, sort, order));

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EntityModel<MemberProfileResponse>> getMemberProfile(@Valid @PathVariable("id") Long id) {

        if (!authService.loggedInUserIsAdmin()) {
            throw new RuntimeException("Only admins can access this api");
        }

        MemberProfileDto memberProfileFromService = memberProfileService.getById(id);
        if (memberProfileFromService == null) {
            return ResponseEntity.notFound().build();
        }

        MemberProfileResponse memberProfileResponse = modelMapper.map(memberProfileFromService, MemberProfileResponse.class);
        EntityModel<MemberProfileResponse> memberProfileResponseEntityModel = memberProfileModelAssembler.toModel(memberProfileResponse);

        return new ResponseEntity<>(memberProfileResponseEntityModel, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<EntityModel<MemberProfileResponse>> updateMemberProfile(
            @Valid @PathVariable("id") Long id, @RequestBody MemberProfile memberProfile) {

        if (!authService.loggedInUserIsAdmin()) {
            throw new RuntimeException("Only admins can access this api");
        }

        MemberProfileDto memberProfileFromService = memberProfileService.updateById(id, modelMapper.map(memberProfile, MemberProfileDto.class));

        MemberProfileResponse memberProfileResponse = modelMapper.map(memberProfileFromService, MemberProfileResponse.class);
        EntityModel<MemberProfileResponse> memberProfileResponseEntityModel = memberProfileModelAssembler.toModel(memberProfileResponse);

        return new ResponseEntity<>(memberProfileResponseEntityModel, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteMemberProfile(@Valid @PathVariable("id") Long id) {

        if (!authService.loggedInUserIsAdmin()) {
            throw new RuntimeException("Only admins can access this api");
        }

        memberProfileService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/me")
    public ResponseEntity<EntityModel<MemberProfileResponse>> getMemberProfile() {

        MemberProfileDto memberProfileFromService = memberProfileService.getById(authService.getLoggedInMember().getId());
        if (memberProfileFromService == null) {
            return ResponseEntity.notFound().build();
        }

        MemberProfileResponse memberProfileResponse = modelMapper.map(memberProfileFromService, MemberProfileResponse.class);
        EntityModel<MemberProfileResponse> memberProfileResponseEntityModel = memberProfileModelAssembler.toModel(memberProfileResponse);

        return new ResponseEntity<>(memberProfileResponseEntityModel, HttpStatus.OK);
    }
}
