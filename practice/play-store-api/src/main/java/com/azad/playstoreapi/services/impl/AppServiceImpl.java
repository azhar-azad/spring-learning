package com.azad.playstoreapi.services.impl;

import com.azad.playstoreapi.exceptions.UnauthorizedAccessException;
import com.azad.playstoreapi.models.dtos.AppDto;
import com.azad.playstoreapi.models.entities.*;
import com.azad.playstoreapi.repos.AppRepository;
import com.azad.playstoreapi.repos.CategoryRepository;
import com.azad.playstoreapi.repos.PublisherRepository;
import com.azad.playstoreapi.repos.RatingHistoryRepository;
import com.azad.playstoreapi.security.auth.AuthService;
import com.azad.playstoreapi.services.AppService;
import com.azad.playstoreapi.utils.AppUtils;
import com.azad.playstoreapi.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private AuthService authService;

    private final AppRepository repository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;
    private final RatingHistoryRepository rhRepository;

    @Autowired
    public AppServiceImpl(AppRepository repository, PublisherRepository publisherRepository,
                          CategoryRepository categoryRepository, RatingHistoryRepository rhRepository) {
        this.repository = repository;
        this.publisherRepository = publisherRepository;
        this.categoryRepository = categoryRepository;
        this.rhRepository = rhRepository;
    }

    @Override
    public AppDto create(AppDto appDto) {

        // Initial App
        AppEntity entity = modelMapper.map(appDto, AppEntity.class);

        // Get saved Publisher and map with App
        PublisherEntity publisher;
        if (authService.loggedInUserIsAdmin()) {
            String pubName = appDto.getPubName();
            publisher = publisherRepository.findByPublisherName(pubName).orElseThrow(
                    () -> new RuntimeException("Publisher not found with pubName: " + pubName));

        } else if (authService.loggedInUserIsPublisher()) {
            Long publisherId = authService.getLoggedInUser().getId();
            publisher = publisherRepository.findById(publisherId).orElseThrow(
                    () -> new RuntimeException("Publisher not found with id: " + publisherId));

        } else {
            throw new UnauthorizedAccessException("Only Admins or Publishers can create a new App");
        }
        entity.setPublisher(publisher);

        // Get saved Categories and add them to App and also map them
        List<String> categoryNames = appDto.getCategoryNames();
        List<CategoryEntity> categories = categoryNames.stream()
                .map(categoryName -> categoryRepository.findByCategoryName(categoryName).orElseThrow(
                        () -> new RuntimeException("Category not found with name: " + categoryName)))
                .collect(Collectors.toList());
        entity.setCategories(categories);

        // Save the App. Mapping done with Publisher only at this stage
        AppEntity savedEntity = repository.save(entity);

        // Get an initial RatingHistory object
        RatingHistoryEntity ratingHistory = getRatingHistory();
        // Map saved App with RatingHistory
        ratingHistory.setApp(savedEntity);
        // Save RatingHistory
        RatingHistoryEntity savedRatingHistory = rhRepository.save(ratingHistory);
        // Map the saved RatingHistory to saved App
        savedEntity.setRatingHistory(savedRatingHistory);

        return modelMapper.map(savedEntity, AppDto.class);
    }

    @Override
    public List<AppDto> getAll(PagingAndSorting ps) {
        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        List<AppEntity> allAppsFromDb = repository.findAll(pageable).getContent();
        if (allAppsFromDb.size() == 0)
            return null;

        return allAppsFromDb.stream()
                .map(appEntity -> modelMapper.map(appEntity, AppDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppDto> getAllByPublisher(Long publisherId, PagingAndSorting ps) {
        return null;
    }

    @Override
    public AppDto getById(Long id) {
        return null;
    }

    @Override
    public AppDto updateById(Long id, AppDto updatedRequest) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    private RatingHistoryEntity getRatingHistory() {
        RatingHistoryEntity ratingHistory = new RatingHistoryEntity();
        ratingHistory.setCurrentRating(0.0);
        ratingHistory.setHighestRating(0);
        ratingHistory.setLowestRating(0);
        ratingHistory.setTotalRatingCount(0L);
        ratingHistory.setFiveStarRatingCount(0L);
        ratingHistory.setFourStarRatingCount(0L);
        ratingHistory.setThreeStarRatingCount(0L);
        ratingHistory.setTwoStarRatingCount(0L);
        ratingHistory.setOneStarRatingCount(0L);
        return ratingHistory;
    }
}
