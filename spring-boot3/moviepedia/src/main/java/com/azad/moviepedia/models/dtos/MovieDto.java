package com.azad.moviepedia.models.dtos;

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

    private Set<Director> directors;
    private Set<Writer> writers;
    private Set<Cast> casts;
}
