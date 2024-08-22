package com.azad.moviepedia.services.impl;

import com.azad.moviepedia.common.PagingAndSorting;
import com.azad.moviepedia.models.constants.GenreName;
import com.azad.moviepedia.models.dtos.AwardDto;
import com.azad.moviepedia.models.dtos.MovieDto;
import com.azad.moviepedia.models.entities.AwardEntity;
import com.azad.moviepedia.models.entities.DirectorEntity;
import com.azad.moviepedia.models.entities.GenreEntity;
import com.azad.moviepedia.models.entities.MovieEntity;
import com.azad.moviepedia.repositories.*;
import com.azad.moviepedia.services.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private WriterRepository writerRepository;
    @Autowired
    private CastRepository castRepository;
    @Autowired
    private AwardRepository awardRepository;

    private final MovieRepository repository;

    @Autowired
    public MovieServiceImpl(MovieRepository repository) {
        this.repository = repository;
    }

    @Override
    public MovieDto create(MovieDto dto) throws RuntimeException {

        MovieEntity movieEntity = modelMapper.map(dto, MovieEntity.class);

        // save or link genre
        dto.getGenres().forEach(genreName -> {
            GenreEntity genre = genreRepository.findByName(genreName.name())
                    .orElseGet(() -> genreRepository.save(new GenreEntity(genreName.name())));
            movieEntity.getGenres().add(genre);
        });

        // save or link directors
        if (!dto.getDirectorIds().isEmpty()) {
            // director ids are passed
            dto.getDirectorIds().forEach(directorId -> {
                DirectorEntity director = directorRepository.findById(directorId)
                        .orElseThrow(() -> new IllegalArgumentException("Director not found with id: " + directorId));
                movieEntity.getDirectors().add(director);
            });
        } else if (!dto.getDirectors().isEmpty()) {
            // director objects are passed
            dto.getDirectors().forEach(directorDto -> {
                DirectorEntity director = directorRepository.findByFirstNameAndLastNameAndBirthDate(
                        directorDto.getFirstName(), directorDto.getLastName(), directorDto.getBirthDate())
                        .orElseGet(() -> {
                            // new Director
                            DirectorEntity newDirector = modelMapper.map(directorDto, DirectorEntity.class);
                            directorDto.getAwards().forEach(awardDto -> {
                                // save or link director awards
                                AwardEntity award = awardRepository.findById(awardDto.getId())
                                        .orElseGet(() -> awardRepository.save(modelMapper.map(awardDto, AwardEntity.class)));
                                newDirector.getAwards().add(award);
                            });
                            return directorRepository.save(newDirector);
                        });
                movieEntity.getDirectors().add(director);
            });
        } else {
            throw new IllegalArgumentException("Must provide Director information");
        }

        // save or link writers
        // save or link casts
        // save or link awards


        return null;
    }

    @Override
    public List<MovieDto> getAll(PagingAndSorting ps) throws RuntimeException {
        return List.of();
    }

    @Override
    public MovieDto getById(Long id) throws RuntimeException {
        return null;
    }

    @Override
    public MovieDto updateById(Long id, MovieDto updatedDto) throws RuntimeException {
        return null;
    }

    @Override
    public void deleteById(Long id) throws RuntimeException {

    }
}
