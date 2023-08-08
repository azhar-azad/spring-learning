package com.azad.simpleprojectmanagement.models.story;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StoryRequest extends Story {

    private Long epicId;
}
