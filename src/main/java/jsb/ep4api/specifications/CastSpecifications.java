package jsb.ep4api.specifications;

import jsb.ep4api.entities.Cast;
import org.springframework.data.jpa.domain.Specification;

public class CastSpecifications {
    public static Specification<Cast> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Cast> hasNameInclude(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Cast> hasNoDeleteFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), false);
    }
}
