package com.azad.CampusConnectApi.controllers;

import com.azad.CampusConnectApi.exceptions.InvalidPathVariableException;
import com.azad.CampusConnectApi.exceptions.RequestBodyEmptyException;
import com.azad.CampusConnectApi.models.dtos.PostDto;
import com.azad.CampusConnectApi.models.requests.PostRequest;
import com.azad.CampusConnectApi.models.responses.PostResponse;
import com.azad.CampusConnectApi.services.PostService;
import com.azad.CampusConnectApi.utils.AppUtils;
import com.azad.CampusConnectApi.utils.PagingAndSortingObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/appUsers/{appUserId}/posts")
public class PostController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @PathVariable Long appUserId, @RequestBody PostRequest request) {

        appUtils.printControllerMethodInfo("POST", "/api/v1/appUsers/"+ appUserId + "/posts", "createPost");

        if (appUserId < 1) {
            throw new InvalidPathVariableException("Invalid resource id value for the entity AppUser");
        }

        if (request == null) {
            throw new RequestBodyEmptyException("Request body is empty for entity Post");
        }

        PostDto postDto = modelMapper.map(request, PostDto.class);
        postDto.setAppUserId(appUserId);

        PostDto savedPostDto = postService.create(postDto);

        return new ResponseEntity<>(modelMapper.map(savedPostDto, PostResponse.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(
            @Valid @PathVariable Long appUserId,
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        appUtils.printControllerMethodInfo("GET", "/api/v1/appUsers/"+ appUserId + "/posts", "getAllPosts");

        if (appUserId < 1) {
            throw new InvalidPathVariableException("Invalid resource id value for the entity AppUser");
        }

        if (page > 0) page--;

        List<PostDto> postDtos = postService.getAllByAppUserId(appUserId, new PagingAndSortingObject(page, limit, sort, order));

        if (postDtos == null || postDtos.size() == 0) {
            return ResponseEntity.noContent().build();
        }

        List<PostResponse> postResponses = new ArrayList<>();
        postDtos.forEach(postDto -> postResponses.add(modelMapper.map(postDto, PostResponse.class)));

        return new ResponseEntity<>(postResponses, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostResponse> getPostById(@Valid @PathVariable Long appUserId, @Valid @PathVariable Long id) {

        appUtils.printControllerMethodInfo("GET", "/api/v1/appUsers/"+appUserId+"/posts/"+id, "getPostById");

        if (appUserId < 1) {
            throw new InvalidPathVariableException("Invalid resource id value for the entity AppUser");
        }

        if (id < 1) {
            throw new InvalidPathVariableException("Invalid resource id value for the entity Post");
        }

        PostDto postDto = postService.getPostByIdAndAppUserId(appUserId, id);

        return new ResponseEntity<>(modelMapper.map(postDto, PostResponse.class), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PostResponse> updatePostById(@Valid @PathVariable Long appUserId,
            @Valid @PathVariable Long id, @RequestBody PostRequest updatedRequest) {

        appUtils.printControllerMethodInfo("PUT", "/api/v1/appUsers/"+appUserId+"/posts/"+id, "updatePostById");

        if (appUserId < 1) {
            throw new InvalidPathVariableException("Invalid resource id value for the entity AppUser");
        }

        if (id < 1) {
            throw new InvalidPathVariableException("Invalid resource id value for the entity Post");
        }

        if (updatedRequest == null) {
            throw new RequestBodyEmptyException("Request body is empty for entity Post");
        }

        PostDto postDto = postService.updatePostByIdAndAppUserId(appUserId, id, modelMapper.map(updatedRequest, PostDto.class));

        return new ResponseEntity<>(modelMapper.map(postDto, PostResponse.class), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletePostById(@Valid @PathVariable Long appUserId, @Valid @PathVariable Long id) {

        appUtils.printControllerMethodInfo("DELETE", "/api/v1/appUsers/"+appUserId+"/posts/"+id, "deletePostById");

        if (appUserId < 1) {
            throw new InvalidPathVariableException("Invalid resource id value for the entity AppUser");
        }

        if (id < 1) {
            throw new InvalidPathVariableException("Invalid resource id value for the entity Post");
        }

        postService.deletePostByIdAndAppUserId(appUserId, id);

        return ResponseEntity.noContent().build();
    }
}
