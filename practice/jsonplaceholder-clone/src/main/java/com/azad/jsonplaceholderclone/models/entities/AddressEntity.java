package com.azad.jsonplaceholderclone.models.entities;

import com.azad.jsonplaceholderclone.security.entities.MemberEntity;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @Column
    private String street;

    @Column
    private String suite;

    @Column(nullable = false)
    private String city;

    @Column
    private String zipcode;

    @OneToOne(mappedBy = "address")
    private MemberProfileEntity memberProfile;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "geo_id", referencedColumnName = "geo_id")
    private GeoEntity geo;

    public AddressEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public MemberProfileEntity getMemberProfile() {
        return memberProfile;
    }

    public void setMemberProfile(MemberProfileEntity memberProfile) {
        this.memberProfile = memberProfile;
    }

    public GeoEntity getGeo() {
        return geo;
    }

    public void setGeo(GeoEntity geo) {
        this.geo = geo;
    }
}
