package jsb.ep4api.services;


import jsb.ep4api.entities.Movie;
import jsb.ep4api.entities.Transaction;
import jsb.ep4api.entities.User;
import jsb.ep4api.entities.UserMovie;
import jsb.ep4api.repositories.UserMovieRepository;
import jsb.ep4api.specifications.UserMovieSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static jsb.ep4api.constants.Constants.*;

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

    @Transactional
    public void updateUserMovieByTransaction(Transaction transaction, User user) {
        Movie movie = transaction.getMovie();

        UserMovie userMovie = getUserMovieByUserIdAndMovieId(user.getId(), movie.getId());

        LocalDateTime currentTime = LocalDateTime.now();

        if (userMovie != null) {
            if (userMovie.getExpiredAt().isAfter(currentTime) && userMovie.getExpiredAt().isBefore(currentTime.plusDays(29))) {
                userMovie.setExpiredAt(userMovie.getExpiredAt().plusDays(30));
            } else if (userMovie.getExpiredAt().isBefore(currentTime)) {
                userMovie.setExpiredAt(currentTime.plusDays(30));
            }

            userMovie.setModifiedAt(currentTime);
            updateUserMovie(userMovie);
        } else {
            userMovie = new UserMovie();
            userMovie.setUser(user);
            userMovie.setMovie(movie);
            userMovie.setExpiredAt(currentTime.plusDays(30));
            userMovie.setCreatedAt(currentTime);
            userMovie.setModifiedAt(currentTime);
            userMovie.setDeleteFlag(DEFAULT_DELETE_FLAG);
            createUserMovie(userMovie);
        }
    }


    public Page<UserMovie> getUserMoviesByUserId(Long userId, Boolean isExpired, Integer pageNo, Integer pageSize, String sortField, String sortDir, String search) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Specification<UserMovie> spec = Specification.where(null);
        spec = spec.and(UserMovieSpecification.hasUserId(userId));
        spec = spec.and(UserMovieSpecification.hasNoDeletedFlag());

        if (search != null) {
            spec = spec.and(UserMovieSpecification.hasMovieTitleContaining(search));
            spec = spec.or(UserMovieSpecification.hasMovieOriginalTitleContaining(search));
        }

        if (isExpired != null) {
            if (isExpired) {
                spec = spec.and(UserMovieSpecification.hasExpired());
            } else {
                spec = spec.and(UserMovieSpecification.hasNotExpired());
            }
        }

        return userMovieRepository.findAll(spec,pageable);
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
