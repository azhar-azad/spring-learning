package com.azad.jsonplaceholderclone.controller;

import com.azad.jsonplaceholderclone.models.responses.ApiResponse;
import com.azad.jsonplaceholderclone.services.MemberProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users/profile")
public class MemberProfileController {

    private final MemberProfileService memberProfileService;

    @Autowired
    public MemberProfileController(MemberProfileService memberProfileService) {
        this.memberProfileService = memberProfileService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createMemberProfile() {

        return null;
    }
}
