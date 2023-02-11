package com.azad.worldcup22api.services.impl;

import com.azad.worldcup22api.models.dtos.TeamStatDto;
import com.azad.worldcup22api.repos.TeamStatRepository;
import com.azad.worldcup22api.services.TeamStatService;
import com.azad.worldcup22api.utils.PagingAndSorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamStatServiceImpl implements TeamStatService {

    private final TeamStatRepository repository;

    @Autowired
    public TeamStatServiceImpl(TeamStatRepository repository) {
        this.repository = repository;
    }

    @Override
    public TeamStatDto create(TeamStatDto reqBody) {
        return null;
    }

    @Override
    public TeamStatDto getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public TeamStatDto getById(Long id) {
        return null;
    }

    @Override
    public TeamStatDto updateById(Long id, TeamStatDto updatedReqBody) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
