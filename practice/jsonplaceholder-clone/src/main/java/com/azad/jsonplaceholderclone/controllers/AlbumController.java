package com.azad.jsonplaceholderclone.controllers;

import com.azad.jsonplaceholderclone.assemblers.AlbumModelAssembler;
import com.azad.jsonplaceholderclone.models.Album;
import com.azad.jsonplaceholderclone.models.dtos.AlbumDto;
import com.azad.jsonplaceholderclone.models.responses.AlbumResponse;
import com.azad.jsonplaceholderclone.models.responses.MemberResponse;
import com.azad.jsonplaceholderclone.services.AlbumService;
import com.azad.jsonplaceholderclone.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/albums")
public class AlbumController {

    @Autowired
    private ModelMapper modelMapper;

    private final AlbumService albumService;
    private final AlbumModelAssembler albumModelAssembler;

    @Autowired
    public AlbumController(AlbumService albumService, AlbumModelAssembler albumModelAssembler) {
        this.albumService = albumService;
        this.albumModelAssembler = albumModelAssembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<AlbumResponse>> createAlbum(@Valid @RequestBody Album album) {

        AlbumDto albumFromService = albumService.create(modelMapper.map(album, AlbumDto.class));

        EntityModel<AlbumResponse> albumResponseEntityModel = albumModelAssembler.toModel(modelMapper.map(albumFromService, AlbumResponse.class));

        return new ResponseEntity<>(albumResponseEntityModel, HttpStatus.CREATED);
    }

    // This method is not HATEOAS driven
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
    public ResponseEntity<CollectionModel<EntityModel<AlbumResponse>>> getAllAlbums(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page > 0) page--;

        List<AlbumDto> allAlbumsFromService = albumService.getAll(new PagingAndSorting(page, limit, sort, order));
        if (allAlbumsFromService == null || allAlbumsFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<EntityModel<AlbumResponse>> albumResponseEntityModels = allAlbumsFromService.stream()
                .map(albumDto -> {
                    AlbumResponse albumResponse = modelMapper.map(albumDto, AlbumResponse.class);
                    return albumModelAssembler.toModel(albumResponse);
                })
                .collect(Collectors.toList());

        CollectionModel<EntityModel<AlbumResponse>> collectionModel = CollectionModel.of(albumResponseEntityModels);
        collectionModel.add(linkTo(methodOn(AlbumController.class).getAllAlbums(1, 25, "", "asc")).withSelfRel());

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    public ResponseEntity<EntityModel<AlbumResponse>> getAlbum(Long id) {
        AlbumDto albumFromService = albumService.getById(id);
        if (albumFromService == null) {
            return ResponseEntity.notFound().build();
        }

        AlbumResponse albumResponse = modelMapper.map(albumFromService, AlbumResponse.class);
        EntityModel<AlbumResponse> albumResponseEntityModel = albumModelAssembler.toModel(albumResponse);

        return new ResponseEntity<>(albumResponseEntityModel, HttpStatus.OK);
    }
}
