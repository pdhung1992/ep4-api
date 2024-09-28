package jsb.ep4api.specifications;

import jsb.ep4api.entities.Studio;
import org.springframework.data.jpa.domain.Specification;

public class StudioSpecifications {
    public static Specification<Studio> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Studio> hasNameInclude(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Studio> hasCountryId(Long countryId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("country").get("id"), countryId);
    }

    public static Specification<Studio> hasEstablishedYear(Integer establishedYear) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("establishedYear"), establishedYear);
    }

    public static Specification<Studio> hasNoDeletedFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("deleteFlag"));
    }
}
