package jsb.ep4api.controllers;

import jsb.ep4api.entities.Movie;
import jsb.ep4api.entities.Rating;
import jsb.ep4api.entities.User;
import jsb.ep4api.payloads.requests.RatingRequest;
import jsb.ep4api.payloads.responses.RequestResponse;
import jsb.ep4api.securities.service.UserDetailsImp;
import jsb.ep4api.services.MovieService;
import jsb.ep4api.services.RatingService;
import jsb.ep4api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static jsb.ep4api.constants.Constants.*;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private UserService userService;


    @PostMapping("/submit")
    public ResponseEntity<?> submitRating(@RequestBody RatingRequest ratingRequest){
        try {
            UserDetailsImp userDetails = (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = userDetails.getId();

            Rating rating = ratingService.getRatingByUserIdAndMovieId(userId, ratingRequest.getMovieId());
            if (rating == null){
                User user = userService.getUserById(userId);
                if (user == null){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponse(
                            HttpStatus.NOT_FOUND.value(),
                            USER_NOT_FOUND_MESSAGE
                    ));
                }
                Movie movie = movieService.getMovieById(ratingRequest.getMovieId());
                if (movie == null){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponse(
                            HttpStatus.NOT_FOUND.value(),
                            MOVIE_NOT_FOUND_MESSAGE
                    ));
                }

                rating = new Rating();
                rating.setValue(ratingRequest.getValue());
                rating.setUser(user);
                rating.setMovie(movie);
                rating.setDeleteFlag(DEFAULT_DELETE_FLAG);
                rating.setCreatedAt(CURRENT_TIME);
                rating.setModifiedAt(CURRENT_TIME);
                ratingService.saveRating(rating);

            } else {
                rating.setValue(ratingRequest.getValue());
                rating.setModifiedAt(CURRENT_TIME);
                ratingService.saveRating(rating);
            }

            return ResponseEntity.ok(new RequestResponse(
                    HttpStatus.OK.value(),
                    RATING_SUBMIT_SUCCESS_MESSAGE
            ));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
