package com.azad.tennis_score_api.common;

import com.azad.tennis_score_api.model.dto.PlayerDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.Map;

public interface ApiResponseModelAssembler<DTO> extends RepresentationModelAssembler<DTO, EntityModel<DTO>> {

    @Override
    EntityModel<DTO> toModel(@NotNull DTO dto);

    @Override
    CollectionModel<EntityModel<DTO>> toCollectionModel(Iterable<? extends DTO> dtos);

    CollectionModel<EntityModel<DTO>> getCollectionModel(Iterable<? extends DTO> dtos, Map<String, String> params);

    void addPrevLink(CollectionModel<EntityModel<DTO>> collectionModel, Map<String, String> params);

    void addNextLink(CollectionModel<EntityModel<DTO>> collectionModel, Map<String, String> params);
}
