package com.azad.moviepedia.models.dtos;

import com.azad.moviepedia.models.constants.GenreName;
import com.azad.moviepedia.models.pojos.Cast;
import com.azad.moviepedia.models.pojos.Director;
import com.azad.moviepedia.models.pojos.Movie;
import com.azad.moviepedia.models.pojos.Writer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class MovieDto extends Movie {

    private Long id;

    private Set<Long> directorIds;
    private Set<Long> writerIds;
    private Set<Long> castIds;
    private Set<Long> awardIds;

    private Set<GenreName> genres;

    private Set<DirectorDto> directors;
    private Set<WriterDto> writers;
    private Set<CastDto> casts;
    private Set<AwardDto> awards;
}
