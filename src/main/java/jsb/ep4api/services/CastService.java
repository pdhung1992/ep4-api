package jsb.ep4api.services;

import jsb.ep4api.entities.Cast;
import jsb.ep4api.repositories.CastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CastService {
    @Autowired
    private CastRepository castRepository;

    public void createCast(Cast cast) {
        castRepository.save(cast);
    }
}
