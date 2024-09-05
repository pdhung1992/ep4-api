package jsb.ep4api.specifications;

import jsb.ep4api.entities.Genre;
import org.springframework.data.jpa.domain.Specification;

public class GenreSpecifications {
    public static Specification<Genre> hasNoDeleteFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), false);
    }
}
