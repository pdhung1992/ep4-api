package jsb.ep4api.specifications;

import jsb.ep4api.entities.Review;
import org.springframework.data.jpa.domain.Specification;

public class ReviewSpecifications {
    public static Specification<Review> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
    public static Specification<Review> hasMovieId(Long movieId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("movie").get("id"), movieId);
    }

    public static Specification<Review> hasUserId(Long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Review> hasNoParentId() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("parentId"));
    }

    public static Specification<Review> hasParentId(Long parentId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("parentId"), parentId);
    }

    public static Specification<Review> hasRating(int rating) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("rating"), rating);
    }

    public static Specification<Review> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Review> hasContent(String content) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("content"), "%" + content + "%");
    }

    public static Specification<Review> hasNoDeletedFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("deleteFlag"));
    }

}
