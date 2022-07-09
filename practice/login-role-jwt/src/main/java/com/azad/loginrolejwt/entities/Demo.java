package com.azad.loginrolejwt.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
public class Demo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "demo_id")
    private Long id;

    private String stringColumn;
    private Integer intColumn;
    private LocalDate dateColumn;
    private LocalDateTime dateTimeColumn;
    private Boolean boolColumn;
    private Character charColumn;

    private LocalDate created;
    private LocalDate modified;

    public Demo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStringColumn() {
        return stringColumn;
    }

    public void setStringColumn(String stringColumn) {
        this.stringColumn = stringColumn;
    }

    public Integer getIntColumn() {
        return intColumn;
    }

    public void setIntColumn(Integer intColumn) {
        this.intColumn = intColumn;
    }

    public LocalDate getDateColumn() {
        return dateColumn;
    }

    public void setDateColumn(LocalDate dateColumn) {
        this.dateColumn = dateColumn;
    }

    public LocalDateTime getDateTimeColumn() {
        return dateTimeColumn;
    }

    public void setDateTimeColumn(LocalDateTime dateTimeColumn) {
        this.dateTimeColumn = dateTimeColumn;
    }

    public Boolean getBoolColumn() {
        return boolColumn;
    }

    public void setBoolColumn(Boolean boolColumn) {
        this.boolColumn = boolColumn;
    }

    public Character getCharColumn() {
        return charColumn;
    }

    public void setCharColumn(Character charColumn) {
        this.charColumn = charColumn;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getModified() {
        return modified;
    }

    public void setModified(LocalDate modified) {
        this.modified = modified;
    }
}
