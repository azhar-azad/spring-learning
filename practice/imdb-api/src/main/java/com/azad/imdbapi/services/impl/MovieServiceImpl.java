package com.azad.imdbapi.services.impl;

import com.azad.imdbapi.dtos.MovieDto;
import com.azad.imdbapi.entities.GenreEntity;
import com.azad.imdbapi.entities.ImdbUserEntity;
import com.azad.imdbapi.entities.MovieEntity;
import com.azad.imdbapi.entities.TitleGenreEntity;
import com.azad.imdbapi.models.Genre;
import com.azad.imdbapi.repos.GenreRepository;
import com.azad.imdbapi.repos.MovieRepository;
import com.azad.imdbapi.repos.TitleGenreRepository;
import com.azad.imdbapi.security.auth.AuthService;
import com.azad.imdbapi.services.MovieService;
import com.azad.imdbapi.utils.AppUtils;
import com.azad.imdbapi.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private AuthService authService;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TitleGenreRepository titleGenreRepository;

    private final MovieRepository repository;

    @Autowired
    public MovieServiceImpl(MovieRepository repository) {
        this.repository = repository;
    }

    /**
     * Only admins should have access
     * */
    @Override
    public MovieDto create(MovieDto request) {

        ImdbUserEntity loggedInUser = authService.getLoggedInUser();

        if (!loggedInUser.getRole().getRoleName().equalsIgnoreCase("ADMIN"))
            throw new RuntimeException("Unauthorized. Needs ADMIN access");

        MovieEntity movieEntity = modelMapper.map(request, MovieEntity.class);
        movieEntity.setPersons(null);

        MovieEntity savedMovie = repository.save(movieEntity);

        List<GenreEntity> genreEntities = saveGenres(request.getGenres());

        for (GenreEntity genreEntity: genreEntities) {
            List<TitleGenreEntity> byGenreIdAndMovieId = titleGenreRepository.findByGenreIdAndMovieId(genreEntity.getId(), movieEntity.getId());
            if (byGenreIdAndMovieId.size() == 0) {
                TitleGenreEntity titleGenreEntity = new TitleGenreEntity();
                titleGenreEntity.setMovieId(savedMovie.getId());
                titleGenreEntity.setGenreId(genreEntity.getId());
                titleGenreRepository.save(titleGenreEntity);
            }
        }

        MovieDto movieDto = modelMapper.map(savedMovie, MovieDto.class);
        movieDto.setGenres(genreEntities.stream()
                .map(genreEntity -> modelMapper.map(genreEntity, Genre.class))
                .collect(Collectors.toList()));

        return movieDto;
    }

    private List<GenreEntity> saveGenres(List<Genre> genres) {

        return genres.stream()
                .map(genre -> {
                    GenreEntity savedGenre = genreRepository.findByGenreName(genre.getGenreName()).orElse(null);
                    if (savedGenre != null) {
                        return savedGenre;
                    }
                    else {
                        GenreEntity genreEntity = modelMapper.map(genre, GenreEntity.class);
                        return genreRepository.save(genreEntity);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public MovieDto getById(Long id) {
        return null;
    }

    @Override
    public MovieDto updateById(Long id, MovieDto updatedRequest) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
