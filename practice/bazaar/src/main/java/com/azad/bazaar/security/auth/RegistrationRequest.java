package com.azad.bazaar.security.auth;

import com.azad.bazaar.models.Member;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class RegistrationRequest extends Member {

    @NotNull(message = "Role(s) must be provided as a comma separated String array")
    private Set<String> roleNames;

    public RegistrationRequest() {
    }

    public Set<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(Set<String> roleNames) {
        this.roleNames = roleNames;
    }
}
