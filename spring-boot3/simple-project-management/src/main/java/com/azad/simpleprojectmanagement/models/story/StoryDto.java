package com.azad.simpleprojectmanagement.models.story;

import com.azad.simpleprojectmanagement.models.epic.Epic;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StoryDto extends Story {

    private Long id;

    private Long epicId;
    private Epic epic;
}
