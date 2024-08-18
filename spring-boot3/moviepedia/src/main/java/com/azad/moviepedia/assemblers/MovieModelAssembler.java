package com.azad.moviepedia.assemblers;

import com.azad.moviepedia.common.AppUtils;
import com.azad.moviepedia.controllers.DirectorRestController;
import com.azad.moviepedia.controllers.MovieRestController;
import com.azad.moviepedia.models.dtos.MovieDto;
import com.azad.moviepedia.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MovieModelAssembler implements RepresentationModelAssembler<MovieDto, EntityModel<MovieDto>> {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private MovieRepository repository;

    @Override
    public EntityModel<MovieDto> toModel(MovieDto entity) {
        EntityModel<MovieDto> entityModel = EntityModel.of(entity);
        entityModel.add(linkTo(methodOn(MovieRestController.class)
                .getEntity(entity.getId()))
                .withSelfRel());
        entityModel.add(linkTo(methodOn(MovieRestController.class)
                .getAllEntity(appUtils.getDefaultReqParamMap()))
                .withRel(IanaLinkRelations.COLLECTION));

        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<MovieDto>> toCollectionModel(Iterable<? extends MovieDto> entities) {
        List<EntityModel<MovieDto>> entityModels =
                StreamSupport.stream(entities.spliterator(), false)
                        .map(this::toModel)
                        .toList();

        return CollectionModel.of(entityModels);
    }

    public CollectionModel<EntityModel<MovieDto>> getCollectionModel(Iterable<? extends MovieDto> entities, Map<String, String> params) {

        CollectionModel<EntityModel<MovieDto>> collectionModel = toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(MovieRestController.class)
                .getAllEntity(params))
                .withSelfRel());

        long totalRecords = repository.count();

        if (appUtils.getPage(params.get("page")) > 1)
            addPrevLink(collectionModel, params);
        if (totalRecords > ((long) appUtils.getPage("page") * appUtils.getLimit(params.get("limit"))))
            addNextLink(collectionModel, params);

        return collectionModel;
    }

    private void addPrevLink(CollectionModel<EntityModel<MovieDto>> collectionModel, Map<String, String> params) {
        params.put("page", String.valueOf(appUtils.getPage(params.get("page")) + 1));
        collectionModel.add(linkTo(methodOn(DirectorRestController.class)
                .getAllEntity(params))
                .withRel(IanaLinkRelations.NEXT));
    }

    private void addNextLink(CollectionModel<EntityModel<MovieDto>> collectionModel, Map<String, String> params) {
        params.put("page", String.valueOf(appUtils.getPage(params.get("page")) - 1));
        collectionModel.add(linkTo(methodOn(DirectorRestController.class)
                .getAllEntity(params))
                .withRel(IanaLinkRelations.PREV));
    }
}
