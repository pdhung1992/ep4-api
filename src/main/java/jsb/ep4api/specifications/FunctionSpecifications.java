package jsb.ep4api.specifications;

import jsb.ep4api.entities.Function;
import org.springframework.data.jpa.domain.Specification;

import static jsb.ep4api.constants.Constants.*;

public class FunctionSpecifications {
    public static Specification<Function> hasNoDeleteFlag() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("deleteFlag"), DEFAULT_DELETE_FLAG);
        };
    }

    public static Specification<Function> hasShowOnMenu() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("isShow"), DEFAULT_SHOW_FLAG);
        };
    }

    public static Specification<Function> hasParentId(Long parentId) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("parentId"), parentId);
        };
    }

    public static Specification<Function> hasNoParentId() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.isNull(root.get("parentId"));
        };
    }

    public static Specification<Function> hasSortOrderAsc() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.asc(root.get("sortOrder")));
            return criteriaBuilder.conjunction();
        };
    }

}
