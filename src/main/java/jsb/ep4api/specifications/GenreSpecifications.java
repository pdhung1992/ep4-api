package jsb.ep4api.specifications;

import jsb.ep4api.entities.Genre;
import org.springframework.data.jpa.domain.Specification;

public class GenreSpecifications {
    public static Specification<Genre> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Genre> hasSlug(String slug) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("slug"), slug);
    }

    public static Specification<Genre> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }
    public static Specification<Genre> hasNoDeleteFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), false);
    }
}
