package com.azad.moviepedia.models.dtos;

import com.azad.moviepedia.models.pojos.Award;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AwardDto extends Award {

    private Long id;
}
