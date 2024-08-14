package jsb.ep4api.services;

import jsb.ep4api.repositories.MovieGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieGenreService {
    @Autowired
    private MovieGenreRepository movieGenreRepository;

}
