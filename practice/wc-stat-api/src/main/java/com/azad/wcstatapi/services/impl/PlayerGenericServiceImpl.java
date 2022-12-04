package com.azad.wcstatapi.services.impl;

import com.azad.wcstatapi.models.Team;
import com.azad.wcstatapi.models.dtos.PlayerDto;
import com.azad.wcstatapi.models.dtos.TeamDto;
import com.azad.wcstatapi.models.entities.PlayerEntity;
import com.azad.wcstatapi.models.entities.TeamEntity;
import com.azad.wcstatapi.repos.PlayerRepository;
import com.azad.wcstatapi.repos.TeamRepository;
import com.azad.wcstatapi.services.PlayerGenericService;
import com.azad.wcstatapi.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PlayerGenericServiceImpl implements PlayerGenericService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TeamRepository teamRepository;

    private final PlayerRepository repository;

    private static final List<String> PLAYER_POSITIONS = Stream.of("GK", "DEF", "MID", "FWD").collect(Collectors.toList());

    @Autowired
    public PlayerGenericServiceImpl(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public PlayerDto create(PlayerDto request) {

        String teamName = request.getTeamName();
        TeamEntity teamEntity = teamRepository.findByTeamName(teamName).orElseThrow(
                () -> new RuntimeException("Team not found with name: " + teamName));

        if (!PLAYER_POSITIONS.contains(request.getPosition()))
            throw new RuntimeException("Invalid player position: " + request.getPosition());

        PlayerEntity playerEntity = repository.findByTeamIdAndPlayerName(teamEntity.getId(), request.getPlayerName()).orElse(null);
        PlayerEntity savedEntity;
        if (playerEntity == null) {
            PlayerEntity entity = modelMapper.map(request, PlayerEntity.class);
            entity.setTeam(teamEntity);
            savedEntity = repository.save(entity);
        } else {
            savedEntity = playerEntity;
        }

        PlayerDto dto = modelMapper.map(savedEntity, PlayerDto.class);
        dto.setTeam(modelMapper.map(teamEntity, Team.class));

        return dto;
    }

    @Override
    public List<PlayerDto> getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public PlayerDto getById(Long id) {
        return null;
    }

    @Override
    public PlayerDto updateById(Long id, PlayerDto updatedRequest) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
