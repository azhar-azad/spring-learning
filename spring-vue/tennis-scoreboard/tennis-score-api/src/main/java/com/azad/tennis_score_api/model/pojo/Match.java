package com.azad.tennis_score_api.model.pojo;

import lombok.Data;

@Data
public class Match {

    private String round;
    private String firstSet;
    private String secondSet;
    private String thirdSet;
    private String fourthSet;
    private String fifthSet;
}
