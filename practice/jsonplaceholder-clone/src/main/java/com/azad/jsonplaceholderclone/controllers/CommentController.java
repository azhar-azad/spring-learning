package com.azad.jsonplaceholderclone.controllers;

import com.azad.jsonplaceholderclone.models.dtos.CommentDto;
import com.azad.jsonplaceholderclone.models.requests.CommentRequest;
import com.azad.jsonplaceholderclone.models.responses.CommentResponse;
import com.azad.jsonplaceholderclone.services.CommentService;
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
@RequestMapping(path = "/comments")
public class CommentController {

    @Autowired
    private ModelMapper modelMapper;

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(path = "/batch")
    public ResponseEntity<List<CommentResponse>> createBatchComments(@Valid @RequestBody List<CommentRequest> commentRequests) {

        List<CommentDto> commentDtos = commentRequests.stream()
                .map(commentRequest -> modelMapper.map(commentRequest, CommentDto.class))
                .collect(Collectors.toList());

        List<CommentDto> savedCommentDtos = new ArrayList<>();
        for (CommentDto commentDto: commentDtos)
            savedCommentDtos.add(commentService.create(commentDto));

        List<CommentResponse> commentResponses = savedCommentDtos.stream()
                .map(commentDto -> modelMapper.map(commentDto, CommentResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(commentResponses, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllComments(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page > 0) page--;

        List<CommentDto> allCommentsFromService = commentService.getAll(new PagingAndSorting(page, limit, sort, order));
        if (allCommentsFromService == null || allCommentsFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<CommentResponse> commentResponses = allCommentsFromService.stream()
                .map(commentDto -> modelMapper.map(commentDto, CommentResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(commentResponses, HttpStatus.OK);
    }
}
