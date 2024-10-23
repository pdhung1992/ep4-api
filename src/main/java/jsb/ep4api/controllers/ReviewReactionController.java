package jsb.ep4api.controllers;

import jsb.ep4api.entities.Review;
import jsb.ep4api.entities.ReviewReaction;
import jsb.ep4api.entities.User;
import jsb.ep4api.payloads.requests.ReviewReactionRequest;
import jsb.ep4api.payloads.responses.RequestResponse;
import jsb.ep4api.securities.service.UserDetailsImp;
import jsb.ep4api.services.ReviewReactionService;
import jsb.ep4api.services.ReviewService;
import jsb.ep4api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static jsb.ep4api.constants.Constants.*;

@RestController
@RequestMapping("/api/review-reactions")
public class ReviewReactionController {
    @Autowired
    private ReviewReactionService reviewReactionService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/click")
    public ResponseEntity<?> createReviewReaction(@RequestBody ReviewReactionRequest reviewReactionRequest){
        try {
            UserDetailsImp userDetailsImp = (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.getUserById(userDetailsImp.getId());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponse(
                        HttpStatus.NOT_FOUND.value(),
                        USER_NOT_FOUND_MESSAGE
                ));
            }

            Review review = reviewService.getReviewById(reviewReactionRequest.getReviewId());
            if (review == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponse(
                        HttpStatus.NOT_FOUND.value(),
                        REVIEW_NOT_FOUND
                ));
            }

            ReviewReaction reviewReaction = reviewReactionService.getReviewReactionById(reviewReactionRequest.getReviewReactionId());
            if (reviewReaction != null && reviewReaction.isReactionType() != reviewReactionRequest.isReactionType() && Objects.equals(reviewReaction.getUser().getId(), user.getId())) {
                reviewReaction.setReactionType(reviewReactionRequest.isReactionType());
                reviewReaction.setModifiedAt(CURRENT_TIME);

                reviewReactionService.createReviewReaction(reviewReaction);

                return ResponseEntity.status(HttpStatus.OK).body(new RequestResponse(
                        HttpStatus.OK.value(),
                        UPDATE_REVIEW_REACTION_SUCCESS
                ));
            } else if (reviewReaction != null && reviewReaction.isReactionType() == reviewReactionRequest.isReactionType() && Objects.equals(reviewReaction.getUser().getId(), user.getId())){
                return ResponseEntity.status(HttpStatus.OK).body(new RequestResponse(
                        HttpStatus.OK.value(),
                        UPDATE_REVIEW_REACTION_FAIL
                ));
            } else {
                ReviewReaction newReviewReaction = new ReviewReaction();
                newReviewReaction.setReview(review);
                newReviewReaction.setUser(user);
                newReviewReaction.setReactionType(reviewReactionRequest.isReactionType());
                newReviewReaction.setDeleteFlag(DEFAULT_DELETE_FLAG);
                newReviewReaction.setCreatedAt(CURRENT_TIME);
                newReviewReaction.setModifiedAt(CURRENT_TIME);

                reviewReactionService.createReviewReaction(newReviewReaction);

                return ResponseEntity.status(HttpStatus.CREATED).body(new RequestResponse(
                        HttpStatus.CREATED.value(),
                        CREATE_REVIEW_REACTION_SUCCESS
                ));
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
