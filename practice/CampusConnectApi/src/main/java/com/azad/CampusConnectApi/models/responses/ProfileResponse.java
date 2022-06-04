package com.azad.CampusConnectApi.models.responses;

import com.azad.CampusConnectApi.models.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse extends Profile {

    private Long id;
    private Long appUserId;
}
