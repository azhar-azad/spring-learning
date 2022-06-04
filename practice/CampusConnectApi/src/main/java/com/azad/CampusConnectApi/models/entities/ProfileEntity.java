package com.azad.CampusConnectApi.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profile")
public class ProfileEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String studyingAt;

    @Column(nullable = false)
    private String homePlace;

    @Column
    private String livesIn;

    @Column
    private String bio;

    @Column
    private String hobbies;

    @Column
    private String relationshipStatus;

    @Column
    private String politicalView;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUserEntity appUser;
}
