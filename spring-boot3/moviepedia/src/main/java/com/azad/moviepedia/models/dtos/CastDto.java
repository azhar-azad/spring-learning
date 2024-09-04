package com.azad.moviepedia.models.dtos;

import com.azad.moviepedia.models.pojos.Cast;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class CastDto extends Cast {

    private Long id;
    private Set<AwardDto> awards;
    private String characterName;
}
