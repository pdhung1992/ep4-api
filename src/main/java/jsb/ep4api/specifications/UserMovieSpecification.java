package jsb.ep4api.specifications;

import jsb.ep4api.entities.UserMovie;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class UserMovieSpecification {
    public static Specification<UserMovie> hasUserId(Long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<UserMovie> hasMovieId(Long movieId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("movie").get("id"), movieId);
    }

    public static Specification<UserMovie> hasNoDeletedFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("deleteFlag"));
    }

    public static Specification<UserMovie> hasNotExpired() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("expiredAt"), LocalDateTime.now());
    }
}
