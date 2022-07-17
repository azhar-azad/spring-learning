package com.azad.bazaar.models.entities;

import com.azad.bazaar.security.entities.MemberEntity;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "item")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private double amountNeeded;

    @Column
    private double approxPricePerUnit;

    @Column
    private boolean isBought;

    @Column
    private double buyingPrice;

    @Column
    private String assignedTo;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime addedAt;

    @Column
    private LocalDate boughtAt;

    @Column
    private String boughtBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "added_by")
    private MemberEntity addedBy;

    public ItemEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getAmountNeeded() {
        return amountNeeded;
    }

    public void setAmountNeeded(double amountNeeded) {
        this.amountNeeded = amountNeeded;
    }

    public double getApproxPricePerUnit() {
        return approxPricePerUnit;
    }

    public void setApproxPricePerUnit(double approxPricePerUnit) {
        this.approxPricePerUnit = approxPricePerUnit;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public LocalDate getBoughtAt() {
        return boughtAt;
    }

    public void setBoughtAt(LocalDate boughtAt) {
        this.boughtAt = boughtAt;
    }

    public MemberEntity getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(MemberEntity addedBy) {
        this.addedBy = addedBy;
    }
}
