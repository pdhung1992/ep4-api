package jsb.ep4api.specifications;

import jsb.ep4api.entities.Classification;
import org.springframework.data.jpa.domain.Specification;

public class ClassificationSpecifications {
    public static Specification<Classification> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }

    public static Specification<Classification> hasNoDeleteFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), false);
    }
}
