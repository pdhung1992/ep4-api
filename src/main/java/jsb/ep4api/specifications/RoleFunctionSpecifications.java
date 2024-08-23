package jsb.ep4api.specifications;

import jsb.ep4api.entities.RoleFunction;
import org.springframework.data.jpa.domain.Specification;

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
}
