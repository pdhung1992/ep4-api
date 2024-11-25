package jsb.ep4api.specifications;

import jsb.ep4api.entities.Role;
import org.springframework.data.jpa.domain.Specification;
import static jsb.ep4api.constants.Constants.*;

public class RoleSpecifications {

    public static Specification<Role> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            String nameInclude = "%" + name + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), nameInclude);
        };
    }

    public static Specification<Role> hasNameInclude(String name) {
        return (root, query, criteriaBuilder) -> {
            String nameInclude = "%" + name + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), nameInclude);
        };
    }

    public static Specification<Role> hasSlug(String slug) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("slug")), slug);
        };
    }

    public static Specification<Role> hasSlugInclude(String slug) {
        return (root, query, criteriaBuilder) -> {
            String slugInclude = "%" + slug + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("slug")), slugInclude);
        };
    }

    public static Specification<Role> hasNotDeleteFlag() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("deleteFlag"), DEFAULT_DELETE_FLAG);
        };
    }

}
