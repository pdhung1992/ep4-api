package jsb.ep4api.specifications;

import jsb.ep4api.entities.CrewMember;
import org.springframework.data.jpa.domain.Specification;

public class CrewMemberSpecifications {
    public static Specification<CrewMember> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<CrewMember> hasMovieId(Long movieId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("movie").get("id"), movieId);
    }

    public static Specification<CrewMember> hasNoDeleteFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), false);
    }
}
