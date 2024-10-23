package jsb.ep4api.specifications;

import jsb.ep4api.entities.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<User> hasUserId(Long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), userId);
    }

    public static Specification<User> hasNoDeletedFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("deleteFlag"));
    }
}
