package com.azad.worldcup22api.services.impl;

import com.azad.worldcup22api.models.dtos.PlayerStatDto;
import com.azad.worldcup22api.repos.PlayerStatRepository;
import com.azad.worldcup22api.services.PlayerStatService;
import com.azad.worldcup22api.utils.PagingAndSorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerStatServiceImpl implements PlayerStatService {

    private final PlayerStatRepository repository;

    @Autowired
    public PlayerStatServiceImpl(PlayerStatRepository repository) {
        this.repository = repository;
    }

    @Override
    public PlayerStatDto create(PlayerStatDto reqBody) {
        return null;
    }

    @Override
    public PlayerStatDto getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public PlayerStatDto getById(Long id) {
        return null;
    }

    @Override
    public PlayerStatDto updateById(Long id, PlayerStatDto updatedReqBody) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
