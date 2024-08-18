package com.azad.moviepedia.assemblers;

import com.azad.moviepedia.common.AppUtils;
import com.azad.moviepedia.controllers.WriterRestController;
import com.azad.moviepedia.models.dtos.WriterDto;
import com.azad.moviepedia.repositories.WriterRepository;
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
public class WriterModelAssembler implements RepresentationModelAssembler<WriterDto, EntityModel<WriterDto>> {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private WriterRepository repository;

    @Override
    public EntityModel<WriterDto> toModel(WriterDto entity) {
        EntityModel<WriterDto> entityModel = EntityModel.of(entity);
        entityModel.add(linkTo(methodOn(WriterRestController.class)
                .getEntity(entity.getId()))
                .withSelfRel());
        entityModel.add(linkTo(methodOn(WriterRestController.class)
                .getAllEntity(appUtils.getDefaultReqParamMap()))
                .withRel(IanaLinkRelations.COLLECTION));

        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<WriterDto>> toCollectionModel(Iterable<? extends WriterDto> entities) {

        List<EntityModel<WriterDto>> entityModels =
                StreamSupport.stream(entities.spliterator(), false)
                        .map(this::toModel)
                        .toList();

        return CollectionModel.of(entityModels);

    }

    public CollectionModel<EntityModel<WriterDto>> getCollectionModel(Iterable<? extends WriterDto> entities, Map<String, String> params) {

        CollectionModel<EntityModel<WriterDto>> collectionModel = toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(WriterRestController.class)
                .getAllEntity(params))
                .withSelfRel());

        long totalRecords = repository.count();

        if (appUtils.getPage(params.get("page")) > 1)
            addPrevLink(collectionModel, params);
        if (totalRecords > ((long) appUtils.getPage("page") * appUtils.getLimit(params.get("limit"))))
            addNextLink(collectionModel, params);

        return collectionModel;
    }

    private void addPrevLink(CollectionModel<EntityModel<WriterDto>> collectionModel, Map<String, String> params) {
        params.put("page", String.valueOf(appUtils.getPage(params.get("page")) + 1));
        collectionModel.add(linkTo(methodOn(WriterRestController.class)
                .getAllEntity(params))
                .withRel(IanaLinkRelations.NEXT));
    }

    private void addNextLink(CollectionModel<EntityModel<WriterDto>> collectionModel, Map<String, String> params) {
        params.put("page", String.valueOf(appUtils.getPage(params.get("page")) - 1));
        collectionModel.add(linkTo(methodOn(WriterRestController.class)
                .getAllEntity(params))
                .withRel(IanaLinkRelations.PREV));
    }
}
