package com.azad.springbootgraphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @QueryMapping(name = "movieById")
    public Movie getMovieById(@Argument int id) {
        return movieRepository.getById(id);
    }

    @MutationMapping
    public Movie addMovie(@Argument int id,
                          @Argument String title,
                          @Argument Integer year,
                          @Argument List<String> genres,
                          @Argument String director) {
        Movie movie = new Movie(id, title, year, genres, director);
        movieRepository.addMovie(movie);
        return movie;
    }
}
