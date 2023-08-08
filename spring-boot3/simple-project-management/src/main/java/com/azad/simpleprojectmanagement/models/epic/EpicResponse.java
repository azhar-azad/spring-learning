package com.azad.simpleprojectmanagement.models.epic;

import com.azad.simpleprojectmanagement.models.issue.Issue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EpicResponse extends Epic {

    private Long id;

    private Issue issueDetails;
}
