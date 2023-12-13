package com.azad.springbootgraphql;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ActorRepository {

    private List<Actor> mockActors;
    private MovieRepository movieRepository;

    public ActorRepository(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @PostConstruct
    public void initMockActors() {
        mockActors = new ArrayList<>(List.of(
                new Actor(1, "Keanu Reeves", "1964-09-02", List.of(movieRepository.getById(1), movieRepository.getById(2), movieRepository.getById(3))),
                new Actor(2, "Laurence Fishburne", "1961-07-30", List.of(movieRepository.getById(1), movieRepository.getById(2), movieRepository.getById(3)))
        ));
    }

    public Actor getById(int id) {
        return mockActors.stream()
                .filter(actor -> actor.id() == id)
                .findFirst()
                .orElse(null);
    }
}
