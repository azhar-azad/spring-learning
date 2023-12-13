package com.azad.springbootgraphql;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieRepository {

    private List<Movie> mockMovies;

    @PostConstruct
    public void initMockMovies() {
        mockMovies = new ArrayList<>(List.of(
                new Movie(1, "The Matrix", 1999, List.of("Action", "Sci-Fi"), "The Wachowskis"),
                new Movie(2, "The Matrix Reloaded", 2003, List.of("Action", "Sci-Fi"), "The Wachowskis"),
                new Movie(3, "The Matrix Revolutions", 2006, List.of("Action", "Sci-Fi"), "The Wachowskis")
        ));
    }

    public Movie getById(int id) {
        return mockMovies.stream()
                .filter(movie -> movie.id() == id)
                .findFirst()
                .orElse(null);
    }

    public void addMovie(Movie movie) {
        mockMovies.add(movie);
    }
}
