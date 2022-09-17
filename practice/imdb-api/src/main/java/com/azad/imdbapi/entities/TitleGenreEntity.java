package com.azad.imdbapi.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "title_genre")
public class TitleGenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "title_genre_id")
    private Long id;

    @Column(name = "genre_id")
    private Long genreId;

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "tv_series_id")
    private Long tvSeriesId;

    public TitleGenreEntity() {
    }

    public Long getId() {
        return id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getTvSeriesId() {
        return tvSeriesId;
    }

    public void setTvSeriesId(Long tvSeriesId) {
        this.tvSeriesId = tvSeriesId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }
}
