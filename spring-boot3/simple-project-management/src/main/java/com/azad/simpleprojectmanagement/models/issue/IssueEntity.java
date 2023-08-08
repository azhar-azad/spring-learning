package com.azad.simpleprojectmanagement.models.issue;

import com.azad.simpleprojectmanagement.models.auth.AppUserEntity;
import com.azad.simpleprojectmanagement.models.board.BoardEntity;
import com.azad.simpleprojectmanagement.models.bug.BugEntity;
import com.azad.simpleprojectmanagement.models.constants.BillingType;
import com.azad.simpleprojectmanagement.models.constants.IssuePriority;
import com.azad.simpleprojectmanagement.models.constants.IssueStatus;
import com.azad.simpleprojectmanagement.models.epic.EpicEntity;
import com.azad.simpleprojectmanagement.models.story.StoryEntity;
import com.azad.simpleprojectmanagement.models.subtask.SubtaskEntity;
import com.azad.simpleprojectmanagement.models.task.TaskEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "issues")
public class IssueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private Long id;

    @Column(name = "ticket_number", nullable = false, unique = true)
    private String ticketNumber;

    @Column(name = "status", nullable = false)
    private IssueStatus status;

    @Column(name = "priority", nullable = false)
    private IssuePriority priority;

    @Column(name = "turnover_date", nullable = false)
    private LocalDate turnoverDate;

    @Column(name = "pilot_date", nullable = false)
    private LocalDate pilotDate;

    @Column(name = "approval_dae")
    private LocalDate approvalDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "customer_target_live_date")
    private LocalDate customerTargetLiveDate;

    @Column(name = "original_estimate", nullable = false)
    private String originalEstimate;

    @Column(name = "time_remaining")
    private String timeRemaining;

    @Column(name = "total_logged")
    private String totalLogged;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;

    @Column(name = "billing_amount")
    private Double billingAmount;

    @Column(name = "billing_type")
    private BillingType billingType;

    @Column(name = "story_points")
    private Double storyPoints;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private AppUserEntity reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private AppUserEntity assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qa_assignee_id")
    private AppUserEntity qaAssignee;

    @OneToOne(mappedBy = "issueDetails")
    private EpicEntity epic;

    @OneToOne(mappedBy = "issueDetails")
    private StoryEntity story;

    @OneToOne(mappedBy = "issueDetails")
    private TaskEntity task;

    @OneToOne(mappedBy = "issueDetails")
    private SubtaskEntity subtask;

    @OneToOne(mappedBy = "issueDetails")
    private BugEntity bug;
}
