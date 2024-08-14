package jsb.ep4api.services;

import jsb.ep4api.repositories.MovieLanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieLanguageService {
    @Autowired
    private MovieLanguageRepository movieLanguageRepository;

}
