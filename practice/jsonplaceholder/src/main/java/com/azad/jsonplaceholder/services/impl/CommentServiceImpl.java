package com.azad.jsonplaceholder.services.impl;

import com.azad.jsonplaceholder.models.dtos.CommentDto;
import com.azad.jsonplaceholder.models.entities.CommentEntity;
import com.azad.jsonplaceholder.models.entities.PostEntity;
import com.azad.jsonplaceholder.repos.CommentRepository;
import com.azad.jsonplaceholder.repos.PostRepository;
import com.azad.jsonplaceholder.security.auth.api.AuthService;
import com.azad.jsonplaceholder.security.entities.MemberEntity;
import com.azad.jsonplaceholder.services.CommentService;
import com.azad.jsonplaceholder.utils.AppUtils;
import com.azad.jsonplaceholder.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private AuthService authService;

    @Autowired
    private PostRepository postRepository;

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto create(CommentDto requestBody) {

        PostEntity postEntity = postRepository.findById(requestBody.getPostId()).orElseThrow(
                () -> new RuntimeException("Post not found with post id: " + requestBody.getPostId()));

        boolean isLoggedInMemberIsOwner = false;
        MemberEntity loggedInMember = authService.getLoggedInMember();
        for (PostEntity post: loggedInMember.getPosts()) {
            if (Objects.equals(post.getId(), requestBody.getPostId())) {
                isLoggedInMemberIsOwner = true;
                break;
            }
        }
        if (!isLoggedInMemberIsOwner)
            throw new RuntimeException("Logged in member is not the owner");

        CommentEntity commentEntity = modelMapper.map(requestBody, CommentEntity.class);
        commentEntity.setPost(postEntity);

        CommentEntity savedComment = commentRepository.save(commentEntity);

        CommentDto commentDto = modelMapper.map(commentEntity, CommentDto.class);
        commentDto.setPostId(savedComment.getPost().getId());

        return commentDto;
    }

    @Override
    public List<CommentDto> getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public CommentDto getById(Long id) {
        return null;
    }

    @Override
    public CommentDto updateById(Long id, CommentDto updatedRequestBody) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
