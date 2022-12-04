package com.azad.wcstatapi.controllers;

import com.azad.wcstatapi.models.Match;
import com.azad.wcstatapi.models.dtos.MatchDto;
import com.azad.wcstatapi.models.responses.MatchResponse;
import com.azad.wcstatapi.services.MatchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/matches")
public class MatchController {

    @Autowired
    private ModelMapper modelMapper;

    private final MatchService service;

    @Autowired
    public MatchController(MatchService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MatchResponse> startMatch(@Valid @RequestBody Match request) {

        MatchDto dto = modelMapper.map(request, MatchDto.class);

        MatchDto savedDto = service.create(dto);

        return new ResponseEntity<>(modelMapper.map(savedDto, MatchResponse.class), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{matchNo}")
    public ResponseEntity<MatchResponse> updateMatch(@Valid @PathVariable("matchNo") Integer matchNo, @Valid @RequestBody Match updatedRequest) {

        MatchDto dto = modelMapper.map(updatedRequest, MatchDto.class);

        MatchDto updatedDto = service.updateByMatchNo(matchNo, dto);

        return new ResponseEntity<>(modelMapper.map(updatedDto, MatchResponse.class), HttpStatus.OK);
    }
}
