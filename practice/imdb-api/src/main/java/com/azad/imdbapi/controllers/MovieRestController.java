package com.azad.imdbapi.controllers;

import com.azad.imdbapi.assemblers.MovieModelAssembler;
import com.azad.imdbapi.dtos.MovieDto;
import com.azad.imdbapi.models.Genre;
import com.azad.imdbapi.requests.MovieRequest;
import com.azad.imdbapi.responses.MovieResponse;
import com.azad.imdbapi.services.MovieService;
import com.azad.imdbapi.utils.AppUtils;
import com.azad.imdbapi.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/movies")
public class MovieRestController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    private final MovieService service;
    private final MovieModelAssembler assembler;

    @Autowired
    public MovieRestController(MovieService service, MovieModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    /**
     * @Path: /api/v1/movies
     * @Method: POST
     * @Desc: Logged-in admin can create movies
     * @Access: ADMIN
     * */
    @PostMapping
    public ResponseEntity<EntityModel<MovieResponse>> createMovie(@Valid @RequestBody MovieRequest request) {

        MovieDto movieDto = modelMapper.map(request, MovieDto.class);

        MovieDto savedMovie = service.create(movieDto);

        MovieResponse movieResponse = modelMapper.map(savedMovie, MovieResponse.class);
        movieResponse.setGenres(savedMovie.getGenres().stream().map(Genre::getGenreName).collect(Collectors.toList()));

        EntityModel<MovieResponse> responseEntityModel = assembler.toModel(movieResponse);
        return new ResponseEntity<>(responseEntityModel, HttpStatus.CREATED);
    }

    /**
     * @Path: /api/v1/movies
     * @Method: GET
     * @Desc: Logged-in users will have access to get all movies
     * @Access: ADMIN, USER
     * */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<MovieResponse>>> getAllMovies(@Valid @RequestParam Map<String, String> params) {

        int page = Math.max(appUtils.getPage(params.get("page")), 0);
        int limit = appUtils.getLimit(params.get("limit"));
        String sort = params.get("sort");
        String order = params.get("order");

        List<MovieDto> allMoviesFromService = service.getAll(new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        if (allMoviesFromService == null || allMoviesFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<MovieResponse> movieResponses = allMoviesFromService.stream()
                .map(movieDto -> modelMapper.map(movieDto, MovieResponse.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<MovieResponse>> responseCollectionModel = assembler.getCollectionModel(movieResponses, params);

        return new ResponseEntity<>(responseCollectionModel, HttpStatus.OK);
    }

    /**
     * @Path: /api/v1/movies/id
     * @Method: GET
     * @Desc: Logged-in users will have access to get movie by id
     * @Access: ADMIN, USER
     * */
    @GetMapping(path = "/{id}")
    public ResponseEntity<EntityModel<MovieResponse>> getMovie(@Valid @PathVariable("id") Long id) {

        MovieDto movieFromService = service.getById(id);
        if (movieFromService == null)
            return ResponseEntity.notFound().build();

        EntityModel<MovieResponse> responseEntityModel = assembler.toModel(modelMapper.map(movieFromService, MovieResponse.class));

        return new ResponseEntity<>(responseEntityModel, HttpStatus.OK);
    }

    /**
     * @Path: /api/v1/movies/id
     * @Method: PUT
     * @Desc: Logged-in users will have access to modify any movie data by id
     * @Access: ADMIN, USER
     * */
    @PutMapping(path = "/{id}")
    public ResponseEntity<EntityModel<MovieResponse>> updateMovie(@Valid @PathVariable("id") Long id, @RequestBody MovieRequest updatedRequest) {

        MovieDto movieFromService = service.updateById(id, modelMapper.map(updatedRequest, MovieDto.class));

        EntityModel<MovieResponse> responseEntityModel = assembler.toModel(modelMapper.map(movieFromService, MovieResponse.class));

        return new ResponseEntity<>(responseEntityModel, HttpStatus.OK);
    }

    /**
     * @Path: /api/v1/movies/id
     * @Method: DELETE
     * @Desc: Logged-in Admins can delete any movie by id
     * @Access: ADMIN
     * */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteMovie(@Valid @PathVariable("id") Long id) {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
