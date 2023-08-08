package com.azad.simpleprojectmanagement.models.task;

import com.azad.simpleprojectmanagement.models.issue.IssueEntity;
import com.azad.simpleprojectmanagement.models.story.StoryEntity;
import com.azad.simpleprojectmanagement.models.subtask.SubtaskEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Column(name = "task_summary", nullable = false)
    private String taskSummary;

    @Column(name = "task_description")
    private String taskDescription;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "issue_details_id")
    private IssueEntity issueDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id")
    private StoryEntity story;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SubtaskEntity> subtasks;
}
