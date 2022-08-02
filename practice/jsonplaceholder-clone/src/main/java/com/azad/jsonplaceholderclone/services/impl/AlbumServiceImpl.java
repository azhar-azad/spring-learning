package com.azad.jsonplaceholderclone.services.impl;

import com.azad.jsonplaceholderclone.models.dtos.AlbumDto;
import com.azad.jsonplaceholderclone.models.dtos.MemberDto;
import com.azad.jsonplaceholderclone.models.entities.AlbumEntity;
import com.azad.jsonplaceholderclone.repos.AlbumRepository;
import com.azad.jsonplaceholderclone.security.auth.api.AuthService;
import com.azad.jsonplaceholderclone.security.entities.MemberEntity;
import com.azad.jsonplaceholderclone.services.AlbumService;
import com.azad.jsonplaceholderclone.utils.AppUtils;
import com.azad.jsonplaceholderclone.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private AuthService authService;

    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public AlbumDto create(AlbumDto requestBody) {

        MemberEntity loggedInMember = authService.getLoggedInMember();

        AlbumEntity albumEntity = modelMapper.map(requestBody, AlbumEntity.class);
        albumEntity.setMember(loggedInMember);

        AlbumEntity savedAlbum = albumRepository.save(albumEntity);

        AlbumDto albumDto = modelMapper.map(savedAlbum, AlbumDto.class);
        albumDto.setUserId(loggedInMember.getId());

        return albumDto;
    }

    @Override
    public List<AlbumDto> getAll(PagingAndSorting ps) {

        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        List<AlbumEntity> allAlbumsFromDb = albumRepository.findAll(pageable).getContent();
        if (allAlbumsFromDb.size() == 0)
            return null;

        return allAlbumsFromDb.stream()
                .map(albumEntity -> {
                    AlbumDto albumDto = modelMapper.map(albumEntity, AlbumDto.class);
                    albumDto.setUserId(albumEntity.getMember().getId());
                    return albumDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public AlbumDto getById(Long id) {
        AlbumEntity albumFromDb = albumRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Album not found with id: " + id));

        return modelMapper.map(albumFromDb, AlbumDto.class);
    }

    @Override
    public AlbumDto updateById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
