package com.azad.simpleprojectmanagement.models.story;

import com.azad.simpleprojectmanagement.models.epic.Epic;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StoryResponse extends Epic {

    private Long id;
    private Epic epic;
}
