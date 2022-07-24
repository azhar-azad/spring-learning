package com.azad.jsonplaceholderclone.services.impl;

import com.azad.jsonplaceholderclone.models.dtos.PostDto;
import com.azad.jsonplaceholderclone.models.entities.PostEntity;
import com.azad.jsonplaceholderclone.repos.PostRepository;
import com.azad.jsonplaceholderclone.security.auth.api.AuthService;
import com.azad.jsonplaceholderclone.security.entities.MemberEntity;
import com.azad.jsonplaceholderclone.services.PostService;
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

        PostEntity savedPost = postRepository.save(postEntity);

        PostDto postDto = modelMapper.map(savedPost, PostDto.class);
        postDto.setUserId(loggedInMember.getId());

        return postDto;
    }

    @Override
    public List<PostDto> getAll(PagingAndSorting ps) {

        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        List<PostEntity> allPostsFromDb = postRepository.findAll(pageable).getContent();
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
        return null;
    }

    @Override
    public PostDto updateById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
