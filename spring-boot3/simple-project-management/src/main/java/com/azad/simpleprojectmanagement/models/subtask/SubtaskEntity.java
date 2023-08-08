package com.azad.simpleprojectmanagement.models.subtask;

import com.azad.simpleprojectmanagement.models.issue.IssueEntity;
import com.azad.simpleprojectmanagement.models.task.TaskEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "subtasks")
public class SubtaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subtask_id")
    private Long id;

    @Column(name = "subtask_summary", nullable = false)
    private String subtaskSummary;

    @Column(name = "subtask_description")
    private String subtaskDescription;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "issue_details_id")
    private IssueEntity issueDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private TaskEntity task;
}
