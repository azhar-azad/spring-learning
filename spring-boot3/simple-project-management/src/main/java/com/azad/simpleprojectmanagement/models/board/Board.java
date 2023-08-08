package com.azad.simpleprojectmanagement.models.board;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Board {

    @NotNull(message = "Board name cannot be null")
    protected String boardName;
}
