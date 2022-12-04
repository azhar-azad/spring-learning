package com.azad.wcstatapi.repos;

import com.azad.wcstatapi.models.entities.MatchEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends PagingAndSortingRepository<MatchEntity, Long> {

    Optional<MatchEntity> findByMatchNo(Integer matchNo);

    Optional<List<MatchEntity>> findByTeam1AndTeam2OrderByMatchDateDesc(String team1, String team2);
}
