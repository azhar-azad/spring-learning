package com.azad.moviepedia.services.impl;

import com.azad.moviepedia.common.AppUtils;
import com.azad.moviepedia.common.PagingAndSorting;
import com.azad.moviepedia.models.dtos.WriterDto;
import com.azad.moviepedia.models.entities.WriterEntity;
import com.azad.moviepedia.repositories.WriterRepository;
import com.azad.moviepedia.services.WriterService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WriterServiceImpl implements WriterService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    private final WriterRepository repository;

    @Autowired
    public WriterServiceImpl(WriterRepository repository) {
        this.repository = repository;
    }

    @Override
    public WriterDto create(WriterDto dto) {
        WriterEntity writer = modelMapper.map(dto, WriterEntity.class);
        writer.setAge(appUtils.calculateAge(dto.getBirthDate()));
        return modelMapper.map(repository.save(writer), WriterDto.class);
    }

    @Override
    public List<WriterDto> getAll(PagingAndSorting ps) {

        Pageable pageable = appUtils.getPageable(ps);

        List<WriterEntity> allWritersFromDb = repository.findAll(pageable).getContent();
        if (allWritersFromDb.isEmpty())
            return null;

        return allWritersFromDb.stream()
                .map(entity -> modelMapper.map(entity, WriterDto.class))
                .toList();
    }

    @Override
    public WriterDto getById(Long id) {

        WriterEntity writer = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

        return modelMapper.map(writer, WriterDto.class);
    }

    @Override
    public WriterDto updateById(Long id, WriterDto updatedDto) {

        WriterEntity writer = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

        if (updatedDto.getFirstName() != null)
            writer.setFirstName(updatedDto.getFirstName());
        if (updatedDto.getLastName() != null)
            writer.setLastName(updatedDto.getLastName());
        if (updatedDto.getBirthDate() != null)
            writer.setBirthDate(updatedDto.getBirthDate());
        if (updatedDto.getBirthPlace() != null)
            writer.setBirthPlace(updatedDto.getBirthPlace());
        if (updatedDto.getMiniBio() != null)
            writer.setMiniBio(updatedDto.getMiniBio());
        writer.setAge(appUtils.calculateAge(updatedDto.getBirthDate()));

        return modelMapper.map(repository.save(writer), WriterDto.class);
    }

    @Override
    public void deleteById(Long id) {

        WriterEntity writer = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

        repository.delete(writer);
    }
}
