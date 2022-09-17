package com.azad.imdbapi.repos;

import com.azad.imdbapi.entities.TitleGenreEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitleGenreRepository extends PagingAndSortingRepository<TitleGenreEntity, Long> {
    List<TitleGenreEntity> findByGenreIdAndMovieId(Long genreId, Long movieId);
}
