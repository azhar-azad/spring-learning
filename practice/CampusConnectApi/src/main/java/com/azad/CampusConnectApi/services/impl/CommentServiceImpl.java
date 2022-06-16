package com.azad.CampusConnectApi.services.impl;

import com.azad.CampusConnectApi.exceptions.ResourceNotFoundException;
import com.azad.CampusConnectApi.models.dtos.CommentDto;
import com.azad.CampusConnectApi.models.entities.AppUserEntity;
import com.azad.CampusConnectApi.models.entities.CommentEntity;
import com.azad.CampusConnectApi.models.entities.PostEntity;
import com.azad.CampusConnectApi.repositories.AppUserRepository;
import com.azad.CampusConnectApi.repositories.CommentRepository;
import com.azad.CampusConnectApi.repositories.LinkRepository;
import com.azad.CampusConnectApi.repositories.PostRepository;
import com.azad.CampusConnectApi.services.CommentService;
import com.azad.CampusConnectApi.utils.ApiUtils;
import com.azad.CampusConnectApi.utils.PagingAndSortingObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private ApiUtils apiUtils;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto create(CommentDto requestDto) {

        // Validate that who made this comment is a registered user
        appUserRepository.findById(requestDto.getCommentedByUserId()).orElseThrow(
                () -> new ResourceNotFoundException("AppUser", requestDto.getCommentedByUserId()));

        // Get postId
        Long postId = requestDto.getPostId();

        // Get Post by postId
        PostEntity post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", postId));

        // Set post to comment mapping (1-to-n) and save comment
        CommentEntity comment = modelMapper.map(requestDto, CommentEntity.class);
        comment.setCommentedAt(LocalDateTime.now());
        comment.setPost(post);
        comment.setLinks(null);
        CommentEntity savedComment = commentRepository.save(comment);

        // Delete all links that are not mapped with any comment
        linkRepository.deleteLinksWithoutCommentMapping();

        // Save the links for this comment
        apiUtils.saveLinksForComment(linkRepository, requestDto.getLinks(), savedComment);

        // Return the comment data as CommentDto
        CommentDto savedCommentDto = modelMapper.map(savedComment, CommentDto.class);
        savedCommentDto.setCommentedByUserId(requestDto.getCommentedByUserId());
        savedCommentDto.setCommentedToUserId(requestDto.getCommentedToUserId());
        savedCommentDto.setPostId(postId);

        return savedCommentDto;
    }

    @Override
    public List<CommentDto> getAll(PagingAndSortingObject ps) {
        return null;
    }

    @Override
    public CommentDto getById(Long id) {
        return null;
    }

    @Override
    public CommentDto updateById(Long id, CommentDto updatedRequestDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
