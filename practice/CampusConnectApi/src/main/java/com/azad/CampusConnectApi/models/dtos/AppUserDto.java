package com.azad.CampusConnectApi.models.dtos;

import com.azad.CampusConnectApi.models.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto extends AppUser {

    private Long id;
}
