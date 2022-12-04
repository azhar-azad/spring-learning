package com.azad.wcstatapi.controllers;

import com.azad.wcstatapi.models.dtos.PlayerDto;
import com.azad.wcstatapi.models.requests.PlayerRequest;
import com.azad.wcstatapi.models.responses.PlayerResponse;
import com.azad.wcstatapi.services.PlayerGenericService;
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
@RequestMapping(path = "/api/v1/players")
public class PlayerController {

    @Autowired
    private ModelMapper modelMapper;

    private final PlayerGenericService service;

    @Autowired
    public PlayerController(PlayerGenericService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PlayerResponse> createPlayer(@Valid @RequestBody PlayerRequest request) {
        PlayerDto dto = modelMapper.map(request, PlayerDto.class);

        PlayerDto savedDto = service.create(dto);

        return new ResponseEntity<>(modelMapper.map(savedDto, PlayerResponse.class), HttpStatus.CREATED);
    }
}
