package com.azad.jsonplaceholder.rest.controllers;

import com.azad.jsonplaceholder.models.Post;
import com.azad.jsonplaceholder.models.dtos.PostDto;
import com.azad.jsonplaceholder.models.responses.PostResponse;
import com.azad.jsonplaceholder.rest.assemblers.PostModelAssembler;
import com.azad.jsonplaceholder.services.PostService;
import com.azad.jsonplaceholder.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostRestController {

    @Autowired
    private ModelMapper modelMapper;

    private final PostService postService;
    private final PostModelAssembler postModelAssembler;

    public PostRestController(PostService postService, PostModelAssembler postModelAssembler) {
        this.postService = postService;
        this.postModelAssembler = postModelAssembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<PostResponse>> createPost(@Valid @RequestBody Post post) {

        PostDto postDto = modelMapper.map(post, PostDto.class);

        PostDto savedPostDto = postService.create(postDto);

        PostResponse postResponse = modelMapper.map(savedPostDto, PostResponse.class);
        EntityModel<PostResponse> postResponseEntityModel = postModelAssembler.toModel(postResponse);

        return new ResponseEntity<>(postResponseEntityModel, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PostResponse>>> getAllPosts(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page < 0) page = 0;

        List<PostDto> allPostsFromService = postService.getAll(
                new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        if (allPostsFromService == null || allPostsFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<PostResponse> postResponses = allPostsFromService.stream()
                .map(postDto -> modelMapper.map(postDto, PostResponse.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<PostResponse>> collectionModel =
                postModelAssembler.getCollectionModel(postResponses, new PagingAndSorting(page, limit, sort, order));

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EntityModel<PostResponse>> getPost(@Valid @PathVariable("id") Long id) {

        PostDto postFromService = postService.getById(id);
        if (postFromService == null)
            return ResponseEntity.notFound().build();

        PostResponse postResponse = modelMapper.map(postFromService, PostResponse.class);
        EntityModel<PostResponse> postResponseEntityModel = postModelAssembler.toModel(postResponse);

        return new ResponseEntity<>(postResponseEntityModel, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<EntityModel<PostResponse>> updatePost(@Valid @PathVariable("id") Long id, @RequestBody Post updatedRequestBody) {

        PostDto postFromService = postService.updateById(id, modelMapper.map(updatedRequestBody, PostDto.class));

        PostResponse postResponse = modelMapper.map(postFromService, PostResponse.class);
        EntityModel<PostResponse> postResponseEntityModel = postModelAssembler.toModel(postResponse);

        return new ResponseEntity<>(postResponseEntityModel, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletePost(@Valid @PathVariable("id") Long id) {

        postService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
