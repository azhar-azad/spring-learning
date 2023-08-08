package com.azad.simpleprojectmanagement.models.bug;

import com.azad.simpleprojectmanagement.models.issue.IssueEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "bugs")
public class BugEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bug_id")
    private Long id;

    @Column(name = "bug_summary", nullable = false)
    private String bugSummary;

    @Column(name = "bug_description")
    private String bugDescription;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "issue_details_id")
    private IssueEntity issueDetails;

    
}
