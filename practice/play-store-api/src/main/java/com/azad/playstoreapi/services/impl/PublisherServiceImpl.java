package com.azad.playstoreapi.services.impl;

import com.azad.playstoreapi.exceptions.UnauthorizedAccessException;
import com.azad.playstoreapi.models.dtos.PublisherDto;
import com.azad.playstoreapi.models.entities.PlayStoreUserEntity;
import com.azad.playstoreapi.models.entities.PublisherEntity;
import com.azad.playstoreapi.models.pojos.PlayStoreUser;
import com.azad.playstoreapi.models.pojos.Publisher;
import com.azad.playstoreapi.repos.PlayStoreUserRepository;
import com.azad.playstoreapi.repos.PublisherRepository;
import com.azad.playstoreapi.security.auth.AuthService;
import com.azad.playstoreapi.services.PublisherService;
import com.azad.playstoreapi.utils.AppUtils;
import com.azad.playstoreapi.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private PlayStoreUserRepository playStoreUserRepository;

    private final PublisherRepository repository;

    @Autowired
    public PublisherServiceImpl(PublisherRepository repository) {
        this.repository = repository;
    }

    @Override
    public PublisherDto create(PublisherDto publisherDto) {

        PlayStoreUserEntity loggedInUser = authService.getLoggedInUser();

        PublisherEntity entity = modelMapper.map(publisherDto, PublisherEntity.class);
        entity.setUser(loggedInUser);
        PublisherEntity savedEntity = repository.save(entity);

        return modelMapper.map(savedEntity, PublisherDto.class);
    }

    @Override
    public List<PublisherDto> getAll(PagingAndSorting ps) {
        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        PlayStoreUserEntity loggedInUser = authService.getLoggedInUser();

        Optional<List<PublisherEntity>> byUserId = repository.findByUserId(loggedInUser.getId(), pageable);
        if (!byUserId.isPresent())
            return null;

        List<PublisherEntity> allPublishersFromDb = byUserId.get();
        if (allPublishersFromDb.size() == 0)
            return null;

        return allPublishersFromDb.stream()
                .map(publisherEntity -> {
                    PublisherDto publisherDto = modelMapper.map(publisherEntity, PublisherDto.class);
                    publisherDto.setUser(modelMapper.map(publisherEntity.getUser(), PlayStoreUser.class));
                    return publisherDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public PublisherDto getById(Long id) {

        PublisherEntity publisherFromDb = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Publisher not found with id: " + id));

        PlayStoreUserEntity loggedInUser = authService.getLoggedInUser();
        if (!Objects.equals(publisherFromDb.getUser().getId(), loggedInUser.getId())) // logged-in user is not the owner of the fetched entity
            if (!authService.loggedInUserIsAdmin()) // logged-in user is not an admin too
                throw new UnauthorizedAccessException("Resource is not authorized for " + loggedInUser.getUsername() + " with id: " + loggedInUser.getId());

        return modelMapper.map(publisherFromDb, PublisherDto.class);
    }

    @Override
    public PublisherDto updateById(Long id, PublisherDto updatedPublisherDto) {

        PublisherEntity publisherFromDb = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Publisher not found with id: " + id));

        PlayStoreUserEntity loggedInUser = authService.getLoggedInUser();
        if (!Objects.equals(publisherFromDb.getUser().getId(), loggedInUser.getId())) // logged-in user is not the owner of the fetched entity
            if (!authService.loggedInUserIsAdmin()) // logged-in user is not an admin too
                throw new UnauthorizedAccessException("Resource is not authorized for " + loggedInUser.getUsername() + " with id: " + loggedInUser.getId());

        if (updatedPublisherDto.getPubName() != null) {
            publisherFromDb.setPubName(updatedPublisherDto.getPubName());
        }

        PublisherEntity updatedPublisher = repository.save(publisherFromDb);

        return modelMapper.map(updatedPublisher, PublisherDto.class);
    }

    @Override
    public void deleteById(Long id) {

        PublisherEntity publisherFromDb = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Publisher not found with id: " + id));

        PlayStoreUserEntity loggedInUser = authService.getLoggedInUser();
        if (!Objects.equals(publisherFromDb.getUser().getId(), loggedInUser.getId())) // logged-in user is not the owner of the fetched entity
            if (!authService.loggedInUserIsAdmin()) // logged-in user is not an admin too
                throw new UnauthorizedAccessException("Resource is not authorized for " + loggedInUser.getUsername() + " with id: " + loggedInUser.getId());

        repository.delete(publisherFromDb);
    }
}
