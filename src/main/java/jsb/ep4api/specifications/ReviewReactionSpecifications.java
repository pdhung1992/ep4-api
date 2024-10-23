package jsb.ep4api.specifications;

import jsb.ep4api.entities.ReviewReaction;
import org.springframework.data.jpa.domain.Specification;

import static jsb.ep4api.constants.Constants.*;

public class ReviewReactionSpecifications {
    public static Specification<ReviewReaction> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
    public static Specification<ReviewReaction> hasReviewId(Long reviewId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("review").get("id"), reviewId);
    }

    public static Specification<ReviewReaction> hasLikeReaction() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("reactionType"), REACTION_TYPE_LIKE);
    }

    public static Specification<ReviewReaction> hasDislikeReaction() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("reactionType"), REACTION_TYPE_DISLIKE);
    }

    public static Specification<ReviewReaction> hasNoDeletedFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), DEFAULT_DELETE_FLAG);
    }
}
