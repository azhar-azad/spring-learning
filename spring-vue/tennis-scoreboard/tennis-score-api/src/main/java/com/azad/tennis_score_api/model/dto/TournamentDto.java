package com.azad.tennis_score_api.model.dto;

import com.azad.tennis_score_api.model.pojo.Tournament;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TournamentDto extends Tournament {

    private Long id;
}
