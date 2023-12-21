package com.azad.multiplex.common;

import com.azad.multiplex.model.entity.RoleEntity;

@FunctionalInterface
public interface RoleCreator {

    RoleEntity create(String roleName);
}
