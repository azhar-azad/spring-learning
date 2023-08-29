package com.azad.moviepedia.models.movie;

import com.azad.moviepedia.models.composits.MoviePeople;
import com.azad.moviepedia.models.people.PeopleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "movie")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    @Column(name = "movie_name", nullable = false)
    private String movieName;

    @Column(name = "summary", nullable = false)
    private String summary;

    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;

    @Column(name = "pg_rating", nullable = false)
    private String pgRating;

    @Column(name = "runtime", nullable = false)
    private String runtime;

//    private String storyLine;

    @Column(name = "user_rating")
    private Double memberRating;

    @Column(name = "critic_rating")
    private Double criticRating;

    @Column(name = "imdb_rating")
    private Double imdbRating;

    @Column(name = "total_ratings")
    private Long totalRatings;

    @Column(name = "total_member_reviews")
    private Long totalMemberReviews;

    @Column(name = "total_critic_reviews")
    private Long totalCriticReviews;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MoviePeople> peoples = new ArrayList<>();

    public MovieEntity() {}

    public void addPeople(PeopleEntity people) {
        MoviePeople moviePeople = new MoviePeople(this, people);
        peoples.add(moviePeople);
        people.getMovies().add(moviePeople);
    }

    public void removePeople(PeopleEntity people) {
        for (Iterator<MoviePeople> iterator = peoples.iterator(); iterator.hasNext();) {
            MoviePeople moviePeople = iterator.next();

            if (moviePeople.getMovie().equals(this) && moviePeople.getPeople().equals(people)) {
                iterator.remove();
                moviePeople.getPeople().getMovies().remove(moviePeople);
                moviePeople.setMovie(null);
                moviePeople.setPeople(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof MovieEntity))
            return false;

        return id != null && id.equals(((MovieEntity) o).getId());
    }
}
