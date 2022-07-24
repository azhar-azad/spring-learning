package com.azad.jsonplaceholderclone.controllers;

import com.azad.jsonplaceholderclone.models.Album;
import com.azad.jsonplaceholderclone.models.dtos.AlbumDto;
import com.azad.jsonplaceholderclone.models.responses.AlbumResponse;
import com.azad.jsonplaceholderclone.services.AlbumService;
import com.azad.jsonplaceholderclone.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/albums")
public class AlbumController {

    @Autowired
    private ModelMapper modelMapper;

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping(path = "/batch")
    public ResponseEntity<List<AlbumResponse>> createBatchAlbums(@Valid @RequestBody List<Album> albums) {

        List<AlbumDto> albumDtos = albums.stream()
                .map(album -> modelMapper.map(album, AlbumDto.class))
                .collect(Collectors.toList());

        List<AlbumDto> savedAlbumDtos = new ArrayList<>();
        for (AlbumDto albumDto: albumDtos)
            savedAlbumDtos.add(albumService.create(albumDto));

        List<AlbumResponse> albumResponses = savedAlbumDtos.stream()
                .map(albumDto -> modelMapper.map(albumDto, AlbumResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(albumResponses, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AlbumResponse>> getAllAlbums(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page > 0) page--;

        List<AlbumDto> allAlbumsFromService = albumService.getAll(new PagingAndSorting(page, limit, sort, order));
        if (allAlbumsFromService == null || allAlbumsFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<AlbumResponse> albumResponses = allAlbumsFromService.stream()
                .map(albumDto -> modelMapper.map(albumDto, AlbumResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(albumResponses, HttpStatus.OK);
    }
}
