package com.azad.tennis_score_api.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "matches")
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long id;

    @Column(name = "round")
    private String round;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "player_one_id")
    private PlayerEntity playerOne;

    @ManyToOne
    @JoinColumn(name = "player_two_id")
    private PlayerEntity playerTwo;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<SetEntity> sets;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private TournamentEntity tournament;
}
