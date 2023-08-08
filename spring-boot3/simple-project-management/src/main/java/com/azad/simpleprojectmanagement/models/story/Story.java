package com.azad.simpleprojectmanagement.models.story;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Story {

    @NotNull(message = "Story summary cannot be null")
    protected String storySummary;

    protected String storyDescription;
}
