package com.azad.wcstatapi.controllers;

import com.azad.wcstatapi.models.Group;
import com.azad.wcstatapi.models.dtos.GroupDto;
import com.azad.wcstatapi.models.responses.GroupResponse;
import com.azad.wcstatapi.services.GroupGenericService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/groups")
public class GroupController {

    @Autowired
    private ModelMapper modelMapper;

    private final GroupGenericService service;

    @Autowired
    public GroupController(GroupGenericService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody Group request) {
        GroupDto dto = modelMapper.map(request, GroupDto.class);

        GroupDto savedDto = service.create(dto);

        return new ResponseEntity<>(modelMapper.map(savedDto, GroupResponse.class), HttpStatus.CREATED);
    }
}
