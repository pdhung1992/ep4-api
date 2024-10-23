package jsb.ep4api.specifications;

import jsb.ep4api.entities.UserPackage;
import org.springframework.data.jpa.domain.Specification;

public class UserPackageSpecifications {
    public static Specification<UserPackage> hasUserId(Long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<UserPackage> hasPackageId(Long packageId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("aPackage").get("id"), packageId);
    }

    public static Specification<UserPackage> hasNotExpired() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("expiredAt"), java.time.LocalDateTime.now());
    }

    public static Specification<UserPackage> hasNoDeletedFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("deleteFlag"));
    }
}
