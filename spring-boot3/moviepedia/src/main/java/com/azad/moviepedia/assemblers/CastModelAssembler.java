package com.azad.moviepedia.assemblers;

import com.azad.moviepedia.common.AppUtils;
import com.azad.moviepedia.controllers.CastRestController;
import com.azad.moviepedia.models.dtos.CastDto;
import com.azad.moviepedia.repositories.CastRepository;
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
public class CastModelAssembler implements RepresentationModelAssembler<CastDto, EntityModel<CastDto>> {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private CastRepository repository;

    @Override
    public EntityModel<CastDto> toModel(CastDto entity) {
        EntityModel<CastDto> entityModel = EntityModel.of(entity);
        entityModel.add(linkTo(methodOn(CastRestController.class)
                .getEntity(entity.getId()))
                .withSelfRel());
        entityModel.add(linkTo(methodOn(CastRestController.class)
                .getAllEntity(appUtils.getDefaultReqParamMap()))
                .withRel(IanaLinkRelations.COLLECTION));

        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<CastDto>> toCollectionModel(Iterable<? extends CastDto> entities) {

        List<EntityModel<CastDto>> entityModels =
                StreamSupport.stream(entities.spliterator(), false)
                        .map(this::toModel)
                        .toList();

        return CollectionModel.of(entityModels);

    }

    public CollectionModel<EntityModel<CastDto>> getCollectionModel(Iterable<? extends CastDto> entities, Map<String, String> params) {

        CollectionModel<EntityModel<CastDto>> collectionModel = toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(CastRestController.class)
                .getAllEntity(params))
                .withSelfRel());

        long totalRecords = repository.count();

        if (appUtils.getPage(params.get("page")) > 1)
            addPrevLink(collectionModel, params);
        if (totalRecords > ((long) appUtils.getPage("page") * appUtils.getLimit(params.get("limit"))))
            addNextLink(collectionModel, params);

        return collectionModel;
    }

    private void addPrevLink(CollectionModel<EntityModel<CastDto>> collectionModel, Map<String, String> params) {
        params.put("page", String.valueOf(appUtils.getPage(params.get("page")) + 1));
        collectionModel.add(linkTo(methodOn(CastRestController.class)
                .getAllEntity(params))
                .withRel(IanaLinkRelations.NEXT));
    }

    private void addNextLink(CollectionModel<EntityModel<CastDto>> collectionModel, Map<String, String> params) {
        params.put("page", String.valueOf(appUtils.getPage(params.get("page")) - 1));
        collectionModel.add(linkTo(methodOn(CastRestController.class)
                .getAllEntity(params))
                .withRel(IanaLinkRelations.PREV));
    }
}
