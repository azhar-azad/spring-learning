package com.azad.jsonplaceholderclone.services.impl;

import com.azad.jsonplaceholderclone.models.Comment;
import com.azad.jsonplaceholderclone.models.dtos.CommentDto;
import com.azad.jsonplaceholderclone.models.entities.CommentEntity;
import com.azad.jsonplaceholderclone.models.entities.PostEntity;
import com.azad.jsonplaceholderclone.repos.CommentRepository;
import com.azad.jsonplaceholderclone.repos.PostRepository;
import com.azad.jsonplaceholderclone.services.CommentService;
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
public class CommentServiceImpl implements CommentService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private PostRepository postRepository;

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto create(CommentDto requestBody) {

        PostEntity postThatThisCommentBelongTo = postRepository.findById(requestBody.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + requestBody.getPostId()));

        CommentEntity commentEntity = modelMapper.map(requestBody, CommentEntity.class);
        commentEntity.setPost(postThatThisCommentBelongTo);

        CommentEntity savedComment = commentRepository.save(commentEntity);

        CommentDto commentDto = modelMapper.map(savedComment, CommentDto.class);
        commentDto.setPostId(postThatThisCommentBelongTo.getId());

        return commentDto;
    }

    @Override
    public List<CommentDto> getAll(PagingAndSorting ps) {
        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        List<CommentEntity> allCommentsFromDb = commentRepository.findAll(pageable).getContent();
        if (allCommentsFromDb.size() == 0)
            return null;

        return allCommentsFromDb.stream()
                .map(commentEntity -> {
                    CommentDto commentDto = modelMapper.map(commentEntity, CommentDto.class);
                    commentDto.setPostId(commentEntity.getPost().getId());
                    return commentDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getById(Long id) {
        return null;
    }

    @Override
    public CommentDto updateById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
