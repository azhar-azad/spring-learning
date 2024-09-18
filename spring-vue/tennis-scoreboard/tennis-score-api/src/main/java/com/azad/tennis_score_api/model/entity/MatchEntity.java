package com.azad.tennis_score_api.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "matches")
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long id;

    private String round;
    private String firstSet;
    private String secondSet;
    private String thirdSet;
    private String fourthSet;
    private String fifthSet;
}
