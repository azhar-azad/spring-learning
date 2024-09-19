package com.azad.tennis_score_api.model.dto;

import com.azad.tennis_score_api.model.pojo.Match;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MatchDto extends Match {

    private Long id;
}
