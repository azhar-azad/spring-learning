package com.azad.simpleprojectmanagement.repositories;

import com.azad.simpleprojectmanagement.models.board.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

//    Optional<List<BoardEntity>> findByProject(Long projectId);
}
