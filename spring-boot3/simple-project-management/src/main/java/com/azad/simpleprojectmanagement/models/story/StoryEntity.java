package com.azad.simpleprojectmanagement.models.story;

import com.azad.simpleprojectmanagement.models.epic.EpicEntity;
import com.azad.simpleprojectmanagement.models.issue.IssueEntity;
import com.azad.simpleprojectmanagement.models.task.TaskEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "stories")
public class StoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_id")
    private Long id;

    @Column(name = "story_summary", nullable = false)
    private String storySummary;

    @Column(name = "story_description")
    private String storyDescription;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "issue_details_id")
    private IssueEntity issueDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "epic_id")
    private EpicEntity epic;

    @OneToMany(mappedBy = "story", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TaskEntity> tasks;
}
