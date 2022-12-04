package com.azad.wcstatapi.controllers;

import com.azad.wcstatapi.models.dtos.TeamDto;
import com.azad.wcstatapi.models.requests.TeamRequest;
import com.azad.wcstatapi.models.responses.TeamResponse;
import com.azad.wcstatapi.services.TeamGenericService;
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
@RequestMapping(path = "/api/v1/teams")
public class TeamController {

    @Autowired
    private ModelMapper modelMapper;

    private final TeamGenericService service;

    @Autowired
    public TeamController(TeamGenericService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TeamResponse> createTeam(@Valid @RequestBody TeamRequest request) {
        TeamDto dto = modelMapper.map(request, TeamDto.class);

        TeamDto savedDto = service.create(dto);

        return new ResponseEntity<>(modelMapper.map(savedDto, TeamResponse.class), HttpStatus.CREATED);
    }
}
