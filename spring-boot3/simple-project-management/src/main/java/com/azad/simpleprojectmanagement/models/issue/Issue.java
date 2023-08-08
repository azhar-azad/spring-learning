package com.azad.simpleprojectmanagement.models.issue;

import com.azad.simpleprojectmanagement.models.constants.BillingType;
import com.azad.simpleprojectmanagement.models.constants.IssuePriority;
import com.azad.simpleprojectmanagement.models.constants.IssueStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class Issue {

    protected String issueNumber;

    @NotNull(message = "Issue status cannot be null")
    protected IssueStatus status;

    @NotNull(message = "Issue priority cannot be null")
    protected IssuePriority priority;

    @NotNull(message = "Issue turnover date cannot be null")
    protected LocalDate turnoverDate;

    @NotNull(message = "Issue pilot date cannot be null")
    protected LocalDate pilotDate;

    protected LocalDate approvalDate;

    @NotNull(message = "Issue due date cannot be null")
    protected LocalDate dueDate;

    protected LocalDate customerTargetLiveDate;

    @NotNull(message = "Issue original estimate cannot be null")
    protected String originalEstimate;

    protected String timeRemaining;
    protected String totalLogged;
    protected LocalDateTime createdAt;
    protected LocalDateTime lastModifiedAt;

    protected Double billingAmount;
    protected BillingType billingType;

    protected Double storyPoints;
}
