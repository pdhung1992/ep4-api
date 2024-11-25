package jsb.ep4api.services;

import jsb.ep4api.entities.MovieFile;
import jsb.ep4api.repositories.MovieFileRepository;
import jsb.ep4api.specifications.MovieFileSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieFileService {
    @Autowired
    private MovieFileRepository movieFileRepository;

    public List<MovieFile> getMovieFilesByMovieId(Long movieId) {
        Specification<MovieFile> spec = Specification.where(null);
        spec = spec.and(MovieFileSpecifications.hasMovieId(movieId));
        spec = spec.and(MovieFileSpecifications.hasNoDeleteFlag());

        return movieFileRepository.findAll(spec);
    }

    public MovieFile getMovieFileByFilename(String filename) {
        Specification<MovieFile> spec = Specification.where(null);
        spec = spec.and(MovieFileSpecifications.hasFilename(filename));
        spec = spec.and(MovieFileSpecifications.hasNoDeleteFlag());

        return movieFileRepository.findOne(spec).orElse(null);
    }

    public void createMovieFile(MovieFile movieFile) {
        movieFileRepository.save(movieFile);
    }
    public void updateMovieFile(MovieFile movieFile) {
        movieFileRepository.save(movieFile);
    }
}
