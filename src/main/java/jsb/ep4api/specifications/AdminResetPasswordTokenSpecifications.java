package jsb.ep4api.specifications;

import jsb.ep4api.entities.AdminResetPasswordToken;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class AdminResetPasswordTokenSpecifications {
    public static Specification<AdminResetPasswordToken> hasToken(String token) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("token"), token);
    }

    public static Specification<AdminResetPasswordToken> hasAdminId(Long adminId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("adminId"), adminId);
    }

    public static Specification<AdminResetPasswordToken> hasNotExpired() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("expiredAt"), LocalDateTime.now());
    }

    public static Specification<AdminResetPasswordToken> hasNotUsed() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isUsed"), false);
    }

    public static Specification<AdminResetPasswordToken> hasNoDeleteFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), false);
    }
}
