package com.azad.online_shop.model.dto.users;

import com.azad.online_shop.model.pojo.Address;
import com.azad.online_shop.model.pojo.Role;
import com.azad.online_shop.model.pojo.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class UserResponse extends User {

    private Long id;
    private Role role;
    private List<Address> addresses;
}
