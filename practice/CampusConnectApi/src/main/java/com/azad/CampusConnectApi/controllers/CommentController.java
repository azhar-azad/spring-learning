package com.azad.CampusConnectApi.controllers;

import com.azad.CampusConnectApi.exceptions.InvalidPathVariableException;
import com.azad.CampusConnectApi.exceptions.RequestBodyEmptyException;
import com.azad.CampusConnectApi.models.dtos.CommentDto;
import com.azad.CampusConnectApi.models.requests.CommentRequest;
import com.azad.CampusConnectApi.models.responses.CommentResponse;
import com.azad.CampusConnectApi.services.CommentService;
import com.azad.CampusConnectApi.utils.AppUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/posts/{postId}/comments")
public class CommentController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@Valid @PathVariable Long postId, @RequestBody CommentRequest request) {

        appUtils.printControllerMethodInfo("POST", "/api/v1/posts/"+postId+"/comments", "createComment");

        if (postId < 1) {
            throw new InvalidPathVariableException("Invalid resource id value for the entity Post");
        }

        if (request == null) {
            throw new RequestBodyEmptyException("Request body is empty for entity Comment");
        }

        CommentDto commentDto = modelMapper.map(request, CommentDto.class);
        commentDto.setPostId(postId);

        CommentDto savedCommentDto = commentService.create(commentDto);

        return new ResponseEntity<>(modelMapper.map(savedCommentDto, CommentResponse.class), HttpStatus.CREATED);
    }
}
