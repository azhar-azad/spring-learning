package com.azad.CampusConnectApi.services;

import com.azad.CampusConnectApi.models.dtos.PostDto;
import com.azad.CampusConnectApi.utils.PagingAndSortingObject;

import java.util.List;

public interface PostService extends GenericApiService<PostDto> {
    List<PostDto> getAllByAppUserId(Long appUserId, PagingAndSortingObject ps);

    PostDto getPostByIdAndAppUserId(Long appUserId, Long id);

    PostDto updatePostByIdAndAppUserId(Long appUserId, Long id, PostDto updatedDto);

    void deletePostByIdAndAppUserId(Long appUserId, Long id);
}
