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

    public List<Movie> getAllMovies() {
        Specification<Movie> spec = Specification.where(null);
        spec = spec.and(MovieSpecifications.hasNoDeletedFlag());
        return movieRepository.findAll(spec);
    }

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
        spec = spec.and(MovieSpecifications.randomOrder());
        return movieRepository.findAll(spec);
    }

    public List<Movie> get10MoviesByCategoryId(Long categoryId) {
        Specification<Movie> spec = Specification.where(null);
        spec = spec.and(MovieSpecifications.belongsToCategory(categoryId));
        spec = spec.and(MovieSpecifications.hasNoDeletedFlag());
        Pageable pageable = PageRequest.of(0, 10);
        return movieRepository.findAll(spec, pageable).getContent();
    }

    public List<Movie> get4MoviesByCategoryId(Long categoryId, Long movieId) {
        Specification<Movie> spec = Specification.where(null);
        spec = spec.and(MovieSpecifications.belongsToCategory(categoryId));
        spec = spec.and(MovieSpecifications.hasNoDeletedFlag());
        spec = spec.and(MovieSpecifications.randomOrder());
        spec = spec.and(MovieSpecifications.hasIdNot(movieId));
        Pageable pageable = PageRequest.of(0, 4);
        return movieRepository.findAll(spec, pageable).getContent();
    }

    public List<Movie> get10MoviesBestByGenreId(List<Long> genreIds) {
        Specification<Movie> spec = Specification.where(null);
        spec = spec.and(MovieSpecifications.hasIdIn(genreIds));
        spec = spec.and(MovieSpecifications.hasNoDeletedFlag());
        spec = spec.and(MovieSpecifications.orderByViews());
        Pageable pageable = PageRequest.of(0, 10);
        return movieRepository.findAll(spec, pageable).getContent();
    }

    public List<Movie> getMoviesByGenreId(int pageNo, int pageSize, List<Long> genreIds) {
        Specification<Movie> spec = Specification.where(null);
        spec = spec.and(MovieSpecifications.hasIdIn(genreIds));
        spec = spec.and(MovieSpecifications.hasNoDeletedFlag());
        spec = spec.and(MovieSpecifications.orderByCreatedAtDesc());
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return movieRepository.findAll(spec, pageable).getContent();
    }

    public Movie getMovieById(Long id) {
        Specification<Movie> spec = Specification.where(null);
        spec = spec.and(MovieSpecifications.hasId(id));
        spec = spec.and(MovieSpecifications.hasNoDeletedFlag());
        return movieRepository.findOne(spec).orElse(null);
    }

    public Movie getMovieBySlug(String slug) {
        Specification<Movie> spec = Specification.where(null);
        spec = spec.and(MovieSpecifications.hasSlug(slug));
        spec = spec.and(MovieSpecifications.hasNoDeletedFlag());
        return movieRepository.findOne(spec).orElse(null);
    }

    public boolean checkMovieIsFree(Long movieId) {
        Specification<Movie> spec = Specification.where(null);
        spec = spec.and(MovieSpecifications.hasId(movieId));
        spec = spec.and(MovieSpecifications.isFreeMovie());
        spec = spec.and(MovieSpecifications.hasNoDeletedFlag());

        return movieRepository.findOne(spec).isPresent();
    }

    public List<Movie> searchMovieByTitle(int pageNo, int pageSize, String title) {
        Specification<Movie> spec = Specification.where(null);
        spec = spec.and(MovieSpecifications.titleContains(title));
        spec = spec.or(MovieSpecifications.originalTitleContains(title));
        spec = spec.and(MovieSpecifications.hasNoDeletedFlag());
        spec = spec.and(MovieSpecifications.orderByCreatedAtDesc());
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return movieRepository.findAll(spec, pageable).getContent();
    }

    public long totalViewOfAllMovies() {
        Specification<Movie> spec = Specification.where(null);
        spec = spec.and(MovieSpecifications.hasNoDeletedFlag());

        long totalView = 0;
        List<Movie> movies = movieRepository.findAll(spec);
        for (Movie movie : movies) {
            totalView += movie.getViews();
        }

        return totalView;
    }

    public void createMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public void updateMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public void deleteMovie(Movie movie) {
        movie.setDeleteFlag(true);
        movieRepository.save(movie);
    }

}
