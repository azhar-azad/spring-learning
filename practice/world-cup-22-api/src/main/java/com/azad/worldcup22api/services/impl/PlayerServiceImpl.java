package com.azad.worldcup22api.services.impl;

import com.azad.worldcup22api.models.dtos.PlayerDto;
import com.azad.worldcup22api.repos.PlayerRepository;
import com.azad.worldcup22api.services.PlayerService;
import com.azad.worldcup22api.utils.PagingAndSorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository repository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public PlayerDto create(PlayerDto reqBody) {
        return null;
    }

    @Override
    public PlayerDto getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public PlayerDto getById(Long id) {
        return null;
    }

    @Override
    public PlayerDto updateById(Long id, PlayerDto updatedReqBody) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
