package com.azad.moviepedia.services.impl;

import com.azad.moviepedia.common.AppUtils;
import com.azad.moviepedia.common.PagingAndSorting;
import com.azad.moviepedia.models.constants.GenreName;
import com.azad.moviepedia.models.dtos.AwardDto;
import com.azad.moviepedia.models.dtos.MovieDto;
import com.azad.moviepedia.models.entities.*;
import com.azad.moviepedia.repositories.*;
import com.azad.moviepedia.services.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

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

        MovieEntity movie = modelMapper.map(dto, MovieEntity.class);

        // save or link genre
        dto.getGenres().forEach(genreName -> {
            GenreEntity genre = genreRepository.findByName(genreName)
                    .orElseGet(() -> genreRepository.save(new GenreEntity(genreName.toUpperCase())));
            movie.getGenres().add(genre);
        });

        // save or link directors
        if (!dto.getDirectors().isEmpty()) {
            dto.getDirectors().forEach(directorDto -> {
                DirectorEntity director = directorRepository.findById(directorDto.getId() == null ? 0 : directorDto.getId())
                        .orElseGet(() -> {
                            DirectorEntity newDirector = modelMapper.map(directorDto, DirectorEntity.class);
                            newDirector.setAge(appUtils.calculateAge(directorDto.getBirthDate()));
                            if (directorDto.getAwards() != null) {
                                newDirector.getAwards().addAll(saveAwards(directorDto.getAwards()));
                            }
                            return directorRepository.save(newDirector);
                        });
                movie.getDirectors().add(director);
            });
        } else {
            throw new IllegalArgumentException("Must provide Director information");
        }

        // save or link writers
        if (!dto.getWriters().isEmpty()) {
            dto.getWriters().forEach(writerDto -> {
                WriterEntity writer = writerRepository.findById(writerDto.getId() == null ? 0 : writerDto.getId())
                        .orElseGet(() -> {
                            WriterEntity newWriter = modelMapper.map(writerDto, WriterEntity.class);
                            newWriter.setAge(appUtils.calculateAge(writerDto.getBirthDate()));
                            if (writerDto.getAwards() != null) {
                                newWriter.getAwards().addAll(saveAwards(writerDto.getAwards()));
                            }
                            return writerRepository.save(newWriter);
                        });
                movie.getWriters().add(writer);
            });
        } else {
            throw new IllegalArgumentException("Must provide Writer information");
        }

        // save or link casts
        if (!dto.getCasts().isEmpty()) {
            dto.getCasts().forEach(castDto -> {
                CastEntity cast = castRepository.findById(castDto.getId() == null ? 0 : castDto.getId())
                        .orElseGet(() -> {
                            CastEntity newCast = modelMapper.map(castDto, CastEntity.class);
                            newCast.setAge(appUtils.calculateAge(castDto.getBirthDate()));
                            if (castDto.getAwards() != null) {
                                newCast.getAwards().addAll(saveAwards(castDto.getAwards()));
                            }
                            return castRepository.save(newCast);
                        });

                MovieCastEntity movieCast = new MovieCastEntity();
                movieCast.setCast(cast);
                movieCast.setMovie(movie);
                movieCast.setCharacterName(castDto.getCharacterName());

                movie.getMovieCasts().add(movieCast);
            });
        } else {
            throw new IllegalArgumentException("Must provide Cast information");
        }

        // save or link awards
        if (!dto.getAwards().isEmpty()) {
            dto.getAwards().forEach(awardDto -> {
                AwardEntity award = awardRepository.findById(awardDto.getId() == null ? 0 : awardDto.getId())
                        .orElseGet(() -> awardRepository.save(modelMapper.map(awardDto, AwardEntity.class)));
                movie.getAwards().add(award);
            });
        }

        return modelMapper.map(repository.save(movie), MovieDto.class);
    }

    private Set<AwardEntity> saveAwards(Set<AwardDto> awards) {
        Set<AwardEntity> awardEntities = new HashSet<>();
        awards.forEach(awardDto -> {
            AwardEntity award = awardRepository.findById(awardDto.getId() == null ? 0 : awardDto.getId())
                    .orElseGet(() -> awardRepository.save(modelMapper.map(awardDto, AwardEntity.class)));
            awardEntities.add(award);
        });
        return awardEntities;
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
