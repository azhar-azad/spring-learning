package com.azad.CampusConnectApi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Link {

    @NotNull(message = "Link URL cannot be empty")
    private String linkUrl;
}
