package com.azad.data.models.responses;

import com.azad.data.models.pojos.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class TagResponse extends Tag {

    private Long id;
    private String message;
    private String unregisteredUserMsg;
}
