package com.azad.tennis_score_api.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tournaments")
public class TournamentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_id")
    private Long id;

    @Column(name = "tournament_name", nullable = false, unique = true)
    private String tournamentName;

    @Column(name = "country", nullable = false)
    private String country;
}
