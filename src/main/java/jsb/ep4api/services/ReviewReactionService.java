package jsb.ep4api.services;

import jsb.ep4api.entities.ReviewReaction;
import jsb.ep4api.repositories.ReviewReactionRepository;
import jsb.ep4api.specifications.ReviewReactionSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ReviewReactionService {
    @Autowired
    private ReviewReactionRepository reviewReactionRepository;

    public Long countReviewLikesByReviewId(Long reviewId){
        Specification<ReviewReaction> spec = Specification.where(null);
        spec = spec.and(ReviewReactionSpecifications.hasReviewId(reviewId));
        spec = spec.and(ReviewReactionSpecifications.hasLikeReaction());
        spec = spec.and(ReviewReactionSpecifications.hasNoDeletedFlag());

        return reviewReactionRepository.count(spec);
    }

    public Long countReviewDislikesByReviewId(Long reviewId){
        Specification<ReviewReaction> spec = Specification.where(null);
        spec = spec.and(ReviewReactionSpecifications.hasReviewId(reviewId));
        spec = spec.and(ReviewReactionSpecifications.hasDislikeReaction());
        spec = spec.and(ReviewReactionSpecifications.hasNoDeletedFlag());

        return reviewReactionRepository.count(spec);
    }

    public ReviewReaction getReviewReactionById(Long reviewReactionId){
        Specification<ReviewReaction> spec = Specification.where(null);
        spec = spec.and(ReviewReactionSpecifications.hasId(reviewReactionId));
        spec = spec.and(ReviewReactionSpecifications.hasNoDeletedFlag());

        return reviewReactionRepository.findOne(spec).orElse(null);
    }

    public void createReviewReaction(ReviewReaction reviewReaction){
        reviewReactionRepository.save(reviewReaction);
    }


}
