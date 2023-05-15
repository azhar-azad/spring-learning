package com.azad.basicecommerce.inventoryservice.service;

import com.azad.basicecommerce.auth.models.AppUser;
import com.azad.basicecommerce.auth.models.AppUserEntity;
import com.azad.basicecommerce.auth.models.RoleEntity;
import com.azad.basicecommerce.auth.reposotories.AppUserRepository;
import com.azad.basicecommerce.auth.reposotories.RoleRepository;
import com.azad.basicecommerce.auth.service.AuthService;
import com.azad.basicecommerce.common.ApiUtils;
import com.azad.basicecommerce.common.PagingAndSorting;
import com.azad.basicecommerce.common.exceptions.ResourceNotFoundException;
import com.azad.basicecommerce.common.exceptions.UnauthorizedAccessException;
import com.azad.basicecommerce.inventoryservice.models.StoreDto;
import com.azad.basicecommerce.inventoryservice.models.StoreEntity;
import com.azad.basicecommerce.inventoryservice.repositories.StoreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiUtils apiUtils;

    @Autowired
    private AuthService authService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final StoreRepository repository;

    @Autowired
    public StoreServiceImpl(StoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public StoreDto create(StoreDto dto) {

        apiUtils.printInfoLog("*** STORE :: CREATE ***");

        AppUserEntity storeOwner = authService.getLoggedInUser();

        if (storeOwner.getRole().getRoleName().equalsIgnoreCase("USER")) {
            // Update storeOwner's role to 'SELLER'
            Optional<RoleEntity> byRoleName = roleRepository.findByRoleName("SELLER");
            if (!byRoleName.isPresent())
                throw new ResourceNotFoundException("Resource Not Found", "ROLE", "seller");
            storeOwner.setRole(byRoleName.get());
            storeOwner = appUserRepository.save(storeOwner);
        }

        if (dto.getDiscount() == null)
            dto.setDiscount(0.0);

        StoreEntity entityFromReq = modelMapper.map(dto, StoreEntity.class);
        entityFromReq.setStoreUid(apiUtils.getHash("store", dto.getStoreName()));
        entityFromReq.setStoreOwnerUid(storeOwner.getUserUid());

        StoreEntity savedEntity = repository.save(entityFromReq);

        StoreDto savedDto = modelMapper.map(savedEntity, StoreDto.class);
        savedDto.setStoreOwner(modelMapper.map(storeOwner, AppUser.class));

        return savedDto;
    }

    @Override
    public List<StoreDto> getAll(PagingAndSorting ps) {

        apiUtils.printInfoLog("*** STORE :: GET ALL ***");

        AppUserEntity loggedInUser = authService.getLoggedInUser();

        Optional<List<StoreEntity>> byStoreOwnerUid =
                repository.findByStoreOwnerUid(loggedInUser.getUserUid(), apiUtils.getPageable(ps));

        return byStoreOwnerUid.map(storeEntities -> storeEntities.stream()
                .map(entity -> {
                    StoreDto dto = modelMapper.map(entity, StoreDto.class);
                    dto.setStoreOwner(modelMapper.map(loggedInUser, AppUser.class));
                    return dto;
                })
                .collect(Collectors.toList())).orElse(null);
    }

    @Override
    public StoreDto getById(Long id) {
        throw new RuntimeException("Method should not be used.");
    }

    @Override
    public StoreDto getByUid(String uid) {

        apiUtils.printInfoLog("*** STORE :: GET ***");

        StoreEntity entityFromDb = repository.findByStoreUid(uid).orElseThrow(
                () -> new ResourceNotFoundException("Store not found with uid: " + uid, "STORE", "uid = " + uid));

        String storeOwnerUid = entityFromDb.getStoreOwnerUid();
        Optional<AppUserEntity> byUserUid = appUserRepository.findByUserUid(storeOwnerUid);
        if (!byUserUid.isPresent())
            throw new ResourceNotFoundException("User not found with uid: " + storeOwnerUid, "USER", "uid = " + storeOwnerUid);

        StoreDto fetchedDto = modelMapper.map(entityFromDb, StoreDto.class);
        fetchedDto.setStoreOwner(modelMapper.map(byUserUid.get(), AppUser.class));

        return fetchedDto;
    }

    @Override
    public StoreDto updateById(Long id, StoreDto updatedDto) {
        throw new RuntimeException("Method should not be used.");
    }

    @Override
    public StoreDto updateByUid(String uid, StoreDto updatedDto) {

        apiUtils.printInfoLog("*** STORE :: UPDATE ***");

        AppUserEntity loggedInUser = authService.getLoggedInUser();

        StoreEntity entityFromDb = repository.findByStoreUid(uid).orElseThrow(
                () -> new ResourceNotFoundException("Store not found with uid: " + uid, "STORE", "uid = " + uid));

        if (loggedInUserIsNotOwner(entityFromDb.getStoreOwnerUid(), loggedInUser.getUserUid())) {
            apiUtils.printErrorLog("*** ERROR :: Logged in user is not the owner ***");
            throw new UnauthorizedAccessException("Unauthorized. Logged in user is not the owner of this store");
        }

        if (updatedDto.getStoreName() != null) {
            entityFromDb.setStoreName(updatedDto.getStoreName());
            entityFromDb.setStoreUid(apiUtils.getHash("store", updatedDto.getStoreName()));
        }
        if (updatedDto.getPictureUrl() != null)
            entityFromDb.setPictureUrl(updatedDto.getPictureUrl());
        if (updatedDto.getDiscount() != null)
            entityFromDb.setDiscount(updatedDto.getDiscount());

        StoreDto savedDto = modelMapper.map(repository.save(entityFromDb), StoreDto.class);
        savedDto.setStoreOwner(modelMapper.map(loggedInUser, AppUser.class));

        return savedDto;
    }

    @Override
    public void deleteById(Long id) {
        throw new RuntimeException("Method should not be used.");
    }

    @Override
    public void deleteByUid(String uid) {

        apiUtils.printInfoLog("*** STORE :: DELETE ***");

        AppUserEntity storeOwner = authService.getLoggedInUser();

        StoreEntity entityFromDb = repository.findByStoreUid(uid).orElseThrow(
                () -> new ResourceNotFoundException("Store not found with uid: " + uid, "STORE", "uid = " + uid));

        if (loggedInUserIsNotOwner(entityFromDb.getStoreOwnerUid(), storeOwner.getUserUid())) {
            throw new UnauthorizedAccessException("Unauthorized. Logged in user is not the owner of this store");
        }

        repository.delete(entityFromDb);
    }

    @Override
    public Long getEntityCount() {
        return repository.count();
    }

    private boolean loggedInUserIsNotOwner(String dbEntityUserUid, String storeOwnerUid) {

        return !Objects.equals(dbEntityUserUid, storeOwnerUid);
    }
}
