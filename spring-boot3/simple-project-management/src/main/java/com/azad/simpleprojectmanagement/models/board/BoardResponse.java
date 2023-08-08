package com.azad.simpleprojectmanagement.models.board;

import com.azad.simpleprojectmanagement.models.project.Project;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BoardResponse extends Board {

    private Long id;
    private Project project;
}
