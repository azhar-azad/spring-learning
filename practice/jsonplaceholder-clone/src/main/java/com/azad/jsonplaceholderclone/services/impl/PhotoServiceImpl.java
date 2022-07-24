package com.azad.jsonplaceholderclone.services.impl;

import com.azad.jsonplaceholderclone.models.dtos.PhotoDto;
import com.azad.jsonplaceholderclone.models.entities.AlbumEntity;
import com.azad.jsonplaceholderclone.models.entities.PhotoEntity;
import com.azad.jsonplaceholderclone.repos.AlbumRepository;
import com.azad.jsonplaceholderclone.repos.PhotoRepository;
import com.azad.jsonplaceholderclone.services.PhotoService;
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
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private AlbumRepository albumRepository;

    private final PhotoRepository photoRepository;

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public PhotoDto create(PhotoDto requestBody) {

        AlbumEntity albumThatThisPhotoBelongTo = albumRepository.findById(requestBody.getAlbumId())
                .orElseThrow(() -> new RuntimeException("Album not found with id: " + requestBody.getAlbumId()));

        PhotoEntity photoEntity = modelMapper.map(requestBody, PhotoEntity.class);
        photoEntity.setAlbum(albumThatThisPhotoBelongTo);

        PhotoEntity savedPhoto = photoRepository.save(photoEntity);

        PhotoDto photoDto = modelMapper.map(savedPhoto, PhotoDto.class);
        photoDto.setAlbumId(albumThatThisPhotoBelongTo.getId());

        return photoDto;
    }

    @Override
    public List<PhotoDto> getAll(PagingAndSorting ps) {

        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        List<PhotoEntity> allPhotosFromDb = photoRepository.findAll(pageable).getContent();
        if (allPhotosFromDb.size() == 0)
            return null;

        return allPhotosFromDb.stream()
                .map(photoEntity -> {
                    PhotoDto photoDto = modelMapper.map(photoEntity, PhotoDto.class);
                    photoDto.setAlbumId(photoEntity.getAlbum().getId());
                    return photoDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public PhotoDto getById(Long id) {
        return null;
    }

    @Override
    public PhotoDto updateById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
