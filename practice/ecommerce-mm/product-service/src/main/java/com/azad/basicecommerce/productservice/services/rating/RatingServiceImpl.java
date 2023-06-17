package com.azad.basicecommerce.productservice.services.rating;

import com.azad.basicecommerce.auth.models.AppUserEntity;
import com.azad.basicecommerce.auth.service.AuthService;
import com.azad.basicecommerce.common.ApiUtils;
import com.azad.basicecommerce.common.PagingAndSorting;
import com.azad.basicecommerce.common.exceptions.ResourceNotFoundException;
import com.azad.basicecommerce.productservice.models.product.ProductEntity;
import com.azad.basicecommerce.productservice.models.rating.RatingDto;
import com.azad.basicecommerce.productservice.models.rating.RatingEntity;
import com.azad.basicecommerce.productservice.repositories.ProductRepository;
import com.azad.basicecommerce.productservice.repositories.RatingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiUtils apiUtils;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthService authService;

    private final RatingRepository repository;

    @Autowired
    public RatingServiceImpl(RatingRepository repository) {
        this.repository = repository;
    }

    @Override
    public RatingDto create(RatingDto dto) {

        apiUtils.logInfo("*** RATING :: CREATE ***");

        AppUserEntity loggedInUser = authService.getLoggedInUser();

        ProductEntity product = productRepository.findByProductUid(dto.getProductUid()).orElseThrow(
                () -> new ResourceNotFoundException("Resource Not Found", "PRODUCT", "uid = " + dto.getProductUid()));

        RatingEntity entity = modelMapper.map(dto, RatingEntity.class);
        entity.setRatingUid(apiUtils.getHash("entity", dto.getRatingValue() + loggedInUser.getUserUid()));
        entity.setUserUid(loggedInUser.getUserUid());
        entity.setProduct(product);

        RatingEntity savedEntity = repository.save(entity);

        product.setTotalRating(product.getTotalRating() + 1);
        product.setAverageRating((product.getAverageRating() + savedEntity.getRatingValue()) / product.getTotalRating());
        productRepository.save(product);

        RatingDto dtoFromEntity = modelMapper.map(savedEntity, RatingDto.class);
        dtoFromEntity.setProductUid(product.getProductUid());

        return dtoFromEntity;
    }

    @Override
    public List<RatingDto> getAll(PagingAndSorting ps) {

        apiUtils.logInfo("*** RATING :: GET ALL BY USER ***");

        Optional<List<RatingEntity>> byUserUid =
                repository.findByUserUid(authService.getLoggedInUser().getUserUid(), apiUtils.getPageable(ps));

        return byUserUid.map(ratingEntities -> ratingEntities.stream()
                .map(entity -> {
                    
                }))
    }

    @Override
    public List<RatingDto> getAllByProduct(String productId, PagingAndSorting ps) {
        return null;
    }

    @Override
    public RatingDto getById(Long id) {
        return null;
    }

    @Override
    public RatingDto getByUid(String uid) {
        return null;
    }

    @Override
    public RatingDto updateById(Long id, RatingDto updatedDto) {
        return null;
    }

    @Override
    public RatingDto updateByUid(String uid, RatingDto updatedDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteByUid(String uid) {

    }

    @Override
    public Long getEntityCount() {
        return null;
    }
}
