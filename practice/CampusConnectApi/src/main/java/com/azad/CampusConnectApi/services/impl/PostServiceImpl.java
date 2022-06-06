package com.azad.CampusConnectApi.services.impl;

import com.azad.CampusConnectApi.exceptions.ResourceNotFoundException;
import com.azad.CampusConnectApi.models.AppUser;
import com.azad.CampusConnectApi.models.Link;
import com.azad.CampusConnectApi.models.dtos.PostDto;
import com.azad.CampusConnectApi.models.entities.AppUserEntity;
import com.azad.CampusConnectApi.models.entities.LinkEntity;
import com.azad.CampusConnectApi.models.entities.PostEntity;
import com.azad.CampusConnectApi.models.requests.PostRequest;
import com.azad.CampusConnectApi.repositories.AppUserRepository;
import com.azad.CampusConnectApi.repositories.LinkRepository;
import com.azad.CampusConnectApi.repositories.PostRepository;
import com.azad.CampusConnectApi.services.PostService;
import com.azad.CampusConnectApi.utils.ApiUtils;
import com.azad.CampusConnectApi.utils.AppUtils;
import com.azad.CampusConnectApi.utils.PagingAndSortingObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private ApiUtils apiUtils;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto create(PostDto requestDto) {

        // Get appUserId
        Long appUserId = requestDto.getAppUserId();

        // Get AppUser by appUserId
        AppUserEntity appUser = appUserRepository.findById(appUserId).orElseThrow(
                () -> new ResourceNotFoundException("AppUser", appUserId));

        // Set appUser to post mapping (1-to-n) and save post
        PostEntity post = modelMapper.map(requestDto, PostEntity.class);
        post.setPostedAt(LocalDateTime.now());
        post.setAppUser(appUser);
        post.setLinks(null); // removing links as links are still not saved, so no mapping possible
        PostEntity savedPost = postRepository.save(post);

        // Delete all links that are not mapped with any post
        linkRepository.deleteLinksWithoutPostIdMapping();

        // Save the links for this post
        apiUtils.saveLinksForPost(linkRepository, requestDto.getLinks(), savedPost);

        // Return the post data as PostDto
        PostDto savedPostDto = modelMapper.map(savedPost, PostDto.class);
        savedPostDto.setLinks(requestDto.getLinks());
        savedPostDto.setAppUserId(appUserId);

        return savedPostDto;
    }

    @Override
    public List<PostDto> getAll(PagingAndSortingObject ps) {
        return null;
    }

    @Override
    public PostDto getById(Long id) {

        PostEntity post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", id));

        List<LinkEntity> links = post.getLinks();
        post.setLinks(null);
        List<String> linkUrls = new ArrayList<>();
        links.forEach(linkEntity -> linkUrls.add(linkEntity.getLinkUrl()));

        PostDto postDto = modelMapper.map(post, PostDto.class);
        postDto.setLinks(linkUrls);

        return postDto;
    }

    @Override
    public PostDto updateById(Long id, PostDto updatedRequestDto) {

        PostEntity post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", id));

        if (updatedRequestDto.getText() != null) {
            post.setText(updatedRequestDto.getText());
        }
        if (updatedRequestDto.getLinks() != null && updatedRequestDto.getLinks().size() != 0) {

            linkRepository.deleteByPostId(post.getId());

            apiUtils.saveLinksForPost(linkRepository, updatedRequestDto.getLinks(), post);
        }

        PostEntity updatedPost = postRepository.save(post);

        List<LinkEntity> links = updatedPost.getLinks();
        updatedPost.setLinks(null);
        List<String> linkUrls = new ArrayList<>();
        links.forEach(linkEntity -> linkUrls.add(linkEntity.getLinkUrl()));

        PostDto postDto = modelMapper.map(updatedPost, PostDto.class);
        postDto.setLinks(linkUrls);

        return postDto;
    }

    @Override
    public void deleteById(Long id) {

        PostEntity post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", id));

        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getAllByAppUserId(Long appUserId, PagingAndSortingObject ps) {

        appUserRepository.findById(appUserId).orElseThrow(() -> new ResourceNotFoundException("AppUser", appUserId));

        Pageable pageable;
        if (ps.getSort().isEmpty()) {
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        } else {
            Sort sortBy = appUtils.getSortBy(ps.getSort(), ps.getOrder());
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), sortBy);
        }

        List<PostEntity> posts = postRepository.findByAppUserId(appUserId, pageable);

        List<PostDto> postDtos = new ArrayList<>();
        for (PostEntity post: posts) {
            List<LinkEntity> links = post.getLinks();
            post.setLinks(null);
            List<String> linkUrls = new ArrayList<>();
            links.forEach(linkEntity -> linkUrls.add(linkEntity.getLinkUrl()));

            PostDto postDto = modelMapper.map(post, PostDto.class);
            postDto.setLinks(linkUrls);
            postDto.setAppUserId(appUserId);
            postDtos.add(postDto);
        }

        return postDtos;
    }

    @Override
    public PostDto getPostByIdAndAppUserId(Long appUserId, Long id) {

        appUserRepository.findById(appUserId).orElseThrow(() -> new ResourceNotFoundException("AppUser", appUserId));

        PostDto postDto = getById(id);
        postDto.setAppUserId(appUserId);

        return postDto;
    }

    @Override
    public PostDto updatePostByIdAndAppUserId(Long appUserId, Long id, PostDto updatedDto) {

        appUserRepository.findById(appUserId).orElseThrow(() -> new ResourceNotFoundException("AppUser", appUserId));

        PostDto postDto = updateById(id, updatedDto);
        postDto.setAppUserId(appUserId);

        return postDto;
    }

    @Override
    public void deletePostByIdAndAppUserId(Long appUserId, Long id) {

        appUserRepository.findById(appUserId).orElseThrow(() -> new ResourceNotFoundException("AppUser", appUserId));

        deleteById(id);
    }


}
