package com.azad.hosteldiningapi.services;

import com.azad.hosteldiningapi.common.PagingAndSorting;
import com.azad.hosteldiningapi.common.exceptions.ResourceNotFoundException;
import com.azad.hosteldiningapi.common.exceptions.UnauthorizedAccessException;
import com.azad.hosteldiningapi.common.utils.ApiUtils;
import com.azad.hosteldiningapi.models.item.ItemDto;
import com.azad.hosteldiningapi.models.item.ItemEntity;
import com.azad.hosteldiningapi.repositories.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiUtils apiUtils;

    @Autowired
    private AuthService authService;

    private final ItemRepository repository;

    @Autowired
    public ItemServiceImpl(ItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public ItemDto create(ItemDto dto) {

        ApiUtils.logInfo(ItemServiceImpl.class, "Item :: CREATE");

        apiUtils.handleUnauthorizedUserAccess(ItemServiceImpl.class,
                "Only Manager can create a new Item", "Item CREATE >> ERROR -> UNAUTHORIZED ACCESS",
                authService.getLoggedInUser().getRole().getRoleName(), "MANAGER");

        ItemEntity item = modelMapper.map(dto, ItemEntity.class);
        item.setCreatedAt(LocalDateTime.now());

        return modelMapper.map(repository.save(item), ItemDto.class);
    }

    @Override
    public List<ItemDto> getAll(PagingAndSorting ps) {

        ApiUtils.logInfo(ItemServiceImpl.class, "Item :: GET ALL");

        List<ItemEntity> items = repository.findAll(apiUtils.getPageable(ps)).getContent();

        if (items.size() == 0)
            return null;

        return items.stream()
                .map(entity -> modelMapper.map(entity, ItemDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto getById(Long id) {
        throw new RuntimeException("Method should not be used.");
    }

    @Override
    public ItemDto getByUid(String uid) {

        ApiUtils.logInfo(ItemServiceImpl.class, "Item :: GET BY UID");

        ItemEntity item = repository.findByUid(uid).orElseThrow(
                () -> new ResourceNotFoundException("Item GET >> ERROR -> RESOURCE NOT FOUND", "ITEM", "uid: " + uid));

        return modelMapper.map(item, ItemDto.class);
    }

    @Override
    public ItemDto updateById(Long id, ItemDto updatedDto) {
        throw new RuntimeException("Method should not be used.");
    }

    @Override
    public ItemDto updateByUid(String uid, ItemDto updatedDto) {

        ApiUtils.logInfo(ItemServiceImpl.class, "Item :: UPDATE BY UID");

        apiUtils.handleUnauthorizedUserAccess(ItemServiceImpl.class,
                "Only Manager can update an Item", "Item UPDATE >> ERROR -> UNAUTHORIZED ACCESS",
                authService.getLoggedInUser().getRole().getRoleName(), "MANAGER");

        ItemEntity item = repository.findByUid(uid).orElseThrow(
                () -> new ResourceNotFoundException("Item UPDATE >> ERROR -> RESOURCE NOT FOUND", "ITEM", "uid: " + uid));

        if (updatedDto.getItemName() != null)
            item.setItemName(updatedDto.getItemName());
        if (updatedDto.getPrice() != null)
            item.setPrice(updatedDto.getPrice());
        if (updatedDto.getDescription() != null)
            item.setDescription(updatedDto.getDescription());
        if (updatedDto.getImageUrl() != null)
            item.setImageUrl(updatedDto.getImageUrl());
        item.setModifiedAt(LocalDateTime.now());

        return modelMapper.map(repository.save(item), ItemDto.class);
    }

    @Override
    public void deleteById(Long id) {
        throw new RuntimeException("Method should not be used.");
    }

    @Override
    public void deleteByUid(String uid) {

        ApiUtils.logInfo(ItemServiceImpl.class, "Item :: DELETE BY UID");

        apiUtils.handleUnauthorizedUserAccess(ItemServiceImpl.class,
                "Only Manager can delete an Item", "Item DELETE >> ERROR -> UNAUTHORIZED ACCESS",
                authService.getLoggedInUser().getRole().getRoleName(), "MANAGER");

        ItemEntity item = repository.findByUid(uid).orElseThrow(
                () -> new ResourceNotFoundException("Item DELETE >> ERROR -> RESOURCE NOT FOUND", "ITEM", "uid: " + uid));

        repository.delete(item);
    }
}
