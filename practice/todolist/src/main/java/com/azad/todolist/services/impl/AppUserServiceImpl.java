package com.azad.todolist.services.impl;

import com.azad.todolist.exceptions.ResourceNotFoundException;
import com.azad.todolist.models.Roles;
import com.azad.todolist.models.dtos.AppUserDto;
import com.azad.todolist.models.entities.AppUserEntity;
import com.azad.todolist.repos.AppUserRepo;
import com.azad.todolist.services.AppUserService;
import com.azad.todolist.utils.AppUtils;
import com.azad.todolist.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final AppUserRepo appUserRepo;

    @Autowired
    public AppUserServiceImpl(AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    @Override
    public AppUserDto create(AppUserDto requestDto) {

        String encodedPass = passwordEncoder.encode(requestDto.getPassword());

        requestDto.setPassword(encodedPass);

        if (requestDto.getRole() == null || !Roles.isValidRole(requestDto.getRole())) {
            requestDto.setRole("ROLE_USER");
        }

        AppUserEntity appUserEntity = appUserRepo.save(modelMapper.map(requestDto, AppUserEntity.class));

        return modelMapper.map(appUserEntity, AppUserDto.class);
    }

    @Override
    public List<AppUserDto> getAll(PagingAndSorting ps) {

        Pageable pageable;
        if (ps.getSort() == null || ps.getSort().equals("")) {
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        } else {
            Sort sort = appUtils.getSortBy(ps.getSort(), ps.getOrder());
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), sort);
        }

        List<AppUserEntity> appUserEntities = appUserRepo.findAll(pageable).getContent();

        return appUserEntities.stream()
                .map(appUserEntity -> modelMapper.map(appUserEntity, AppUserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AppUserDto getByEntityId(Long entityId) {

        AppUserEntity appUserEntity = appUserRepo.findById(entityId).orElseThrow(
                () -> new ResourceNotFoundException("AppUser", "entityId"));

        return modelMapper.map(appUserEntity, AppUserDto.class);
    }

    @Override
    public AppUserDto updateByEntityId(Long entityId, AppUserDto updatedDto) {

        AppUserEntity appUserEntity = appUserRepo.findById(entityId).orElseThrow(
                () -> new ResourceNotFoundException("AppUser", "entityId"));

        if (updatedDto.getFirstName() != null)
            appUserEntity.setFirstName(updatedDto.getFirstName());
        if (updatedDto.getLastName() != null)
            appUserEntity.setLastName(updatedDto.getLastName());
        if (updatedDto.getEmail() != null)
            appUserEntity.setEmail((updatedDto.getEmail()));

        return modelMapper.map(appUserRepo.save(appUserEntity), AppUserDto.class);
    }

    @Override
    public void deleteByEntityId(Long entityId) {

        AppUserEntity appUserEntity = appUserRepo.findById(entityId).orElseThrow(
                () -> new ResourceNotFoundException("AppUser", "entityId"));

        appUserRepo.delete(appUserEntity);
    }

    @Override
    public AppUserDto getByEmail(String email) {

        AppUserEntity appUserEntity = appUserRepo.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("AppUser", "email"));

        return modelMapper.map(appUserEntity, AppUserDto.class);
    }

    @Override
    public List<AppUserDto> getSearchResult(String searchKey, String searchTerm) {

        List<AppUserEntity> appUserEntities = new ArrayList<>();

        if (searchKey.equalsIgnoreCase("firstName"))
            appUserEntities = appUserRepo.findByFirstName(searchTerm).orElse(null);
        else if (searchKey.equalsIgnoreCase("lastName"))
            appUserEntities = appUserRepo.findByLastName(searchTerm).orElse(null);
        else if (searchKey.equalsIgnoreCase("email"))
            appUserEntities.add(appUserRepo.findByEmail(searchTerm).orElse(null));
        else if (searchKey.equalsIgnoreCase("role"))
            appUserEntities = appUserRepo.findByRole(searchTerm).orElse(null);
        else
            throw new RuntimeException("Invalid search key");

        if (appUserEntities == null || appUserEntities.size() == 0)
            return null;

        return appUserEntities.stream()
                .map(appUserEntity -> modelMapper.map(appUserEntity, AppUserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AppUserDto getByUserId(String userId) {
        return null;
    }

    @Override
    public AppUserDto updateByUserId(String userId, AppUserDto updatedUserDto) {
        return null;
    }

    @Override
    public void deleteByUserId(String userId) {
    }
}
