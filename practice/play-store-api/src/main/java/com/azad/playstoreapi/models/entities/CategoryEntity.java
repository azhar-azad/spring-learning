package com.azad.playstoreapi.models.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name", nullable = false, length = 50)
    private String categoryName;

    @ManyToMany
    @JoinTable(
            name = "app_category",
            joinColumns = @JoinColumn(name = "app_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<AppEntity> apps;

    public CategoryEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<AppEntity> getApps() {
        return apps;
    }

    public void setApps(List<AppEntity> apps) {
        this.apps = apps;
    }
}
