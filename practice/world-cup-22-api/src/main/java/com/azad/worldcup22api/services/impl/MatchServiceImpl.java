package com.azad.worldcup22api.services.impl;

import com.azad.worldcup22api.models.dtos.MatchDto;
import com.azad.worldcup22api.repos.MatchRepository;
import com.azad.worldcup22api.services.MatchService;
import com.azad.worldcup22api.utils.PagingAndSorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository repository;

    @Autowired
    public MatchServiceImpl(MatchRepository repository) {
        this.repository = repository;
    }

    @Override
    public MatchDto create(MatchDto reqBody) {
        return null;
    }

    @Override
    public MatchDto getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public MatchDto getById(Long id) {
        return null;
    }

    @Override
    public MatchDto updateById(Long id, MatchDto updatedReqBody) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
