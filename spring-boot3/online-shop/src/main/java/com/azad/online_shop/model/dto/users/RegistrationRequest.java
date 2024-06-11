package com.azad.online_shop.model.dto.users;

import com.azad.online_shop.model.pojo.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegistrationRequest extends User {

    private String roleName;
}
