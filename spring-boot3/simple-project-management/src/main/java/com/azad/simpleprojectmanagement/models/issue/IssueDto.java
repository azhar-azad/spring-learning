package com.azad.simpleprojectmanagement.models.issue;

import com.azad.simpleprojectmanagement.models.auth.AppUser;
import com.azad.simpleprojectmanagement.models.board.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class IssueDto extends Issue {

    private Long id;
    private Board board;
    private AppUser reporter;
    private AppUser assignee;
    private AppUser qaAssignee;
}
