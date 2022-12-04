package com.azad.wcstatapi.services.impl;

import com.azad.wcstatapi.models.constants.MatchStatus;
import com.azad.wcstatapi.models.constants.PlayerPosition;
import com.azad.wcstatapi.models.constants.TournamentStage;
import com.azad.wcstatapi.models.dtos.MatchDto;
import com.azad.wcstatapi.models.entities.MatchEntity;
import com.azad.wcstatapi.models.entities.PlayerEntity;
import com.azad.wcstatapi.models.entities.TeamDataEntity;
import com.azad.wcstatapi.models.entities.TeamEntity;
import com.azad.wcstatapi.repos.MatchRepository;
import com.azad.wcstatapi.repos.PlayerRepository;
import com.azad.wcstatapi.repos.TeamDataRepository;
import com.azad.wcstatapi.repos.TeamRepository;
import com.azad.wcstatapi.services.MatchService;
import com.azad.wcstatapi.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class MatchGenericServiceImpl implements MatchService {

    @Value("${TEST_MODE}")
    private boolean TEST_MODE;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamDataRepository teamDataRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private final MatchRepository repository;

    @Autowired
    public MatchGenericServiceImpl(MatchRepository repository) {
        this.repository = repository;
    }

    @Override
    public MatchDto create(MatchDto request) {

        TeamEntity teamEntity1 = teamRepository.findByTeamName(request.getTeam1()).orElseThrow(
                () -> new RuntimeException("Team not found with name: " + request.getTeam1()));
        TeamEntity teamEntity2 = teamRepository.findByTeamName(request.getTeam2()).orElseThrow(
                () -> new RuntimeException("Team not found with name: " + request.getTeam2()));
        if (teamEntity1.getTeamName().equalsIgnoreCase(teamEntity2.getTeamName())) {
            throw new RuntimeException("Cannot play with same team");
        }

        if (request.getRound().toUpperCase().equalsIgnoreCase(TournamentStage.GROUP_STAGE.name())) {
            teamEntity1.setGroupMatchPlayed(teamEntity1.getGroupMatchPlayed() == null ? 1 : teamEntity1.getGroupMatchPlayed() + 1);
            teamEntity2.setGroupMatchPlayed(teamEntity2.getGroupMatchPlayed() == null ? 1 : teamEntity2.getGroupMatchPlayed() + 1);

            if (teamEntity1.getGroupGoalScored() == null) teamEntity1.setGroupGoalScored(0);
            if (teamEntity2.getGroupGoalScored() == null) teamEntity2.setGroupGoalScored(0);

            if (teamEntity1.getGroupGoalConceded() == null) teamEntity1.setGroupGoalConceded(0);
            if (teamEntity2.getGroupGoalConceded() == null) teamEntity2.setGroupGoalConceded(0);

            if (teamEntity1.getPoints() == null) teamEntity1.setPoints(0);
            if (teamEntity2.getPoints() == null) teamEntity2.setPoints(0);
        }
        teamEntity1.setTournamentStatus(request.getRound().toUpperCase());
        teamEntity2.setTournamentStatus(request.getRound().toUpperCase());

        TeamDataEntity teamData1 = teamEntity1.getTeamData();
        TeamDataEntity teamData2 = teamEntity2.getTeamData();
        teamData1.setPlayed(teamData1.getPlayed() == null ? 1 : teamData1.getPlayed() + 1);
        teamData2.setPlayed(teamData2.getPlayed() == null ? 1 : teamData2.getPlayed() + 1);
        teamDataRepository.save(teamData1);
        teamDataRepository.save(teamData2);

        teamRepository.save(teamEntity1);
        teamRepository.save(teamEntity2);

        String result = request.getResult();
        if (!result.startsWith("0") || !result.endsWith("0")) {
            throw new RuntimeException("Result should be 0-0 at the start of match");
        }

        List<PlayerEntity> playerEntities1 = playerRepository.findByTeamId(teamEntity1.getId()).orElseThrow(
                () -> new RuntimeException("Players not found for team: " + teamEntity1.getTeamName()));
        List<PlayerEntity> playerEntities2 = playerRepository.findByTeamId(teamEntity2.getId()).orElseThrow(
                () -> new RuntimeException("Players not found for team: " + teamEntity2.getTeamName()));

        assert playerEntities1 != null;
        assert playerEntities2 != null;

        if (playerEntities1.size() > 0)
            playerEntities1.forEach((playerEntity) -> {
                playerEntity.setMatchCount(playerEntity.getMatchCount() == null ? 1 : playerEntity.getMatchCount() + 1);
                if (playerEntity.getGoalCount() == null)    playerEntity.setGoalCount(0);
                if (playerEntity.getAssistCount() == null)  playerEntity.setAssistCount(0);
                if (playerEntity.getPosition().equalsIgnoreCase(PlayerPosition.GK.name()))
                    if (playerEntity.getSaveCount() == null) playerEntity.setSaveCount(0);
            });

        if (playerEntities2.size() > 0)
            playerEntities2.forEach((playerEntity) -> {
                playerEntity.setMatchCount(playerEntity.getMatchCount() == null ? 1 : playerEntity.getMatchCount() + 1);
                if (playerEntity.getGoalCount() == null)    playerEntity.setGoalCount(0);
                if (playerEntity.getAssistCount() == null)  playerEntity.setAssistCount(0);
                if (playerEntity.getPosition().equalsIgnoreCase(PlayerPosition.GK.name()))
                    if (playerEntity.getSaveCount() == null) playerEntity.setSaveCount(0);
            });

        playerRepository.saveAll(playerEntities1);
        playerRepository.saveAll(playerEntities2);

        MatchEntity entity = modelMapper.map(request, MatchEntity.class);

        MatchEntity savedEntity = repository.save(entity);

        return modelMapper.map(savedEntity, MatchDto.class);
    }

    @Override
    public List<MatchDto> getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public MatchDto getById(Long id) {
        return null;
    }

    private void handleDrawResult(MatchDto updatedRequest, MatchEntity entity, String goalCount,
                                   TeamEntity teamEntity1, TeamEntity teamEntity2,
                                   TeamDataEntity teamDataEntity1, TeamDataEntity teamDataEntity2) {
        entity.setWinner(null);
        entity.setLooser(null);

        entity.setResult(updatedRequest.getResult());
        entity.setStatus(updatedRequest.getStatus());

        teamDataEntity1.setDraw(teamDataEntity1.getDraw() + 1);
        teamDataEntity1.setGoalsFor(teamDataEntity1.getGoalsFor() + Integer.parseInt(goalCount));
        teamDataEntity1.setGoalsAgainst(teamDataEntity1.getGoalsAgainst() + Integer.parseInt(goalCount));
        teamDataEntity1.setPoints(teamDataEntity1.getPoints() + 1);

        teamDataEntity2.setDraw(teamDataEntity2.getDraw() + 1);
        teamDataEntity2.setGoalsFor(teamDataEntity2.getGoalsFor() + Integer.parseInt(goalCount));
        teamDataEntity2.setGoalsAgainst(teamDataEntity2.getGoalsAgainst() + Integer.parseInt(goalCount));
        teamDataEntity2.setPoints(teamDataEntity2.getPoints() + 1);

        TeamDataEntity savedTeamDataEntity1 = teamDataRepository.save(teamDataEntity1);
        TeamDataEntity savedTeamDataEntity2 = teamDataRepository.save(teamDataEntity2);

        teamEntity1.setTeamData(savedTeamDataEntity1);
        teamEntity2.setTeamData(savedTeamDataEntity2);

        teamRepository.save(teamEntity1);
        teamRepository.save(teamEntity2);
    }

    private void handleResult(MatchDto updatedRequest, MatchEntity entity,
                              TeamEntity teamEntity1, TeamEntity teamEntity2,
                              TeamDataEntity teamDataEntity1, TeamDataEntity teamDataEntity2) {
        entity.setDraw(null);

        entity.setWinner(updatedRequest.getWinner());
        entity.setLooser(updatedRequest.getLooser());

        entity.setResult(updatedRequest.getResult());
        entity.setStatus(updatedRequest.getStatus());

        TeamEntity winnerTeam, looserTeam;
        TeamDataEntity winnerTeamData, looserTeamData;

        if (updatedRequest.getWinner() != null && updatedRequest.getLooser() != null) {
            if (teamEntity1.getTeamName().equalsIgnoreCase(updatedRequest.getWinner())) {
                winnerTeam = teamEntity1;
                looserTeam = teamEntity2;
                winnerTeamData = teamDataEntity1;
                looserTeamData = teamDataEntity2;
            } else {
                winnerTeam = teamEntity2;
                looserTeam = teamEntity1;
                winnerTeamData = teamDataEntity2;
                looserTeamData = teamDataEntity1;
            }
        } else {
            throw new RuntimeException("Winner and looser team not specified.");
        }

        int winnerTeamGoalCount, looserTeamGoalCount;
        if (Integer.parseInt(goalsSplit[0]) > Integer.parseInt(goalsSplit[1])) {
            winnerTeamGoalCount = Integer.parseInt(goalsSplit[0]);
            looserTeamGoalCount = Integer.parseInt(goalsSplit[1]);
        } else {
            winnerTeamGoalCount = Integer.parseInt(goalsSplit[1]);
            looserTeamGoalCount = Integer.parseInt(goalsSplit[0]);
        }

        winnerTeamData.setWon(winnerTeamData.getWon() + 1);
        winnerTeamData.setGoalsFor(winnerTeamData.getGoalsFor() + winnerTeamGoalCount);
        winnerTeamData.setGoalsAgainst(winnerTeamData.getGoalsAgainst() + looserTeamGoalCount);
        winnerTeamData.setGoalDifference(winnerTeamGoalCount - looserTeamGoalCount);
        winnerTeamData.setPoints(winnerTeamData.getPoints() + 3);

        looserTeamData.setLost(looserTeamData.getLost() + 1);
        looserTeamData.setGoalsFor(looserTeamData.getGoalsFor() + looserTeamGoalCount);
        looserTeamData.setGoalsAgainst(looserTeamData.getGoalsAgainst() + winnerTeamGoalCount);
        looserTeamData.setGoalDifference(looserTeamGoalCount - winnerTeamGoalCount);

        TeamDataEntity winnerTeamDataEntity = teamDataRepository.save(winnerTeamData);
        TeamDataEntity looserTeamDataEntity = teamDataRepository.save(looserTeamData);

        winnerTeam.setTeamData(winnerTeamDataEntity);
        looserTeam.setTeamData(looserTeamDataEntity);

        teamRepository.save(winnerTeam);
        teamRepository.save(looserTeam);
    }

    @Override
    public MatchDto updateById(Long id, MatchDto updatedRequest) {

        MatchEntity entity = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Match not found with id: " + id));

        TeamEntity winnerTeam, looserTeam;
        TeamDataEntity winnerTeamData, looserTeamData;

        TeamEntity teamEntity1 = teamRepository.findByTeamName(entity.getTeam1()).orElseThrow(
                () -> new RuntimeException("Team not found with name: " + entity.getTeam1()));
        TeamEntity teamEntity2 = teamRepository.findByTeamName(entity.getTeam2()).orElseThrow(
                () -> new RuntimeException("Team not found with name: " + entity.getTeam2()));

        TeamDataEntity teamDataEntity1 = teamDataRepository.findByTeamId(teamEntity1.getId()).orElseThrow(
                () -> new RuntimeException("TeamData not found with teamId for " + entity.getTeam1()));
        TeamDataEntity teamDataEntity2 = teamDataRepository.findByTeamId(teamEntity2.getId()).orElseThrow(
                () -> new RuntimeException("TeamData not found with teamId for " + entity.getTeam2()));

        String[] goalsSplit = updatedRequest.getResult().split("-");
        String drawGoalCount = null;
        if (goalsSplit.length != 2) {
            throw new RuntimeException("Invalid result");
        }
        if (!Objects.equals(goalsSplit[0], goalsSplit[1])) {
            if (updatedRequest.getDraw())
                throw new RuntimeException("Invalid result as the game is drawn");
            else
                drawGoalCount = goalsSplit[0];
        }

        if (updatedRequest.getDraw() != null && updatedRequest.getDraw()) {
            handleDrawResult(updatedRequest, entity, drawGoalCount, teamEntity1, teamEntity2, teamDataEntity1, teamDataEntity2);
        } else {
            handleResult();
        }

        Map<String, List<String>> goalScorers = updatedRequest.getGoalScorers();
        Map<String, List<String>> assistProviders = updatedRequest.getAssistProviders();
        Map<String, List<String>> gkSaves = updatedRequest.getGkSaves();

        List<String> team1GoalScorers = goalScorers.get(teamEntity1.getTeamName());
        List<String> team2GoalScorers = goalScorers.get(teamEntity2.getTeamName());

        if (team1GoalScorers != null && team1GoalScorers.size() > 0) {
            team1GoalScorers.forEach((team1GoalScorer) -> {
                String[] split = team1GoalScorer.split(">>"); // split[0] = player name, split[1] = if not exists, 1
                PlayerEntity playerEntity = playerRepository.findByTeamIdAndPlayerName(teamEntity1.getId(), split[0]).orElseThrow(
                        () -> new RuntimeException("Player not found for " + teamEntity1.getTeamName() + " with name: " + split[0]));
                if (split.length > 1)
                    playerEntity.setGoalCount(playerEntity.getGoalCount() == null
                            ? Integer.parseInt(split[1]) : playerEntity.getGoalCount() + Integer.parseInt(split[1]));
                else
                    playerEntity.setGoalCount(playerEntity.getGoalCount() == null ? 1 : playerEntity.getGoalCount() + 1);

                playerRepository.save(playerEntity);
            });
        }

        if (team2GoalScorers != null && team2GoalScorers.size() > 0) {
            team2GoalScorers.forEach((team2GoalScorer) -> {
                String[] split = team2GoalScorer.split(">>"); // split[0] = player name, split[1] = if not exists, 1
                PlayerEntity playerEntity = playerRepository.findByTeamIdAndPlayerName(teamEntity2.getId(), split[0]).orElseThrow(
                        () -> new RuntimeException("Player not found for " + teamEntity2.getTeamName() + " with name: " + split[0]));
                if (split.length > 1)
                    playerEntity.setGoalCount(playerEntity.getGoalCount() == null
                            ? Integer.parseInt(split[1]) : playerEntity.getGoalCount() + Integer.parseInt(split[1]));
                else
                    playerEntity.setGoalCount(playerEntity.getGoalCount() == null ? 1 : playerEntity.getGoalCount() + 1);

                playerRepository.save(playerEntity);
            });
        }

        List<String> team1AssistProviders = assistProviders.get(teamEntity1.getTeamName());
        List<String> team2AssistProviders = assistProviders.get(teamEntity2.getTeamName());

        if (team1AssistProviders != null && team1AssistProviders.size() > 0) {
            team1AssistProviders.forEach((team1AssistProvider) -> {
                String[] split = team1AssistProvider.split(">>"); // split[0] = player name, split[1] = if not exists, 1
                PlayerEntity playerEntity = playerRepository.findByTeamIdAndPlayerName(teamEntity1.getId(), split[0]).orElseThrow(
                        () -> new RuntimeException("Player not found for " + teamEntity1.getTeamName() + " with name: " + split[0]));
                if (split.length > 1)
                    playerEntity.setAssistCount(playerEntity.getAssistCount() == null
                            ? Integer.parseInt(split[1]) : playerEntity.getAssistCount() + Integer.parseInt(split[1]));
                else
                    playerEntity.setAssistCount(playerEntity.getAssistCount() == null ? 1 : playerEntity.getAssistCount() + 1);

                playerRepository.save(playerEntity);
            });
        }

        if (team2AssistProviders != null && team2AssistProviders.size() > 0) {
            team2AssistProviders.forEach((team2AssistProvider) -> {
                String[] split = team2AssistProvider.split(">>"); // split[0] = player name, split[1] = if not exists, 1
                PlayerEntity playerEntity = playerRepository.findByTeamIdAndPlayerName(teamEntity2.getId(), split[0]).orElseThrow(
                        () -> new RuntimeException("Player not found for " + teamEntity2.getTeamName() + " with name: " + split[0]));
                if (split.length > 1)
                    playerEntity.setAssistCount(playerEntity.getAssistCount() == null
                            ? Integer.parseInt(split[1]) : playerEntity.getAssistCount() + Integer.parseInt(split[1]));
                else
                    playerEntity.setAssistCount(playerEntity.getAssistCount() == null ? 1 : playerEntity.getAssistCount() + 1);

                playerRepository.save(playerEntity);
            });
        }

        List<String> team1GkSaves = gkSaves.get(teamEntity1.getTeamName());
        List<String> team2GkSaves = gkSaves.get(teamEntity2.getTeamName());

        if (team1GkSaves != null && team1GkSaves.size() > 0) {
            team1GkSaves.forEach((team1GkSave) -> {
                String[] split = team1GkSave.split(">>"); // split[0] = player name, split[1] = if not exists, 1
                PlayerEntity playerEntity = playerRepository.findByTeamIdAndPlayerName(teamEntity1.getId(), split[0]).orElseThrow(
                        () -> new RuntimeException("Player not found for " + teamEntity1.getTeamName() + " with name: " + split[0]));
                if (split.length > 1)
                    playerEntity.setSaveCount(playerEntity.getSaveCount() == null
                            ? Integer.parseInt(split[1]) : playerEntity.getSaveCount() + Integer.parseInt(split[1]));
                else
                    playerEntity.setSaveCount(playerEntity.getSaveCount() == null ? 1 : playerEntity.getSaveCount() + 1);

                playerRepository.save(playerEntity);
            });
        }

        if (team2GkSaves != null && team2GkSaves.size() > 0) {
            team2GkSaves.forEach((team1GkSave) -> {
                String[] split = team1GkSave.split(">>"); // split[0] = player name, split[1] = if not exists, 1
                PlayerEntity playerEntity = playerRepository.findByTeamIdAndPlayerName(teamEntity2.getId(), split[0]).orElseThrow(
                        () -> new RuntimeException("Player not found for " + teamEntity2.getTeamName() + " with name: " + split[0]));
                if (split.length > 1)
                    playerEntity.setSaveCount(playerEntity.getSaveCount() == null
                            ? Integer.parseInt(split[1]) : playerEntity.getSaveCount() + Integer.parseInt(split[1]));
                else
                    playerEntity.setSaveCount(playerEntity.getSaveCount() == null ? 1 : playerEntity.getSaveCount() + 1);

                playerRepository.save(playerEntity);
            });
        }

        MatchEntity savedEntity = repository.save(entity);

        return modelMapper.map(savedEntity, MatchDto.class);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public MatchDto updateByMatchNo(Integer matchNo, MatchDto updatedRequest) {
        MatchEntity entity = repository.findByMatchNo(matchNo).orElseThrow(
                () -> new RuntimeException("Match not found with matchNo: " + matchNo));

        return updateById(entity.getId(), updatedRequest);
    }
}
