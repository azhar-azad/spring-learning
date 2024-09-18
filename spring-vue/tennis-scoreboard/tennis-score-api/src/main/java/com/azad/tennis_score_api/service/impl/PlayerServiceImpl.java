package com.azad.tennis_score_api.service.impl;

import com.azad.tennis_score_api.common.PagingAndSorting;
import com.azad.tennis_score_api.model.dto.PlayerDto;
import com.azad.tennis_score_api.repository.PlayerRepository;
import com.azad.tennis_score_api.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository repository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public PlayerDto create(PlayerDto playerDto) throws RuntimeException {
        return null;
    }

    @Override
    public List<PlayerDto> getAll(PagingAndSorting ps) throws RuntimeException {
        return List.of();
    }

    @Override
    public PlayerDto getById(Long id) throws RuntimeException {
        return null;
    }

    @Override
    public PlayerDto updateById(Long id, PlayerDto updatedDto) throws RuntimeException {
        return null;
    }

    @Override
    public void deleteById(Long id) throws RuntimeException {

    }
}
