package com.azad.jsonplaceholder.rest.controllers;

import com.azad.jsonplaceholder.models.Comment;
import com.azad.jsonplaceholder.models.dtos.CommentDto;
import com.azad.jsonplaceholder.models.responses.CommentResponse;
import com.azad.jsonplaceholder.rest.assemblers.CommentModelAssembler;
import com.azad.jsonplaceholder.security.auth.api.AuthService;
import com.azad.jsonplaceholder.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/posts/{postId}/comments")
public class CommentRestController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthService authService;

    private final CommentService commentService;
    private final CommentModelAssembler commentModelAssembler;

    @Autowired
    public CommentRestController(CommentService commentService, CommentModelAssembler commentModelAssembler) {
        this.commentService = commentService;
        this.commentModelAssembler = commentModelAssembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<CommentResponse>> createComment(@Valid @RequestBody Comment comment,
                                                                      @Valid @PathVariable("postId") Long postId) {

        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        commentDto.setPostId(postId);

        CommentDto savedCommentDto = commentService.create(commentDto);

        CommentResponse commentResponse = modelMapper.map(savedCommentDto, CommentResponse.class);
        EntityModel<CommentResponse> commentResponseEntityModel = commentModelAssembler.toModel(commentResponse);

        return new ResponseEntity<>(commentResponseEntityModel, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CommentResponse>>> getAllComments(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        return null;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EntityModel<CommentResponse>> getComment(@Valid @PathVariable("id") Long id) {

        return null;
    }
}
