package jsb.ep4api.specifications;

import jsb.ep4api.entities.Admin;
import org.springframework.data.jpa.domain.Specification;

import static jsb.ep4api.constrants.Constants.*;

public class AdminSpecifications {
    public static Specification<Admin> hasUsername(String username) {
        return (root, query, criteriaBuilder) -> {
            String usernameInclude = "%" + username + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), usernameInclude);
        };
    }

    public static Specification<Admin> hasFullName(String fullName) {
        return (root, query, criteriaBuilder) -> {
            String fullNameInclude = "%" + fullName + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), fullNameInclude);
        };
    }

    public static Specification<Admin> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            String emailInclude = "%" + email + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), emailInclude);
        };
    }

    public static Specification<Admin> hasRoleId(Long roleId) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("role").get("id"), roleId);
        };
    }

    public static Specification<Admin> hasNotDeleteFlag() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("deleteFlag"), DEFAULT_DELETE_FLAG);
        };
    }

}
