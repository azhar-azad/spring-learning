package com.azad.moviepedia.models.dtos;

import com.azad.moviepedia.models.pojos.Writer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class WriterDto extends Writer {

    private Long id;
    private Set<AwardDto> awards;
}
