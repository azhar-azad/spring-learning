package com.azad.wcstatapi.services.impl;

import com.azad.wcstatapi.models.Group;
import com.azad.wcstatapi.models.TeamData;
import com.azad.wcstatapi.models.dtos.TeamDto;
import com.azad.wcstatapi.models.entities.TeamDataEntity;
import com.azad.wcstatapi.models.entities.GroupEntity;
import com.azad.wcstatapi.models.entities.TeamEntity;
import com.azad.wcstatapi.repos.TeamDataRepository;
import com.azad.wcstatapi.repos.GroupRepository;
import com.azad.wcstatapi.repos.TeamRepository;
import com.azad.wcstatapi.services.TeamGenericService;
import com.azad.wcstatapi.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamGenericServiceImpl implements TeamGenericService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TeamDataRepository teamDataRepository;
    @Autowired
    private GroupRepository groupRepository;
    private final TeamRepository repository;

    @Autowired
    public TeamGenericServiceImpl(TeamRepository repository) {
        this.repository = repository;
    }

    @Override
    public TeamDto create(TeamDto request) {

        String groupName = request.getGroupName();
        GroupEntity group = groupRepository.findByWcGroupName(groupName).orElseThrow(
                () -> new RuntimeException("Group not found with name: " + groupName));

        TeamEntity entity = modelMapper.map(request, TeamEntity.class);
        entity.setGroup(group);

        TeamEntity savedEntity = repository.save(entity);

        TeamDataEntity teamDataEntity = new TeamDataEntity();
        teamDataEntity.setTeam(savedEntity);
        teamDataEntity.setTeamName(savedEntity.getTeamName());
        teamDataEntity.setPlayed(0);
        teamDataEntity.setWon(0);
        teamDataEntity.setDraw(0);
        teamDataEntity.setLost(0);
        teamDataEntity.setPoints(0);
        teamDataEntity.setGoalsFor(0);
        teamDataEntity.setGoalsAgainst(0);
        teamDataEntity.setGoalDifference(0);
        TeamDataEntity savedTeamDataEntity = teamDataRepository.save(teamDataEntity);

        TeamDto dto = modelMapper.map(savedEntity, TeamDto.class);
        dto.setGroup(modelMapper.map(group, Group.class));
        dto.setTeamData(modelMapper.map(savedTeamDataEntity, TeamData.class));

        return dto;
    }

    @Override
    public List<TeamDto> getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public TeamDto getById(Long id) {
        return null;
    }

    @Override
    public TeamDto updateById(Long id, TeamDto updatedRequest) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
