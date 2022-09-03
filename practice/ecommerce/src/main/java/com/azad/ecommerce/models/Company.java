package com.azad.ecommerce.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Company {

    @NotNull(message = "Company name cannot be empty")
    @Size(min = 2, max = 20, message = "Company name length has to be in between 2 to 20 characters")
    private String name;
    private String catchPhrase;
    private String bs;

    public Company() {
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
}
