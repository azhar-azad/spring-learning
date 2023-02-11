package com.azad.worldcup22api.services.impl;

import com.azad.worldcup22api.models.dtos.TeamDto;
import com.azad.worldcup22api.repos.TeamRepository;
import com.azad.worldcup22api.services.TeamService;
import com.azad.worldcup22api.utils.PagingAndSorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository repository;

    @Autowired
    public TeamServiceImpl(TeamRepository repository) {
        this.repository = repository;
    }

    @Override
    public TeamDto create(TeamDto reqBody) {
        return null;
    }

    @Override
    public TeamDto getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public TeamDto getById(Long id) {
        return null;
    }

    @Override
    public TeamDto updateById(Long id, TeamDto updatedReqBody) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
