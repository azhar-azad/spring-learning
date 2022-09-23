package com.azad.imdbapi.services.impl;

import com.azad.imdbapi.dtos.MovieDto;
import com.azad.imdbapi.entities.*;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
     * Only admins can create new movie entity
     * */
    @Override
    public MovieDto create(MovieDto request) {

        ImdbUserEntity loggedInUser = authService.getLoggedInUser();

        if (!loggedInUser.getRole().getRoleName().equalsIgnoreCase("ADMIN"))
            throw new RuntimeException("Unauthorized. Needs ADMIN access");

        MovieEntity movieEntity = modelMapper.map(request, MovieEntity.class);
        movieEntity.setPersons(null);

        MovieEntity savedMovie = repository.save(movieEntity);

        List<GenreEntity> savedGenres = saveGenres(request.getGenres());

        mapMovieToGenres(savedMovie, savedGenres);

        MovieDto movieDto = modelMapper.map(savedMovie, MovieDto.class);
        movieDto.setGenres(savedGenres.stream()
                .map(genreEntity -> modelMapper.map(genreEntity, Genre.class))
                .collect(Collectors.toList()));

        return movieDto;
    }

    @Override
    public List<MovieDto> getAll(PagingAndSorting ps) {

        Pageable pageable;
        if (ps.getSort() == null)
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        List<MovieEntity> allMoviesFromDb = repository.findAll(pageable).getContent();
        if (allMoviesFromDb.size() == 0)
            return null;

        return getMovieDtosFromMovieEntities(allMoviesFromDb);
    }

    @Override
    public List<MovieDto> getAllByGenre(String genreName) {

        GenreEntity genreFromDb = genreRepository.findByGenreName(genreName).orElse(null);
        if (genreFromDb == null)
            return null;

        List<TitleGenreEntity> byGenreId = titleGenreRepository.findByGenreId(genreFromDb.getId());
        List<MovieEntity> allMoviesFromDbByGenre = byGenreId.stream()
                .map(titleGenreEntity -> repository.findById(titleGenreEntity.getMovieId()).orElse(null))
                .collect(Collectors.toList());

        if (allMoviesFromDbByGenre.size() == 0)
            return null;

        return getMovieDtosFromMovieEntities(allMoviesFromDbByGenre);
    }

    @Override
    public List<MovieDto> getAllByYear(String year, PagingAndSorting ps) {

        Pageable pageable;
        if (ps.getSort() == null)
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        List<MovieEntity> allMoviesFromDbByYear = repository.findByReleaseYear(year, pageable);
        if (allMoviesFromDbByYear.size() == 0)
            return null;

        return getMovieDtosFromMovieEntities(allMoviesFromDbByYear);
    }

    @Override
    public MovieDto getById(Long id) {

        MovieEntity movieFromDb = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Movie not found with id: " + id));

        return getMovieDtoFromMovieEntity(movieFromDb);
    }

    @Override
    public MovieDto updateById(Long id, MovieDto updatedRequest) {

        MovieEntity movieFromDb = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Movie not found with id: " + id));

        if (updatedRequest.getTitle() != null)
            movieFromDb.setTitle(updatedRequest.getTitle());
        if (updatedRequest.getSummary() != null)
            movieFromDb.setSummary(updatedRequest.getSummary());
        if (updatedRequest.getImdbId() != null)
            movieFromDb.setImdbId(updatedRequest.getImdbId());
        if (updatedRequest.getPosterPath() != null)
            movieFromDb.setPosterPath(updatedRequest.getPosterPath());
        if (updatedRequest.getRating() != null)
            movieFromDb.setRating(updatedRequest.getRating());
        if (updatedRequest.getReleaseYear() != null)
            movieFromDb.setReleaseYear(updatedRequest.getReleaseYear());
        if (updatedRequest.getRuntime() != null)
            movieFromDb.setRuntime(updatedRequest.getRuntime());

        // todo: implement genre update
//        if (updatedRequest.getGenres().size() > 0) {
//            List<GenreEntity> updatedGenres = updateGenre(movieFromDb.getId(), updatedRequest.getGenres());
//        }

        return getMovieDtoFromMovieEntity(movieFromDb);
    }

    private List<GenreEntity> updateGenre(Long movieId, List<Genre> genres) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

        MovieEntity movieFromDb = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Movie not found with id: " + id));

        repository.delete(movieFromDb);
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

    private void mapMovieToGenres(MovieEntity savedMovie, List<GenreEntity> savedGenres) {
        for (GenreEntity genreEntity: savedGenres) {
            List<TitleGenreEntity> byGenreIdAndMovieId = titleGenreRepository.findByGenreIdAndMovieId(genreEntity.getId(), savedMovie.getId());
            if (byGenreIdAndMovieId.size() == 0) {
                TitleGenreEntity titleGenreEntity = new TitleGenreEntity();
                titleGenreEntity.setMovieId(savedMovie.getId());
                titleGenreEntity.setGenreId(genreEntity.getId());
                titleGenreRepository.save(titleGenreEntity);
            }
        }
    }

    private MovieDto getMovieDtoFromMovieEntity(MovieEntity movieEntity) {
        MovieDto movieDto = modelMapper.map(movieEntity, MovieDto.class);
        movieDto.setGenres(titleGenreRepository.findByMovieId(movieEntity.getId())
                .stream()
                .map(titleGenreEntity -> {
                    GenreEntity genreEntity = genreRepository.findById(titleGenreEntity.getGenreId()).orElseThrow(
                            () -> new RuntimeException("No genre found with id: " + titleGenreEntity.getGenreId()));
                    return modelMapper.map(genreEntity, Genre.class);
                }).collect(Collectors.toList()));
        return movieDto;
    }

    private List<MovieDto> getMovieDtosFromMovieEntities(List<MovieEntity> movieEntities) {
        return movieEntities.stream()
                .map(this::getMovieDtoFromMovieEntity).collect(Collectors.toList());
    }
}
