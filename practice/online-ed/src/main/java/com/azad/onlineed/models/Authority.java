package com.azad.onlineed.models;

import javax.validation.constraints.NotNull;

public class Authority {

    @NotNull(message = "Authority name cannot be empty")
    private String authorityName;

    public Authority() {
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }
}
