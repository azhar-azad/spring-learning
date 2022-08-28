package com.azad.ListShare.models;

public class Access {

    private String name;
    private Character shortForm;
    private Integer factor; // R->1, M->2, D->3

    public Access() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getShortForm() {
        return shortForm;
    }

    public void setShortForm(Character shortForm) {
        this.shortForm = shortForm;
    }

    public Integer getFactor() {
        return factor;
    }

    public void setFactor(Integer factor) {
        this.factor = factor;
    }
}
