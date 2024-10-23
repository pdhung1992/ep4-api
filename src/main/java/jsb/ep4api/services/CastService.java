package jsb.ep4api.services;

import jsb.ep4api.entities.Cast;
import jsb.ep4api.repositories.CastRepository;
import jsb.ep4api.specifications.CastSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CastService {
    @Autowired
    private CastRepository castRepository;

    public List<Cast> getMainCastsByMovieId(Long movieId) {
        Specification<Cast> spec = Specification.where(null);
        spec = spec.and(CastSpecifications.hasMovieId(movieId));
        spec = spec.and(CastSpecifications.isMainCast());
        spec = spec.and(CastSpecifications.hasNoDeleteFlag());

        return castRepository.findAll(spec);
    }

    public void createCast(Cast cast) {
        castRepository.save(cast);
    }
}
