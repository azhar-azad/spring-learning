package com.azad.CampusConnectApi.models.responses;

import com.azad.CampusConnectApi.models.AppUser;
import com.azad.CampusConnectApi.models.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse extends Post {

    private Long id;
    private Long appUserId;
}
