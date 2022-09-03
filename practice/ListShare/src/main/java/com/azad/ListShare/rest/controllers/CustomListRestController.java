package com.azad.ListShare.rest.controllers;

import com.azad.ListShare.models.dtos.CustomListDto;
import com.azad.ListShare.models.requests.CustomListRequest;
import com.azad.ListShare.models.responses.CustomListResponse;
import com.azad.ListShare.rest.assemblers.CustomListModelAssembler;
import com.azad.ListShare.services.CustomListService;
import com.azad.ListShare.utils.AppUtils;
import com.azad.ListShare.utils.PagingAndSorting;
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
@RequestMapping(path = "/api/v1/lists")
public class CustomListRestController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    private final CustomListService service;
    private final CustomListModelAssembler assembler;

    @Autowired
    public CustomListRestController(CustomListService service, CustomListModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<CustomListResponse>> createList(@Valid @RequestBody CustomListRequest customListRequest) {

        CustomListDto customListDto = modelMapper.map(customListRequest, CustomListDto.class);

        CustomListDto savedCustomListDto = service.create(customListDto);

        CustomListResponse customListResponse = modelMapper.map(savedCustomListDto, CustomListResponse.class);
        EntityModel<CustomListResponse> entityModel = assembler.toModel(customListResponse);

        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CustomListResponse>>> getAllLists(@Valid @RequestParam Map<String, String> requestParams) {

        int page = Math.max(appUtils.getIntParamValue(requestParams, "page"), 0);
        int limit = appUtils.getIntParamValue(requestParams, "limit");
        String sort = appUtils.getStringParamValue(requestParams, "sort");
        String order = appUtils.getStringParamValue(requestParams, "order");

        List<CustomListDto> allListsFromService = service.getAll(
                new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        if (allListsFromService == null || allListsFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<CustomListResponse> customListResponses = allListsFromService.stream()
                .map(customListDto -> modelMapper.map(customListDto, CustomListResponse.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<CustomListResponse>> collectionModel = assembler.getCollectionModel(customListResponses, requestParams);

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EntityModel<CustomListResponse>> getList(@Valid @PathVariable("id") Long id) {

        CustomListDto customListFromService = service.getById(id);
        if (customListFromService == null)
            return ResponseEntity.notFound().build();

        CustomListResponse customListResponse = modelMapper.map(customListFromService, CustomListResponse.class);
        EntityModel<CustomListResponse> entityModel = assembler.toModel(customListResponse);

        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<EntityModel<CustomListResponse>> updateList(@Valid @PathVariable("id") Long id, @RequestBody CustomListRequest updatedRequest) {

        CustomListDto customListFromService = service.updateById(id, modelMapper.map(updatedRequest, CustomListDto.class));

        CustomListResponse customListResponse = modelMapper.map(customListFromService, CustomListResponse.class);
        EntityModel<CustomListResponse> entityModel = assembler.toModel(customListResponse);

        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }
}
