package jsb.ep4api.specifications;

import jsb.ep4api.entities.Rating;
import org.springframework.data.jpa.domain.Specification;

public class RatingSpecifications {
    public static Specification<Rating> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Rating> hasValue(Integer value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("value"), value);
    }

    public static Specification<Rating> hasUserId(Long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Rating> hasMovieId(Long movieId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("movie").get("id"), movieId);
    }

    public static Specification<Rating> hasNoDeleteFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), false);
    }
}
