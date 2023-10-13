package com.azad.moviepedia.services.movie;

import com.azad.moviepedia.common.AppUtils;
import com.azad.moviepedia.common.PagingAndSorting;
import com.azad.moviepedia.models.auth.MemberEntity;
import com.azad.moviepedia.models.movie.MovieDto;
import com.azad.moviepedia.models.movie.MovieEntity;
import com.azad.moviepedia.models.people.People;
import com.azad.moviepedia.models.people.PeopleEntity;
import com.azad.moviepedia.models.people.PeopleRole;
import com.azad.moviepedia.repositories.MovieRepository;
import com.azad.moviepedia.repositories.PeopleRepository;
import com.azad.moviepedia.services.auth.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private AuthService authService;

    private final MovieRepository movieRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, PeopleRepository peopleRepository) {
        this.movieRepository = movieRepository;
        this.peopleRepository = peopleRepository;
    }

    @Override
    public MovieDto create(MovieDto dto) {

        MemberEntity loggedInMember = authService.getLoggedInMember();

        MovieEntity movie = modelMapper.map(dto, MovieEntity.class);

        if (dto.getDirectors() != null || !dto.getDirectors().isEmpty()) {
            List<PeopleEntity> directors = dto.getDirectors().stream()
                    .map(director -> {
                        Optional<PeopleEntity> byFullName = peopleRepository.findByFullName(director.getFullName());
                        return byFullName.orElseGet(() -> peopleRepository.save(modelMapper.map(director, PeopleEntity.class)));
                    })
                    .toList();
            movie.addPeoples(directors, PeopleRole.DIRECTOR);
        }

        if (dto.getWriters() != null || !dto.getWriters().isEmpty()) {
            List<PeopleEntity> writers = dto.getWriters().stream()
                    .map(writer -> {
                        Optional<PeopleEntity> byFullName = peopleRepository.findByFullName(writer.getFullName());
                        return byFullName.orElseGet(() -> peopleRepository.save(modelMapper.map(writer, PeopleEntity.class)));
                    })
                    .toList();
            movie.addPeoples(writers, PeopleRole.WRITER);
        }

        if (dto.getStars() != null || !dto.getStars().isEmpty()) {
            List<PeopleEntity> stars = dto.getStars().stream()
                    .map(star -> {
                        Optional<PeopleEntity> byFullName = peopleRepository.findByFullName(star.getFullName());
                        return byFullName.orElseGet(() -> peopleRepository.save(modelMapper.map(star, PeopleEntity.class)));
                    })
                    .toList();
            movie.addPeoples(stars, PeopleRole.ACTOR);
        }

        movie.setMemberRating(0.0);
        movie.setCriticRating(0.0);
        movie.setTotalRatings(0L);
        movie.setTotalMemberReviews(0L);
        movie.setTotalCriticReviews(0L);
        movie.setIsVerified(!Objects.equals(loggedInMember.getRole().getRoleName(), "USER"));

        MovieEntity savedMovie = movieRepository.save(movie);

        MovieDto savedDto = modelMapper.map(savedMovie, MovieDto.class);

        return savedDto;
    }

    @Override
    public List<MovieDto> getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public MovieDto getById(Long id) {
        return null;
    }

    @Override
    public MovieDto updateById(Long id, MovieDto updatedDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
