package com.azad.moviepedia.models.dtos;

import com.azad.moviepedia.models.pojos.Writer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WriterDto extends Writer {

    private Long id;
}
