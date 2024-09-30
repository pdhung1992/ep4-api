package jsb.ep4api.services;

import jsb.ep4api.entities.Movie;
import jsb.ep4api.repositories.MovieRepository;
import jsb.ep4api.specifications.MovieSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public Page<Movie> getMovies(
            int pageNo,
            int pageSize,
            String sortField,
            String sortDir,
            String title,
            Long countryId
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Specification<Movie> spec = Specification.where(null);
        if (title != null) {
            spec = spec.and(MovieSpecifications.titleContains(title));
        }
        if (countryId != null) {
            spec = spec.and(MovieSpecifications.hasCountryId(countryId));
        }
        spec = spec.and(MovieSpecifications.hasNoDeletedFlag());

        return movieRepository.findAll(spec, pageable);
    }

    public Page<Movie> getDisplayMovies(
            int pageNo,
            int pageSize,
            String sortField,
            String sortDir,
            String title,
            Long countryId
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Specification<Movie> spec = Specification.where(null);
        if (title != null) {
            spec = spec.and(MovieSpecifications.titleContains(title));
        }
        if (countryId != null) {
            spec = spec.and(MovieSpecifications.hasCountryId(countryId));
        }
        spec = spec.and(MovieSpecifications.hasNoDeletedFlag());
        spec = spec.and(MovieSpecifications.hasShowFlag(true));

        return movieRepository.findAll(spec, pageable);
    }

    public List<Movie> getShowAtHomeMovies() {
        Specification<Movie> spec = Specification.where(null);
        spec = spec.and(MovieSpecifications.hasNoDeletedFlag());
        spec = spec.and(MovieSpecifications.hasShowAtHomeFlag(true));
        return movieRepository.findAll(spec);
    }

    public Movie getMovieById(Long id) {
        Specification<Movie> spec = Specification.where(null);
        spec = spec.and(MovieSpecifications.hasId(id));
        spec = spec.and(MovieSpecifications.hasNoDeletedFlag());
        return movieRepository.findOne(spec).orElse(null);
    }

    public void createMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public void updateMovie(Movie movie) {
        movieRepository.save(movie);
    }


}
