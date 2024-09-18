package com.azad.tennis_score_api.controller;

import com.azad.tennis_score_api.assembler.TournamentModelAssembler;
import com.azad.tennis_score_api.common.ApiRestController;
import com.azad.tennis_score_api.model.dto.TournamentDto;
import com.azad.tennis_score_api.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/tournaments")
public class TournamentRestController implements ApiRestController<TournamentDto> {

    private final TournamentService service;
    private final TournamentModelAssembler assembler;

    @Autowired
    public TournamentRestController(TournamentService service, TournamentModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Override
    public ResponseEntity<EntityModel<TournamentDto>> create(TournamentDto request) {
        return null;
    }

    @Override
    public ResponseEntity<CollectionModel<EntityModel<TournamentDto>>> getAll(Map<String, String> params) {
        return null;
    }

    @Override
    public ResponseEntity<EntityModel<TournamentDto>> get(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<EntityModel<TournamentDto>> update(Long id, TournamentDto updatedRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return null;
    }
}
