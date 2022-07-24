package com.azad.jsonplaceholderclone.controllers;

import com.azad.jsonplaceholderclone.models.dtos.PhotoDto;
import com.azad.jsonplaceholderclone.models.requests.PhotoRequest;
import com.azad.jsonplaceholderclone.models.responses.PhotoResponse;
import com.azad.jsonplaceholderclone.services.PhotoService;
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
@RequestMapping(path = "/photos")
public class PhotoController {

    @Autowired
    private ModelMapper modelMapper;

    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping(path = "/batch")
    public ResponseEntity<List<PhotoResponse>> createBatchPhotos(@Valid @RequestBody List<PhotoRequest> photoRequests) {

        List<PhotoDto> photoDtos = photoRequests.stream()
                .map(photoRequest -> modelMapper.map(photoRequest, PhotoDto.class))
                .collect(Collectors.toList());

        List<PhotoDto> savedPhotoDtos = new ArrayList<>();
        for (PhotoDto photoDto: photoDtos)
            savedPhotoDtos.add(photoService.create(photoDto));

        List<PhotoResponse> photoResponses = savedPhotoDtos.stream()
                .map(photoDto -> modelMapper.map(photoDto, PhotoResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(photoResponses, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PhotoResponse>> getAllPhotos(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page > 0) page--;

        List<PhotoDto> allPhotosFromService = photoService.getAll(new PagingAndSorting(page, limit, sort, order));
        if (allPhotosFromService == null || allPhotosFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<PhotoResponse> photoResponses = allPhotosFromService.stream()
                .map(photoDto -> modelMapper.map(photoDto, PhotoResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(photoResponses, HttpStatus.OK);
    }
}
