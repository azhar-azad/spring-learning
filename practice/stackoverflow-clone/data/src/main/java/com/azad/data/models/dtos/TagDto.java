package com.azad.data.models.dtos;

import com.azad.data.models.pojos.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class TagDto extends Tag {

    private Long id;
    private String message;
    private String unregisteredUserMsg;

    private String createdByUser;
    private String updatedByUser;
}
