package com.azad.simpleprojectmanagement.models.epic;

import com.azad.simpleprojectmanagement.models.issue.IssueEntity;
import com.azad.simpleprojectmanagement.models.story.StoryEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "epics")
public class EpicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "epic_id")
    private Long id;

    @Column(name = "epic_name", nullable = false)
    private String epicName;

    @Column(name = "epic_description", nullable = false)
    private String epicDescription;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "issue_details_id")
    private IssueEntity issueDetails;

    @OneToMany(mappedBy = "epic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StoryEntity> stories;
}
