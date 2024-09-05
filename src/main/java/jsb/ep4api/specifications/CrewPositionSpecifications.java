package jsb.ep4api.specifications;

import jsb.ep4api.entities.CrewPosition;
import org.springframework.data.jpa.domain.Specification;

public class CrewPositionSpecifications {
    public static Specification<CrewPosition> hasNoDeleteFlag() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteFlag"), false);
    }
}
