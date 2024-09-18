package com.azad.tennis_score_api.controller;

import com.azad.tennis_score_api.assembler.PlayerModelAssembler;
import com.azad.tennis_score_api.common.ApiRestController;
import com.azad.tennis_score_api.model.dto.PlayerDto;
import com.azad.tennis_score_api.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/players")
public class PlayerRestController implements ApiRestController<PlayerDto> {

    private final PlayerService service;
    private final PlayerModelAssembler assembler;

    @Autowired
    public PlayerRestController(PlayerService service, PlayerModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping(path = "/test")
    public String test() {
        return "/api/v1/players is online";
    }

    @Override
    public ResponseEntity<EntityModel<PlayerDto>> create(PlayerDto request) {
        return null;
    }

    @Override
    public ResponseEntity<CollectionModel<EntityModel<PlayerDto>>> getAll(Map<String, String> params) {
        return null;
    }

    @Override
    public ResponseEntity<EntityModel<PlayerDto>> get(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<EntityModel<PlayerDto>> update(Long id, PlayerDto updatedRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return null;
    }
}
