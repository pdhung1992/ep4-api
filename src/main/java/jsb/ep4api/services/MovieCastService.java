package jsb.ep4api.services;

import jsb.ep4api.repositories.MovieCastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieCastService {
    @Autowired
    private MovieCastRepository movieCastRepository;

}
