package com.azad.jsonplaceholder.services.impl;

import com.azad.jsonplaceholder.models.dtos.PostDto;
import com.azad.jsonplaceholder.models.entities.PostEntity;
import com.azad.jsonplaceholder.repos.PostRepository;
import com.azad.jsonplaceholder.security.auth.api.AuthService;
import com.azad.jsonplaceholder.security.entities.MemberEntity;
import com.azad.jsonplaceholder.services.PostService;
import com.azad.jsonplaceholder.utils.AppUtils;
import com.azad.jsonplaceholder.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AppUtils appUtils;
    @Autowired
    private AuthService authService;

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto create(PostDto requestBody) {

        MemberEntity loggedInMember = authService.getLoggedInMember();

        PostEntity postEntity = modelMapper.map(requestBody, PostEntity.class);
        postEntity.setMember(loggedInMember);

        PostEntity savedPostEntity = postRepository.save(postEntity);

        PostDto postDto = modelMapper.map(savedPostEntity, PostDto.class);
        postDto.setUserId(savedPostEntity.getMember().getId());

        return postDto;
    }

    @Override
    public List<PostDto> getAll(PagingAndSorting ps) {

        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        MemberEntity loggedInMember = authService.getLoggedInMember();

        Optional<List<PostEntity>> byUserId = postRepository.findByUserId(loggedInMember.getId(), pageable);
        if (!byUserId.isPresent())
            return null;

        List<PostEntity> allPostsFromDb = byUserId.get();
        if (allPostsFromDb.size() == 0)
            return null;

        return allPostsFromDb.stream()
                .map(postEntity -> {
                    PostDto postDto = modelMapper.map(postEntity, PostDto.class);
                    postDto.setUserId(postEntity.getMember().getId());
                    return postDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public PostDto getById(Long id) {

        MemberEntity loggedInMember = authService.getLoggedInMember();

        PostEntity postFromDb = postRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Post not found with id: " + id));

        if (!Objects.equals(postFromDb.getMember().getId(), loggedInMember.getId())) // logged-in user is not the owner of the fetched post
            if (!authService.loggedInUserIsAdmin()) // logged-in user is not an admin too
                throw new RuntimeException("Resource not authorized for " + loggedInMember.getUsername() + " with id: " + loggedInMember.getId());

        PostDto postDto = modelMapper.map(postFromDb, PostDto.class);
        postDto.setUserId(postFromDb.getMember().getId());

        return postDto;
    }

    @Override
    public PostDto updateById(Long id, PostDto updatedRequestBody) {

        MemberEntity loggedInMember = authService.getLoggedInMember();

        PostEntity postFromDb = postRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Post not found with id: " + id));

        if (!Objects.equals(postFromDb.getMember().getId(), loggedInMember.getId())) // logged-in user is not the owner of the fetched Post
            if (!authService.loggedInUserIsAdmin()) // logged-in user is not an admin too
                throw new RuntimeException("Resource not authorized for " + loggedInMember.getUsername() + " with id: " + loggedInMember.getId());

        if (updatedRequestBody.getTitle() != null)
            postFromDb.setTitle(updatedRequestBody.getTitle());
        if (updatedRequestBody.getBody() != null)
            postFromDb.setBody(updatedRequestBody.getBody());

        PostEntity updatedPost = postRepository.save(postFromDb);

        PostDto postDto = modelMapper.map(updatedPost, PostDto.class);
        postDto.setUserId(updatedPost.getMember().getId());

        return postDto;
    }

    @Override
    public void deleteById(Long id) {

        MemberEntity loggedInMember = authService.getLoggedInMember();

        PostEntity postFromDb = postRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Post not found with id: " + id));

        if (!Objects.equals(postFromDb.getMember().getId(), loggedInMember.getId())) // logged-in user is not the owner of the fetched post
            if (!authService.loggedInUserIsAdmin()) // logged-in user is not an admin too
                throw new RuntimeException("Resource not authorized for " + loggedInMember.getUsername() + " with id: " + loggedInMember.getId());

        postRepository.delete(postFromDb);
    }
}
