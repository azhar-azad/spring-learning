package com.azad.online_shop.model.entity;

import com.azad.online_shop.model.contant.AddressType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @Column(name = "address_type", nullable = false)
    private AddressType addressType;

    @Column(name = "house_no")
    private String houseNo;

    @Column(name = "road_no")
    private String roadNo;

    @Column(name = "area")
    private String area;

    @Column(name = "sub_district")
    private String subDistrict;

    @Column(name = "district")
    private String district;

    @Column(name = "division")
    private String division;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
