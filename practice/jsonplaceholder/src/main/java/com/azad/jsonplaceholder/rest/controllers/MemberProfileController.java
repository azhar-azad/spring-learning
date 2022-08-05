package com.azad.jsonplaceholder.rest.controllers;

import com.azad.jsonplaceholder.models.MemberProfile;
import com.azad.jsonplaceholder.models.dtos.MemberProfileDto;
import com.azad.jsonplaceholder.models.responses.MemberProfileResponse;
import com.azad.jsonplaceholder.rest.assemblers.MemberProfileModelAssembler;
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
public class MemberProfileController {

    @Autowired
    private ModelMapper modelMapper;

    private final MemberProfileService memberProfileService;
    private final MemberProfileModelAssembler memberProfileModelAssembler;

    @Autowired
    public MemberProfileController(MemberProfileService memberProfileService, MemberProfileModelAssembler memberProfileModelAssembler) {
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

        MemberProfileDto memberProfileFromService = memberProfileService.getById(id);
        if (memberProfileFromService == null) {
            return ResponseEntity.notFound().build();
        }

        MemberProfileResponse memberProfileResponse = modelMapper.map(memberProfileFromService, MemberProfileResponse.class);
        EntityModel<MemberProfileResponse> memberProfileResponseEntityModel = memberProfileModelAssembler.toModel(memberProfileResponse);

        return new ResponseEntity<>(memberProfileResponseEntityModel, HttpStatus.OK);
    }
}
