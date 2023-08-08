package com.azad.simpleprojectmanagement.models.task;

import com.azad.simpleprojectmanagement.models.issue.Issue;
import com.azad.simpleprojectmanagement.models.story.Story;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TaskResponse extends Task {

    private Long id;
    private Story story;
    private Issue issueDetails;
}
