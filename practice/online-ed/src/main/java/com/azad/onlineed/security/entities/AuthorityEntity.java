package com.azad.onlineed.security.entities;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class AuthorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String authorityName;

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
}
