package com.azad.tennis_score_api.model.dto;

import com.azad.tennis_score_api.model.pojo.Player;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlayerDto extends Player {

    private Long id;
}
