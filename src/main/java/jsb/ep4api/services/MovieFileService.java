package jsb.ep4api.services;

import jsb.ep4api.entities.MovieFile;
import jsb.ep4api.repositories.MovieFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieFileService {
    @Autowired
    private MovieFileRepository movieFileRepository;

    public void createMovieFile(MovieFile movieFile) {
        movieFileRepository.save(movieFile);
    }
}
