package com.azad.moviepedia.services;

import com.azad.moviepedia.models.constants.GenreName;
import com.azad.moviepedia.models.entities.GenreEntity;
import com.azad.moviepedia.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

@Service
public class GenreService {

    private final GenreRepository repository;

    @Autowired
    public GenreService(GenreRepository repository) {
        this.repository = repository;
    }

    public void initGenres() {
        Set<GenreName> genreNameSet = EnumSet.allOf(GenreName.class);
        for (GenreName genreName : genreNameSet) {
            Optional<GenreEntity> byName = repository.findByName(genreName.name());
            if (byName.isEmpty()) {
                repository.save(new GenreEntity(genreName.name()));
            }
        }
    }
}
