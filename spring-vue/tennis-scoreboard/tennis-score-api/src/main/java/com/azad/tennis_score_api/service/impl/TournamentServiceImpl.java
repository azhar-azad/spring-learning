package com.azad.tennis_score_api.service.impl;

import com.azad.tennis_score_api.common.PagingAndSorting;
import com.azad.tennis_score_api.model.dto.TournamentDto;
import com.azad.tennis_score_api.repository.TournamentRepository;
import com.azad.tennis_score_api.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository repository;

    @Autowired
    public TournamentServiceImpl(TournamentRepository repository) {
        this.repository = repository;
    }

    @Override
    public TournamentDto create(TournamentDto tournamentDto) throws RuntimeException {
        return null;
    }

    @Override
    public List<TournamentDto> getAll(PagingAndSorting ps) throws RuntimeException {
        return List.of();
    }

    @Override
    public TournamentDto getById(Long id) throws RuntimeException {
        return null;
    }

    @Override
    public TournamentDto updateById(Long id, TournamentDto updatedDto) throws RuntimeException {
        return null;
    }

    @Override
    public void deleteById(Long id) throws RuntimeException {

    }
}
