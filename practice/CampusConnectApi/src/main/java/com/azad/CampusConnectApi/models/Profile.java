package com.azad.CampusConnectApi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @NotNull(message = "Current educational institute name cannot be empty")
    private String studyingAt;

    @NotNull(message = "Home place cannot be empty")
    private String homePlace;

    private String livesIn;

    private String bio;

    private List<String> hobbies;

    private String relationshipStatus;

    private String politicalView;
}
