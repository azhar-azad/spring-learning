package com.azad.ecommerce.models.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "address_type")
public class AddressTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_type_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "addressType", cascade = CascadeType.ALL)
    private List<AddressEntity> addressEntities;

    public AddressTypeEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<AddressEntity> getAddressEntities() {
        return addressEntities;
    }

    public void setAddressEntities(List<AddressEntity> addressEntities) {
        this.addressEntities = addressEntities;
    }
}
