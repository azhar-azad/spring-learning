package com.azad.onlinecourse.models.instructor;

import com.azad.onlinecourse.models.auth.AppUserEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "instructors")
public class InstructorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instructor_id")
    private Long id;

    @Column(name = "headline", nullable = false)
    private String headline;

    @Column(name = "biography", nullable = false)
    private String biography;

    @Column(name = "website")
    private String website;

    @OneToOne(mappedBy = "instructor")
    private AppUserEntity user;
}
