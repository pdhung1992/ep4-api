package jsb.ep4api.controllers;

import jakarta.validation.Valid;
import jsb.ep4api.entities.Movie;
import jsb.ep4api.entities.Review;
import jsb.ep4api.entities.User;
import jsb.ep4api.payloads.requests.ReviewRequest;
import jsb.ep4api.payloads.responses.RequestResponse;
import jsb.ep4api.payloads.responses.ReviewResponse;
import jsb.ep4api.payloads.responses.SpecResponse;
import jsb.ep4api.securities.service.UserDetailsImp;
import jsb.ep4api.services.MovieService;
import jsb.ep4api.services.ReviewReactionService;
import jsb.ep4api.services.ReviewService;
import jsb.ep4api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static jsb.ep4api.constants.Constants.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewReactionService reviewReactionService;
    @Autowired
    UserService userService;
    @Autowired
    MovieService movieService;

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<?> getReviewsByMovieId(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize,
            @PathVariable Long movieId
    ) {
        try {
            if (pageNo == null) {
                pageNo = 1;
            }
            if (pageSize == null) {
                pageSize = 10;
            }

            Page<Review> reviews = reviewService.getReviewsByMovieId(pageNo, pageSize, movieId);
            List<Review> reviewList = reviews.getContent();
            List<ReviewResponse> reviewResponses = new ArrayList<>();

            if (!reviewList.isEmpty()) {
                for (Review review : reviewList) {
                    ReviewResponse reviewResponse = new ReviewResponse();
                    reviewResponse.setId(review.getId());
                    reviewResponse.setTitle(review.getTitle());
                    reviewResponse.setContent(review.getContent());
                    reviewResponse.setRating(review.getRating());
                    reviewResponse.setParentId(review.getParentId());
                    reviewResponse.setMovieId(review.getMovie().getId());
                    reviewResponse.setUserId(review.getUser().getId());
                    reviewResponse.setUserName(review.getUser().getFullName());
                    reviewResponse.setLikeCount(reviewReactionService.countReviewLikesByReviewId(review.getId()));
                    reviewResponse.setDislikeCount(reviewReactionService.countReviewDislikesByReviewId(review.getId()));
                    reviewResponses.add(reviewResponse);
                }
            }

            int totalPages = reviews.getTotalPages();
            long totalItems = reviews.getTotalElements();
            long fromItem = (pageNo - 1) * pageSize + 1;
            long toItem = 0;
            if (totalItems > pageSize) {
                toItem = fromItem + pageSize - 1;
            } else {
                toItem = totalItems;
            }

            SpecResponse specResponse = new SpecResponse();
            specResponse.setCurrentPage(pageNo);
            specResponse.setTotalPages(totalPages);
            specResponse.setTotalItems(totalItems);
            specResponse.setFromItem(fromItem);
            specResponse.setToItem(toItem);
            specResponse.setPageSize(pageSize);
            specResponse.setData(reviewResponses);

            return ResponseEntity.ok(specResponse);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/parent/{parentId}")
    public ResponseEntity<?> getReviewsByParentId(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize,
            @PathVariable Long parentId
    ) {
        try {
            if (pageNo == null) {
                pageNo = 1;
            }
            if (pageSize == null) {
                pageSize = 10;
            }

            Page<Review> reviews = reviewService.getReviewsByParentId(pageNo, pageSize, parentId);
            List<Review> reviewList = reviews.getContent();
            List<ReviewResponse> reviewResponses = new ArrayList<>();

            if (!reviewList.isEmpty()) {
                for (Review review : reviewList) {
                    ReviewResponse reviewResponse = new ReviewResponse();
                    reviewResponse.setId(review.getId());
                    reviewResponse.setTitle(review.getTitle());
                    reviewResponse.setContent(review.getContent());
                    reviewResponse.setRating(review.getRating());
                    reviewResponse.setParentId(review.getParentId());
                    reviewResponse.setMovieId(review.getMovie().getId());
                    reviewResponse.setUserId(review.getUser().getId());
                    reviewResponse.setUserName(review.getUser().getFullName());
                    reviewResponse.setLikeCount(reviewReactionService.countReviewLikesByReviewId(review.getId()));
                    reviewResponse.setDislikeCount(reviewReactionService.countReviewDislikesByReviewId(review.getId()));
                    reviewResponses.add(reviewResponse);
                }
            }

            int totalPages = reviews.getTotalPages();
            long totalItems = reviews.getTotalElements();
            long fromItem = (pageNo - 1) * pageSize + 1;
            long toItem = 0;
            if (totalItems > pageSize) {
                toItem = fromItem + pageSize - 1;
            } else {
                toItem = totalItems;
            }

            SpecResponse specResponse = new SpecResponse();
            specResponse.setCurrentPage(pageNo);
            specResponse.setTotalPages(totalPages);
            specResponse.setTotalItems(totalItems);
            specResponse.setFromItem(fromItem);
            specResponse.setToItem(toItem);
            specResponse.setPageSize(pageSize);
            specResponse.setData(reviewResponses);

            return ResponseEntity.ok(specResponse);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createReview(@Valid @RequestBody ReviewRequest createReviewRequest, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errorMessages);
            }

            UserDetailsImp userDetailsImp = (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.getUserById(userDetailsImp.getId());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponse(
                        HttpStatus.NOT_FOUND.value(),
                        USER_NOT_FOUND_MESSAGE
                ));
            }

            Movie movie = movieService.getMovieById(createReviewRequest.getMovieId());
            if (movie == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponse(
                        HttpStatus.NOT_FOUND.value(),
                        MOVIE_NOT_FOUND_MESSAGE
                ));
            }

            Review review = new Review();
            review.setTitle(createReviewRequest.getTitle());
            review.setContent(createReviewRequest.getContent());
            review.setRating(createReviewRequest.getRating());
            review.setParentId(createReviewRequest.getParentId());
            review.setMovie(movie);
            review.setUser(user);
            review.setDeleteFlag(DEFAULT_DELETE_FLAG);
            review.setCreatedAt(CURRENT_TIME);
            review.setModifiedAt(CURRENT_TIME);

            reviewService.createReview(review);

            return ResponseEntity.status(HttpStatus.CREATED).body(new RequestResponse(
                    HttpStatus.CREATED.value(),
                    CREATE_REVIEW_SUCCESS
            ));

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{reviewId}")
    public ResponseEntity<?> updateReview(
            @Valid
            @PathVariable Long reviewId,
            @RequestBody ReviewRequest updateReviewRequest,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errorMessages);
            }

            UserDetailsImp userDetailsImp = (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.getUserById(userDetailsImp.getId());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponse(
                        HttpStatus.NOT_FOUND.value(),
                        USER_NOT_FOUND_MESSAGE
                ));
            }

            Review review = reviewService.getReviewById(reviewId);
            if (review == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponse(
                        HttpStatus.NOT_FOUND.value(),
                        REVIEW_NOT_FOUND
                ));
            }

            review.setTitle(updateReviewRequest.getTitle());
            review.setContent(updateReviewRequest.getContent());
            review.setRating(updateReviewRequest.getRating());
            review.setModifiedAt(CURRENT_TIME);

            reviewService.updateReview(review);
            return ResponseEntity.ok(new RequestResponse(
                    HttpStatus.OK.value(),
                    UPDATE_REVIEW_SUCCESS
            ));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
