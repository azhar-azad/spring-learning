package com.azad.bazaar.controllers;

import com.azad.bazaar.models.Item;
import com.azad.bazaar.models.dtos.ItemDto;
import com.azad.bazaar.models.responses.ApiResponse;
import com.azad.bazaar.models.responses.ItemResponse;
import com.azad.bazaar.security.auth.AuthService;
import com.azad.bazaar.services.ItemService;
import com.azad.bazaar.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/items")
public class ItemController {

    @Autowired
    private ModelMapper modelMapper;

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // Add an item to list
    @PostMapping
    public ResponseEntity<ApiResponse> createItem(@Valid @RequestBody Item request) {

        ItemDto itemDto = modelMapper.map(request, ItemDto.class);

        ItemDto savedItemDto = itemService.create(itemDto);

        return new ResponseEntity<>(new ApiResponse(true, "Item Added To List",
                Collections.singletonMap("items", Collections.singletonList(modelMapper.map(savedItemDto, ItemResponse.class)))),
                HttpStatus.CREATED);
    }

    // Show all items in list
    @GetMapping
    public ResponseEntity<ApiResponse> getAllItems(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page > 0) page--;

        List<ItemDto> itemDtos = itemService.getAll(new PagingAndSorting(page, limit, sort, order));

        if (itemDtos.size() == 0)
            return new ResponseEntity<>(new ApiResponse(true, "No Item Found", null),
                    HttpStatus.NO_CONTENT);

        List<ItemResponse> itemResponses = itemDtos.stream()
                .map(itemDto -> {
                    ItemResponse itemResponse = modelMapper.map(itemDto, ItemResponse.class);
                    itemResponse.setAddedBy(null);
                    return itemResponse;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ApiResponse(true, "All Items Listed",
                Collections.singletonMap("items", itemResponses)), HttpStatus.OK);
    }

    // Click on single item to view
    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> getItem(@Valid @PathVariable Long id) {

        ItemDto itemDto = itemService.getById(id);

        return new ResponseEntity<>(new ApiResponse(true, "Item Fetched",
                Collections.singletonMap("items", Collections.singletonList(modelMapper.map(itemDto, ItemResponse.class)))),
                HttpStatus.OK);
    }



    // Click on single item to update
    // Click on single item to delete

    // Click on "Bought" button
    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> itemBought(@Valid @PathVariable Long id, @RequestBody Item requestBody) {



        return null;
    }
}
