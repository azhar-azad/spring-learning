package com.azad.moviepedia.services.impl;

import com.azad.moviepedia.common.AppUtils;
import com.azad.moviepedia.common.PagingAndSorting;
import com.azad.moviepedia.models.dtos.DirectorDto;
import com.azad.moviepedia.models.entities.DirectorEntity;
import com.azad.moviepedia.repositories.DirectorRepository;
import com.azad.moviepedia.services.DirectorService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorServiceImpl implements DirectorService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    private final DirectorRepository repository;

    @Autowired
    public DirectorServiceImpl(DirectorRepository repository) {
        this.repository = repository;
    }

    @Override
    public DirectorDto create(DirectorDto dto) {
        DirectorEntity director = modelMapper.map(dto, DirectorEntity.class);
        director.setAge(appUtils.calculateAge(dto.getBirthDate()));
        return modelMapper.map(repository.save(director), DirectorDto.class);
    }

    @Override
    public List<DirectorDto> getAll(PagingAndSorting ps) {

        Pageable pageable = appUtils.getPageable(ps);

        List<DirectorEntity> allDirectorsFromDb = repository.findAll(pageable).getContent();
        if (allDirectorsFromDb.isEmpty())
            return null;

        return allDirectorsFromDb.stream()
                .map(entity -> modelMapper.map(entity, DirectorDto.class))
                .toList();
    }

    @Override
    public DirectorDto getById(Long id) {

        DirectorEntity director = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

        return modelMapper.map(director, DirectorDto.class);
    }

    @Override
    public DirectorDto updateById(Long id, DirectorDto updatedDto) {

        DirectorEntity director = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

        if (updatedDto.getFirstName() != null)
            director.setFirstName(updatedDto.getFirstName());
        if (updatedDto.getLastName() != null)
            director.setLastName(updatedDto.getLastName());
        if (updatedDto.getBirthDate() != null)
            director.setBirthDate(updatedDto.getBirthDate());
        if (updatedDto.getBirthPlace() != null)
            director.setBirthPlace(updatedDto.getBirthPlace());
        if (updatedDto.getMiniBio() != null)
            director.setMiniBio(updatedDto.getMiniBio());
        director.setAge(appUtils.calculateAge(updatedDto.getBirthDate()));

        return modelMapper.map(repository.save(director), DirectorDto.class);
    }

    @Override
    public void deleteById(Long id) {

        DirectorEntity director = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

        repository.delete(director);
    }
}
