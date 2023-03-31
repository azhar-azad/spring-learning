package com.azad.data.models.pojos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class Tag {

    @NotNull(message = "Tag name cannot be null.")
    protected String name;

    @NotNull(message = "Tag description cannot be null.")
    protected String description;

    protected Long questionCount;
}
