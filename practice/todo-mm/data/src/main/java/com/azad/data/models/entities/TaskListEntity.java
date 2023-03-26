package com.azad.data.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/***
 * A TaskList that will host one or more tasks.
 * One List Many Tasks.
 */

@NoArgsConstructor
@Data
@Entity
@Table(name = "lists")
public class TaskListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_id")
    private Long id;

    @Column(name = "list_title", nullable = false)
    private String listTitle;

    @Column(name = "list_details")
    private String listDetails;

    @OneToMany(mappedBy = "taskList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TaskEntity> tasks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUserEntity user;
}
