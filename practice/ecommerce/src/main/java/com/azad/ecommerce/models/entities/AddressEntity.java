package com.azad.ecommerce.models.entities;

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
    private UserProfileEntity userProfile;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "geo_id", referencedColumnName = "geo_id")
    private GeoEntity geo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_type_id")
    private AddressTypeEntity addressType;

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

    public UserProfileEntity getMemberProfile() {
        return userProfile;
    }

    public void setMemberProfile(UserProfileEntity userProfile) {
        this.userProfile = userProfile;
    }

    public GeoEntity getGeo() {
        return geo;
    }

    public void setGeo(GeoEntity geo) {
        this.geo = geo;
    }

    public UserProfileEntity getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileEntity userProfile) {
        this.userProfile = userProfile;
    }

    public AddressTypeEntity getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressTypeEntity addressType) {
        this.addressType = addressType;
    }
}
