package com.azad.basicecommerce.productservice.services.review;

import com.azad.basicecommerce.auth.models.AppUserEntity;
import com.azad.basicecommerce.auth.service.AuthService;
import com.azad.basicecommerce.common.ApiUtils;
import com.azad.basicecommerce.common.PagingAndSorting;
import com.azad.basicecommerce.common.exceptions.ResourceNotFoundException;
import com.azad.basicecommerce.common.exceptions.UnauthorizedAccessException;
import com.azad.basicecommerce.productservice.models.product.ProductEntity;
import com.azad.basicecommerce.productservice.models.review.Review;
import com.azad.basicecommerce.productservice.models.review.ReviewDto;
import com.azad.basicecommerce.productservice.models.review.ReviewEntity;
import com.azad.basicecommerce.productservice.repositories.ProductRepository;
import com.azad.basicecommerce.productservice.repositories.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiUtils apiUtils;

    @Autowired
    private AuthService authService;

    @Autowired
    private ProductRepository productRepository;

    private final ReviewRepository repository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public ReviewDto create(ReviewDto dto) {

        apiUtils.printInfoLog("*** REVIEW :: CREATE ***");

        ProductEntity product = productRepository.findByProductUid(dto.getProductUid()).orElseThrow(
                () -> new ResourceNotFoundException("Resource Not Found", "PRODUCT", "uid = " + dto.getProductUid()));

        ReviewEntity entityFromDto = modelMapper.map(dto, ReviewEntity.class);
        entityFromDto.setReviewUid(apiUtils.getHash("review", dto.getReviewText().length() > 8 ?
                dto.getReviewText().substring(0, 7) : dto.getReviewText()));
        entityFromDto.setProduct(product);
        entityFromDto.setReviewerUid(authService.getLoggedInUser().getUserUid());

        ReviewEntity savedEntity = repository.save(entityFromDto);

        ReviewDto dtoFromEntity = modelMapper.map(savedEntity, ReviewDto.class);
        dtoFromEntity.setProductUid(savedEntity.getProduct().getProductUid());

        return dtoFromEntity;
    }

    @Override
    public List<ReviewDto> getAll(PagingAndSorting ps) {

        apiUtils.printInfoLog("*** REVIEW :: GET ALL BY USER ***");

        AppUserEntity loggedInUser = authService.getLoggedInUser();

        Optional<List<ReviewEntity>> byReviewerUid = repository.findByReviewerUid(loggedInUser.getUserUid());

        return byReviewerUid.map(reviewEntities -> reviewEntities.stream()
                .map(entity -> {
                    ReviewDto dto = modelMapper.map(entity, ReviewDto.class);
                    dto.setProductUid(entity.getProduct().getProductUid());
                    return dto;
                }).collect(Collectors.toList())).orElse(null);
    }

    @Override
    public List<ReviewDto> getAllByProduct(String productUid, PagingAndSorting ps) {

        apiUtils.printInfoLog("*** REVIEW :: GET ALL BY PRODUCT ***");

        ProductEntity product = productRepository.findByProductUid(productUid).orElseThrow(
                () -> new ResourceNotFoundException("Resource Not Found", "PRODUCT", "uid = " + productUid));

        Optional<List<ReviewEntity>> byProductId = repository.findByProductId(product.getId());

        return byProductId.map(reviewEntities -> reviewEntities.stream()
                .map(entity -> {
                    ReviewDto dto = modelMapper.map(entity, ReviewDto.class);
                    dto.setProductUid(productUid);
                    return dto;
                }).collect(Collectors.toList())).orElse(null);

    }

    @Override
    public ReviewDto getById(Long id) {
        throw new RuntimeException("Method should not be used");
    }

    @Override
    public ReviewDto getByUid(String uid) {

        apiUtils.printInfoLog("*** REVIEW :: GET BY UID ***");

        ReviewEntity entity = repository.findByReviewUid(uid).orElseThrow(
                () -> new ResourceNotFoundException("Review Not Found", "REVIEW", "uid = " + uid));

        ReviewDto dto = modelMapper.map(entity, ReviewDto.class);
        dto.setProductUid(entity.getProduct().getProductUid());

        return dto;
    }

    @Override
    public ReviewDto updateById(Long id, ReviewDto updatedDto) {
        throw new RuntimeException("Method should not be used");
    }

    @Override
    public ReviewDto updateByUid(String uid, ReviewDto updatedDto) {

        apiUtils.printInfoLog("*** REVIEW :: UPDATE BY UID ***");

        ReviewEntity entity = repository.findByReviewUid(uid).orElseThrow(
                () -> new ResourceNotFoundException("Review Not Found", "REVIEW", "uid = " + uid));

        if (!entity.getReviewerUid().equals(authService.getLoggedInUser().getUserUid())) {
            apiUtils.printErrorLog("*** Review can only be updated by the Reviewer himself/herself ***");
            throw new UnauthorizedAccessException("Unauthorized Access");
        }

        if (updatedDto.getReviewText() != null)
            entity.setReviewText(updatedDto.getReviewText());
        entity.setReviewUid(apiUtils.getHash("review", entity.getReviewText().length() > 8 ?
                entity.getReviewText().substring(0, 7) : entity.getReviewText()));

        ReviewEntity updatedEntity = repository.save(entity);

        ReviewDto dto = modelMapper.map(updatedEntity, ReviewDto.class);
        dto.setProductUid(updatedEntity.getProduct().getProductUid());

        return dto;
    }

    @Override
    public void deleteById(Long id) {
        throw new RuntimeException("Method should not be used");
    }

    @Override
    public void deleteByUid(String uid) {

        apiUtils.printInfoLog("*** REVIEW :: DELETE BY UID ***");

        ReviewEntity entity = repository.findByReviewUid(uid).orElseThrow(
                () -> new ResourceNotFoundException("Review Not Found", "REVIEW", "uid = " + uid));

        if (!entity.getReviewerUid().equals(authService.getLoggedInUser().getUserUid())) {
            apiUtils.printErrorLog("*** Review can only be deleted by the Reviewer himself/herself ***");
            throw new UnauthorizedAccessException("Unauthorized Access");
        }

        repository.delete(entity);
    }

    @Override
    public Long getEntityCount() {
        return repository.count();
    }
}
