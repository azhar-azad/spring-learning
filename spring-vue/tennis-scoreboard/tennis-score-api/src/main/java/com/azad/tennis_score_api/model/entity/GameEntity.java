package com.azad.tennis_score_api.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "games")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;

    @Column(name = "player_one_score")
    private int playerOneScore;

    @Column(name = "player_two_score")
    private int playerTwoScore;

    @ManyToOne
    @JoinColumn(name = "set_id")
    private SetEntity set;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<PointEntity> points;
}
