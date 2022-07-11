package com.azad.onlineed.security.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authorities")
public class AuthorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String authorityName;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private Set<RoleEntity> roles = new HashSet<>();

    public AuthorityEntity() {
    }

    public Integer getId() {
        return id;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}
