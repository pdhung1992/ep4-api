package jsb.ep4api.specifications;

import jsb.ep4api.entities.UserResetPasswordToken;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class UserResetPasswordTokenSpecifications {
    public static Specification<UserResetPasswordToken> hasToken(String token) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("token"), token);
    }

    public static Specification<UserResetPasswordToken> hasUserId(Long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"), userId);
    }

    public static Specification<UserResetPasswordToken> hasNotExpired() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("expiredAt"), LocalDateTime.now());
    }

    public static Specification<UserResetPasswordToken> hasNotUsed() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isUsed"), false);
    }

    public static Specification<UserResetPasswordToken> hasNoDeleteFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), false);
    }
}
