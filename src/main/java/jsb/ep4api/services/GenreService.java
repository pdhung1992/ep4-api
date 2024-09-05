package jsb.ep4api.services;

import jsb.ep4api.entities.Genre;
import jsb.ep4api.repositories.GenreRepository;
import jsb.ep4api.specifications.GenreSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        Specification<Genre> spec = Specification.where(null);
        spec = spec.and(GenreSpecifications.hasNoDeleteFlag());
        return genreRepository.findAll(spec);
    }
}
