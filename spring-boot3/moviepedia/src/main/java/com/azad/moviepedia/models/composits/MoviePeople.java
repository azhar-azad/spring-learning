package com.azad.moviepedia.models.composits;

import com.azad.moviepedia.models.movie.MovieEntity;
import com.azad.moviepedia.models.people.PeopleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "movie_people")
public class MoviePeople {

    @EmbeddedId
    private MoviePeopleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")
    private MovieEntity movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("peopleId")
    private PeopleEntity people;

    @Column(name = "role")
    private String role;

    private MoviePeople() {}

    public MoviePeople(MovieEntity movie, PeopleEntity people, String role) {
        this.movie = movie;
        this.people = people;
        this.role = role;
        this.id = new MoviePeopleId(movie.getId(), people.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        MoviePeople that = (MoviePeople) o;
        return Objects.equals(movie, that.movie) &&
                Objects.equals(people, that.people);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, people);
    }
}
