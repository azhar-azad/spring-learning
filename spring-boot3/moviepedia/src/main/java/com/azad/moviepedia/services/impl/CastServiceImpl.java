package com.azad.moviepedia.services.impl;

import com.azad.moviepedia.common.AppUtils;
import com.azad.moviepedia.common.PagingAndSorting;
import com.azad.moviepedia.models.dtos.CastDto;
import com.azad.moviepedia.models.entities.CastEntity;
import com.azad.moviepedia.repositories.CastRepository;
import com.azad.moviepedia.services.CastService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CastServiceImpl implements CastService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    private final CastRepository repository;

    @Autowired
    public CastServiceImpl(CastRepository repository) {
        this.repository = repository;
    }

    @Override
    public CastDto create(CastDto dto) {
        CastEntity cast = modelMapper.map(dto, CastEntity.class);
        cast.setAge(appUtils.calculateAge(dto.getBirthDate()));
        return modelMapper.map(repository.save(cast), CastDto.class);
    }

    @Override
    public List<CastDto> getAll(PagingAndSorting ps) {

        Pageable pageable = appUtils.getPageable(ps);

        List<CastEntity> allCastsFromDb = repository.findAll(pageable).getContent();
        if (allCastsFromDb.isEmpty())
            return null;

        return allCastsFromDb.stream()
                .map(entity -> modelMapper.map(entity, CastDto.class))
                .toList();
    }

    @Override
    public CastDto getById(Long id) {

        CastEntity cast = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

        return modelMapper.map(cast, CastDto.class);
    }

    @Override
    public CastDto updateById(Long id, CastDto updatedDto) {

        CastEntity cast = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

        if (updatedDto.getFirstName() != null)
            cast.setFirstName(updatedDto.getFirstName());
        if (updatedDto.getLastName() != null)
            cast.setLastName(updatedDto.getLastName());
        if (updatedDto.getBirthDate() != null)
            cast.setBirthDate(updatedDto.getBirthDate());
        if (updatedDto.getBirthPlace() != null)
            cast.setBirthPlace(updatedDto.getBirthPlace());
        if (updatedDto.getMiniBio() != null)
            cast.setMiniBio(updatedDto.getMiniBio());
        cast.setAge(appUtils.calculateAge(updatedDto.getBirthDate()));

        return modelMapper.map(repository.save(cast), CastDto.class);
    }

    @Override
    public void deleteById(Long id) {

        CastEntity cast = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

        repository.delete(cast);
    }
}
