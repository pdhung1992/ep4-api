package jsb.ep4api.specifications;

import jsb.ep4api.entities.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<User> hasUserId(Long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), userId);
    }

    public static Specification<User> hasFullNameInclude(String fullName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("fullName"), "%" + fullName + "%");
    }

    public static Specification<User> hasPhoneInclude(String phone) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("phone"), "%" + phone + "%");
    }

    public static Specification<User> hasEmailInclude(String email) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), "%" + email + "%");
    }

    public static Specification<User> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("isActive"));
    }

    public static Specification<User> hasNoDeletedFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("deleteFlag"));
    }
}
