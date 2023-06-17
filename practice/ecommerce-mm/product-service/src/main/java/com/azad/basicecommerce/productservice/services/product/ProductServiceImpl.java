package com.azad.basicecommerce.productservice.services.product;

import com.azad.basicecommerce.auth.models.AppUserEntity;
import com.azad.basicecommerce.auth.service.AuthService;
import com.azad.basicecommerce.common.ApiUtils;
import com.azad.basicecommerce.common.PagingAndSorting;
import com.azad.basicecommerce.common.exceptions.ResourceNotFoundException;
import com.azad.basicecommerce.common.exceptions.UnauthorizedAccessException;
import com.azad.basicecommerce.inventoryservice.models.StoreEntity;
import com.azad.basicecommerce.inventoryservice.repositories.StoreRepository;
import com.azad.basicecommerce.productservice.models.category.CategoryEntity;
import com.azad.basicecommerce.productservice.models.product.ProductDto;
import com.azad.basicecommerce.productservice.models.product.ProductEntity;
import com.azad.basicecommerce.productservice.repositories.CategoryRepository;
import com.azad.basicecommerce.productservice.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiUtils apiUtils;

    @Autowired
    private AuthService authService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StoreRepository storeRepository;

    private final ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductDto create(ProductDto dto) {

        apiUtils.logInfo("*** PRODUCT :: CREATE ***");

        AppUserEntity loggedInUser = authService.getLoggedInUser();
        String roleName = loggedInUser.getRole().getRoleName();
        if (!roleName.equalsIgnoreCase("SELLER")
                && !roleName.equalsIgnoreCase("ADMIN")) {
            apiUtils.logError("*** PRODUCT :: Only SELLER or ADMIN can add a product ***");
            throw new UnauthorizedAccessException("Unauthorized Resource");
        }

        CategoryEntity category = categoryRepository.findByCategoryUid(dto.getCategoryUid()).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found", "CATEGORY", "uid = " + dto.getCategoryUid()));

        StoreEntity store = storeRepository.findByStoreUid(dto.getStoreUid()).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found", "STORE", "uid = " + dto.getStoreUid()));
        if (!store.getStoreOwnerUid().equalsIgnoreCase(loggedInUser.getUserUid())) {
            apiUtils.logError("*** PRODUCT :: Logged in user is not the owner of the store that will be linked to this product");
            throw new UnauthorizedAccessException("Unauthorized Resource");
        }

        ProductEntity entityFromDto = modelMapper.map(dto, ProductEntity.class);
        entityFromDto.setProductUid(apiUtils.getHash("PRODUCT", dto.getProductName() + dto.getBrand() + dto.getPrice()));
        entityFromDto.setStoreUid(store.getStoreUid());
        entityFromDto.setCategory(category);
        entityFromDto.setDiscount(0.0);
        entityFromDto.setAverageRating(0.0);
        entityFromDto.setTotalRating(0L);
        entityFromDto.setTotalReview(0L);

        ProductEntity savedEntity = repository.save(entityFromDto);

        ProductDto dtoToReturn = modelMapper.map(savedEntity, ProductDto.class);
        dtoToReturn.setCategoryUid(category.getCategoryUid());

        return dtoToReturn;
    }

    @Override
    public List<ProductDto> getAll(PagingAndSorting ps) {
        throw new RuntimeException("Method should not be used");
    }

    @Override
    public List<ProductDto> getAllByStoreUid(String storeUid, PagingAndSorting ps) {

        apiUtils.logInfo("*** PRODUCT :: GET ALL BY STORE ***");

        Optional<List<ProductEntity>> byStoreUid = repository.findByStoreUid(storeUid, apiUtils.getPageable(ps));
        if (!byStoreUid.isPresent())
            return null;

        List<ProductEntity> entitiesFromDb = byStoreUid.get();
        if (entitiesFromDb.size() == 0)
            return null;

        return entitiesFromDb.stream()
                .map(entity -> {
                    CategoryEntity category = categoryRepository.findById(entity.getCategory().getId()).orElse(null);
                    ProductDto dto = modelMapper.map(entity, ProductDto.class);
                    dto.setCategoryUid(category != null ? category.getCategoryUid() : null);
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllByCategoryUid(String categoryUid, PagingAndSorting ps) {

        apiUtils.logInfo("*** PRODUCT :: GET ALL BY CATEGORY ***");

        CategoryEntity category = categoryRepository.findByCategoryUid(categoryUid).orElseThrow(
                () -> new ResourceNotFoundException("Resource Not Found", "CATEGORY", "uid = " + categoryUid));

        Optional<List<ProductEntity>> byCategoryId = repository.findByCategoryId(category.getId(), apiUtils.getPageable(ps));
        if (!byCategoryId.isPresent())
            return null;

        List<ProductEntity> entitiesFromDb = byCategoryId.get();
        if (entitiesFromDb.size() == 0)
            return null;

        return entitiesFromDb.stream()
                .map(entity -> {
                    ProductDto dto = modelMapper.map(entity, ProductDto.class);
                    dto.setCategoryUid(categoryUid);
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public ProductDto getById(Long id) {
        throw new RuntimeException("Method should not be used");
    }

    @Override
    public ProductDto getByUid(String uid) {

        apiUtils.logInfo("*** PRODUCT :: GET BY UID ***");

        ProductEntity entity = repository.findByProductUid(uid).orElseThrow(
                () -> new ResourceNotFoundException("Resource Not Found", "PRODUCT", "uid = " + uid));

        ProductDto dto = modelMapper.map(entity, ProductDto.class);
        dto.setCategoryUid(entity.getCategory().getCategoryUid());

        return dto;
    }

    @Override
    public ProductDto updateById(Long id, ProductDto updatedDto) {
        throw new RuntimeException("Method should not be used");
    }

    @Override
    public ProductDto updateByUid(String uid, ProductDto updatedDto) {

        apiUtils.logInfo("*** PRODUCT :: UPDATE BY UID ***");

        ProductEntity entity = repository.findByProductUid(uid).orElseThrow(
                () -> new ResourceNotFoundException("Resource Not Found", "PRODUCT", "uid = " + uid));

        AppUserEntity loggedInUser = authService.getLoggedInUser();
        StoreEntity store = storeRepository.findByStoreUid(entity.getStoreUid()).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found", "STORE", "uid = " + entity.getStoreUid()));

        if (!loggedInUser.getRole().getRoleName().equalsIgnoreCase("SELLER")
                || !store.getStoreOwnerUid().equalsIgnoreCase(loggedInUser.getUserUid())) {
            apiUtils.logError("*** PRODUCT :: Only SELLER can update the product of his/her store ***");
            throw new UnauthorizedAccessException("Unauthorized Resource");
        }

        if (updatedDto.getProductName() != null) {
            entity.setProductName(updatedDto.getProductName());
        }
        if (updatedDto.getBrand() != null)
            entity.setBrand(updatedDto.getBrand());
        if (updatedDto.getDescription() != null)
            entity.setBrand(updatedDto.getBrand());
        if (updatedDto.getPictureUrl() != null)
            entity.setPictureUrl(updatedDto.getPictureUrl());
        if (updatedDto.getPrice() != null)
            entity.setPrice(updatedDto.getPrice());
        if (updatedDto.getEstimatedDeliveryTime() != null)
            entity.setEstimatedDeliveryTime(updatedDto.getEstimatedDeliveryTime());
        if (updatedDto.getAvailableInStock() != null)
            entity.setAvailableInStock(updatedDto.getAvailableInStock());
        if (updatedDto.getDiscount() != null)
            entity.setDiscount(updatedDto.getDiscount());
        if (updatedDto.getReturnPolicy() != null)
            entity.setReturnPolicy(updatedDto.getReturnPolicy());
        if (updatedDto.getWarranty() != null)
            entity.setWarranty(updatedDto.getWarranty());

        entity.setProductUid(apiUtils.getHash("product", entity.getProductName() + entity.getBrand() + entity.getPrice()));

        ProductEntity updatedEntity = repository.save(entity);

        ProductDto dto = modelMapper.map(updatedEntity, ProductDto.class);
        dto.setCategoryUid(updatedDto.getCategoryUid());

        return dto;
    }

    @Override
    public void deleteById(Long id) {
        throw new RuntimeException("Method should not be used");
    }

    @Override
    public void deleteByUid(String uid) {

        apiUtils.logInfo("*** PRODUCT :: DELETE BY UID ***");

        ProductEntity entity = repository.findByProductUid(uid).orElseThrow(
                () -> new ResourceNotFoundException("Resource Not Found", "PRODUCT", "uid = " + uid));

        AppUserEntity loggedInUser = authService.getLoggedInUser();
        StoreEntity store = storeRepository.findByStoreUid(entity.getStoreUid()).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found", "STORE", "uid = " + entity.getStoreUid()));

        if (!loggedInUser.getRole().getRoleName().equalsIgnoreCase("SELLER")
                || !store.getStoreOwnerUid().equalsIgnoreCase(loggedInUser.getUserUid())) {
            apiUtils.logError("*** PRODUCT :: Only SELLER can update the product of his/her store ***");
            throw new UnauthorizedAccessException("Unauthorized Resource");
        }

        repository.delete(entity);
    }

    @Override
    public Long getEntityCount() {
        return repository.count();
    }
}
