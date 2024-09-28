package jsb.ep4api.specifications;

import jsb.ep4api.entities.Language;
import org.springframework.data.jpa.domain.Specification;

public class LanguageSpecifications {
    public static Specification<Language> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Language> hasNoDeleteFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), false);
    }
}
