package com.azad.moviepedia.models.movie;

import com.azad.moviepedia.models.people.People;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class MovieRequest extends Movie {

    private List<People> directors;
    private List<People> writers;
    private List<People> stars;
}
