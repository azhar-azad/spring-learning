package com.azad.ListShare.services.impl;

import com.azad.ListShare.models.Item;
import com.azad.ListShare.models.Member;
import com.azad.ListShare.models.dtos.CustomListDto;
import com.azad.ListShare.models.dtos.ItemDto;
import com.azad.ListShare.models.entities.CustomListEntity;
import com.azad.ListShare.models.entities.ItemEntity;
import com.azad.ListShare.models.entities.MemberEntity;
import com.azad.ListShare.repos.CustomListRepository;
import com.azad.ListShare.repos.ItemRepository;
import com.azad.ListShare.security.auth.AuthService;
import com.azad.ListShare.services.CustomListService;
import com.azad.ListShare.utils.AppUtils;
import com.azad.ListShare.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomListServiceImpl implements CustomListService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private AuthService authService;

    @Autowired
    private ItemRepository itemRepository;

    private final CustomListRepository customListRepository;

    @Autowired
    public CustomListServiceImpl(CustomListRepository customListRepository) {
        this.customListRepository = customListRepository;
    }

    @Override
    public CustomListDto create(CustomListDto request) {

        MemberEntity loggedInMember = authService.getLoggedInMember();

        CustomListEntity customListEntity = modelMapper.map(request, CustomListEntity.class);
        customListEntity.setItems(null); // items will be mapped after they are being individually saved
        customListEntity.setMember(loggedInMember);
        customListEntity.setCreatedDate(LocalDate.now());
        customListEntity.setModifiedTime(LocalDateTime.now());

        CustomListEntity customList = customListRepository.save(customListEntity);

        List<Item> savedItems = request.getItems().stream()
                .map(item -> {
                    ItemEntity itemEntity = modelMapper.map(item, ItemEntity.class);
                    itemEntity.setList(customList); // items are being mapped with the list
                    ItemEntity savedItemEntity = itemRepository.save(itemEntity);
                    return modelMapper.map(savedItemEntity, Item.class);
                })
                .collect(Collectors.toList());

        CustomListDto customListDto = modelMapper.map(customList, CustomListDto.class);
        customListDto.setUserId(loggedInMember.getId());
        customListDto.setItems(savedItems);

        return customListDto;
    }

    @Override
    public List<CustomListDto> getAll(PagingAndSorting ps) {

        MemberEntity loggedInMember = authService.getLoggedInMember();

        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        Optional<List<CustomListEntity>> byUserId = customListRepository.findByUserId(loggedInMember.getId(), pageable);
        if (!byUserId.isPresent())
            return null;

        List<CustomListEntity> allListsFromDb = byUserId.get();
        if (allListsFromDb.size() == 0)
            return null;

        return allListsFromDb.stream()
                .map(customListEntity -> {
                    CustomListDto customListDto = modelMapper.map(customListEntity, CustomListDto.class);
                    customListDto.setUserId(customListEntity.getMember().getId());
                    return customListDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomListDto getById(Long id) {

        MemberEntity loggedInMember = authService.getLoggedInMember();

        CustomListEntity customListFromDb = customListRepository.findById(id).orElseThrow(
                () -> new RuntimeException("List not found with id: " + id));

        if (!Objects.equals(customListFromDb.getMember().getId(), loggedInMember.getId())) // logged-in user is not the owner
            if (!authService.loggedInUserIsAdmin()) // logged-in user is not an admin too
                throw new RuntimeException("Resource not authorized for " + loggedInMember.getUsername() + " with id: " + loggedInMember.getId());

        CustomListDto customListDto = modelMapper.map(customListFromDb, CustomListDto.class);
        customListDto.setUserId(customListFromDb.getMember().getId());

        return customListDto;
    }

    @Override
    public CustomListDto updateById(Long id, CustomListDto updatedRequest) {

        MemberEntity loggedInMember = authService.getLoggedInMember();

        CustomListEntity customListFromDb = customListRepository.findById(id).orElseThrow(
                () -> new RuntimeException("List not found with id: " + id));

        if (!Objects.equals(customListFromDb.getMember().getId(), loggedInMember.getId())) // logged-in user is not the owner
            if (!authService.loggedInUserIsAdmin()) // logged-in user is not an admin too
                throw new RuntimeException("Resource not authorized for " + loggedInMember.getUsername() + " with id: " + loggedInMember.getId());

        if (updatedRequest.getListName() != null)
            customListFromDb.setListName(updatedRequest.getListName());
        if (updatedRequest.getDescription() != null)
            customListFromDb.setDescription(updatedRequest.getDescription());
        customListFromDb.setModifiedTime(LocalDateTime.now());
        CustomListEntity customListEntity = customListRepository.save(customListFromDb);

        List<Item> updatedRequestItems = updatedRequest.getItems();
        if (updatedRequestItems != null) {
            List<ItemEntity> customListFromDbItems = customListFromDb.getItems();
            List<Item> convertedItems = customListFromDbItems.stream()
                    .map(itemEntity -> modelMapper.map(itemEntity, Item.class))
                    .collect(Collectors.toList());

            for (Item item: updatedRequestItems) {
                if (convertedItems.contains(item)) {
//                    ItemEntity itemEntity = itemRepository.findById()
                }
            }
        }

        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
