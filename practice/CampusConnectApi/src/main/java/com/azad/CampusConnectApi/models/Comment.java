package com.azad.CampusConnectApi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @NotNull(message = "AppUserId of the comment owner cannot be empty")
    private Long commentedByUserId;

    private String commentText;
    private List<String> links;
}
