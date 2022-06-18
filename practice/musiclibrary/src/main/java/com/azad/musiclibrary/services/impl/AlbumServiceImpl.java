package com.azad.musiclibrary.services.impl;

import com.azad.musiclibrary.models.dtos.AlbumDto;
import com.azad.musiclibrary.repositories.AlbumRepository;
import com.azad.musiclibrary.services.AlbumService;
import com.azad.musiclibrary.utils.PagingAndSortingObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    private AlbumRepository albumRepository;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public AlbumDto create(AlbumDto requestDto) {
        return null;
    }

    @Override
    public List<AlbumDto> getAll(PagingAndSortingObject ps) {
        return null;
    }

    @Override
    public AlbumDto getById(Long id) {
        return null;
    }

    @Override
    public AlbumDto updateById(Long id, AlbumDto updatedRequestDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
