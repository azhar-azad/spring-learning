package com.azad.bazaar.services.impl;

import com.azad.bazaar.models.Member;
import com.azad.bazaar.models.dtos.ItemDto;
import com.azad.bazaar.models.entities.ItemEntity;
import com.azad.bazaar.repos.ItemRepo;
import com.azad.bazaar.security.auth.AuthService;
import com.azad.bazaar.services.ItemService;
import com.azad.bazaar.utils.AppUtils;
import com.azad.bazaar.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthService authService;

    private final ItemRepo itemRepo;

    @Autowired
    public ItemServiceImpl(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }

    @Override
    public ItemDto create(ItemDto request) {

        ItemEntity itemEntity = modelMapper.map(request, ItemEntity.class);
        itemEntity.setAddedBy(authService.getLoggedInMember());

        return modelMapper.map(itemRepo.save(itemEntity), ItemDto.class);
    }

    @Override
    public List<ItemDto> getAll(PagingAndSorting ps) {

        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else {
            Sort sort = appUtils.getSortBy(ps.getSort(), ps.getOrder());
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), sort);
        }

        List<ItemEntity> itemEntities = itemRepo.findAll(pageable).getContent();

        return itemEntities.stream()
                .map(itemEntity -> {
                    ItemDto itemDto = modelMapper.map(itemEntity, ItemDto.class);
                    Member addedBy = itemDto.getAddedBy();
                    itemDto.setAddedByName(addedBy.getFirstName() + " " + addedBy.getLastName());
                    return itemDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto getById(Long id) {

        ItemEntity itemEntity = itemRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Item not found with id " + id));

        return modelMapper.map(itemEntity, ItemDto.class);
    }

    @Override
    public ItemDto updateById(Long id, ItemDto updatedRequest) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
