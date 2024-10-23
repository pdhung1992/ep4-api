package jsb.ep4api.services;

import jsb.ep4api.entities.UserMovie;
import jsb.ep4api.repositories.UserMovieRepository;
import jsb.ep4api.specifications.UserMovieSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserMovieService {
    @Autowired
    private UserMovieRepository userMovieRepository;

    public boolean checkUserCanWatchMovie(Long userId, Long movieId) {
        Specification<UserMovie> spec = Specification.where(null);
        spec = spec.and(UserMovieSpecification.hasUserId(userId));
        spec = spec.and(UserMovieSpecification.hasMovieId(movieId));
        spec = spec.and(UserMovieSpecification.hasNoDeletedFlag());
        spec = spec.and(UserMovieSpecification.hasNotExpired());

        return userMovieRepository.findOne(spec).isPresent();
    }

    public void createUserMovie(UserMovie userMovie) {
        userMovieRepository.save(userMovie);
    }

    public void updateUserMovie(UserMovie userMovie) {
        userMovieRepository.save(userMovie);
    }

    public void deleteUserMovie(Long id) {
        UserMovie userMovie = userMovieRepository.findById(id).orElse(null);
        if (userMovie != null) {
            userMovie.setDeleteFlag(true);
            userMovieRepository.save(userMovie);
        }
    }
}
