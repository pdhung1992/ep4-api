package jsb.ep4api.specifications;

import jsb.ep4api.entities.RoleFunction;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static jsb.ep4api.constants.Constants.*;

public class RoleFunctionSpecifications {
    public static Specification<RoleFunction> hasNoDeleteFlag() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("deleteFlag"), DEFAULT_DELETE_FLAG);
        };
    }

    public static Specification<RoleFunction> hasRoleId(Long roleId) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("role").get("id"), roleId);
        };
    }

    public static Specification<RoleFunction> hasUserId(Long userId) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("role").get("user").get("id"), userId);
        };
    }

    public static Specification<RoleFunction> hasFunctionId(Long functionId) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("function").get("id"), functionId);
        };
    }
}
