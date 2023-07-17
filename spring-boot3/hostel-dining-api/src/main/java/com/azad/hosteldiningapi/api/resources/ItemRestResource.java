package com.azad.hosteldiningapi.api.resources;

import com.azad.hosteldiningapi.api.assembers.ItemResponseModelAssembler;
import com.azad.hosteldiningapi.common.PagingAndSorting;
import com.azad.hosteldiningapi.common.generics.GenericApiRestController;
import com.azad.hosteldiningapi.common.utils.ApiUtils;
import com.azad.hosteldiningapi.models.item.ItemDto;
import com.azad.hosteldiningapi.models.item.ItemRequest;
import com.azad.hosteldiningapi.models.item.ItemResponse;
import com.azad.hosteldiningapi.services.ItemService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/items")
public class ItemRestResource implements GenericApiRestController<ItemRequest, ItemResponse> {

    private final String BASE_URL = "/api/v1/items";
    private final ItemResponse globalItemResponse = new ItemResponse();

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiUtils apiUtils;

    private final ItemService service;
    private final ItemResponseModelAssembler assembler;

    @Autowired
    public ItemRestResource(ItemService service, ItemResponseModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    @Override
    public ResponseEntity<EntityModel<ItemResponse>> createEntity(@Valid @RequestBody ItemRequest request) {

        apiUtils.printRequestInfo(ItemRestResource.class, BASE_URL, "POST", "MANAGER");

        ItemDto dtoFromService;
        try {
            dtoFromService = service.create(modelMapper.map(request, ItemDto.class));
        } catch (Exception ex) {
            ApiUtils.logError(ItemRestResource.class, "CREATE Item :: FAILED (Exception Occurred)");
            return ResponseEntity.internalServerError().build();
        }
        ApiUtils.logInfo(ItemRestResource.class, "CREATE Item :: SUCCESS");

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(dtoFromService, ItemResponse.class)),
                HttpStatus.CREATED);
    }

    @GetMapping
    @Override
    public ResponseEntity<CollectionModel<EntityModel<ItemResponse>>> getAllEntities(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order) {

        apiUtils.printRequestInfo(ItemRestResource.class, BASE_URL, "GET", "MANAGER, STUDENT");

        if (page < 0) page = 0;

        List<ItemDto> dtosFromService;
        try {
            dtosFromService = service.getAll(new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        } catch (Exception ex) {
            ApiUtils.logError(ItemRestResource.class, "GET ALL Items :: FAILED (Exception Occurred)");
            return ResponseEntity.internalServerError().build();
        }
        ApiUtils.logInfo(ItemRestResource.class, "GET ALL Items :: SUCCESS");

        if (dtosFromService == null || dtosFromService.size() == 0)
            return ResponseEntity.noContent().build();

        return new ResponseEntity<>(
                assembler.getCollectionModel(
                        dtosFromService.stream()
                                .map(dto -> modelMapper.map(dto, ItemResponse.class))
                                .collect(Collectors.toList()),
                        new PagingAndSorting(page, limit, sort, order)),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EntityModel<ItemResponse>> getEntity(Long id) {
        return null;
    }

    @GetMapping(path = "/{itemUid}")
    @Override
    public ResponseEntity<EntityModel<ItemResponse>> getEntity(@Valid @PathVariable("itemUid") String uid) {

        apiUtils.printRequestInfo(ItemRestResource.class, BASE_URL+"/"+uid, "GET", "MANAGER, STUDENT");

        ItemDto dtoFromService;
        try {
            dtoFromService = service.getByUid(uid);
        } catch (Exception ex) {
            ApiUtils.logError(ItemRestResource.class, "GET Item :: FAILED (Exception Occurred)");
            return ResponseEntity.internalServerError().build();
        }
        ApiUtils.logInfo(ItemRestResource.class, "GET Item :: SUCCESS");

        if (dtoFromService == null)
            return ResponseEntity.notFound().build();

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(dtoFromService, ItemResponse.class)),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EntityModel<ItemResponse>> updateEntity(Long id, ItemRequest updatedRequest) {
        return null;
    }

    @PutMapping(path = "/{itemUid}")
    @Override
    public ResponseEntity<EntityModel<ItemResponse>> updateEntity(
            @Valid @PathVariable("itemUid") String uid, @RequestBody ItemRequest updatedRequest) {

        apiUtils.printRequestInfo(ItemRestResource.class, BASE_URL+"/"+uid, "PUT", "MANAGER");

        ItemDto dtoFromService;
        try {
            dtoFromService = service.updateByUid(uid, modelMapper.map(updatedRequest, ItemDto.class));
        } catch (Exception ex) {
            ApiUtils.logError(ItemRestResource.class, "UPDATE Item :: FAILED (Exception Occurred)");
            return ResponseEntity.internalServerError().build();
        }
        ApiUtils.logInfo(ItemRestResource.class, "UPDATE Item :: SUCCESS");

        return new ResponseEntity<>(assembler.toModel(modelMapper.map(dtoFromService, ItemResponse.class)),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteEntity(Long id) {
        return null;
    }

    @DeleteMapping(path = "/{itemUid}")
    @Override
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("itemUid") String uid) {

        apiUtils.printRequestInfo(ItemRestResource.class, BASE_URL+"/"+uid, "DELETE", "MANAGER");

        try {
            service.deleteByUid(uid);
        } catch (Exception ex) {
            ApiUtils.logError(ItemRestResource.class, "DELETE Item :: FAILED (Exception Occurred)");
            return ResponseEntity.internalServerError().build();
        }
        ApiUtils.logInfo(ItemRestResource.class, "DELETE Item :: SUCCESS");

        return ResponseEntity.ok("Item Deleted");
    }
}
