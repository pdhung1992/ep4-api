package jsb.ep4api.services;

import jsb.ep4api.entities.MovieGenre;
import jsb.ep4api.repositories.MovieGenreRepository;
import jsb.ep4api.specifications.MovieGenreSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieGenreService {
    @Autowired
    private MovieGenreRepository movieGenreRepository;

    public List<MovieGenre> getMovieGenresByMovieId(Long id) {
        Specification<MovieGenre> spec = Specification.where(null);
        spec = spec.and(MovieGenreSpecifications.hasMovieId(id));
        spec = spec.and(MovieGenreSpecifications.hasNoDeletedFlag());

        return movieGenreRepository.findAll(spec);
    }

    public List<MovieGenre> getMovieGenresByGenreId(Long id) {
        Specification<MovieGenre> spec = Specification.where(null);
        spec = spec.and(MovieGenreSpecifications.hasGenreId(id));
        spec = spec.and(MovieGenreSpecifications.hasNoDeletedFlag());

        return movieGenreRepository.findAll(spec);
    }

    public MovieGenre getMovieGenreById(Long id) {
        Specification<MovieGenre> spec = Specification.where(null);
        spec = spec.and(MovieGenreSpecifications.hasId(id));
        spec = spec.and(MovieGenreSpecifications.hasNoDeletedFlag());

        return movieGenreRepository.findOne(spec).orElse(null);
    }

    public MovieGenre getMovieGenreByMovieIdAndGenreId(Long movieId, Long genreId) {
        Specification<MovieGenre> spec = Specification.where(null);
        spec = spec.and(MovieGenreSpecifications.hasMovieId(movieId));
        spec = spec.and(MovieGenreSpecifications.hasGenreId(genreId));
        spec = spec.and(MovieGenreSpecifications.hasNoDeletedFlag());

        return movieGenreRepository.findOne(spec).orElse(null);
    }

    public void createMovieGenre(MovieGenre movieGenre) {
        movieGenreRepository.save(movieGenre);
    }
    public void updateMovieGenre(MovieGenre movieGenre) {
        movieGenreRepository.save(movieGenre);
    }
    public void deleteMovieGenre(MovieGenre movieGenre) {
        movieGenreRepository.save(movieGenre);
    }
}
