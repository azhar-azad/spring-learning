package com.azad.imdbapi.assemblers;

import com.azad.imdbapi.controllers.MovieRestController;
import com.azad.imdbapi.repos.MovieRepository;
import com.azad.imdbapi.responses.MovieResponse;
import com.azad.imdbapi.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MovieModelAssembler implements RepresentationModelAssembler<MovieResponse, EntityModel<MovieResponse>> {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private MovieRepository repository;

    @Override
    public EntityModel<MovieResponse> toModel(MovieResponse entity) {

        EntityModel<MovieResponse> entityModel = EntityModel.of(entity);

        entityModel.add(linkTo(methodOn(MovieRestController.class).getMovie(entity.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(MovieRestController.class)
                .getAllMovies(appUtils.getDefaultReqParamMap())).withRel(IanaLinkRelations.COLLECTION));

        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<MovieResponse>> toCollectionModel(Iterable<? extends MovieResponse> entities) {

        List<EntityModel<MovieResponse>> entityModels = new ArrayList<>();

        for (MovieResponse entity: entities)
            entityModels.add(toModel(entity));

        return CollectionModel.of(entityModels);
    }

    public CollectionModel<EntityModel<MovieResponse>> getCollectionModel(Iterable<? extends MovieResponse> entities, Map<String, String> params) {

        CollectionModel<EntityModel<MovieResponse>> collectionModel = toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(MovieRestController.class).getAllMovies(params)).withSelfRel());

        long totalRecords = repository.count();

        if (appUtils.getPage(params.get("page")) > 1)
            addPrevLink(collectionModel, params);
        if (totalRecords > ((long) appUtils.getPage(params.get("page")) * appUtils.getLimit(params.get("limit"))))
            addNextList(collectionModel, params);

        return collectionModel;
    }

    private void addNextList(CollectionModel<EntityModel<MovieResponse>> collectionModel, Map<String, String> params) {
        params.put("page", String.valueOf(appUtils.getPage(params.get("page")) + 1));
        collectionModel.add(linkTo(methodOn(MovieRestController.class).getAllMovies(params)).withRel(IanaLinkRelations.NEXT));
    }

    private void addPrevLink(CollectionModel<EntityModel<MovieResponse>> collectionModel, Map<String, String> params) {
        params.put("page", String.valueOf(appUtils.getPage(params.get("page")) - 1));
        collectionModel.add(linkTo(methodOn(MovieRestController.class).getAllMovies(params)).withRel(IanaLinkRelations.PREV));
    }
}
