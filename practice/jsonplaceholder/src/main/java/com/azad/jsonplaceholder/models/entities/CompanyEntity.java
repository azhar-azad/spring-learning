package com.azad.jsonplaceholder.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "company")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;

    @Column(name = "company_name")
    private String name;

    @Column(name = "company_catch_phrase")
    private String catchPhrase;

    @Column(name = "company_bs")
    private String bs;

    @OneToOne(mappedBy = "company")
    private MemberProfileEntity memberProfile;

    public CompanyEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    public String getBs() {
        return bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }

    public MemberProfileEntity getMemberProfile() {
        return memberProfile;
    }

    public void setMemberProfile(MemberProfileEntity memberProfile) {
        this.memberProfile = memberProfile;
    }
}
