package com.azad.tennis_score_api.assembler;

import com.azad.tennis_score_api.common.ApiResponseModelAssembler;
import com.azad.tennis_score_api.common.AppUtils;
import com.azad.tennis_score_api.controller.PlayerRestController;
import com.azad.tennis_score_api.model.dto.PlayerDto;
import com.azad.tennis_score_api.repository.PlayerRepository;
import lombok.Setter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlayerModelAssembler implements ApiResponseModelAssembler<PlayerDto> {

    @Setter
    private AppUtils appUtils;

    @Setter
    private PlayerRepository repository;

    @Override
    public EntityModel<PlayerDto> toModel(PlayerDto entity) {
        EntityModel<PlayerDto> entityModel = EntityModel.of(entity);
        entityModel.add(linkTo(methodOn(PlayerRestController.class)
                .get(entity.getId()))
                .withSelfRel());
        entityModel.add(linkTo(methodOn(PlayerRestController.class)
                .getAll(appUtils.getDefaultReqParamMap()))
                .withRel(IanaLinkRelations.COLLECTION));

        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<PlayerDto>> toCollectionModel(Iterable<? extends PlayerDto> entities) {
        List<EntityModel<PlayerDto>> entityModels =
                StreamSupport.stream(entities.spliterator(), false)
                        .map(this::toModel)
                        .toList();

        return CollectionModel.of(entityModels);
    }

    public CollectionModel<EntityModel<PlayerDto>> getCollectionModel(
            Iterable<? extends PlayerDto> entities, Map<String, String> params) {
        CollectionModel<EntityModel<PlayerDto>> collectionModel = toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(PlayerRestController.class)
                .getAll(params))
                .withSelfRel());

        long totalRecords = repository.count();

        if (appUtils.getPage(params.get("page")) > 1)
            addPrevLink(collectionModel, params);
        if (totalRecords > ((long) appUtils.getPage(params.get("page")) * appUtils.getLimit(params.get("limit"))))
            addNextLink(collectionModel, params);

        return collectionModel;
    }

    public void addPrevLink(CollectionModel<EntityModel<PlayerDto>> collectionModel, Map<String, String> params) {
        params.put("page", String.valueOf(appUtils.getPage(params.get("page")) + 1));
        collectionModel.add(linkTo(methodOn(PlayerRestController.class)
                .getAll(params))
                .withRel(IanaLinkRelations.NEXT));
    }

    public void addNextLink(CollectionModel<EntityModel<PlayerDto>> collectionModel, Map<String, String> params) {
        params.put("page", String.valueOf(appUtils.getPage(params.get("page")) - 1));
        collectionModel.add(linkTo(methodOn(PlayerRestController.class)
                .getAll(params))
                .withRel(IanaLinkRelations.PREV));
    }
}
