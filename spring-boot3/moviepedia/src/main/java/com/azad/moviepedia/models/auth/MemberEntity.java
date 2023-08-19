package com.azad.moviepedia.models.auth;

import com.azad.moviepedia.models.memberinfo.MemberInfoEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "member_from", nullable = false)
    private LocalDate memberFrom;

    private boolean enabled;
    private boolean expired;
    private boolean locked;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private RoleEntity role = new RoleEntity();

    @OneToOne(mappedBy = "member")
    private MemberInfoEntity memberInfo;
}