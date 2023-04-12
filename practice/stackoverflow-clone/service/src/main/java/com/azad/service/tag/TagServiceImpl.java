package com.azad.service.tag;

import com.azad.common.AppUtils;
import com.azad.common.PagingAndSorting;
import com.azad.data.models.dtos.TagDto;
import com.azad.data.models.entities.AppUserEntity;
import com.azad.data.models.entities.TagEntity;
import com.azad.data.repos.TagRepository;
import com.azad.security.auth.AuthService;
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
public class TagServiceImpl implements TagService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private AuthService authService;

    private final TagRepository repository;

    @Autowired
    public TagServiceImpl(TagRepository repository) {
        this.repository = repository;
    }

    @Override
    public TagDto create(TagDto dto) {

        AppUserEntity loggedInUser;
        try {
            loggedInUser = authService.getLoggedInUser();
        } catch (Exception ex) {
            dto.setUnregisteredUserMsg("Please log in to create a new tag.");
            return dto;
        }

        String tagName = dto.getName();
        Optional<TagEntity> alreadySavedTag = repository.findByName(tagName);
        if (alreadySavedTag.isPresent()) {
            TagDto alreadySavedDto = modelMapper.map(alreadySavedTag.get(), TagDto.class);
            alreadySavedDto.setMessage("Tag is already created by another user. " +
                    "Please update this tag or create a new one with different name.");
            return alreadySavedDto;
        }

        TagEntity entity = modelMapper.map(dto, TagEntity.class);
        entity.setCreatedByUser(loggedInUser.getUsername());

        return modelMapper.map(repository.save(entity), TagDto.class);
    }

    @Override
    public List<TagDto> getAll(PagingAndSorting ps) {

        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(),
                    appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        List<TagEntity> allTagsFromDb = repository.findAll(pageable).getContent();
        if (allTagsFromDb.size() == 0)
            return null;

        return allTagsFromDb.stream()
                .map(tagEntity -> modelMapper.map(tagEntity, TagDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TagDto getById(Long id) {
        return null;
    }

    @Override
    public TagDto updateById(Long id, TagDto updatedDto) {

        AppUserEntity loggedInUser;
        try {
            loggedInUser = authService.getLoggedInUser();
        } catch (Exception ex) {
            updatedDto.setUnregisteredUserMsg("Please log in to update a tag.");
            return updatedDto;
        }

        TagEntity existingEntity = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Tag not found with id: " + id));

        if (updatedDto.getName() != null && loggedInUser.getUsername().equals(existingEntity.getCreatedByUser()))
            existingEntity.setName(updatedDto.getName());
        if (updatedDto.getDescription() != null)
            existingEntity.setDescription(updatedDto.getDescription());
        existingEntity.setUpdatedByUser(loggedInUser.getUsername());

        TagEntity savedEntity = repository.save(existingEntity);

        return modelMapper.map(savedEntity, TagDto.class);
    }

    @Override
    public void deleteById(Long id) {

    }
}
