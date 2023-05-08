package com.azad.ecommerce.inventoryservice.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "stores")
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "store_uid", nullable = false, unique = true)
    private String storeUid; // storeName

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "store_owner_uid")
    private String storeOwnerUid;
}
