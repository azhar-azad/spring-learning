package com.azad.moviepedia.models.dtos;

import com.azad.moviepedia.models.pojos.Director;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class DirectorDto extends Director {

    private Long id;
    private Set<AwardDto> awards;
}
