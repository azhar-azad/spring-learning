package com.azad.CampusConnectApi.models.responses;

import com.azad.CampusConnectApi.models.AppUser;
import com.azad.CampusConnectApi.models.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserResponse extends AppUser {

    private Long id;
    private Profile profile;
}
