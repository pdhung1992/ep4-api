package jsb.ep4api.specifications;

import jsb.ep4api.entities.Country;
import org.springframework.data.jpa.domain.Specification;

public class CountrySpecifications {
    public static Specification<Country> hasNoDeleteFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), false);
    }

    public static Specification<Country> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Country> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
}
