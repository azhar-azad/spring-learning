package com.azad.data.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Column(name = "task_title", nullable = false)
    private String taskTitle;

    @Column(name = "task_details")
    private String taskDetails;

    @Column(name = "target_date")
    private LocalDate targetDate;

    @Column(name = "is_starred")
    private boolean isStarred;

    @Column(name = "is_private")
    private boolean isPrivate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id")
    private TaskListEntity taskList;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SubtaskEntity> subtasks;
}
