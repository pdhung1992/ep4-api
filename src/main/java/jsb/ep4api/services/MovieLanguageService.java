package jsb.ep4api.services;

import jsb.ep4api.entities.MovieLanguage;
import jsb.ep4api.repositories.MovieLanguageRepository;
import jsb.ep4api.specifications.MovieLanguageSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class MovieLanguageService {
    @Autowired
    private MovieLanguageRepository movieLanguageRepository;

    public MovieLanguage getMovieLanguageById(Long id) {
        Specification<MovieLanguage> spec = Specification.where(null);
        spec = spec.and(MovieLanguageSpecifications.hasId(id));
        spec = spec.and(MovieLanguageSpecifications.hasNoDeletedFlag());

        return movieLanguageRepository.findOne(spec).orElse(null);
    }

    public MovieLanguage getMovieLanguageByMovieIdAndLanguageId(Long movieId, Long languageId) {
        Specification<MovieLanguage> spec = Specification.where(null);
        spec = spec.and(MovieLanguageSpecifications.hasMovieId(movieId));
        spec = spec.and(MovieLanguageSpecifications.hasLanguageId(languageId));
        spec = spec.and(MovieLanguageSpecifications.hasNoDeletedFlag());

        return movieLanguageRepository.findOne(spec).orElse(null);
    }

    public void createMovieLanguage(MovieLanguage movieLanguage) {
        movieLanguageRepository.save(movieLanguage);
    }
    public void updateMovieLanguage(MovieLanguage movieLanguage) {
        movieLanguageRepository.save(movieLanguage);
    }
    public void deleteMovieLanguage(MovieLanguage movieLanguage) {
        movieLanguageRepository.save(movieLanguage);
    }
}
