package com.azad.tennis_score_api.model.entity;

import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "sets")
public class SetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "set_id")
    private Long id;

    @Column(name = "player_one_score")
    private int playerOneScore;

    @Column(name = "player_two_score")
    private int playerTwoScore;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private MatchEntity match;

    @OneToMany(mappedBy = "set", cascade = CascadeType.ALL)
    private List<GameEntity> games;
}
