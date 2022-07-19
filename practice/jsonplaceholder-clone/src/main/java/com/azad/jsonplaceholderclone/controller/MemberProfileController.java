package com.azad.jsonplaceholderclone.controller;

import com.azad.jsonplaceholderclone.models.Address;
import com.azad.jsonplaceholderclone.models.Company;
import com.azad.jsonplaceholderclone.models.MemberProfile;
import com.azad.jsonplaceholderclone.models.dtos.MemberProfileDto;
import com.azad.jsonplaceholderclone.models.responses.ApiResponse;
import com.azad.jsonplaceholderclone.models.responses.MemberProfileResponse;
import com.azad.jsonplaceholderclone.services.MemberProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping(path = "/api/users/profile")
public class MemberProfileController {

    @Autowired
    private ModelMapper modelMapper;

    private final MemberProfileService memberProfileService;

    @Autowired
    public MemberProfileController(MemberProfileService memberProfileService) {
        this.memberProfileService = memberProfileService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createMemberProfile(@Valid @RequestBody MemberProfile requestBody) {

        MemberProfileDto memberProfileDto = memberProfileService.create(modelMapper.map(requestBody, MemberProfileDto.class));

        return new ResponseEntity<>(new ApiResponse(true, "Profile Created",
                Collections.singletonMap("profile", modelMapper.map(memberProfileDto, MemberProfileResponse.class))),
                HttpStatus.CREATED);
    }
}
