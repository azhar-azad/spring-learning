package com.azad.moviepedia.models.composits;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class MoviePeopleId implements Serializable {

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "people_id")
    private Long peopleId;

    private MoviePeopleId() {}

    public MoviePeopleId(Long movieId, Long peopleId) {
        this.movieId = movieId;
        this.peopleId = peopleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        MoviePeopleId that = (MoviePeopleId) o;
        return Objects.equals(movieId, that.movieId) &&
                Objects.equals(peopleId, that.peopleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, peopleId);
    }
}
