package com.azad.jsonplaceholderclone.repos;

import com.azad.jsonplaceholderclone.models.entities.PostEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PagingAndSortingRepository<PostEntity, Long> {
}
