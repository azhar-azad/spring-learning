package com.azad.tennis_score_api.model.dto;

import com.azad.tennis_score_api.model.pojo.Point;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PointDto extends Point {

    private Long id;

    private PlayerDto server;
    private Long playerId;
}
