package com.azad.onlineed.models.requests;

import com.azad.onlineed.models.Authority;
import com.azad.onlineed.models.Role;

import java.util.Set;

public class RoleRequest extends Role {

    private Set<String> authorityNames;

    public RoleRequest() {
    }

    public Set<String> getAuthorityNames() {
        return authorityNames;
    }

    public void setAuthorityNames(Set<String> authorityNames) {
        this.authorityNames = authorityNames;
    }
}
