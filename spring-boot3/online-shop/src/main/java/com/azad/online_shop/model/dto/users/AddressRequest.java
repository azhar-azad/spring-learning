package com.azad.online_shop.model.dto.users;

import com.azad.online_shop.model.pojo.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddressRequest extends Address {
}
