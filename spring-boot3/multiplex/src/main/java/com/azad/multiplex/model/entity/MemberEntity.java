package com.azad.multiplex.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email_address", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String mobile;

    @Column(name = "password", nullable = false)
    private String password;

    private boolean enabled;
    private boolean expired;
    private boolean locked;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private RoleEntity role = new RoleEntity();

    @OneToOne(mappedBy = "member")
    private MemberInfoEntity memberInfo;
}
