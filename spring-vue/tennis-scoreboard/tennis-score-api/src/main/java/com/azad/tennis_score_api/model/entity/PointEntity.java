package com.azad.tennis_score_api.model.entity;

import com.azad.tennis_score_api.model.constant.PointAttribute;
import com.azad.tennis_score_api.model.constant.ServeType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "points")
public class PointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ServeType serveType;

    @Column(name = "score", nullable = false)
    private String score;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameEntity game;

    @ManyToOne
    @JoinColumn(name = "server_id")
    private PlayerEntity server;  // who served this point

    @ElementCollection
    @CollectionTable(name = "point_attributes",
            joinColumns = @JoinColumn(name = "point_id"))
    @Column(name = "attribute")
    private List<PointAttribute> pointAttributes;
}
