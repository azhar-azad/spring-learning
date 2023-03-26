package com.azad.data.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "user_list_access")
public class AccessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "access_id")
    private Long id;

    @Column(name = "access_name", nullable = false)
    private String accessName;

    @Column(name = "user_id", nullable = false)
    private Long appUserId;

    @Column(name = "list_id", nullable = false)
    private Long taskListId;
}
