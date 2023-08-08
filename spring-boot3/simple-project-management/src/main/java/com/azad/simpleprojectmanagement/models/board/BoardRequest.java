package com.azad.simpleprojectmanagement.models.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BoardRequest extends Board {

    private String projectName;
}
