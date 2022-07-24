package com.azad.jsonplaceholderclone.controllers;

import com.azad.jsonplaceholderclone.models.Post;
import com.azad.jsonplaceholderclone.models.dtos.PostDto;
import com.azad.jsonplaceholderclone.models.responses.PostResponse;
import com.azad.jsonplaceholderclone.services.PostService;
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
@RequestMapping(path = "/posts")
public class PostController {

    @Autowired
    private ModelMapper modelMapper;

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(path = "/batch")
    public ResponseEntity<List<PostResponse>> createBatchPosts(@Valid @RequestBody List<Post> posts) {

        List<PostDto> postDtos = posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        List<PostDto> savedPostDtos = new ArrayList<>();
        for (PostDto postDto: postDtos)
            savedPostDtos.add(postService.create(postDto));

        List<PostResponse> postResponses = savedPostDtos.stream()
                .map(postDto -> modelMapper.map(postDto, PostResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(postResponses, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page > 0) page--;

        List<PostDto> allPostsFromService = postService.getAll(new PagingAndSorting(page, limit, sort, order));
        if (allPostsFromService == null || allPostsFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<PostResponse> postResponses = allPostsFromService.stream()
                .map(postDto -> modelMapper.map(postDto, PostResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(postResponses, HttpStatus.OK);
    }
}
