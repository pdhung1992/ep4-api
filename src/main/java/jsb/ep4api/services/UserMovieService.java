package jsb.ep4api.services;

import jsb.ep4api.entities.Movie;
import jsb.ep4api.entities.Transaction;
import jsb.ep4api.entities.User;
import jsb.ep4api.entities.UserMovie;
import jsb.ep4api.repositories.UserMovieRepository;
import jsb.ep4api.specifications.UserMovieSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static jsb.ep4api.constants.Constants.CURRENT_TIME;
import static jsb.ep4api.constants.Constants.MOVIE_NOT_FOUND_MESSAGE;

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

    public UserMovie getUserMovieByUserIdAndMovieId(Long userId, Long movieId) {
        Specification<UserMovie> spec = Specification.where(null);
        spec = spec.and(UserMovieSpecification.hasUserId(userId));
        spec = spec.and(UserMovieSpecification.hasMovieId(movieId));
        spec = spec.and(UserMovieSpecification.hasNoDeletedFlag());
        return userMovieRepository.findOne(spec).orElse(null);
    }

    public void updateUserMovieByTransaction(Transaction transaction, User user) {
        Movie movie = transaction.getMovie();

        UserMovie userMovie = getUserMovieByUserIdAndMovieId(user.getId(), movie.getId());
        if (userMovie != null) {
            if (userMovie.getExpiredAt().isAfter(CURRENT_TIME)) {
                userMovie.setExpiredAt(userMovie.getExpiredAt().plusDays(60));
                updateUserMovie(userMovie);
            } else {
                userMovie.setExpiredAt(CURRENT_TIME.plusDays(60));
                updateUserMovie(userMovie);
            }
        } else {
            userMovie = new UserMovie();
            userMovie.setUser(user);
            userMovie.setMovie(movie);
            userMovie.setExpiredAt(CURRENT_TIME.plusDays(60));
            createUserMovie(userMovie);
        }
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
